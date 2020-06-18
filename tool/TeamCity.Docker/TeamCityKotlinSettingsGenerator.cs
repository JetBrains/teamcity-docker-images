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

            var buildTypes = new List<string>();
            var buildGraphResult = _buildGraphsFactory.Create(graph);
            if (buildGraphResult.State == Result.Error)
            {
                return;
            }

            var tagPrefixes = _options.TagPrefixes.ToList();

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

            foreach (var buildGraph in buildGraphs)
            {
                var name = buildGraph.name;
                if (string.IsNullOrWhiteSpace(name))
                {
                    name = "Build Docker Images";
                }
                
                if (!names.Add(name))
                {
                    name = $"{name} {++counter}";
                }

                var id = "_" + name.Replace(' ', '_').Replace('-', '_').Replace('.', '_');
                lines.AddRange(GenerateBuildType(id, name, tagPrefixes, buildGraph.graph, buildGraph.weight));

                buildTypes.Add(id);
                lines.Add(string.Empty);
            }

            var allImages = buildGraphs
                .SelectMany(i => i.graph.Nodes.Select(j => j.Value).OfType<Image>())
                .ToList();

            var groupedByImageId = allImages
                .Where(i => i.File.HasManifest)
                .GroupBy(i => i.File.ImageId)
                .ToList();

            foreach (var tagPrefix in tagPrefixes)
            {
                lines.Add($"object {buildId}_{NormalizeName(tagPrefix)}_publish: BuildType(");
                lines.Add("{");
                lines.Add($"name = \"Publish {tagPrefix}\"");

                lines.Add("steps {");
                foreach (var groupByImageId in groupedByImageId)
                {
                    var manifestName = $"{RepositoryName}/{groupByImageId.Key}:{tagPrefix}";
                    var createArgs = new List<string>
                    {
                        "create",
                        "-a",
                        manifestName
                    };

                    foreach (var image in groupByImageId)
                    {
                        var tag = image.File.Tags.FirstOrDefault() ?? "latest";
                        createArgs.Add($"{RepositoryName}/{groupByImageId.Key}:{tagPrefix}-{tag}");
                    }

                    lines.AddRange(CreateDockerCommand($"manifest create {groupByImageId.Key}", "manifest", createArgs));

                    var pushArgs = new List<string>
                    {
                        "push",
                        manifestName
                    };

                    lines.AddRange(CreateDockerCommand($"manifest push {groupByImageId.Key}", "manifest", pushArgs));

                    var inspectArgs = new List<string>
                    {
                        "inspect",
                        manifestName,
                        "--verbose"
                    };

                    lines.AddRange(CreateDockerCommand($"manifest inspect {groupByImageId.Key}", "manifest", inspectArgs));
                }
                
                lines.Add("}");

                lines.Add("dependencies {");
                lines.Add($"snapshot(AbsoluteId(\"{_options.TeamCityBuildConfigurationId}\"))");
                lines.Add("{\nonDependencyFailure = FailureAction.IGNORE\n}");
                foreach (var buildType in buildTypes)
                {
                    lines.Add($"snapshot({buildId}{buildType})");
                    lines.Add("{\nonDependencyFailure = FailureAction.IGNORE\n}");
                }
                lines.Add("}");

                lines.Add("requirements {");
                lines.Add($"noLessThanVer(\"docker.version\", \"{MinDockerVersion}\")");
                lines.Add("equals(\"docker.server.osType\", \"windows\")");
                lines.Add("}");

                lines.Add("features {");
                if (!string.IsNullOrWhiteSpace(_options.TeamCityDockerRegistryId))
                {
                    lines.Add("dockerSupport {");
                    lines.Add("loginToRegistry = on {");
                    lines.Add($"dockerRegistryId = \"{_options.TeamCityDockerRegistryId}\"");
                    lines.Add("}");
                    lines.Add("}");
                }
                lines.Add("}");

                lines.Add("})");

                lines.Add(string.Empty);
            }

            lines.Add("project {");
            lines.Add("vcsRoot(RemoteTeamcityImages)");
            foreach (var tagPrefix in tagPrefixes)
            {
                lines.Add($"buildType({buildId}_{NormalizeName(tagPrefix)}_publish)");
            }

            foreach (var buildType in buildTypes)
            {
                lines.Add($"buildType({buildId}{buildType})");
            }

            lines.Add("}"); // project

            lines.Add(string.Empty);

            lines.Add("object RemoteTeamcityImages : GitVcsRoot({");
            lines.Add("name = \"remote teamcity images\"");
            lines.Add("url = \"https://github.com/JetBrains/teamcity-docker-images.git\"");
            lines.Add("})");

            graph.TryAddNode(new FileArtifact(_pathService.Normalize(Path.Combine(_options.TeamCityDslPath, "settings.kts")), lines), out _);
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

        private IEnumerable<string> GenerateBuildType(string id, string name, IReadOnlyCollection<string> tagPrefixes, IGraph<IArtifact, Dependency> buildGraph, int weight)
        {
            var buildId = NormalizeName(_options.TeamCityBuildConfigurationId);
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

            yield return $"object {buildId}{id} : BuildType({{";
            yield return $"name = \"Build {name}\"";
            yield return $"description  = \"{description}\"";
            yield return "vcs {root(RemoteTeamcityImages)}";
            yield return "steps {";

            // docker pull
            foreach (var refer in refs)
            {
                //refer.RepoTag
                yield return "dockerCommand {";
                yield return $"name = \"pull {refer.RepoTag}\"";
                yield return "commandType = other {";

                yield return "subCommand = \"pull\"";
                yield return $"commandArgs = \"{refer.RepoTag}\"";

                yield return "}";
                yield return "}";

                yield return string.Empty;
            }

            // docker build
            foreach (var image in images)
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

            // docker image tag
            foreach (var tagPrefix in tagPrefixes)
            {
                foreach (var image in images)
                {
                    if (image.File.Tags.Any())
                    {
                        foreach (var tag in image.File.Tags)
                        {
                            yield return "dockerCommand {";
                            yield return $"name = \"tag {image.File.ImageId}:{tagPrefix}-{tag}\"";
                            yield return "commandType = other {";

                            yield return "subCommand = \"tag\"";
                            yield return $"commandArgs = \"{image.File.ImageId}:{tag} {RepositoryName}{image.File.ImageId}:{tagPrefix}-{tag}\"";

                            yield return "}";
                            yield return "}";

                            yield return string.Empty;
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

            yield return "}";

            yield return "features {";

            if (weight > 0)
            {
                yield return "freeDiskSpace {";
                yield return $"requiredSpace = \"{weight}gb\"";
                yield return "failBuild = true";
                yield return "}";
            }

            if (!string.IsNullOrWhiteSpace(_options.TeamCityDockerRegistryId))
            {
                yield return "dockerSupport {";
                yield return "loginToRegistry = on {";
                yield return $"dockerRegistryId = \"{_options.TeamCityDockerRegistryId}\"";
                yield return "}";
                yield return "}";
            }

            // ReSharper disable once StringLiteralTypo
            yield return "swabra {";
            yield return "forceCleanCheckout = true";
            yield return "}";

            yield return "}";

            if (!string.IsNullOrWhiteSpace(_options.TeamCityBuildConfigurationId))
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

            yield return "})";
            yield return string.Empty;
        }

        private static string NormalizeName(string name) =>
            name
                .Replace("%", "")
                .Replace(".", "_");
    }
}
