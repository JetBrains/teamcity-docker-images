// ReSharper disable ClassNeverInstantiated.Global
namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using System.Collections.Immutable;
    using System.IO;
    using System.Linq;
    using System.Text.RegularExpressions;
    using Generic;
    using IoC;
    using Model;

    internal class DockerGraphFactory: IFactory<IGraph<IArtifact, Dependency>, IEnumerable<Template>>
    {
        private static readonly Regex ReferenceRegex = new Regex(@"^\s*(?<reference>.+?)(\s+(?<weight>\d+)|)$", RegexOptions.CultureInvariant | RegexOptions.IgnoreCase | RegexOptions.Compiled);
        private static readonly Dependency GenerateDependency = new Dependency(DependencyType.Generate);
        private const string CommentPrefix = "##";
        private const string IdPrefix = "# Id ";
        private const string TagPrefix = "# Tag ";
        private const string PlatformPrefix = "# Platform ";
        private const string BasedOnPrefix = "# Based on ";
        private const string ComponentsPrefix = "# Install ";
        private const string RepoPrefix = "# Repo ";
        private const string WeightPrefix = "# Weight ";
        private const string RequiresPrefix = "# Requires ";

        private readonly IContentParser _contentParser;
        private readonly IPathService _pathService;

        public DockerGraphFactory(
            [NotNull] IContentParser contentParser,
            [NotNull] IPathService pathService)
        {
            _contentParser = contentParser ?? throw new ArgumentNullException(nameof(contentParser));
            _pathService = pathService ?? throw new ArgumentNullException(nameof(pathService));
        }

        public Result<IGraph<IArtifact, Dependency>> Create(IEnumerable<Template> templates)
        {
            var graph = new Graph<IArtifact, Dependency>();
            var nodeDict = new Dictionary<string, INode<IArtifact>>();
            foreach (var template in templates)
            {
                foreach (var variant in template.Variants)
                {
                    var lines = _contentParser.Parse(template.Lines, variant.Variables).ToImmutableList();
                    var imageId = "unknown";
                    var tags = new List<string>();
                    var platform = string.Empty;
                    var references = new List<Reference>();
                    var components = new List<string>();
                    var repositories = new List<string>();
                    var comments = new List<string>();
                    var dockerfileLines = new List<Line>();
                    var weight = 0;
                    var requirements = new List<Requirement>();

                    foreach (var line in lines)
                    {
                        var isMetadata = false;
                        if (line.Type == LineType.Comment)
                        {
                            isMetadata =
                                TrySetByPrefix(line.Text, CommentPrefix, value => comments.Add(value.Trim())) ||
                                TrySetByPrefix(line.Text, IdPrefix, value => imageId = value) ||
                                TrySetByPrefix(line.Text, TagPrefix, value => tags.Add(value)) ||
                                TrySetByPrefix(line.Text, PlatformPrefix, value => platform = value) ||
                                TrySetByPrefix(line.Text, BasedOnPrefix, value =>
                                {
                                    var match = ReferenceRegex.Match(value);
                                    if (match.Success)
                                    {
                                        var weightValue = 0;
                                        if (int.TryParse(match.Groups["weight"].Value, out var refWeightValue))
                                        {
                                            weightValue = refWeightValue;
                                        }

                                        references.Add(new Reference(match.Groups["reference"].Value, new Weight(weightValue)));
                                    }
                                }) ||
                                TrySetByPrefix(line.Text, ComponentsPrefix, value => components.Add(value)) ||
                                TrySetByPrefix(line.Text, RepoPrefix, value => repositories.Add(value)) ||
                                TrySetByPrefix(line.Text, WeightPrefix, value =>
                                {
                                    if (int.TryParse(value, out var weightValue))
                                    {
                                        weight = weightValue;
                                    }
                                }) ||
                                TrySetByPrefix(line.Text, RequiresPrefix, expression =>
                                {
                                    var values = expression.Split(" ");
                                    if (values.Length >= 2 && Enum.TryParse<RequirementType>(values[1], true, out var requirementType))
                                    {
                                        requirements.Add(new Requirement(values[0], requirementType, values.Length > 2 ? values[2] : string.Empty));
                                    }
                                });
                        }

                        if (!isMetadata)
                        {
                            dockerfileLines.Add(line);
                        }
                    }

                    var dirName = Path.GetDirectoryName(variant.ConfigFile) ?? string.Empty;
                    var description = Path.GetFileName(dirName);
                    var dockerfile = new Dockerfile(
                        _pathService.Normalize(variant.BuildPath),
                        imageId,
                        platform,
                        description,
                        tags.Where(i => !string.IsNullOrWhiteSpace(i)).ToList(),
                        components.Where(i => !string.IsNullOrWhiteSpace(i)).ToList(),
                        repositories.Where(i => !string.IsNullOrWhiteSpace(i)).ToList(),
                        comments,
                        references,
                        new Weight(weight),
                        dockerfileLines,
                        template.Ignore,
                        requirements);

                    if (graph.TryAddNode(new Image(dockerfile), out var dockerImageNode))
                    {
                        foreach (var tag in tags)
                        {
                            nodeDict[$"{imageId}:{tag}"] = dockerImageNode;
                        }

                        if (graph.TryAddNode(new GeneratedDockerfile(_pathService.Normalize(Path.Combine(dockerfile.Path, "Dockerfile")), dockerfile.Lines), out var dockerfileNode))
                        {
                            graph.TryAddLink(dockerImageNode, GenerateDependency, dockerfileNode, out _);
                        }
                    }
                }
            }

            var imageNodes = 
                from node in graph.Nodes
                let image = node.Value as Image
                where image != null
                select new {node, image};

            // Add references
            foreach (var from in imageNodes.ToList())
            {
                foreach (var reference in from.image.File.References)
                {
                    if (nodeDict.TryGetValue(reference.RepoTag, out var toNode))
                    {
                        graph.TryAddLink(from.node, new Dependency(DependencyType.Build), toNode, out _);
                    }
                    else
                    {
                        if (graph.TryAddNode(reference, out var referenceNode))
                        {
                            nodeDict[reference.RepoTag] = referenceNode;
                        }

                        graph.TryAddLink(from.node, new Dependency(DependencyType.Pull), referenceNode, out _);
                    }
                }
            }

            return new Result<IGraph<IArtifact, Dependency>>(graph);
        }

        private static bool TrySetByPrefix([NotNull] string text, [NotNull] string prefix, Action<string> setter)
        {
            if (text == null)
            {
                throw new ArgumentNullException(nameof(text));
            }

            if (prefix == null)
            {
                throw new ArgumentNullException(nameof(prefix));
            }

            if (text.StartsWith(prefix))
            {
                setter(text.Substring(prefix.Length).Trim());
                return true;
            }

            return false;
        }
    }
}
