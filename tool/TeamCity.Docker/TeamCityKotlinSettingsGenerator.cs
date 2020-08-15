using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using IoC;
using TeamCity.Docker.Generic;
using TeamCity.Docker.Model;
// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    internal class TeamCityKotlinSettingsGenerator : IGenerator
    {
        private const string MinDockerVersion = "18.05.0";
        private const string BuildNumberParam = "dockerImage.teamcity.buildNumber";
        private string BuildNumberPattern = $"buildNumberPattern=\"%{BuildNumberParam}%-%build.counter%\"";
        private const string RemoveManifestsScript = "\"\"if exist \"%%USERPROFILE%%\\.docker\\manifests\\\" rmdir \"%%USERPROFILE%%\\.docker\\manifests\\\" /s /q\"\"";
        [NotNull] private readonly string BuildRepositoryName = "%docker.buildRepository%";
        [NotNull] private readonly string DeployRepositoryName = "%docker.deployRepository%";
        [NotNull] private readonly IGenerateOptions _options;
        [NotNull] private readonly IFactory<IEnumerable<IGraph<IArtifact, Dependency>>, IGraph<IArtifact, Dependency>> _buildGraphsFactory;
        [NotNull] private readonly IPathService _pathService;
        [NotNull] private readonly IBuildPathProvider _buildPathProvider;
        [NotNull] private readonly IFactory<NodesDescription, IEnumerable<INode<IArtifact>>> _nodesDescriptionsFactory;

        public TeamCityKotlinSettingsGenerator(
            [NotNull] IGenerateOptions options,
            [NotNull] IFactory<IEnumerable<IGraph<IArtifact, Dependency>>, IGraph<IArtifact, Dependency>> buildGraphsFactory,
            [NotNull] IPathService pathService,
            [NotNull] IBuildPathProvider buildPathProvider,
            [NotNull] IFactory<NodesDescription, IEnumerable<INode<IArtifact>>> nodesDescriptionFactory)
        {
            _options = options ?? throw new ArgumentNullException(nameof(options));
            _buildGraphsFactory = buildGraphsFactory ?? throw new ArgumentNullException(nameof(buildGraphsFactory));
            _pathService = pathService ?? throw new ArgumentNullException(nameof(pathService));
            _buildPathProvider = buildPathProvider ?? throw new ArgumentNullException(nameof(buildPathProvider));
            _nodesDescriptionsFactory = nodesDescriptionFactory ?? throw new ArgumentNullException(nameof(nodesDescriptionFactory));
        }

        public void Generate([NotNull] IGraph<IArtifact, Dependency> graph)
        {
            if (graph == null) throw new ArgumentNullException(nameof(graph));
            if (string.IsNullOrWhiteSpace(_options.TeamCityDslPath))
            {
                return;
            }

            if (string.IsNullOrWhiteSpace(_options.TeamCityBuildConfigurationId))
            {
                return;
            }

            // ReSharper disable once UseObjectOrCollectionInitializer
            var lines = new List<string>();
            lines.Add("import jetbrains.buildServer.configs.kotlin.v2019_2.*");
            lines.Add("import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*");
            lines.Add("import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script");
            lines.Add("import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot");
            lines.Add("import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport");
            lines.Add("import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace");
            // ReSharper disable once StringLiteralTypo
            lines.Add("import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra");
            lines.Add("import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand");
            lines.Add("version = \"2019.2\"");
            lines.Add(string.Empty);

            var buildGraphResult = _buildGraphsFactory.Create(graph);
            if (buildGraphResult.State == Result.Error)
            {
                return;
            }

            var names = new Dictionary<string, int>();
            var buildGraphs = (
                from buildGraph in buildGraphResult.Value
                let weight = buildGraph.Nodes.Select(i => i.Value.Weight.Value).Sum()
                let nodesDescription = _nodesDescriptionsFactory.Create(buildGraph.Nodes)
                orderby nodesDescription.State != Result.Error ? nodesDescription.Value.Name : string.Empty
                select new { graph = buildGraph, weight })
                .ToList();

            var localBuildTypes = new List<string>();
            var hubBuildTypes = new List<string>();
            var allImages = buildGraphs
                .SelectMany(i => i.graph.Nodes.Select(j => j.Value).OfType<Image>())
                .Where(i => i.File.Repositories.Any())
                .ToList();

            // Build and push on local registry
            var buildAndPushLocalBuildTypes = new List<string>();
            foreach (var buildGraph in buildGraphs)
            {
                var path = _buildPathProvider.GetPath(buildGraph.graph).ToList();
                var description = _nodesDescriptionsFactory.Create(path);
                var name = description.State != Result.Error
                    ? description.Value.Name
                    : string.Join("", path.Select(i => i.Value).OfType<Image>().Select(i => i.File.Platform).Distinct().OrderBy(i => i));

                if (names.TryGetValue(name, out var counter))
                {
                    name = $"{name} {++counter}";
                }
                else
                {
                    names[name] = 1;
                }

                var buildTypeId = $"push_local_{NormalizeName(name)}";
                var onPause = !path.Select(i => i.Value).OfType<Image>().Any(i => i.File.Repositories.Any());
                localBuildTypes.Add(buildTypeId);
                if (!onPause)
                {
                    buildAndPushLocalBuildTypes.Add(buildTypeId);
                }

                lines.AddRange(GenerateBuildAndPushType(buildTypeId, name, path, buildGraph.weight, onPause));
            }

            // Publish on local registry
            var localPublishGroups =
                from image in allImages
                from tag in image.File.Tags.Skip(1)
                group image by tag;

            var publishLocalId = "publish_local";
            localBuildTypes.Add(publishLocalId);
            lines.AddRange(CreateManifestBuildConfiguration(publishLocalId, BuildRepositoryName, "Publish", localPublishGroups, true, buildAndPushLocalBuildTypes.ToArray()));

            // Push on docker hub
            var pushOnHubBuildTypes = new List<string>();
            var platforms = allImages.Select(i => i.File.Platform).Distinct();
            foreach (var platform in platforms)
            {
                var buildTypeId = $"push_hub_{NormalizeName(platform)}";
                pushOnHubBuildTypes.Add(buildTypeId);
                lines.AddRange(CreatePushBuildConfiguration(buildTypeId, platform, allImages, publishLocalId));
            }

            hubBuildTypes.AddRange(pushOnHubBuildTypes);

            // Publish on docker hub
            var publishOnHubBuildTypes = new List<string>();
            var publishOnHubGroups =
                from grp in
                    from image in allImages
                    from tag in image.File.Tags.Skip(1)
                    group image by tag
                group grp by grp.Key.ToLowerInvariant() == "latest" ? "latest" : "version";

            foreach (var group in publishOnHubGroups)
            {
                var buildTypeId = $"publish_hub_{NormalizeName(group.Key)}";
                publishOnHubBuildTypes.Add(buildTypeId);
                lines.AddRange(CreateManifestBuildConfiguration(buildTypeId, DeployRepositoryName, $"Publish as {group.Key}", group, false, pushOnHubBuildTypes.ToArray()));
            }

            hubBuildTypes.AddRange(publishOnHubBuildTypes);

            // Local project
            lines.Add("object LocalProject : Project({");
            lines.Add("name = \"Local registry\"");
            foreach (var buildType in localBuildTypes.Distinct())
            {
                lines.Add($"buildType({buildType})");
            }
            lines.Add("})");

            // Hub project
            lines.Add("object HubProject : Project({");
            lines.Add("name = \"Docker hub\"");
            foreach (var buildType in hubBuildTypes.Distinct())
            {
                lines.Add($"buildType({buildType})");
            }
            lines.Add("})");

            // root project
            lines.Add("project {");
            lines.Add("vcsRoot(RemoteTeamcityImages)");
            lines.Add("subProject(LocalProject)");
            lines.Add("subProject(HubProject)");
            lines.Add("params {");
            lines.Add($"param(\"{BuildNumberParam}\", \"%dep.{_options.TeamCityBuildConfigurationId}.build.number%\")");
            lines.Add("}");

            lines.Add("}");
            lines.Add(string.Empty);

            // vcs
            lines.Add("object RemoteTeamcityImages : GitVcsRoot({");
            lines.Add("name = \"remote teamcity images\"");
            lines.Add("url = \"https://github.com/JetBrains/teamcity-docker-images.git\"");
            lines.Add("branch = \"refs/heads/Lakhnau-2020.1.x\"");
            lines.Add("})");
            // vcs

            graph.TryAddNode(new FileArtifact(_pathService.Normalize(Path.Combine(_options.TeamCityDslPath, "settings.kts")), lines), out _);
        }

        private IEnumerable<string> CreatePushBuildConfiguration(string buildTypeId, string platform, IEnumerable<Image> allImages, params string[] buildBuildTypes)
        {
            var images = allImages.Where(i => i.File.Platform == platform).ToList();
            yield return $"object {buildTypeId}: BuildType(";
            yield return "{";
            yield return $"name = \"Push {platform}\"";
            yield return BuildNumberPattern;

            yield return "steps {";
            foreach (var image in images)
            {
                // docker pull
                var tag = image.File.Tags.First();
                var repo = $"{image.File.ImageId}:{tag}";
                var repoTag = $"{BuildRepositoryName}{repo}";
                foreach (var pullCommand in CreatePullCommand(repoTag, repo))
                {
                    yield return pullCommand;
                }

                var newRepo = $"{DeployRepositoryName}{image.File.ImageId}";
                var newRepoTag = $"{newRepo}:{image.File.Tags.First()}";
                foreach (var tagCommand in CreateTagCommand(repoTag, newRepoTag, repo))
                {
                    yield return tagCommand;
                }

                foreach (var pushCommand in CreatePushCommand($"{newRepo}", repo, tag))
                {
                    yield return pushCommand;
                }
            }

            yield return "}";

            yield return "features {";

            var weight = images.Sum(i => i.File.Weight.Value);
            if (weight > 0)
            {
                foreach (var feature in CreateFreeDiskSpaceFeature(weight))
                {
                    yield return feature;
                }
            }

            foreach (var feature in CreateDockerFeature())
            {
                yield return feature;
            }

            // ReSharper disable once StringLiteralTypo
            foreach (var feature in CreateSwabraFeature())
            {
                yield return feature;
            }

            yield return "}";

            foreach (var lines in CreateDockerRequirements(platform))
            {
                yield return lines;
            }

            foreach (var dependencies in CreateSnapshotDependencies(buildBuildTypes, false))
            {
                yield return dependencies;
            }

            yield return "})";
            yield return string.Empty;
        }

        private IEnumerable<string> CreateManifestBuildConfiguration(string buildTypeId, string repositoryName, string name, IEnumerable<IGrouping<string, Image>> images, bool dependsOnContext, params string[] dependencies)
        {
            yield return $"object {buildTypeId}: BuildType(";
            yield return "{";
            yield return $"name = \"{name}\"";
            yield return BuildNumberPattern;
            yield return "enablePersonalBuilds = false";
            yield return "type = BuildTypeSettings.Type.DEPLOYMENT";
            yield return "maxRunningBuilds = 1";

            yield return "steps {";
            foreach (var line in AddScript("remove manifests", RemoveManifestsScript))
            {
                yield return line;
            }

            foreach (var group in images)
            {
                var groupedByImageId = group.GroupBy(i => i.File.ImageId);
                foreach (var groupByImageId in groupedByImageId)
                {
                    foreach (var line in CreateManifestCommands(repositoryName, group.Key, groupByImageId.Key, groupByImageId))
                    {
                        yield return line;
                    }
                }
            }

            yield return "}";

            foreach (var line in CreateSnapshotDependencies(dependencies, dependsOnContext))
            {
                yield return line;
            }

            foreach (var lines in CreateDockerRequirements("windows", MinDockerVersion))
            {
                yield return lines;
            }

            yield return "features {";
            foreach (var line in CreateDockerFeature())
            {
                yield return line;
            }
            yield return "}";

            yield return "})";
            yield return string.Empty;
        }

        private static IEnumerable<string> CreateDockerRequirements(string platform, string minDockerVersion = "")
        {
            yield return "requirements {";
            if (!string.IsNullOrWhiteSpace(minDockerVersion))
            {
                yield return $"noLessThanVer(\"docker.version\", \"{minDockerVersion}\")";
            }

            yield return $"equals(\"docker.server.osType\", \"{platform}\")";
            yield return "}";
        }

        private IEnumerable<string> CreateManifestCommands(string repositoryName, string tag, string imageId, IEnumerable<Image> images)
        {
            var repo = $"{repositoryName}{imageId}";
            var manifestName = $"{repo}:{tag}";
            var createArgs = new List<string>
            {
                "create",
                manifestName
            };

            foreach (var image in images)
            {
                createArgs.Add($"{repo}:{image.File.Tags.First()}");
            }

            foreach (var line in CreateDockerCommand($"manifest create {imageId}:{tag}", "manifest", createArgs))
            {
                yield return line;
            }

            var pushArgs = new List<string>
            {
                "push",
                manifestName
            };

            foreach (var line in CreateDockerCommand($"manifest push {imageId}:{tag}", "manifest", pushArgs))
            {
                yield return line;
            }

            var inspectArgs = new List<string>
            {
                "inspect",
                manifestName,
                "--verbose"
            };

            foreach (var line in CreateDockerCommand($"manifest inspect {imageId}:{tag}", "manifest", inspectArgs))
            {
                yield return line;
            }
        }

        private IEnumerable<string> GenerateBuildAndPushType(
            string buildTypeId,
            string name,
            IReadOnlyCollection<INode<IArtifact>> path,
            int weight,
            bool onPause)
        {
            var images = path.Select(i => i.Value).OfType<Image>().ToList();
            var references = path.Select(i => i.Value).OfType<Reference>().ToList();

            var groups =
                from image in images
                group image by image.File.ImageId
                into groupsByImageId
                from groupByImageId in
                    from image in groupsByImageId
                    group image by image.File
                group groupByImageId by groupsByImageId.Key;

            var description = new StringBuilder();
            foreach (var grp in groups)
            {
                if (description.Length > 0)
                {
                    description.Append(" ");
                }

                description.Append(grp.Key);
                foreach (var dockerfile in grp)
                {
                    var tags = string.Join(",", dockerfile.Key.Tags);
                    if (!string.IsNullOrWhiteSpace(tags))
                    {
                        description.Append(':');
                        description.Append(tags);
                    }
                }
            }

            var pauseStr = onPause ? "ON PAUSE " : "";
            yield return $"object {buildTypeId} : BuildType({{";
            yield return $"name = \"{pauseStr}Build and push {name}\"";
            yield return BuildNumberPattern;
            yield return $"description  = \"{description}\"";

            if (!onPause)
            {
                yield return "vcs {root(RemoteTeamcityImages)}";
                yield return "steps {";

                // docker pull
                foreach (var pullCommand in references.SelectMany(refer => CreatePullCommand(refer.RepoTag, refer.RepoTag)))
                {
                    yield return pullCommand;
                }

                // docker build
                foreach (var buildCommand in images.SelectMany(CreateBuildCommand))
                {
                    yield return buildCommand;
                }

                // docker image tag
                foreach (var image in images)
                {
                    if (image.File.Tags.Any())
                    {
                        var tag = image.File.Tags.First();
                        foreach (var tagCommand in CreateTagCommand($"{image.File.ImageId}:{tag}", $"{BuildRepositoryName}{image.File.ImageId}:{tag}", $"{image.File.ImageId}:{tag}"))
                        {
                            yield return tagCommand;
                        }
                    }
                }

                // docker push
                foreach (var image in images)
                {
                    var tag = image.File.Tags.First();
                    foreach (var pushCommand in CreatePushCommand($"{BuildRepositoryName}{image.File.ImageId}", $"{image.File.ImageId}:{tag}", tag))
                    {
                        yield return pushCommand;
                    }
                }

                yield return "}";

                yield return "features {";

                if (weight > 0)
                {
                    foreach (var feature in CreateFreeDiskSpaceFeature(weight))
                    {
                        yield return feature;
                    }
                }

                foreach (var feature in CreateDockerFeature())
                {
                    yield return feature;
                }

                // ReSharper disable once StringLiteralTypo
                foreach (var feature in CreateSwabraFeature())
                {
                    yield return feature;
                }

                yield return "}";

                foreach (var dependencies in CreateArtifactsDependencies())
                {
                    yield return dependencies;
                }
            }

            yield return "})";
            yield return string.Empty;
        }

        private IEnumerable<string> CreateSnapshotDependencies(IEnumerable<string> dependencies, bool dependsOnContext)
        {
            yield return "dependencies {";
            if (dependsOnContext)
            {
                yield return $"snapshot(AbsoluteId(\"{_options.TeamCityBuildConfigurationId}\"))";
                yield return "{\nonDependencyFailure = FailureAction.IGNORE\n}";
            }

            foreach (var buildTypeId in dependencies)
            {
                yield return $"snapshot({buildTypeId})";
                yield return "{\nonDependencyFailure =  FailureAction.FAIL_TO_START\n}";
            }

            yield return "}";
        }

        private IEnumerable<string> CreateArtifactsDependencies()
        {
            if(string.IsNullOrWhiteSpace(_options.TeamCityBuildConfigurationId))
            {
                yield break;
            }

            yield return "dependencies {";
            yield return $"dependency(AbsoluteId(\"{_options.TeamCityBuildConfigurationId}\")) {{";
            yield return "snapshot { onDependencyFailure = FailureAction.IGNORE }";
            yield return "artifacts {";
            yield return $"artifactRules = \"TeamCity-*.tar.gz!/**=>{_pathService.Normalize(_options.ContextPath)}\"";
            yield return "}";
            yield return "}";
            yield return "}";
        }

        // ReSharper disable once IdentifierTypo
        private static IEnumerable<string> CreateSwabraFeature()
        {
            // ReSharper disable once StringLiteralTypo
            yield return "swabra {";
            yield return "forceCleanCheckout = true";
            yield return "}";
        }

        private IEnumerable<string> CreateDockerFeature()
        {
            if (string.IsNullOrWhiteSpace(_options.TeamCityDockerRegistryId))
            {
                yield break;
            }

            yield return "dockerSupport {";
            yield return "cleanupPushedImages = true";
            yield return "loginToRegistry = on {";
            yield return $"dockerRegistryId = \"{_options.TeamCityDockerRegistryId}\"";
            yield return "}";
            yield return "}";
        }

        private static IEnumerable<string> CreateFreeDiskSpaceFeature(int weight)
        {
            yield return "freeDiskSpace {";
            yield return $"requiredSpace = \"{weight}gb\"";
            yield return "failBuild = true";
            yield return "}";
        }

        private IEnumerable<string> CreatePushCommand(string imageId, string name, params string[] tags)
        {
            yield return "dockerCommand {";
            yield return $"name = \"push {name}\"";
            yield return "commandType = push {";

            yield return "namesAndTags = \"\"\"";
            foreach (var tag in tags)
            {
                yield return $"{imageId}:{tag}";
            }

            yield return "\"\"\".trimIndent()";

            yield return "}";
            yield return "}";

            yield return string.Empty;
        }

        private IEnumerable<string> CreateTagCommand(string repoTag, string newRepoTag, string name)
        {
            yield return "dockerCommand {";
            yield return $"name = \"tag {name}\"";
            yield return "commandType = other {";

            yield return "subCommand = \"tag\"";
            yield return $"commandArgs = \"{repoTag} {newRepoTag}\"";

            yield return "}";
            yield return "}";

            yield return string.Empty;
        }

        private IEnumerable<string> CreateBuildCommand(Image image)
        {
            var tag = image.File.Tags.First();
            yield return "dockerCommand {";
            yield return $"name = \"build {image.File.ImageId}:{tag}\"";
            yield return "commandType = build {";

            yield return "source = file {";
            yield return $"path = \"\"\"{_pathService.Normalize(Path.Combine(_options.TargetPath, image.File.Path, "Dockerfile"))}\"\"\"";
            yield return "}";

            yield return $"contextDir = \"{_pathService.Normalize(_options.ContextPath)}\"";

            yield return "namesAndTags = \"\"\"";
            yield return $"{image.File.ImageId}:{tag}";

            yield return "\"\"\".trimIndent()";

            yield return "}";
            yield return $"param(\"dockerImage.platform\", \"{image.File.Platform}\")";
            yield return "}";

            yield return string.Empty;
        }

        private static IEnumerable<string> CreatePullCommand(string repoTag, string name)
        {
            yield return "dockerCommand {";
            yield return $"name = \"pull {name}\"";
            yield return "commandType = other {";

            yield return "subCommand = \"pull\"";
            yield return $"commandArgs = \"{repoTag}\"";

            yield return "}";
            yield return "}";

            yield return string.Empty;
        }

        private IEnumerable<string> CreateDockerCommand(string name, string command, IEnumerable<string> args)
        {
            yield return "dockerCommand {";
            yield return $"name = \"{name}\"";
            yield return "commandType = other {";
            yield return $"subCommand = \"{command}\"";
            yield return $"commandArgs = \"{string.Join(" ", args)}\"";
            yield return "}";
            yield return "}";
        }

        private static string NormalizeName(string name) =>
            name
                .Replace(' ', '_')
                .Replace('-', '_')
                .Replace("%", "")
                .Replace(".", "_");

        private static IEnumerable<string> AddScript(string name, string script)
        {
            yield return "script {";
            yield return $"name = \"{name}\"";
            yield return $"scriptContent = \"{script}\"";
            yield return "}";
        }

        // ReSharper disable once UnusedMember.Local
        private IEnumerable<string> CreateComposingBuildConfiguration(string buildTypeId, string name, params string[] buildBuildTypes)
        {
            yield return $"object {buildTypeId}: BuildType(";
            yield return "{";
            yield return $"name = \"{name}\"";
            yield return BuildNumberPattern;

            yield return "steps {";
            yield return "}";

            foreach (var line in CreateSnapshotDependencies(buildBuildTypes, false))
            {
                yield return line;
            }

            yield return "})";
            yield return string.Empty;
        }
    }
}
