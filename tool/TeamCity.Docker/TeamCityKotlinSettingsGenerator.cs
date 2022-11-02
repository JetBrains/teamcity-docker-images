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
    using Constants;

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

        /// <summary>
        /// Generates the following TeamCity Build Configurations (KotlinDSL) responsible for ...
        /// ... building and publishing docker images. The following configurations are created: ...
        /// 1. Build and push to local registry. Name pattern: "PushLocal*.kts".
        /// 2. Publishing docker manifests into registry. Name pattern: "PublishHub*.kts".
        /// 3. Pushing into Dockerhub. Name pattern: "PushHub*.kts"
        /// </summary>
        /// <param> <c>graph</c> - RDF graph containing description of target TeamCity build chain. <param>
        /// 
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
                string publishToDockerhubBuildId = $"publish_hub_{NormalizeName(group.Key)}";
                publishOnHubBuildTypes.Add(publishToDockerhubBuildId);
                graph.TryAddNode(AddFile(publishToDockerhubBuildId,CreateManifestBuildConfiguration(publishToDockerhubBuildId, 
                                                                        DeployRepositoryName, $"Publish as {group.Key}",
                                                                        group.ToList(), string.Empty, false, 
                                                                        pushOnHubBuildTypes.ToArray())), out _);
            }

            hubBuildTypes.AddRange(publishOnHubBuildTypes);

            // -- post-push docker image validation
            const string validationBuildTypeId = "image_validation";
            graph.TryAddNode(AddFile(validationBuildTypeId, CreateImageValidationConfig(validationBuildTypeId, allImages)), out _);


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

        /// <summary>
        /// Adds imports on top of the Build Configuration (Kotlin DSL) file.
        /// </summary>
        /// <param name="fileName"> target KotlinDSL file </param>
        /// <param name="lines"> import lines to be added. Please, check method's body to see pre-defined importing packages. </param>
        /// <returns></returns>
        private FileArtifact AddFile(string fileName, IEnumerable<string> lines)
        {
            var curLines = new List<string>
            {
                "// NOTE: THIS IS AN AUTO-GENERATED FILE. IT HAD BEEN CREATED USING TEAMCITY.DOCKER PROJECT. ...",
                "// ... IF NEEDED, PLEASE, EDIT DSL GENERATOR RATHER THAN THE FILES DIRECTLY. ... ",
                "// ... FOR MORE DETAILS, PLEASE, REFER TO DOCUMENTATION WITHIN THE REPOSITORY.",
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
                // Failure Conditions
                "import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.BuildFailureOnText",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.failOnText",
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

        /// <summary>
        /// Creates KotlinDSL's TeamCity build configuration for the creation and uploading of TeamCity's Docker iamges. 
        /// </summary>
        /// <param name="buildTypeId">ID of build confguration within TeamCity</param>
        /// <param name="platform">target palform for the images (e.g. specific distributives of Linux, Windows)</param>
        /// <param name="allImages">list of Docker images</param>
        /// <param name="buildBuildTypes">types of TeamCity builds (e.g. publish_local - the naming is up to user)</param>
        /// <returns></returns>
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

        /// <summary>
        /// Generates Kotlin DSL file with build configuration for post-push Docker image check.
        /// A post-push validation build had been done in purpose of lower cost for failure within build chain.
        /// It includes checks needed for service purposes only.
        /// </summary>
        /// <param name="buildTypeId"></param>
        /// <param name="platform"></param>
        /// <param name="allImages"></param>
        /// <param name="buildBuildTypes"></param>
        /// <returns></returns>
        private IEnumerable<string> CreateImageValidationConfig(string buildTypeId, IEnumerable<Image> allImages) {
            
            // -- Validation is done via Kotlin Script located within file on agent
            yield return "import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.kotlinFile";

            yield return $"object {buildTypeId}: BuildType(";
            yield return "{";
            yield return "name = \"Validation (post-push) of Docker images\"";
            yield return _buildNumberPattern;


            yield return "steps {";
            foreach (var image in allImages)
            {
                // docker pull
                // -- we use "new repo" since the original value is not distinguishable (e.g. linux-EAP)
                var newRepo = $"{DeployRepositoryName}{image.File.ImageId}";
                var newRepoTag = $"{newRepo}:{image.File.Tags.First()}";
                
                foreach (var verificationScriptCallStep in CreateImageVerificationStep(newRepoTag))
                {
                    // generate verification call for each of the images
                    yield return verificationScriptCallStep;
                }
            }
            yield return "}";

            foreach (var failureCondition in CreateFailureConditionRegExpPattern("*DockerImageValidationException.*")) {
                yield return failureCondition;
            }

            // -- depends on Docker image build.
            foreach (var dependencies in CreateDockerImageValidationSnapDependencies("TC_Trunk_BuildDistDocker"))
            {
                yield return dependencies;
            }


            yield return "})";
            yield return string.Empty;
        }

        /// <summary>
        /// Generates TeamCity build configuration (Kotlin DSL) for publishment of Docker image manifests.
        /// </summary>
        /// <param name="buildTypeId">creating build ID</param>
        /// <param name="repositoryName">target repository for publishment</param>
        /// <param name="name">build name</param>
        /// <param name="images">list of Docker images</param>
        /// <param name="imagePostfix">postfix that should be appended to the tags of all images</param>
        /// <param name="onStaging">indicates if the build is being created for staging purposes</param>
        /// <param name="dependencies">dependencides of the build (other TeamCity build configuration, if any)</param>
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

                // docker image tag & verification
                foreach (var image in images)
                {
                    if (image.File.Tags.Any())
                    {
                        var tag = image.File.Tags.First();

                        // 1. "tag" command
                        foreach (var tagCommand in CreateTagCommand($"{image.File.ImageId}:{tag}", $"{BuildRepositoryName}{image.File.ImageId}{BuildImagePostfix}:{tag}", $"{image.File.ImageId}:{tag}"))
                        {
                            yield return tagCommand;
                        }

                        // 2. verification. It's done after re-tag to make the image easily distinguishable
                        foreach (var tagCommand in CreateImageVerificationStep($"{BuildRepositoryName}{image.File.ImageId}{BuildImagePostfix}:{tag}"))
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

        /// <summary>
        /// Generates dependencies {...} block of Kotlin DSL pipeline. Within this scope, ...
        /// ... it includes dependencies from builds responsible for the creation of Docker images.
        /// <returns></returns>
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

        /// <summary>
        /// Creates dependencies {...} block for build configuration responsible for post-push ...
        /// ... validation of Docker images.
        /// <returns></returns>
        private IEnumerable<string> CreateDockerImageValidationSnapDependencies(string dependantBuildId) {
            
            if (dependantBuildId == null) {
                // dependant build ID must be specified, otherwise the block wouldn't be useful
                yield break;
            }
            yield return "dependencies {";
            yield return $"dependency(AbsoluteId(\"{dependantBuildId}\")) {{";
            // doesn't make sense to start verification in case upstream (image build) had failed
            yield return "snapshot { onDependencyFailure = FailureAction.FAIL_TO_START }";
            // dependency {...}
            yield return "}";
            // dependencies {...}
            yield return "}";
        }

        /// <summary>
        /// Creates failure conditions that terminated the build if an error message with given pattern had occured.
        /// </summary>
        /// <param name="pattern">Error pattern.</param>
        /// <returns></returns>
        private IEnumerable<string> CreateFailureConditionRegExpPattern(string pattern) {
            if (pattern == null) {
                yield break;
            }

            yield return "failureConditions {";

            // Condition num.1 - failOnText {...}
            yield return "failOnText {";
            // -- not setting "ID" - that'd be auto-generated
            yield return $"conditionType = {TeamCityConstants.Conditions.REGEXP}";
            yield return $"pattern = \"{pattern}\"";
            yield return "reverse = false";
            // end of "failOnText{...}
            yield return "}";

            // end of failureConditions {...}
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

        /// <summary>
        /// Constructs Kotlin DSL's dockerCommand {...} for image push.
        /// </summary>
        /// <param name="imageId">Docker image ID</param>
        /// <param name="name">step name</param>
        /// <param name="tags">target Docker image tags</param>
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

        /// <summary>
        /// Constructs Kotlin DSL's dockerComamnd {...} for image re-tag.
        /// </summary>
        /// <param name="repoTag">original Docker iamge tag</param>
        /// <param name="newRepoTag"> target Docker image tag</param>
        /// <param name="name">step name</param>
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

        /// <summary>
        /// Constructs Kotlin DSL's step for preparation to dockerCommand {...}, such as ...
        /// ... the creation of .dockerignore, append of the entries into ti. 
        /// </summary>
        /// <param name="image">info about Docker image</param>
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

        /// <summary>
        /// Constructs Kotlin DSL's dockerCommand {...} for image build.
        /// </summary>
        /// <param name="image">info about Docker image</param>
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

        /// <summary>
        /// Constructs Kotlin DSL's Docker image verification step.
        /// <param name="imageFqdn">Docker mage< fully-qualified domain name/param>
        private IEnumerable<string> CreateImageVerificationStep(string imageFqdn) {
             yield return "kotlinFile {";
            yield return $"name = \"Image Verification - {imageFqdn}\"";
            yield return "path = \"tool/automation/ImageValidation.kts\"";
            yield return $"arguments = \"{imageFqdn}\" }}";
            yield return string.Empty;
        }

        /// <summary>
        /// Constructs Kotlin DSL's dockerCommand {...} for Docker Image pull.
        /// </summary>
        /// <param name="repoTag"> image's registry </param>
        /// <param name="name">image's tag</param>
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
