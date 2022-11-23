// ReSharper disable ClassNeverInstantiated.Global
namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Text;
    using Generic;
    using IoC;
    using Model;

    internal class TeamCityKotlinSettingsGenerator : IGenerator
    {
        private const string MinDockerVersion = "18.05.0";
        private const string BuildNumberParam = "dockerImage.teamcity.buildNumber";
        private readonly string _buildNumberPattern = $"buildNumberPattern=\"%{BuildNumberParam}%-%build.counter%\"";
        private const string RemoveManifestsScript = "\"\"if exist \"%%USERPROFILE%%\\.docker\\manifests\\\" rmdir \"%%USERPROFILE%%\\.docker\\manifests\\\" /s /q\"\"";
        [NotNull] private readonly string BuildRepositoryName = "%docker.buildRepository%";
        [NotNull] private readonly string BuildImagePostfix = "%docker.buildImagePostfix%";
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

        public void Generate(IGraph<IArtifact, Dependency> graph)
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

                graph.TryAddNode(AddFile(buildTypeId, GenerateBuildAndPushType(buildTypeId, name, path, buildGraph.weight, onPause)), out _);
            }

            // Publish on staging registry
            var localPublishGroups =
                from image in allImages
                from tag in image.File.Tags.Skip(1)
                group image by tag;

            var publishLocalId = "publish_local";
            localBuildTypes.Add(publishLocalId);
            graph.TryAddNode(AddFile(publishLocalId, CreateManifestBuildConfiguration(publishLocalId, BuildRepositoryName, "Publish", localPublishGroups.ToList(), BuildImagePostfix, true, buildAndPushLocalBuildTypes.ToArray())), out _);
            
            // Push on docker hub
            var pushOnHubBuildTypes = new List<string>();
            var platforms = allImages.Select(i => i.File.Platform).Distinct();
            foreach (var platform in platforms)
            {
                var buildTypeId = $"push_hub_{NormalizeName(platform)}";
                pushOnHubBuildTypes.Add(buildTypeId);
                graph.TryAddNode(AddFile(buildTypeId, CreatePushBuildConfiguration(buildTypeId, platform, allImages, publishLocalId)), out _);
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
                graph.TryAddNode(AddFile(buildTypeId, CreateManifestBuildConfiguration(buildTypeId, DeployRepositoryName, $"Publish as {group.Key}", group.ToList(), string.Empty, false, pushOnHubBuildTypes.ToArray())), out _);
            }

            hubBuildTypes.AddRange(publishOnHubBuildTypes);

            // Local project
            // ReSharper disable once UseObjectOrCollectionInitializer
            var lines = new List<string>();
            lines.Add("object LocalProject : Project({");
            lines.Add("name = \"Staging registry\"");
            foreach (var buildType in localBuildTypes.Distinct())
            {
                lines.Add($"buildType({NormalizeFileName(buildType)}.{buildType})");
            }
            lines.Add("})");

            graph.TryAddNode(AddFile("LocalProject", lines), out _);
            lines.Clear();

            // Hub project
            lines.Add("object HubProject : Project({");
            lines.Add("name = \"Docker hub\"");
            foreach (var buildType in hubBuildTypes.Distinct())
            {
                lines.Add($"buildType({NormalizeFileName(buildType)}.{buildType})");
            }

            lines.Add("})");

            graph.TryAddNode(AddFile("HubProject", lines), out _);
            lines.Clear();
        }

        private FileArtifact AddFile(string fileName, IEnumerable<string> lines)
        {
            var curLines = new List<string>
            {
                "package generated",
                string.Empty,
                "import jetbrains.buildServer.configs.kotlin.v2019_2.*",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace",
                // ReSharper disable once StringLiteralTypo
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand",
                "import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo",
                string.Empty
            };
            // ReSharper disable once StringLiteralTypo

            curLines.AddRange(lines);
            return new FileArtifact(_pathService.Normalize(Path.Combine(_options.TeamCityDslPath, NormalizeFileName(fileName) + ".kts")), curLines);
        }

        private string NormalizeFileName(string fileName) => new string(FixFileName(fileName).ToArray());

        private IEnumerable<char> FixFileName(IEnumerable<char> chars)
        {
            var first = true;
            foreach (var c in chars)
            {
                if (c == '_')
                {
                    first = true;
                    continue;
                }

                if (first)
                {
                    yield return char.ToUpper(c);
                }
                else
                {
                    yield return c;
                }

                first = false;
            }
        }

        private IEnumerable<string> CreatePushBuildConfiguration(string buildTypeId, string platform, IEnumerable<Image> allImages, params string[] buildBuildTypes)
        {
            var images = allImages.Where(i => i.File.Platform == platform).ToList();
            yield return $"object {buildTypeId}: BuildType(";
            yield return "{";
            yield return $"name = \"Push {platform}\"";
            yield return _buildNumberPattern;

            yield return "steps {";
            foreach (var image in images)
            {
                // docker pull
                var tag = image.File.Tags.First();
                var repo = $"{image.File.ImageId}{BuildImagePostfix}:{tag}";
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

            foreach (var param in CreateSpaceParams(weight))
            {
                yield return param;
            }

            var requirements = images.SelectMany(i => i.File.Requirements).Distinct().ToList();
            foreach (var lines in CreateDockerRequirements(requirements, platform))
            {
                yield return lines;
            }

            foreach (var dependencies in CreateSnapshotDependencies(buildBuildTypes, null))
            {
                yield return dependencies;
            }

            yield return "})";
            yield return string.Empty;
        }

        private IEnumerable<string> CreateManifestBuildConfiguration(string buildTypeId, string repositoryName, string name, IReadOnlyCollection<IGrouping<string, Image>> images, string imagePostfix, bool? onStaging, params string[] dependencies)
        {
            yield return $"object {buildTypeId}: BuildType(";
            yield return "{";
            yield return $"name = \"{name}\"";
            yield return _buildNumberPattern;
            yield return "enablePersonalBuilds = false";
            yield return "type = BuildTypeSettings.Type.DEPLOYMENT";
            yield return "maxRunningBuilds = 1";

            yield return "steps {";
            foreach (var line in AddScript("remove manifests", RemoveManifestsScript))
            {
                yield return line;
            }

            foreach (var group in images.OrderBy(i => i.Key))
            {
                var groupedByImageId = group.GroupBy(i => i.File.ImageId);
                foreach (var groupByImageId in groupedByImageId)
                {
                    foreach (var line in CreateManifestCommands(repositoryName, group.Key, groupByImageId.Key, imagePostfix, groupByImageId))
                    {
                        yield return line;
                    }
                }
            }

            yield return "}";

            foreach (var line in CreateSnapshotDependencies(dependencies, onStaging))
            {
                yield return line;
            }

            var requirements = images.SelectMany(i => i).SelectMany(i => i.File.Requirements).Distinct().ToList();
            foreach (var lines in CreateDockerRequirements(requirements, "windows", MinDockerVersion))
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

        private static IEnumerable<string> CreateDockerRequirements(IReadOnlyCollection<Requirement> requirements, string platform = "", string minDockerVersion = "")
        {
            yield return "requirements {";
            if (!string.IsNullOrWhiteSpace(minDockerVersion))
            {
                yield return $"noLessThanVer(\"docker.version\", \"{minDockerVersion}\")";
            }

            if (!string.IsNullOrWhiteSpace(platform))
            {
                yield return $"contains(\"docker.server.osType\", \"{platform}\")";
            }

            foreach (var requirement in requirements)
            {
                if (string.IsNullOrWhiteSpace(requirement.Value))
                {
                    yield return $"{requirement.Type.ToString().ToLowerInvariant()}(\"{requirement.Property}\")";
                }
                else
                {
                    yield return $"{requirement.Type.ToString().ToLowerInvariant()}(\"{requirement.Property}\", \"{requirement.Value}\")";   
                }
            }
            yield return "}";
        }

        private IEnumerable<string> CreateManifestCommands(string repositoryName, string tag, string imageId, string imagePostfix, IEnumerable<Image> images)
        {
            if (string.IsNullOrWhiteSpace(tag) || !char.IsLetterOrDigit(tag[0]))
            {
                yield break;
            }

            var repo = $"{repositoryName}{imageId}{imagePostfix}";
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
            var images = path.Select(i => i.Value).OfType<Image>().Where(i => onPause || i.File.Repositories.Any()).ToList();
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
            yield return _buildNumberPattern;
            yield return $"description  = \"{description}\"";

            if (!onPause)
            {
                yield return "vcs {root(TeamCityDockerImagesRepo)}";
                yield return "steps {";

                // docker pull
                foreach (var command in references.SelectMany(refer => CreatePullCommand(refer.RepoTag, refer.RepoTag)))
                {
                    yield return command;
                }

                // docker build
                foreach (var command in images.SelectMany(image => CreatePrepareContextCommand(image).Concat(CreateBuildCommand(image))))
                {
                    yield return command;
                }

                // docker image tag
                foreach (var image in images)
                {
                    if (image.File.Tags.Any())
                    {
                        var tag = image.File.Tags.First();
                        foreach (var tagCommand in CreateTagCommand($"{image.File.ImageId}:{tag}", $"{BuildRepositoryName}{image.File.ImageId}{BuildImagePostfix}:{tag}", $"{image.File.ImageId}:{tag}"))
                        {
                            yield return tagCommand;
                        }
                    }
                }

                // docker push
                foreach (var image in images)
                {
                    var tag = image.File.Tags.First();
                    foreach (var pushCommand in CreatePushCommand($"{BuildRepositoryName}{image.File.ImageId}{BuildImagePostfix}", $"{image.File.ImageId}:{tag}", tag))
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

                foreach (var param in CreateSpaceParams(weight))
                {
                    yield return param;
                }
                
                var requirements = images.SelectMany(i => i.File.Requirements).Distinct().ToList();
                foreach (var lines in CreateDockerRequirements(requirements))
                {
                    yield return lines;
                }
            }

            yield return "})";
            yield return string.Empty;
        }

        private static IEnumerable<string> CreateSpaceParams(int weight)
        {
            if (weight > 0)
            {
                yield return "params {";
                yield return $"param(\"system.teamcity.agent.ensure.free.space\", \"{weight}gb\")";
                yield return "}";
            }
        }

        private IEnumerable<string> CreateSnapshotDependencies(IEnumerable<string> dependencies, bool? onStaging)
        {
            yield return "dependencies {";
            if (onStaging != null)
            {
                yield return $"snapshot(AbsoluteId(\"{_options.TeamCityBuildConfigurationId}\"))";
                if (onStaging == true)
                {
                    yield return "{\nonDependencyFailure = FailureAction.FAIL_TO_START\nreuseBuilds = ReuseBuilds.ANY\nsynchronizeRevisions = false\n}";
                }
                else
                {
                    yield return "{\nreuseBuilds = ReuseBuilds.ANY\nonDependencyFailure = FailureAction.IGNORE\n}";
                }
            }

            foreach (var buildTypeId in dependencies.OrderBy(i => i))
            {
                yield return $"snapshot({NormalizeFileName(buildTypeId)}.{buildTypeId})";
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
            yield return "snapshot { onDependencyFailure = FailureAction.IGNORE\nreuseBuilds = ReuseBuilds.ANY }";
            yield return "artifacts {";
            yield return $"artifactRules = \"TeamCity.zip!/**=>{_pathService.Normalize(_options.ContextPath)}/TeamCity\"";
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
            
            yield return "removeImageAfterPush = false";

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

        private IEnumerable<string> CreatePrepareContextCommand(Image image)
        {
            var tag = image.File.Tags.First();
            yield return "script {";
            yield return $"name = \"context {image.File.ImageId}:{tag}\"";
            yield return "scriptContent = \"\"\"";

            // ReSharper disable once IdentifierTypo
            // ReSharper disable once StringLiteralTypo
            var dockerignore = Path.Combine(_options.ContextPath, ".dockerignore").Replace("\\", "/");
            yield return $"echo 2> {dockerignore}";
            foreach (var ignore in image.File.Ignores)
            {
                yield return $"echo {ignore} >> {dockerignore}";
            }

            yield return "\"\"\".trimIndent()";
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
            yield return "commandArgs = \"--no-cache\"";
            
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
    }
}
