// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Text.RegularExpressions;
    using System.Threading;
    using System.Threading.Tasks;
    using Generic;
    using global::Docker.DotNet;
    using global::Docker.DotNet.Models;
    using IoC;
    using Model;

    internal class BuildCommand: ICommand<IBuildOptions>
    {
        [NotNull] private readonly ILogger _logger;
        [NotNull] private readonly IMessageLogger _messageLogger;
        [NotNull] private readonly CancellationTokenSource _cancellationTokenSource;
        [NotNull] private readonly IFileSystem _fileSystem;
        [NotNull] private readonly IPathService _pathService;
        [NotNull] private readonly IBuildOptions _options;
        [NotNull] private readonly IConfigurationExplorer _configurationExplorer;
        [NotNull] private readonly IFactory<IGraph<IArtifact, Dependency>, IEnumerable<Template>> _buildGraphFactory;
        [NotNull] private readonly IFactory<IEnumerable<IGraph<IArtifact, Dependency>>, IGraph<IArtifact, Dependency>> _buildGraphsFactory;
        [NotNull] private readonly IFactory<NodesDescription, IEnumerable<INode<IArtifact>>> _nodesDescriptionFactory;
        [NotNull] private readonly IBuildPathProvider _buildPathProvider;
        [NotNull] private readonly IContextFactory _contextFactory;
        [NotNull] private readonly IStreamService _streamService;
        [NotNull] private readonly IDockerClient _dockerClient;

        public BuildCommand(
            [NotNull] ILogger logger,
            [NotNull] IMessageLogger messageLogger,
            [NotNull] CancellationTokenSource cancellationTokenSource,
            [NotNull] IFileSystem fileSystem,
            [NotNull] IPathService pathService,
            [NotNull] IBuildOptions options,
            [NotNull] IConfigurationExplorer configurationExplorer,
            [NotNull] IFactory<IGraph<IArtifact, Dependency>, IEnumerable<Template>> buildGraphFactory,
            [NotNull] IFactory<IEnumerable<IGraph<IArtifact, Dependency>>, IGraph<IArtifact, Dependency>> buildGraphsFactory,
            [NotNull] IFactory<NodesDescription, IEnumerable<INode<IArtifact>>> nodesDescriptionFactory,
            [NotNull] IBuildPathProvider buildPathProvider,
            [NotNull] IContextFactory contextFactory,
            [NotNull] IStreamService streamService,
            [NotNull] IDockerClient dockerClient)
        {
            _logger = logger ?? throw new ArgumentNullException(nameof(logger));
            _messageLogger = messageLogger ?? throw new ArgumentNullException(nameof(messageLogger));
            _cancellationTokenSource = cancellationTokenSource ?? throw new ArgumentNullException(nameof(cancellationTokenSource));
            _fileSystem = fileSystem ?? throw new ArgumentNullException(nameof(fileSystem));
            _pathService = pathService ?? throw new ArgumentNullException(nameof(pathService));
            _options = options ?? throw new ArgumentNullException(nameof(options));
            _configurationExplorer = configurationExplorer ?? throw new ArgumentNullException(nameof(configurationExplorer));
            _buildGraphFactory = buildGraphFactory ?? throw new ArgumentNullException(nameof(buildGraphFactory));
            _buildGraphsFactory = buildGraphsFactory ?? throw new ArgumentNullException(nameof(buildGraphsFactory));
            _nodesDescriptionFactory = nodesDescriptionFactory ?? throw new ArgumentNullException(nameof(nodesDescriptionFactory));
            _buildPathProvider = buildPathProvider ?? throw new ArgumentNullException(nameof(buildPathProvider));
            _contextFactory = contextFactory ?? throw new ArgumentNullException(nameof(contextFactory));
            _streamService = streamService ?? throw new ArgumentNullException(nameof(streamService));
            _dockerClient = dockerClient ?? throw new ArgumentNullException(nameof(dockerClient));
        }

        public async Task<Result> Run()
        {
            var templates = _configurationExplorer.Explore(_options.SourcePath, _options.ConfigurationFiles);
            if (templates.State == Result.Error)
            {
                return Result.Error;
            }

            var buildGraphResult = _buildGraphFactory.Create(templates.Value);
            if (buildGraphResult.State == Result.Error)
            {
                return Result.Error;
            }

            var buildGraphsResult = _buildGraphsFactory.Create(buildGraphResult.Value);
            if (buildGraphsResult.State == Result.Error)
            {
                return Result.Error;
            }

            var buildGraphs = buildGraphsResult.Value.ToList();
            if (!buildGraphs.Any())
            {
                _logger.Log("Nothing to build.", Result.Error);
                return Result.Error;
            }

            var dockerFilesRootPath = _fileSystem.UniqueName;
            var contextStreamResult = await _contextFactory.Create(dockerFilesRootPath, buildGraphResult.Value.Nodes.Select(i => i.Value).OfType<Image>().Select(i => i.File));
            if (contextStreamResult.State == Result.Error)
            {
                return Result.Error;
            }

            var contextStream = contextStreamResult.Value;
            using (contextStream)
            using (_logger.CreateBlock("Build"))
            {
                foreach (var buildGraph in buildGraphs)
                {
                    var nameResult = _nodesDescriptionFactory.Create(buildGraph.Nodes);
                    var name = nameResult.State != Result.Error ? nameResult.Value.Name : "Unnamed graph";
                    if (!string.IsNullOrWhiteSpace(_options.FilterRegex) && !new Regex(_options.FilterRegex).IsMatch(name))
                    {
                        _logger.Log($"\"{name}\" was skipped according to filter \"{_options.FilterRegex}\".", Result.Warning);
                        continue;
                    }

                    using (_logger.CreateBlock(name))
                    {
                        var buildPath = _buildPathProvider.GetPath(buildGraph).ToList();
                        foreach (var buildNode in buildPath)
                        {
                            switch (buildNode.Value)
                            {
                                case Image image:
                                    var dockerFile = image.File;
                                    using (_logger.CreateBlock(dockerFile.ToString()))
                                    {
                                        var id = Guid.NewGuid().ToString();
                                        var labels = new Dictionary<string, string> { { "InternalImageId", id } };

                                        var tags = (
                                            from tag in dockerFile.Tags
                                            select $"{dockerFile.ImageId}:{tag}")
                                            .Distinct()
                                            .ToList();

                                        contextStream.Position = 0;
                                        var dockerFilePathInContext = _pathService.Normalize(Path.Combine(dockerFilesRootPath, dockerFile.Path));
                                        var buildParameters = new ImageBuildParameters
                                        {
                                            Dockerfile = dockerFilePathInContext,
                                            Tags = tags,
                                            PullParent = true,
                                            Labels = labels
                                        };

                                        using (var buildEventStream = await _dockerClient.Images.BuildImageFromDockerfileAsync(
                                            contextStream,
                                            buildParameters,
                                            _cancellationTokenSource.Token))
                                        {
                                            _streamService.ProcessLines(buildEventStream, line => { _messageLogger.Log(line); });
                                        }

                                        var filter = new Dictionary<string, IDictionary<string, bool>>
                                        {
                                            {"label", labels.ToDictionary(i => $"{i.Key}={i.Value}", _ => true)}
                                        };

                                        var images = await _dockerClient.Images.ListImagesAsync(new ImagesListParameters { Filters = filter });
                                        if (images.Count == 0)
                                        {
                                            _logger.Log($"Error while building the image {dockerFile}", Result.Error);
                                            return Result.Error;
                                        }
                                    }

                                    break;
                            }
                        }
                    }
                }
            }

            return Result.Success;
        }
    }
}
