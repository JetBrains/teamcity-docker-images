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
        
        // Apart from other conditions, the optional flag for ARM-based Docker Image allows to enable them for investigation / development purposes.
        private const bool IsArmBasedImageBuildEnabled = true;

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
        /// Generates the following TeamCity Build Configurations (KotlinDSL) responsible for:
        /// - 1. Build and push to local registry. Name pattern: "PushLocal*.kts".
        /// - 2. Publishing docker manifests into registry. Name pattern: "PublishHub*.kts".
        /// - 3. Pushing into Dockerhub. Name pattern: "PushHub*.kts"
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
                // ARM-based Docker Images Are not currently supported by TeamCity.
                .Where(i => IsArmBasedImageBuildEnabled || i.File.Tags.First().IndexOf("arm", StringComparison.OrdinalIgnoreCase) < 0)
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

            // Validation of Docker Image Size
            const string validationBuildTypeId = "image_validation";
            graph.TryAddNode(AddFile(validationBuildTypeId, CreateImageValidationConfig(validationBuildTypeId, allImages, BuildImagePostfix)), out _);
            localBuildTypes.Add(validationBuildTypeId);

            // Local project
            // ReSharper disable once UseObjectOrCollectionInitializer
            var lines = new List<string>();
            lines.Add("object LocalProject : Project({");
            lines.Add("\t name = \"Staging registry\"");
            foreach (var buildType in localBuildTypes.Distinct())
            {
                lines.Add($"\t buildType({NormalizeFileName(buildType)}.{buildType})");
            }

            // "Hosted" (static) configurations for image build
            lines.Add("\t buildType(PushStagingLinux2004_Aarch64.push_staging_linux_2004_aarch64)");

            lines.Add("})");

            graph.TryAddNode(AddFile("LocalProject", lines), out _);
            lines.Clear();

            // Hub project
            lines.Add("object HubProject : Project({");
            lines.Add("\t name = \"Docker hub\"");
            foreach (var buildType in hubBuildTypes.Distinct())
            {
                lines.Add($"\t buildType({NormalizeFileName(buildType)}.{buildType})");
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
                "import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot",
                // ReSharper disable once StringLiteralTypo
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra",
                "import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo",
                // -- build features
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace",
                // -- Failure Conditions
                "import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.BuildFailureOnText",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.failOnText",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.BuildFailureOnMetric",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.failOnMetricChange",
                // -- Build Steps
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.kotlinFile",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand",
                // -- All Triggers
                "import jetbrains.buildServer.configs.kotlin.v2019_2.Trigger",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.VcsTrigger",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger",
                "import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs",
                // -- Build configurations
                "import hosted.BuildAndPushHosted",

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
        /// Creates KotlinDSL's TeamCity build configuration for the creation and uploading of TeamCity's Docker images.
        /// </summary>
        /// <param name="buildTypeId">ID of build configuration within TeamCity</param>
        /// <param name="platform">target platform for the images (e.g. specific distributive of Linux, Windows)</param>
        /// <param name="allImages">list of Docker images</param>
        /// <param name="buildBuildTypes">types of TeamCity builds (e.g. publish_local - the naming is up to user)</param>
        /// <returns></returns>
        private IEnumerable<string> CreatePushBuildConfiguration(string buildTypeId, string platform, IEnumerable<Image> allImages, params string[] buildBuildTypes)
        {
            var images = allImages.Where(i => i.File.Platform == platform).ToList();
            yield return $"object {buildTypeId}: BuildType(" + "{";
             yield return $"\t name = \"Push {platform}\"";
            yield return $"\t{_buildNumberPattern}";

            yield return "\t steps {";
            foreach (var image in images)
            {
                // docker pull
                var tag = image.File.Tags.First();
                var repo = $"{image.File.ImageId}{BuildImagePostfix}:{tag}";
                var repoTag = $"{BuildRepositoryName}{repo}";
                foreach (var pullCommand in CreatePullCommand(repoTag, repo))
                {
                    yield return $"\t\t{pullCommand}";
                }

                var newRepo = $"{DeployRepositoryName}{image.File.ImageId}";
                var newRepoTag = $"{newRepo}:{image.File.Tags.First()}";
                foreach (var tagCommand in CreateTagCommand(repoTag, newRepoTag, repo))
                {
                    yield return $"\t\t{tagCommand}";
                }

                foreach (var pushCommand in CreatePushCommand($"{newRepo}", repo, tag))
                {
                    yield return $"\t\t{pushCommand}";
                }
            }

            yield return "\t }";

            yield return "\t features {";

            var weight = images.Sum(i => i.File.Weight.Value);
            if (weight > 0)
            {
                foreach (var feature in CreateFreeDiskSpaceFeature(weight))
                {
                    yield return $"\t\t {feature}";
                }
            }

            foreach (var feature in CreateDockerFeature())
            {
                yield return $"\t\t {feature}";
            }

            // ReSharper disable once StringLiteralTypo
            foreach (var feature in CreateSwabraFeature())
            {
                yield return $"\t\t {feature}";
            }

            yield return "\t }";

            foreach (var param in CreateSpaceParams(weight))
            {
                yield return $"\t{param}";
            }

            var requirements = images.SelectMany(i => i.File.Requirements).Distinct().ToList();
            foreach (var lines in CreateDockerRequirements(requirements, platform))
            {
                yield return $"\t {lines}";
            }

            foreach (var dependencies in CreateSnapshotDependencies(buildBuildTypes, null))
            {
                yield return $"\t{dependencies}";
            }

            yield return "})";
            yield return string.Empty;
        }


        /// <summary>
        /// Generates Kotlin DSL file with build configuration for post-push Docker image check.
        /// A post-push validation build had been done for the purpose of lower cost for failure within build chain.
        /// </summary>
        /// <param name="buildTypeId">Identifier the the creating build configuration.</param>
        /// <param name="allImages">Images that will be checked in context of build configuration.</param>
        /// <param name="imagePostfix">(might be empty) postfix to image repository name, e.g.: "-staging"</param> 
        private IEnumerable<string> CreateImageValidationConfig(string buildTypeId, IEnumerable<Image> allImages, string imagePostfix) {
            

            yield return $"object {buildTypeId}: BuildType(" + "{";
            yield return "\t name = \"Validation of Size Regression - Staging Docker Images (Windows / Linux)\"";
            yield return $"\t {_buildNumberPattern}";

            // VCS Root Is needed in order to launch automation framework
            yield return String.Join('\n',
                "\t vcs {",
                "\t\t root(TeamCityDockerImagesRepo)",
                "\t }" 
            );

            // Trigger is needed to execute the image once they've been published into Dockerhub
            yield return String.Join('\n',
                "\n\t triggers {",
                "\t\t // Execute the build once the images are available within %deployRepository%",
                "\t\t finishBuildTrigger {",
                "\t\t\t buildType = \"${PublishHubVersion.publish_hub_version.id}\"",
                "\t\t\t // if filter won't be specified, only <default> branch would be included",
			    "\t\t\t  branchFilter = \"\"\"",
				"\t\t\t\t +:development/*",
				"\t\t\t\t +:release/*",
			    "\t\t\t \"\"\".trimIndent()",
                "\t\t }",
                "\t }"
            );

            // Parameters are needed in order to prevent unnecessarry dependency from an inherited parameter
            yield return String.Join('\n',
                "\n\t params {",
                "\t\t // -- inherited parameter, removed in debug purposes",
                "\t\t param(\"dockerImage.teamcity.buildNumber\", \"-\")",
                "\t }"
            );


            // -- Declare a set of images that we'd need to iterate over
            // -- containing elements for Kotlin hashmap declaration: ...
            // -- ... <Image Static Name> - <Image Name with Parameter References>
            List<string> validationHashmapEntries = new List<string>();

            foreach (var image in allImages) {
                var newRepo = $"{DeployRepositoryName}{image.File.ImageId}{imagePostfix}";
                var newRepoTag = $"{newRepo}:{image.File.Tags.First()}";
                // Add as as Kotlin DSL list element
                // -- hashmap: <name>, <parameter reference domain name>
                validationHashmapEntries.Add($"\"{image.File.ImageId}-{image.File.Tags.First()}\" to \"{newRepoTag}\"");
            }

            // -- Create declaration of HashMap within DSL which will be used for generation of step "{ ... }" blocks
            yield return String.Join('\n',
                "\n\t val targetImages: HashMap<String, String> = hashMapOf(",
                // concatenate previously created hashmap entries for the declaration within DSL
                String.Join(", \n\t\t", validationHashmapEntries),
                "\t  )"
            );

            // Generate steps in order to validate the images within the list above
            yield return String.Join('\n',
                "\n\t steps {",
                "\t\t   targetImages.forEach { (imageVerificationStepId, imageDomainName) ->",
                "\t\t     // Generate validation for each image fully-qualified domain name (FQDN)",
                "\t\t     gradle {",
                "\t\t\t       name = \"Image Verification - $imageVerificationStepId\"",
                // "%docker.buildRepository.login% %docker.stagingRepository.token%" are defined within TeamCity server
                "\t\t\t       tasks = \"clean build run --args=\\\"validate  $imageDomainName %docker.stagingRepository.login% %docker.stagingRepository.token%\\\"\"",
                "\t\t\t       workingDir = \"tool/automation/framework\"",
                "\t\t\t       buildFile = \"build.gradle\"",
                "\t\t\t       jdkHome = \"%env.JDK_11_x64%\"",
                "\t\t\t       executionMode = BuildStep.ExecutionMode.ALWAYS",
                "\t\t\t     }",
                "\t\t   }",
                "\t }"
            );

            // Generate failure conditions
            yield return String.Join('\n',
                "\n\t failureConditions {",
                "\t\t   // Failed in case the validation via framework didn't succeed",
                "\t\t   failOnText {",
                "\t\t\t     conditionType = BuildFailureOnText.ConditionType.CONTAINS",
                "\t\t\t     pattern = \"DockerImageValidationException\"",
                "\t\t\t     failureMessage = \"Docker Image validation have failed\"",
                "\t\t\t     // allows the steps to continue running even in case of one problem",
                "\t\t\t     reportOnlyFirstMatch = false",
                "\t\t   }",
                "\t }"
            );

            // Generate requirements
            yield return String.Join('\n',
                "\n\t requirements {",
                "\t\t  exists(\"env.JDK_11\")",
                "\t\t  // Images are validated mostly via DockerHub REST API. In case ...",
                "\t\t  // ... Docker agent will be used, platform-compatibility must be addressed, ...",
                "\t\t  // ... especially in case of Windows images.",
                "\t\t  contains(\"teamcity.agent.jvm.os.name\", \"Linux\")",
                "\t }"
            );

            // Generate build features
            yield return String.Join('\n',
                "\n\t features {",
                "\t\t   dockerSupport {",
                "\t\t\t     cleanupPushedImages = false",
                "\t\t\t     loginToRegistry = on {",
                // Registries: DockerHub, JetBrains Space
                "\t\t\t       dockerRegistryId = \"PROJECT_EXT_774,PROJECT_EXT_315\"",
                "\t\t\t     }",
                "\t\t   }",
                "\t }"
            );

            // Dependencies
            yield return String.Join('\n',
            "\t dependencies {",
            "\t\t    // Last build of Docker Image",
            "\t\t    dependency(BuildAndPushHosted.BuildAndPushHosted) {",
            "\t\t\t      artifacts {",
            "\t\t\t\t        artifactRules = \"TeamCity.zip!/**=>context/TeamCity\"",
            "\t\t\t\t        cleanDestination = true",
            "\t\t\t\t        lastSuccessful()",
            "\t\t\t      }",
            "\t\t    }",
            "\t }"
            );

            yield return "})";
            yield return string.Empty;
        }

        /// <summary>
        /// Generates TeamCity build configuration (Kotlin DSL) for publishing of Docker image manifests.
        /// </summary>
        /// <param name="buildTypeId">creating build ID</param>
        /// <param name="repositoryName">target repository for image publishing</param>
        /// <param name="name">build name</param>
        /// <param name="images">list of Docker images</param>
        /// <param name="imagePostfix">postfix that should be appended to the tags of all images</param>
        /// <param name="onStaging">indicates if the build is being created for staging purposes</param>
        /// <param name="dependencies">dependencies of the build (other TeamCity build configuration, if any)</param>
        private IEnumerable<string> CreateManifestBuildConfiguration(string buildTypeId, string repositoryName, string name, IReadOnlyCollection<IGrouping<string, Image>> images, string imagePostfix, bool? onStaging, params string[] dependencies)
        {   
            yield return $"object {buildTypeId}: BuildType(" + "{";
            yield return $"\t name = \"{name}\"";
            yield return $"\t{_buildNumberPattern}";
            yield return "\t enablePersonalBuilds = false";
            yield return "\t type = BuildTypeSettings.Type.DEPLOYMENT";
            yield return "\t maxRunningBuilds = 1";

            yield return "\t steps {";
            foreach (var line in AddScript("remove manifests", RemoveManifestsScript))
            {
                yield return $"\t\t {line}";
            }

            foreach (var group in images.OrderBy(i => i.Key))
            {
                var groupedByImageId = group.GroupBy(i => i.File.ImageId);
                foreach (var groupByImageId in groupedByImageId)
                {
                    foreach (var line in CreateManifestCommands(repositoryName, group.Key, groupByImageId.Key, imagePostfix, groupByImageId))
                    {
                        yield return $"\t{line}";
                    }
                }
            }

            yield return "\t }";

            foreach (var line in CreateSnapshotDependencies(dependencies, onStaging))
            {
                yield return $"\t{line}";
            }

            var requirements = images.SelectMany(i => i).SelectMany(i => i.File.Requirements).Distinct().ToList();
            foreach (var lines in CreateDockerRequirements(requirements, "windows", MinDockerVersion))
            {
                yield return $"\t{lines}";
            }

            yield return "\t features {";
            foreach (var line in CreateDockerFeature())
            {
                yield return $"\t\t {line}";
            }
            yield return "\t}";

            yield return "})";
            yield return string.Empty;
        }

        private static IEnumerable<string> CreateDockerRequirements(IReadOnlyCollection<Requirement> requirements, string platform = "", string minDockerVersion = "")
        {
            yield return "requirements {";
            if (!string.IsNullOrWhiteSpace(minDockerVersion))
            {
                yield return $"\t noLessThanVer(\"docker.version\", \"{minDockerVersion}\")";
            }

            if (!string.IsNullOrWhiteSpace(platform))
            {
                yield return $"\t contains(\"docker.server.osType\", \"{platform}\")";
            }

            foreach (var requirement in requirements)
            {
                if (string.IsNullOrWhiteSpace(requirement.Value))
                {
                    yield return $"\t {requirement.Type.ToString().ToLowerInvariant()}(\"{requirement.Property}\")";
                }
                else
                {
                    yield return $"\t {requirement.Type.ToString().ToLowerInvariant()}(\"{requirement.Property}\", \"{requirement.Value}\")";   
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
            yield return $"\t name = \"{pauseStr}Build and push {name}\"";
            yield return $"\t {_buildNumberPattern}";
            yield return $"\t description  = \"{description}\"";

            if (!onPause)
            {
                yield return "\t vcs {";
                yield return "\t\t root(TeamCityDockerImagesRepo)";
                yield return "\t }";


                yield return "\n \t steps {";

                // "docker pull" command types @ steps { ... } block
                foreach (var command in references.SelectMany(refer => CreatePullCommand(refer.RepoTag, refer.RepoTag)))
                {
                    yield return $"\t\t{command}";
                }

                // "docker build" command types @ steps { ... } block
                foreach (var command in images.SelectMany(image => CreatePrepareContextCommand(image).Concat(CreateBuildCommand(image))))
                {
                    yield return $"\t\t{command}";
                }

                // "docker image tag" command types @ steps { ... } block
                foreach (var image in images)
                {
                    if (image.File.Tags.Any())
                    {
                        var tag = image.File.Tags.First();

                        // "tag" command
                        foreach (var tagCommand in CreateTagCommand($"{image.File.ImageId}:{tag}", $"{BuildRepositoryName}{image.File.ImageId}{BuildImagePostfix}:{tag}", $"{image.File.ImageId}:{tag}"))
                        {
                            yield return $"\t\t{tagCommand}";
                        }
                    }
                }

                // "docker push" command types @ steps { ... } block
                foreach (var image in images)
                {
                    var tag = image.File.Tags.First();
                    foreach (var pushCommand in CreatePushCommand($"{BuildRepositoryName}{image.File.ImageId}{BuildImagePostfix}", $"{image.File.ImageId}:{tag}", tag))
                    {
                        yield return $"\t\t{pushCommand}";
                    }
                }

                // end of steps {...} block
                yield return "\t}";

                yield return "\tfeatures {";

                if (weight > 0)
                {
                    foreach (var feature in CreateFreeDiskSpaceFeature(weight))
                    {
                        yield return $"\t\t{feature}";
                    }
                }

                foreach (var feature in CreateDockerFeature())
                {
                    yield return $"\t\t{feature}";
                }

                // ReSharper disable once StringLiteralTypo
                foreach (var feature in CreateSwabraFeature())
                {
                    yield return $"\t\t{feature}";
                }

                // end of features { ... } block
                yield return "\t}";

                foreach (var dependencies in CreateArtifactsDependencies())
                {
                    yield return $"\t{dependencies}";
                }

                foreach (var param in CreateSpaceParams(weight))
                {
                    yield return $"\t{param}";
                }
                
                var requirements = images.SelectMany(i => i.File.Requirements).Distinct().ToList();
                foreach (var lines in CreateDockerRequirements(requirements))
                {
                    yield return $"\t{lines}";
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
                yield return $"\t param(\"system.teamcity.agent.ensure.free.space\", \"{weight}gb\")";
                yield return "}";
            }
        }

        private IEnumerable<string> CreateSnapshotDependencies(IEnumerable<string> dependencies, bool? onStaging)
        {
            yield return "\t dependencies {";
            if (onStaging != null)
            {
                yield return $"\t\t snapshot(AbsoluteId(\"{_options.TeamCityBuildConfigurationId}\"))" + " {\n";
                if (onStaging == true)
                {
                    yield return "\t\t\t onDependencyFailure = FailureAction.FAIL_TO_START \n \t\t\t reuseBuilds = ReuseBuilds.ANY \n \t\t\t synchronizeRevisions = false \n \t\t }";
                }
                else
                {
                    yield return "\t\t\t reuseBuilds = ReuseBuilds.ANY \n \t\t\t onDependencyFailure = FailureAction.IGNORE \n\t }";
                }
            }

            foreach (var buildTypeId in dependencies.OrderBy(i => i))
            {
                yield return $"\t\t snapshot({NormalizeFileName(buildTypeId)}.{buildTypeId})" + " {\n";
                yield return "\t\t\t onDependencyFailure =  FailureAction.FAIL_TO_START \n \t\t }";
            }

            yield return "\t }";
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
            yield return $"\t dependency(AbsoluteId(\"{_options.TeamCityBuildConfigurationId}\")) {{";
            
            yield return "\t\t snapshot {";
            yield return "\t\t\t onDependencyFailure = FailureAction.IGNORE";
            yield return "\t\t\t reuseBuilds = ReuseBuilds.ANY";
            yield return "\t\t }";

            yield return "\t\t artifacts {";
            yield return $"\t\t\t artifactRules = \"TeamCity.zip!/**=>{_pathService.Normalize(_options.ContextPath)}/TeamCity\"";
            yield return "\t\t }";
            yield return "\t }";
            yield return "}";
        }

        /// <summary>
        /// Creates failure conditions that terminated the build if an error message with given pattern had occurred.
        /// </summary>
        /// <param name="pattern">Error pattern.</param>
        /// <param name="reportOnlyFirstMatch">Indicates if the steps must continue execution even if after failure.</param> 
        /// <returns></returns>
        private IEnumerable<string> CreateFailureConditionRegExpPattern(string pattern, bool reportOnlyFirstMatch = false) {
            if (pattern == null) {
                yield break;
            }

            yield return "failureConditions {";

            yield return "\t failOnText {";
            yield return $"\t\t conditionType = {TeamCityConstants.Conditions.REGEXP}";
            yield return $"\t\t pattern = \"{pattern}\"";
            yield return "\t\t // allows the steps to continue running even in case of one problem";
            yield return $"\t\t reportOnlyFirstMatch = {reportOnlyFirstMatch.ToString().ToLower()}";
            // end of "failOnText{...}
            yield return "\t }";

            // end of failureConditions {...}
            yield return "}";
        }

        // ReSharper disable once IdentifierTypo
        private static IEnumerable<string> CreateSwabraFeature()
        {
            // ReSharper disable once StringLiteralTypo
            yield return "swabra {";
            yield return "\t forceCleanCheckout = true";
            yield return "}";
        }

        private IEnumerable<string> CreateDockerFeature()
        {
            if (string.IsNullOrWhiteSpace(_options.TeamCityDockerRegistryId))
            {
                yield break;
            }

            yield return "dockerSupport {";
            yield return "\t cleanupPushedImages = false";
            yield return "\t loginToRegistry = on {";
            yield return $"\t\t dockerRegistryId = \"{_options.TeamCityDockerRegistryId}\"";
            yield return "\t }";
            yield return "}";
        }

        private static IEnumerable<string> CreateFreeDiskSpaceFeature(int weight)
        {
            yield return "freeDiskSpace {";
            yield return $"\t requiredSpace = \"{weight}gb\"";
            yield return "\t failBuild = true";
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
            yield return $"\t {GetDockerStepStatusDsl(name)}";

            yield return $"\t name = \"push {name}\"";
            yield return "\t commandType = push {";

            yield return "\t\t namesAndTags = \"\"\"";
            foreach (var tag in tags)
            {
                yield return $"{imageId}:{tag}";
            }

            yield return "\"\"\".trimIndent()";
            
            yield return "\t\t removeImageAfterPush = false";

            yield return "\t }";
            yield return "}";

            yield return string.Empty;
        }

        /// <summary>
        /// Constructs Kotlin DSL's dockerCommand {...} for image re-tag.
        /// </summary>
        /// <param name="repoTag">original Docker image tag</param>
        /// <param name="newRepoTag"> target Docker image tag</param>
        /// <param name="name">step name</param>
        private IEnumerable<string> CreateTagCommand(string repoTag, string newRepoTag, string name)
        {
            yield return "dockerCommand {";
            yield return $"\t{GetDockerStepStatusDsl(repoTag)}";

            yield return $"\t name = \"tag {name}\"";
            yield return "\t commandType = other {";

            yield return "\t\t subCommand = \"tag\"";
            yield return $"\t\t commandArgs = \"{repoTag} {newRepoTag}\"";

            yield return "\t}";
            yield return "}";

            yield return string.Empty;
        }

        /// <summary>
        /// Constructs Kotlin DSL's step for preparation to dockerCommand {...}, such as ...
        /// ... the creation of .dockerignore, append of the entries into it.
        /// </summary>
        /// <param name="image">info about Docker image</param>
        private IEnumerable<string> CreatePrepareContextCommand(Image image)
        {
            var tag = image.File.Tags.First();
            yield return "script {";
            yield return $"\t{GetDockerStepStatusDsl(tag)}";

            yield return $"\t name = \"context {image.File.ImageId}:{tag}\"";
            yield return "\t scriptContent = \"\"\"";

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
            yield return $"{GetDockerStepStatusDsl(tag)}";

            yield return $"\t name = \"build {image.File.ImageId}:{tag}\"";
            yield return "\t commandType = build {";
            
            yield return "\t\t source = file {";
            yield return $"\t\t\t path = \"\"\"{_pathService.Normalize(Path.Combine(_options.TargetPath, image.File.Path, "Dockerfile"))}\"\"\"";
            yield return "\t\t }";

            yield return $"\t contextDir = \"{_pathService.Normalize(_options.ContextPath)}\"";
            yield return "\t commandArgs = \"--no-cache\"";
            
            yield return "\t namesAndTags = \"\"\"";
            yield return $"{image.File.ImageId}:{tag}";
            yield return "\"\"\".trimIndent()";

            yield return "}";
            yield return $"param(\"dockerImage.platform\", \"{image.File.Platform}\")";
            yield return "}";

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
            yield return $"\t {GetDockerStepStatusDsl(repoTag)}";

            yield return $"\t name = \"pull {name}\"";
            yield return "\t commandType = other {";

            yield return $"\t\t subCommand = \"pull\"";
            yield return $"\t\t commandArgs = \"{repoTag}\"";

            yield return "\t }";
            yield return "}";

            yield return string.Empty;
        }

        private IEnumerable<string> CreateDockerCommand(string name, string command, IEnumerable<string> args)
        {
            yield return "dockerCommand {";
            yield return $"\t name = \"{name}\"";
            yield return "\t commandType = other {";
            yield return $"\t\t subCommand = \"{command}\"";
            yield return $"\t\t commandArgs = \"{string.Join(" ", args)}\"";
            yield return "\t }";
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
            yield return $"\t name = \"{name}\"";
            yield return $"\t scriptContent = \"{script}\"";
            yield return "}";
        }

        /// <summary>
        /// Constructs Kotlin DSL instructions responsible for the determination of status for ...
        /// ... dockerCommand { ... } build configuration step.
        /// </summary>
        /// <param name="imageId">Docker Image ID</param>
        private static string GetDockerStepStatusDsl(string imageId) {
            // -- ARM images are currently not supported
            bool isArmImage = imageId.IndexOf("arm", StringComparison.OrdinalIgnoreCase) >= 0;
                        Console.WriteLine(imageId);

            return (!isArmImage || IsArmBasedImageBuildEnabled)
                    ? string.Empty
                    : "// ARM-based images are currently not supported by TeamCity \n"
                        + "\t\t\tenabled = false";
        }
    }
}
