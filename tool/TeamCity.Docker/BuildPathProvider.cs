using System;
using System.Collections.Generic;
using System.Linq;
using IoC;
using TeamCity.Docker.Generic;
using TeamCity.Docker.Model;
// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    internal class BuildPathProvider : IBuildPathProvider
    {
        public IEnumerable<INode<IArtifact>> GetPath(IGraph<IArtifact, Dependency> buildGraph)
        {
            if (buildGraph == null) throw new ArgumentNullException(nameof(buildGraph));

            var path = new List<INode<IArtifact>>();
            var leaves = buildGraph.Nodes.Except(buildGraph.Links.Select(i => i.To)).ToList();
            foreach (var leaf in leaves)
            {
                path.AddRange(GetPathInternal(buildGraph, leaf));
            }

            path.Reverse();

            return path.Distinct();
        }

        public IEnumerable<INode<IArtifact>> GetPath(IGraph<IArtifact, Dependency> buildGraph, [NotNull] INode<IArtifact> leafNode)
        {
            if (buildGraph == null) throw new ArgumentNullException(nameof(buildGraph));
            if (leafNode == null) throw new ArgumentNullException(nameof(leafNode));

            return GetPathInternal(buildGraph, leafNode).Reverse().Distinct();
        }

        private static IEnumerable<INode<IArtifact>> GetPathInternal(IGraph<IArtifact, Dependency> graph, INode<IArtifact> leafNode)
        {
            yield return leafNode;

            var dependencies = (
                    from dependencyLink in graph.Links
                    where dependencyLink.From.Equals(leafNode)
                    select dependencyLink.To);

            var images =
                from dependency in dependencies
                let image = dependency.Value as Image
                orderby image?.File
                select new { dependency, image };

            var childNodes =
                from image in images
                from childNode in GetPathInternal(graph, image.dependency)
                select childNode;

            foreach (var childNode in childNodes)
            {
                yield return childNode;
            }
        }
    }
}
