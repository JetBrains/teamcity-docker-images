// ReSharper disable ClassNeverInstantiated.Global
namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Threading.Tasks;
    using Generic;
    using IoC;
    using Model;

    internal class GenerateCommand: ICommand<IGenerateOptions>
    {
        private readonly ILogger _logger;
        [NotNull] private readonly IFileSystem _fileSystem;
        [NotNull] private readonly IPathService _pathService;
        [NotNull] private readonly IGenerateOptions _options;
        [NotNull] private readonly IConfigurationExplorer _configurationExplorer;
        [NotNull] private readonly IFactory<IGraph<IArtifact, Dependency>, IEnumerable<Template>> _buildGraphFactory;
        [NotNull] private readonly IEnumerable<IGenerator> _generators;

        public GenerateCommand(
            [NotNull] ILogger logger,
            [NotNull] IFileSystem fileSystem,
            [NotNull] IPathService pathService,
            [NotNull] IGenerateOptions options,
            [NotNull] IConfigurationExplorer configurationExplorer,
            [NotNull] IFactory<IGraph<IArtifact, Dependency>, IEnumerable<Template>> buildGraphFactory,
            [NotNull] IEnumerable<IGenerator> generators)
        {
            _logger = logger ?? throw new ArgumentNullException(nameof(logger));
            _fileSystem = fileSystem ?? throw new ArgumentNullException(nameof(fileSystem));
            _pathService = pathService ?? throw new ArgumentNullException(nameof(pathService));
            _options = options ?? throw new ArgumentNullException(nameof(options));
            _configurationExplorer = configurationExplorer ?? throw new ArgumentNullException(nameof(configurationExplorer));
            _buildGraphFactory = buildGraphFactory ?? throw new ArgumentNullException(nameof(buildGraphFactory));
            
            // -- Kotlin DSL generators, Script Generators, README files generators
            _generators = generators ?? throw new ArgumentNullException(nameof(generators));
        }

        public Task<Result> Run()
        {
            var templates = _configurationExplorer.Explore(_options.SourcePath, _options.ConfigurationFiles);
            if (templates.State == Result.Error)
            {
                return Task.FromResult(Result.Error);
            }

            var graph = _buildGraphFactory.Create(templates.Value);
            if (graph.State == Result.Error)
            {
                return Task.FromResult(Result.Error);
            }

            using (_logger.CreateBlock("Generate"))
            {
                foreach (var generator in _generators)
                {
                    generator.Generate(graph.Value);
                }

                var dockerFiles = graph.Value.Nodes.Select(i => i.Value).OfType<GeneratedDockerfile>();
                foreach (var dockerfile in dockerFiles)
                {
                    var path = _pathService.Normalize(Path.Combine(_options.TargetPath, dockerfile.Path));
                    _logger.Log(path);
                    _fileSystem.WriteLines(path, dockerfile.Lines.Select(i => i.Text));
                }

                var artifacts = graph.Value.Nodes.Select(i => i.Value).OfType<FileArtifact>();
                foreach (var artifact in artifacts)
                {
                    _logger.Log(artifact.Path);
                    _fileSystem.WriteLines(artifact.Path, artifact.Lines);
                }
            }

            return Task.FromResult(graph.State);
        }
    }
}
