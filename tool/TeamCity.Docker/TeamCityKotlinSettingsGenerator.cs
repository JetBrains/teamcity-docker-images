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
        [NotNull] private readonly string RepositoryName = "%docker.pushRepository%";
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

            var buildBuildTypes = new List<string>();
            foreach (var buildGraph in buildGraphs)
            {
                // build build config
                var name = buildGraph.name;
                if (string.IsNullOrWhiteSpace(name))
                {
                    name = "Build Docker Images";
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

            var allImages = buildGraphs
                .SelectMany(i => i.graph.Nodes.Select(j => j.Value).OfType<Image>())
                .ToList();

            var rootBuildTypes = new List<string>();
            foreach (var tagPrefix in _options.TagPrefixes)
            {
                // publish build config
                var publishBuildTypeId = $"{buildId}_{NormalizeName(tagPrefix)}_publish";
                rootBuildTypes.Add(publishBuildTypeId);
                lines.AddRange(CreatePublishBuildConfiguration(publishBuildTypeId, tagPrefix, allImages, buildBuildTypes));
                // publish build config
            }

            // project
            lines.Add("project {");
            lines.Add("vcsRoot(RemoteTeamcityImages)");
            foreach (var buildType in rootBuildTypes.Concat(buildBuildTypes))
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

        private IEnumerable<string> CreatePublishBuildConfiguration(string buildTypeId, string tagPrefix, IEnumerable<Image> allImages, IEnumerable<string> buildBuildTypes)
        {
            var groupedByImageId = allImages
                .Where(i => i.File.HasManifest)
                .GroupBy(i => i.File.ImageId);

            yield return $"object {buildTypeId}: BuildType(";
            yield return "{";
            yield return $"name = \"Publish {tagPrefix}\"";

            yield return "steps {";
            foreach (var groupByImageId in groupedByImageId)
            {
                var manifestName = $"{RepositoryName}{groupByImageId.Key}:{tagPrefix}";
                var createArgs = new List<string>
                {
                    "create",
                    "-a",
                    manifestName
                };

                foreach (var image in groupByImageId)
                {
                    var tag = image.File.Tags.FirstOrDefault() ?? "latest";
                    createArgs.Add($"{manifestName}-{tag}");
                }

                foreach (var line in CreateDockerCommand($"manifest create {groupByImageId.Key}", "manifest", createArgs))
                {
                    yield return line;
                }

                var pushArgs = new List<string>
                {
                    "push",
                    manifestName
                };

                foreach (var line in CreateDockerCommand($"manifest push {groupByImageId.Key}", "manifest", pushArgs))
                {
                    yield return line;
                }

                var inspectArgs = new List<string>
                {
                    "inspect",
                    manifestName,
                    "--verbose"
                };

                foreach (var line in CreateDockerCommand($"manifest inspect {groupByImageId.Key}", "manifest", inspectArgs))
                {
                    yield return line;
                }
            }

            yield return "}";

            foreach (var line in CreateSnapshotDependencies(buildBuildTypes))
            {
                yield return line;
            }

            yield return "requirements {";
            yield return $"noLessThanVer(\"docker.version\", \"{MinDockerVersion}\")";
            yield return "equals(\"docker.server.osType\", \"windows\")";
            yield return "}";

            yield return "features {";
            if (!string.IsNullOrWhiteSpace(_options.TeamCityDockerRegistryId))
            {
                foreach (var line in CreateDockerFeature())
                {
                    yield return line;
                }
            }

            yield return "}";

            yield return "})";
            yield return string.Empty;
        }

        private IEnumerable<string> GenerateBuildType(string buildTypeId, string name, IReadOnlyCollection<string> tagPrefixes, IGraph<IArtifact, Dependency> buildGraph, int weight)
        {
            var path = _buildPathProvider.GetPath(buildGraph).ToList();
            var images = path.Select(i => i.Value).OfType<Image>().ToList();
            var refs = path.Select(i => i.Value).OfType<Reference>().ToList();

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
                foreach (var aa in grp)
                {
                    var tags = string.Join(",", aa.Key.Tags);
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
            foreach (var pullCommand in refs.SelectMany(refer => CreatePullCommand(refer.RepoTag)))
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
                            foreach (var tagCommand in CreateTagCommand($"{image.File.ImageId}:{tag}", $"{RepositoryName}{image.File.ImageId}:{tagPrefix}-{tag}"))
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

                foreach (var pushCommand in CreatePushCommand(image, tags))
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

            if (!string.IsNullOrWhiteSpace(_options.TeamCityDockerRegistryId))
            {
                foreach (var feature in CreateDockerFeature())
                {
                    yield return feature;
                }
            }

            // ReSharper disable once StringLiteralTypo
            foreach (var feature in CreateSwabraFeature())
            {
                yield return feature;
            }

            yield return "}";

            if (!string.IsNullOrWhiteSpace(_options.TeamCityBuildConfigurationId))
            {
                foreach (var dependencies in CreateArtifactsDependencies())
                {
                    yield return dependencies;
                }
            }

            yield return "})";
            yield return string.Empty;
        }

        private IEnumerable<string> CreateSnapshotDependencies(IEnumerable<string> buildTypes)
        {
            yield return "dependencies {";
            yield return $"snapshot(AbsoluteId(\"{_options.TeamCityBuildConfigurationId}\"))";
            yield return "{\nonDependencyFailure = FailureAction.IGNORE\n}";
            foreach (var buildTypeId in buildTypes)
            {
                yield return $"snapshot({buildTypeId})";
                yield return "{\nonDependencyFailure = FailureAction.IGNORE\n}";
            }

            yield return "}";
        }

        private IEnumerable<string> CreateArtifactsDependencies()
        {
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
            yield return "dockerSupport {";
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

        private IEnumerable<string> CreatePushCommand(Image image, string[] tags)
        {
            yield return "dockerCommand {";
            yield return $"name = \"push {image.File.ImageId}:{string.Join(",", tags)}\"";
            yield return "commandType = push {";

            yield return "namesAndTags = \"\"\"";
            foreach (var tag in tags)
            {
                yield return $"{RepositoryName}{image.File.ImageId}:{tag}";
            }

            yield return "\"\"\".trimIndent()";

            yield return "}";
            yield return "}";

            yield return string.Empty;
        }

        private IEnumerable<string> CreateTagCommand(string imageName, string newImageName)
        {
            yield return "dockerCommand {";
            yield return $"name = \"tag {newImageName.Replace(RepositoryName, string.Empty)}\"";
            yield return "commandType = other {";

            yield return "subCommand = \"tag\"";
            yield return $"commandArgs = \"{imageName} {newImageName}\"";

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

        private static IEnumerable<string> CreatePullCommand(string repoTag)
        {
            yield return "dockerCommand {";
            yield return $"name = \"pull {repoTag}\"";
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
