// ReSharper disable ClassNeverInstantiated.Global
namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Text.RegularExpressions;
    using Generic;
    using IoC;
    using Model;

    internal class ReadmeFilesGenerator : IGenerator
    {
        private static readonly Dependency GenerateDependency = new Dependency(DependencyType.Generate);
        [NotNull] private readonly IGenerateOptions _options;
        [NotNull] private readonly IPathService _pathService;
        [NotNull] private readonly IScriptGenerator _scriptGenerator;

        public ReadmeFilesGenerator(
            [NotNull] IGenerateOptions options,
            [NotNull] IPathService pathService,
            [NotNull] IScriptGenerator scriptGenerator)
        {
            _options = options ?? throw new ArgumentNullException(nameof(options));
            _pathService = pathService ?? throw new ArgumentNullException(nameof(pathService));
            _scriptGenerator = scriptGenerator ?? throw new ArgumentNullException(nameof(scriptGenerator));
        }

        public void Generate(IGraph<IArtifact, Dependency> graph)
        {
            if (graph == null) throw new ArgumentNullException(nameof(graph));

            var groups = (
                from node in graph.Nodes
                let image = node.Value as Image
                where image != null
                group node by image.File.ImageId
                into groupsByImageId
                orderby groupsByImageId.Key
                from groupByImageId in
                    from groupByImageId in groupsByImageId
                    let image = groupByImageId.Value as Image
                    where image != null
                    orderby image.File
                    group groupByImageId by image.File
                group groupByImageId by groupsByImageId.Key)
                .ToList();

            foreach (var groupByImageId in groups)
            {
                var imageId = groupByImageId.Key;
                var lines = new List<string>();
                graph.TryAddNode(new FileArtifact(GetReadmeFile(imageId), lines), out var readmeNode);
                var groupByImage = groupByImageId.ToList();

                lines.Add($"## {imageId} tags");
                lines.Add(string.Empty);

                var otherImages = groups.Where(i => i.Key != imageId).ToList();
                if (otherImages.Any())
                {
                    lines.Add("Other tags");
                    lines.Add(string.Empty);
                    foreach (var image in otherImages)
                    {
                        lines.Add($"- [{image.Key}]({GetReadmeFilePath(image.Key)})");
                    }
                }

                lines.Add(string.Empty);

                // ReSharper disable once IdentifierTypo
                var mutliArch = (
                    from grp in (
                        from image in groupByImage
                        where image.Key.Repositories.Any()
                        from tag in image.Key.Tags.Skip(1)
                        where tag.Length > 0 && char.IsLetterOrDigit(tag[0])
                        orderby tag descending
                        group image by tag)
                    orderby $"{grp.Count()} {grp.Key}" descending
                    select grp)
                    .ToList();

                if (mutliArch.Any())
                {
                    lines.Add("#### multi-architecture");
                    lines.Add(string.Empty);
                    lines.Add("When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.");
                    lines.Add(string.Empty);
                    foreach (var dockerfileByMultiArchTag in mutliArch)
                    {
                        lines.Add($"- [{dockerfileByMultiArchTag.Key}](#{Normalize(dockerfileByMultiArchTag.Key)})");
                    }

                    lines.Add(string.Empty);
                }

                var dockerfileGroups =
                    from image in groupByImage
                    orderby image.Key.Platform
                    group image by image.Key.Platform
                    into grp1
                    from grp2 in (
                        from image in grp1
                        orderby image.Key.Description descending
                        group image by image.Key.Description)
                    group grp2 by grp1.Key;

                foreach (var dockerfileByPlatform in dockerfileGroups)
                {
                    lines.Add($"#### {dockerfileByPlatform.Key}");
                    lines.Add(string.Empty);

                    foreach (var dockerfileByDescription in dockerfileByPlatform)
                    {
                        lines.Add($"- {dockerfileByDescription.Key}");
                        foreach (var dockerfile in dockerfileByDescription)
                        {
                            lines.Add($"  - [{GetReadmeTagName(dockerfile.Key)}](#{GetTagLink(dockerfile.Key)})");
                        }
                    }

                    lines.Add(string.Empty);
                }

                lines.Add(string.Empty);

                foreach (var dockerfileByMultiArchTag in mutliArch)
                {
                    lines.Add($"### {dockerfileByMultiArchTag.Key}");
                    lines.Add(string.Empty);
                    var platforms = string.Join(", ", dockerfileByMultiArchTag.Select(i => $"{i.Key.Platform} {i.Key.Description}").Distinct().OrderBy(i => i));
                    lines.Add($"Supported platforms: {platforms}");
                    lines.Add(string.Empty);
                    lines.Add("#### Content");
                    lines.Add(string.Empty);
                    foreach (var dockerfile in dockerfileByMultiArchTag)
                    {
                        lines.Add($"- [{GetReadmeTagName(dockerfile.Key)}](#{GetTagLink(dockerfile.Key)})");
                    }

                    lines.Add(string.Empty);
                }
                
                // Adding Dockerfile links
                lines.Add(string.Empty);
                lines.Add("# Dockerfile links\n");
                lines.Add(GetLinkForOs(groupByImage, "Linux"));
                lines.Add(GetLinkForOs(groupByImage, "Windows"));
                lines.Add(string.Empty);

                foreach (var groupByFile in groupByImage)
                {
                    var dockerFile = groupByFile.Key;
                    lines.Add($"### {GetReadmeTagName(dockerFile)}");

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
                    lines.Add($"Container platform: {dockerFile.Platform}");

                    foreach (var node in groupByFile)
                    {
                        var weight = 0;
                        var script = _scriptGenerator.GenerateScript(graph, node, artifact =>
                        {
                            weight += artifact.Weight.Value;
                            return true;
                        }).ToList();

                        if (weight > 0)
                        {
                            lines.Add(string.Empty);
                            lines.Add("Docker build commands:");
                            lines.Add(string.Empty);

                            lines.Add("```");
                            lines.AddRange(script);
                            lines.Add("```");
                            
                            if (weight > 0)
                            {
                                lines.Add(string.Empty);
                                lines.Add($"_The required free space to generate image(s) is about **{weight} GB**._");
                            }

                            lines.Add(string.Empty);
                        }
                    }

                    foreach (var node in groupByFile)
                    {
                        graph.TryAddLink(node, GenerateDependency, readmeNode, out _);
                    }
                }
            }
        }

        /// <summary>
        /// Returns documentation string indicating existing Dockerfile links.
        /// </summary>
        /// <param name="imageNodes">list containing grouping of target Docker Images (Dockerfile, name, ID, etc.)</param>
        /// <param name="osIdentifier">ID of the OS (Windows / Linux)</param>
        /// <returns>documentation line with the links to given Dockerfiles</returns>
        private string GetLinkForOs(List<IGrouping<Dockerfile, INode<IArtifact>>> imageNodes, string osIdentifier)
        {
            string urlPrefix = "https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/";
            string osLinks = string.Join(", ", imageNodes
                .Where(obj => _pathService.Normalize(Path.Combine(obj.Key.Path, "Dockerfile")).Contains(osIdentifier, StringComparison.OrdinalIgnoreCase))
                .Select(obj => $"[{obj.Key}]({urlPrefix}{_pathService.Normalize(Path.Combine(obj.Key.Path, "Dockerfile"))})"));
            return $"* **{osIdentifier}**. {osLinks}\n";
        }
        
        private string GetReadmeFile(string imageId)
        {
            return _pathService.Normalize(Path.Combine(_options.TargetPath, GetReadmeFilePath(imageId)));
        }

        private static string GetReadmeFilePath(string imageId) =>
            imageId + ".md";

        private string GetTagLink(Dockerfile dockerFile) =>
            Normalize(GetReadmeTagName(dockerFile));

        private string GetReadmeTagName(Dockerfile dockerFile) =>
            dockerFile.Tags.First();

        private static string Normalize(string text) =>
            text
                .Replace(",", string.Empty)
                .Replace(".", string.Empty)
                .Replace(" ", "-");
    }
}
