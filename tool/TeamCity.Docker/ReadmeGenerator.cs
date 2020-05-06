using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using IoC;
using TeamCity.Docker.Generic;
using TeamCity.Docker.Model;
// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    internal class ReadmeGenerator : IGenerator
    {
        private static readonly Dependency GenerateDependency = new Dependency(DependencyType.Generate);
        [NotNull] private readonly IGenerateOptions _options;
        [NotNull] private readonly IPathService _pathService;
        [NotNull] private readonly IBuildPathProvider _buildPathProvider;

        public ReadmeGenerator(
            [NotNull] IGenerateOptions options,
            [NotNull] IPathService pathService,
            [NotNull] IBuildPathProvider buildPathProvider)
        {
            _options = options ?? throw new ArgumentNullException(nameof(options));
            _pathService = pathService ?? throw new ArgumentNullException(nameof(pathService));
            _buildPathProvider = buildPathProvider ?? throw new ArgumentNullException(nameof(buildPathProvider));
        }

        public void Generate([NotNull] IGraph<IArtifact, Dependency> graph)
        {
            if (graph == null) throw new ArgumentNullException(nameof(graph));

            var groups =
                from node in graph.Nodes
                let image = node.Value as Image
                where image != null
                group node by image.File.ImageId
                into groupsByImageId
                from groupByImageId in
                    from groupByImageId in groupsByImageId
                    let image = groupByImageId.Value as Image
                    where image != null
                    let repoCount = image.File.Repositories.Count(i => !string.IsNullOrWhiteSpace(i))
                    orderby repoCount descending
                    group groupByImageId by image.File
                group groupByImageId by groupsByImageId.Key;

            foreach (var groupByImageId in groups)
            {
                var imageId = groupByImageId.Key;
                var lines = new List<string>();
                graph.TryAddNode(new FileArtifact(_pathService.Normalize(Path.Combine(_options.TargetPath, GetReadmeFilePath(imageId))), lines), out var readmeNode);
                var groupByImage = groupByImageId.ToList();

                lines.Add("### Tags");
                lines.Add(string.Empty);

                foreach (var groupByFile in groupByImage)
                {
                    var dockerFile = groupByFile.Key;
                    lines.Add($"- [{GetReadmeTagName(dockerFile)}](#whale-{GetTagLink(dockerFile)})");
                }

                lines.Add(string.Empty);

                foreach (var groupByFile in groupByImage)
                {
                    var dockerFile = groupByFile.Key;
                    lines.Add($"### :whale: {GetReadmeTagName(dockerFile)}");

                    lines.Add(string.Empty);
                    lines.Add($"[Dockerfile]({_pathService.Normalize(Path.Combine(dockerFile.Path, "Dockerfile"))})");

                    if (dockerFile.Comments.Any())
                    {
                        lines.Add(string.Empty);
                        foreach (var comment in dockerFile.Comments)
                        {
                            lines.Add(comment);
                        }
                    }

                    if (dockerFile.Repositories.Any(i => !string.IsNullOrWhiteSpace(i)))
                    {
                        lines.Add(string.Empty);
                        lines.Add("The docker image is available on:");
                        lines.Add(string.Empty);
                        foreach (var repo in dockerFile.Repositories.Where(i => !string.IsNullOrWhiteSpace(i)))
                        {
                            lines.Add($"- [{repo}{dockerFile.ImageId}]({repo}{dockerFile.ImageId})");
                        }
                    }
                    else
                    {
                        lines.Add("The docker image is not available and may be created manually.");
                    }

                    if (dockerFile.Components.Any())
                    {
                        lines.Add(string.Empty);
                        lines.Add("Installed components:");
                        lines.Add(string.Empty);
                        foreach (var component in dockerFile.Components)
                        {
                            lines.Add($"- {component}");
                        }
                    }

                    lines.Add(string.Empty);
                    lines.Add($"Container Platform: {dockerFile.Platform}");

                    foreach (var node in groupByFile)
                    {
                        var artifacts = _buildPathProvider.GetPath(graph, node).Select(i => i.Value).ToList();
                        var images = artifacts.OfType<Image>().ToList();
                        var weight = 0;

                        if (images.Any())
                        {
                            lines.Add(string.Empty);
                            lines.Add("Build commands:");
                            lines.Add(string.Empty);

                            lines.Add("```");
                            foreach (var image in images)
                            {
                                lines.Add(GenerateCommand(image));
                                weight += image.Weight.Value;
                            }

                            lines.Add("```");
                        }

                        var references = artifacts.OfType<Reference>();
                        if (references.Any())
                        {
                            lines.Add(string.Empty);
                            lines.Add("Base images:");
                            lines.Add(string.Empty);

                            lines.Add("```");
                            foreach (var reference in artifacts.OfType<Reference>())
                            {
                                lines.Add(GeneratePullCommand(reference));
                                weight += reference.Weight.Value;
                            }

                            lines.Add("```");
                        }

                        if (weight > 0)
                        {
                            lines.Add(string.Empty);
                            lines.Add($"_The required free space to generate image(s) is about **{weight} GB**._");
                        }
                    }

                    foreach (var node in groupByFile)
                    {
                        graph.TryAddLink(node, GenerateDependency, readmeNode, out _);
                    }
                }
            }
        }

        private static string GeneratePullCommand(Reference reference) => 
            $"docker pull {reference.RepoTag}";

        private string GenerateCommand(Image image)
        {
            var dockerFilePath = _pathService.Normalize(Path.Combine(_options.TargetPath, image.File.Path, "Dockerfile"));
            var tags = string.Join(" ", image.File.Tags.Select(tag => $"-t {image.File.ImageId}:{tag}"));
            return $"docker build -f \"{dockerFilePath}\" {tags} \"{_options.ContextPath}\"";
        }

        private static string GetReadmeFilePath(string imageId) =>
            imageId + ".md";

        private static string GetTagLink(Dockerfile dockerFile) =>
            GetReadmeTagName(dockerFile).Replace(".", string.Empty).Replace(" ", "-");

        private static string GetReadmeTagName(Dockerfile dockerFile) =>
            string.Join(" or ", dockerFile.Tags);
    }
}
