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
        [NotNull] private readonly string BuildRepositoryName = "%docker.buildRepository%";
        [NotNull] private readonly string DeployRepositoryName = "%docker.deployRepository%";
        [NotNull] private readonly IGenerateOptions _options;
        [NotNull] private readonly IFactory<IEnumerable<IGraph<IArtifact, Dependency>>, IGraph<IArtifact, Dependency>> _buildGraphsFactory;
        [NotNull] private readonly IPathService _pathService;
        [NotNull] private readonly IBuildPathProvider _buildPathProvider;
        [NotNull] private readonly IFactory<string, IGraph<IArtifact, Dependency>> _graphNameFactory;

        public TeamCityKotlinSettingsGenerator(
            [NotNull] IGenerateOptions options,
            [NotNull] IFactory<IEnumerable<IGraph<IArtifact, Dependency>>, IGraph<IArtifact, Dependency>> buildGraphsFactory,
            [NotNull] IPathService pathService,
            [NotNull] IBuildPathProvider buildPathProvider,
            [NotNull] IFactory<string, IGraph<IArtifact, Dependency>> graphNameFactory)
        {
            _options = options ?? throw new ArgumentNullException(nameof(options));
            _buildGraphsFactory = buildGraphsFactory ?? throw new ArgumentNullException(nameof(buildGraphsFactory));
            _pathService = pathService ?? throw new ArgumentNullException(nameof(pathService));
            _buildPathProvider = buildPathProvider ?? throw new ArgumentNullException(nameof(buildPathProvider));
            _graphNameFactory = graphNameFactory ?? throw new ArgumentNullException(nameof(graphNameFactory));
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

            var buildId = NormalizeName(_options.TeamCityBuildConfigurationId);

            // ReSharper disable once UseObjectOrCollectionInitializer
            var lines = new List<string>();
            lines.Add("import jetbrains.buildServer.configs.kotlin.v2019_2.*");
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

            var counter = 0;
            var names = new HashSet<string>();
            var buildGraphs = (
                from buildGraph in buildGraphResult.Value
                let hasRepoToPush = buildGraph.Nodes.Select(i => i.Value).OfType<Image>().Any(i => i.File.Repositories.Any())
                where hasRepoToPush
                let name = _graphNameFactory.Create(buildGraph).Value
                let weight = buildGraph.Nodes.Select(i => i.Value.Weight.Value).Sum()
                orderby name
                select new { graph = buildGraph, name, weight })
                .ToList();

            var buildTypes = new List<string>();

            var buildBuildTypes = new List<string>();
            foreach (var buildGraph in buildGraphs)
            {
                // build build config
                var name = buildGraph.name;
                if (string.IsNullOrWhiteSpace(name))
                {
                    name = "Build";
                }
                
                if (!names.Add(name))
                {
                    name = $"{name} {++counter}";
                }

                var buildTypeId = $"{buildId}_{NormalizeName(name)}";
                buildBuildTypes.Add(buildTypeId);
                lines.AddRange(GenerateBuildType(buildTypeId, name, _options.TagPrefixes.ToList(), buildGraph.graph, buildGraph.weight));
                // build build config
            }

            buildTypes.AddRange(buildBuildTypes);

            var allImages = buildGraphs
                .SelectMany(i => i.graph.Nodes.Select(j => j.Value).OfType<Image>())
                .ToList();

            // build all build config
            var buildAllBuildTypeId = $"{buildId}_build_all";
            buildTypes.Add(buildAllBuildTypeId);
            lines.AddRange(CreateComposingBuildConfiguration(buildAllBuildTypeId, "Build", buildBuildTypes.ToArray()));
            // build all build config

            var manifestBuildTypes = new List<string>();
            foreach (var tagPrefix in _options.TagPrefixes)
            {
                // manifests build config
                var buildTypeId = $"{buildId}_{NormalizeName(tagPrefix)}_manifest";
                manifestBuildTypes.Add(buildTypeId);
                lines.AddRange(CreateManifestBuildConfiguration(buildTypeId, BuildRepositoryName, $"Manifest on build {tagPrefix}", tagPrefix, allImages, buildAllBuildTypeId));
                // manifests build config
            }

            buildTypes.AddRange(manifestBuildTypes);

            // manifest build config
            var manifestAllBuildTypeId = $"{buildId}_manifest_all";
            buildTypes.Add(manifestAllBuildTypeId);
            lines.AddRange(CreateComposingBuildConfiguration(manifestAllBuildTypeId, "Manifest on build", new List<string>(manifestBuildTypes) { buildAllBuildTypeId}.ToArray()));
            // manifest build config

            var deployBuildTypes = new List<string>();
            var platforms = allImages.Select(i => i.File.Platform).Distinct();
            foreach (var platform in platforms)
            {
                // deploy build config
                var buildTypeId = $"{buildId}_{NormalizeName(platform)}_deploy";
                deployBuildTypes.Add(buildTypeId);
                lines.AddRange(CreateDeployBuildConfiguration(buildTypeId, platform, allImages, buildAllBuildTypeId));
                // deploy build config
            }

            buildTypes.AddRange(deployBuildTypes);

            // deploy build config
            var deployAllBuildTypeId = $"{buildId}_deploy_all";
            buildTypes.Add(deployAllBuildTypeId);
            lines.AddRange(CreateComposingBuildConfiguration(deployAllBuildTypeId, "Deploy", new List<string>(deployBuildTypes) { buildAllBuildTypeId }.ToArray()));
            // deploy build config

            var manifestOnHubBuildTypes = new List<string>();
            foreach (var tagPrefix in _options.TagPrefixes)
            {
                // manifests on hub build config
                var buildTypeId = $"{buildId}_{NormalizeName(tagPrefix)}_manifest_hub";
                manifestOnHubBuildTypes.Add(buildTypeId);
                lines.AddRange(CreateManifestBuildConfiguration(buildTypeId, DeployRepositoryName, $"Manifest on deploy {tagPrefix}", tagPrefix, allImages, deployAllBuildTypeId));
                // manifests build config
            }

            buildTypes.AddRange(manifestOnHubBuildTypes);

            // manifest build config
            var manifestHubAllBuildTypeId = $"{buildId}_manifest_hub_all";
            buildTypes.Add(manifestHubAllBuildTypeId);
            lines.AddRange(CreateComposingBuildConfiguration(manifestHubAllBuildTypeId, "Manifest on deploy", new List<string>(manifestOnHubBuildTypes) {deployAllBuildTypeId}.ToArray()));
            // manifest build config

            // project
            lines.Add("project {");
            lines.Add("vcsRoot(RemoteTeamcityImages)");
            foreach (var buildType in buildTypes.Distinct())
            {
                lines.Add($"buildType({buildType})");
            }
            lines.Add("}");
            // project

            lines.Add(string.Empty);

            // vcs
            lines.Add("object RemoteTeamcityImages : GitVcsRoot({");
            lines.Add("name = \"remote teamcity images\"");
            lines.Add("url = \"https://github.com/JetBrains/teamcity-docker-images.git\"");
            lines.Add("})");
            // vcs

            graph.TryAddNode(new FileArtifact(_pathService.Normalize(Path.Combine(_options.TeamCityDslPath, "settings.kts")), lines), out _);
        }

        private IEnumerable<string> CreateDeployBuildConfiguration(string buildTypeId, string platform, IEnumerable<Image> allImages, params string[] buildBuildTypes)
        {
            var images = allImages.Where(i => i.File.Platform == platform).ToList();
            yield return $"object {buildTypeId}: BuildType(";
            yield return "{";
            yield return $"name = \"Deploy {platform}\"";

            yield return "steps {";
            foreach (var image in images)
            {
                // docker pull
                var repoTag = $"{BuildRepositoryName}{image.File.ImageId}:{image.File.Tags.FirstOrDefault() ?? "latest"}";
                foreach (var pullCommand in CreatePullCommand(repoTag, image.File.ImageId))
                {
                    yield return pullCommand;
                }

                foreach (var repository in image.File.Repositories)
                {
                    var newRepo = $"{DeployRepositoryName}{image.File.ImageId}";
                    foreach (var tag in image.File.Tags)
                    {
                        var newRepoTag = $"{newRepo}:{tag}";
                        foreach (var tagCommand in CreateTagCommand(repoTag, newRepoTag, newRepoTag))
                        {
                            yield return tagCommand;
                        }
                    }

                    foreach (var pushCommand in CreatePushCommand($"{newRepo}", newRepo, image.File.Tags.ToArray()))
                    {
                        yield return pushCommand;
                    }
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

            foreach (var dependencies in CreateSnapshotDependencies(buildBuildTypes))
            {
                yield return dependencies;
            }

            yield return "})";
            yield return string.Empty;
        }

        private IEnumerable<string> CreateManifestBuildConfiguration(string buildTypeId, string repositoryName, string name, string tagPrefix, IEnumerable<Image> allImages, string buildAllTypeId)
        {
            var groupedByImageId = allImages
                .Where(i => i.File.HasManifest)
                .GroupBy(i => i.File.ImageId);

            yield return $"object {buildTypeId}: BuildType(";
            yield return "{";
            yield return $"name = \"{name}\"";

            yield return "steps {";
            foreach (var groupByImageId in groupedByImageId)
            {
                foreach (var line in CreateManifestCommands(repositoryName, tagPrefix, groupByImageId.Key, groupByImageId))
                {
                    yield return line;
                }
            }

            yield return "}";

            foreach (var line in CreateSnapshotDependencies(Enumerable.Repeat(buildAllTypeId, 1)))
            {
                yield return line;
            }

            foreach (var lines in CreateDockerRequirements())
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

        private IEnumerable<string> CreateComposingBuildConfiguration(string buildTypeId, string name, params string[] buildBuildTypes)
        {
            yield return $"object {buildTypeId}: BuildType(";
            yield return "{";
            yield return $"name = \"{name}\"";

            yield return "steps {";
            yield return "}";

            foreach (var line in CreateSnapshotDependencies(buildBuildTypes))
            {
                yield return line;
            }

            yield return "})";
            yield return string.Empty;
        }

        private static IEnumerable<string> CreateDockerRequirements()
        {
            yield return "requirements {";
            yield return $"noLessThanVer(\"docker.version\", \"{MinDockerVersion}\")";
            yield return "equals(\"docker.server.osType\", \"windows\")";
            yield return "}";
        }

        private IEnumerable<string> CreateManifestCommands(string repositoryName, string tagPrefix, string imageId, IEnumerable<Image> images)
        {
            var manifestName = $"{repositoryName}{imageId}:{tagPrefix}";
            var createArgs = new List<string>
            {
                "create",
                "-a",
                manifestName
            };

            foreach (var image in images)
            {
                var tag = image.File.Tags.FirstOrDefault() ?? "latest";
                createArgs.Add($"{manifestName}-{tag}");
            }

            foreach (var line in CreateDockerCommand($"manifest create {imageId}", "manifest", createArgs))
            {
                yield return line;
            }

            var pushArgs = new List<string>
            {
                "push",
                manifestName
            };

            foreach (var line in CreateDockerCommand($"manifest push {imageId}", "manifest", pushArgs))
            {
                yield return line;
            }

            var inspectArgs = new List<string>
            {
                "inspect",
                manifestName,
                "--verbose"
            };

            foreach (var line in CreateDockerCommand($"manifest inspect {imageId}", "manifest", inspectArgs))
            {
                yield return line;
            }
        }

        private IEnumerable<string> GenerateBuildType(string buildTypeId, string name, IReadOnlyCollection<string> tagPrefixes, IGraph<IArtifact, Dependency> buildGraph, int weight)
        {
            var path = _buildPathProvider.GetPath(buildGraph).ToList();
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

            yield return $"object {buildTypeId} : BuildType({{";
            yield return $"name = \"Build {name}\"";
            yield return $"description  = \"{description}\"";
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
            foreach (var tagPrefix in tagPrefixes)
            {
                foreach (var image in images)
                {
                    if (image.File.Tags.Any())
                    {
                        foreach (var tag in image.File.Tags)
                        {
                            foreach (var tagCommand in CreateTagCommand($"{image.File.ImageId}:{tag}", $"{BuildRepositoryName}{image.File.ImageId}:{tagPrefix}-{tag}", $"{image.File.ImageId}:{tagPrefix}-{tag}"))
                            {
                                yield return tagCommand;
                            }
                        }
                    }
                }
            }

            // docker push
            foreach (var image in images)
            {
                var tags = (
                    from tag in image.File.Tags
                    from tagPrefix in tagPrefixes
                    select $"{tagPrefix}-{tag}")
                    .ToArray();

                foreach (var pushCommand in CreatePushCommand($"{BuildRepositoryName}{image.File.ImageId}", image.File.ImageId, tags))
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

            yield return "})";
            yield return string.Empty;
        }

        private IEnumerable<string> CreateSnapshotDependencies(IEnumerable<string> dependencies)
        {
            yield return "dependencies {";
            yield return $"snapshot(AbsoluteId(\"{_options.TeamCityBuildConfigurationId}\"))";
            yield return "{\nonDependencyFailure = FailureAction.IGNORE\n}";
            foreach (var buildTypeId in dependencies)
            {
                yield return $"snapshot({buildTypeId})";
                yield return "{\nonDependencyFailure = FailureAction.IGNORE\n}";
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

        private IEnumerable<string> CreatePushCommand(string imageId, string name, string[] tags)
        {
            yield return "dockerCommand {";
            yield return $"name = \"push {name}:{string.Join(",", tags)}\"";
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
            var tags = image.File.Tags.Select(tag => tag).Distinct().ToArray();

            yield return "dockerCommand {";
            yield return $"name = \"build {image.File.ImageId}:{string.Join(",", tags)}\"";
            yield return "commandType = build {";

            yield return "source = file {";
            yield return $"path = \"\"\"{_pathService.Normalize(Path.Combine(_options.TargetPath, image.File.Path, "Dockerfile"))}\"\"\"";
            yield return "}";

            yield return $"contextDir = \"{_pathService.Normalize(_options.ContextPath)}\"";

            yield return "namesAndTags = \"\"\"";
            foreach (var tag in tags)
            {
                yield return $"{image.File.ImageId}:{tag}";
            }

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
    }
}
