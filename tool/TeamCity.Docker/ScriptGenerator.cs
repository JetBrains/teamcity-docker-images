using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using IoC;
using TeamCity.Docker.Generic;
using TeamCity.Docker.Model;

namespace TeamCity.Docker
{
    internal class ScriptGenerator: IScriptGenerator
    {
        [NotNull] private readonly IGenerateOptions _options;
        [NotNull] private readonly IPathService _pathService;
        [NotNull] private readonly IBuildPathProvider _buildPathProvider;

        public ScriptGenerator(
            [NotNull] IGenerateOptions options,
            [NotNull] IPathService pathService,
            [NotNull] IBuildPathProvider buildPathProvider)
        {
            _options = options ?? throw new ArgumentNullException(nameof(options));
            _pathService = pathService ?? throw new ArgumentNullException(nameof(pathService));
            _buildPathProvider = buildPathProvider ?? throw new ArgumentNullException(nameof(buildPathProvider));
        }

        public IEnumerable<string> GenerateScript(IGraph<IArtifact, Dependency> graph, INode<IArtifact> node, Func<IArtifact, bool> artifactSelector)
        {
            var artifacts = _buildPathProvider.GetPath(graph, node).Select(i => i.Value).ToList();
            var images = artifacts.OfType<Image>().ToList();

            if (images.Any())
            {
                foreach (var reference in artifacts.OfType<Reference>())
                {
                    if (artifactSelector(reference))
                    {
                        yield return $"docker pull {reference.RepoTag}";
                    }
                }

                var dockerignore = Path.Combine(_options.ContextPath, ".dockerignore").Replace("\\", "/");
                var ignores = new HashSet<string>(StringComparer.InvariantCultureIgnoreCase);
                var isFirst = true;
                foreach (var image in images)
                {
                    if (artifactSelector(image))
                    {
                        if (ignores.Except(image.File.Ignores).Any())
                        {
                            yield return $"echo 2> {dockerignore}";
                            ignores.Clear();
                            isFirst = false;
                        }

                        foreach (var ignore in image.File.Ignores.Except(ignores))
                        {
                            var redirection = isFirst ? ">" : ">>";
                            isFirst = false;
                            yield return $"echo {ignore} {redirection} {dockerignore}";
                            ignores.Add(ignore);
                        }

                        var dockerFilePath = _pathService.Normalize(Path.Combine(_options.TargetPath, image.File.Path, "Dockerfile"));
                        yield return $"docker build -f \"{dockerFilePath}\" -t {image.File.ImageId}:{image.File.Tags.First()} \"{_options.ContextPath}\"";
                    }
                }
            }
        }
    }
}
