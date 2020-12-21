namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using Generic;
    using IoC;
    using Model;

    // ReSharper disable once ClassNeverInstantiated.Global
    internal class ScriptsGenerator : IGenerator
    {
        private static readonly string WindowsEndOfLine = System.Text.Encoding.UTF8.GetString(new byte[] { 0x0d, 0x0a });
        private static readonly string LinuxEndOfLine = System.Text.Encoding.UTF8.GetString(new byte[] { 0x0a});
        
        [NotNull] private readonly IGenerateOptions _options;
        [NotNull] private readonly IPathService _pathService;
        [NotNull] private readonly IScriptGenerator _scriptGenerator;

        public ScriptsGenerator(
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
            var groups = (
                    from node in graph.Nodes
                    let image = node.Value as Image
                    where image != null
                    group node by image.File.ImageId
                    into groupsByImageId
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
                foreach (var groupByFile in groupByImageId)
                {
                    foreach (var node in groupByFile)
                    {
                        var image = (Image)node.Value;
                        switch (image.File.Platform.ToLowerInvariant())
                        {
                            case "windows":
                                AddScriptNode(graph, node, WindowsEndOfLine, string.Empty, ".cmd");
                                break;

                            case "linux":
                                AddScriptNode(graph, node, LinuxEndOfLine, "#!/bin/bash", ".sh");
                                AddScriptNode(graph, node, WindowsEndOfLine, string.Empty, ".cmd");
                                break;
                        }
                    }
                }
            }
        }

        private void AddScriptNode(IGraph<IArtifact, Dependency> graph, INode<IArtifact> node, string newLine, string header, string extension)
        {
            var image = (Image) node.Value;
            var lines = new List<string>();
            var scriptFileName = $"{image.File.ImageId}-{image.File.Tags.First()}{extension}";
            var root = string.Join("/", Enumerable.Repeat("..", _options.TargetPath.Split('\\', '/').Length));
            if (!string.IsNullOrWhiteSpace(header))
            {
                lines.Add(header);
            }

            lines.Add($"cd {root}");
            lines.AddRange(_scriptGenerator.GenerateScript(graph, node, _ => true));
            var scriptFilePath = _pathService.Normalize(Path.Combine(_options.TargetPath, scriptFileName));
            graph.TryAddNode(new FileArtifact(scriptFilePath, new[] {string.Join(newLine, lines)}), out var _);
        }
    }
}