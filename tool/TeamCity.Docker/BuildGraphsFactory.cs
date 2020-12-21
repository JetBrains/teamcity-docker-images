namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using Generic;
    using Model;

    // ReSharper disable once ClassNeverInstantiated.Global
    internal class BuildGraphsFactory : IFactory<IEnumerable<IGraph<IArtifact, Dependency>>, IGraph<IArtifact, Dependency>>
    {
        public Result<IEnumerable<IGraph<IArtifact, Dependency>>> Create(IGraph<IArtifact, Dependency> graph)
        {
            if (graph == null) throw new ArgumentNullException(nameof(graph));
            return new Result<IEnumerable<IGraph<IArtifact, Dependency>>>(CreateInternal(graph));
        }

        private IEnumerable<IGraph<IArtifact, Dependency>> CreateInternal(IGraph<IArtifact, Dependency> graph)
        {
            if (graph == null) throw new ArgumentNullException(nameof(graph));

            var buildGraph = graph.Copy(i => i.Value is Image || i.Value is Reference);
            var cutNodes = new HashSet<INode<IArtifact>>();
            while (true)
            {
                var bestCut = new HashSet<INode<IArtifact>>(buildGraph.FindMinimumCutByStoerWagner(links => links.Select(i => i.From.Value.Weight.Value).Concat(Enumerable.Repeat(0, 1)).Sum(), out var bestCost));
                if (bestCost > 0)
                {
                    yield return buildGraph.Copy(node => node.Value is Image || node.Value is Reference);
                    break;
                }

                if (bestCut.Count > buildGraph.NodesCount >> 1)
                {
                    bestCut = new HashSet<INode<IArtifact>>(graph.Nodes.Except(bestCut));
                }

                cutNodes.UnionWith(bestCut);
                yield return buildGraph.Copy(node => (node.Value is Image || node.Value is Reference) && bestCut.Contains(node));
                buildGraph = buildGraph.Copy(node => !cutNodes.Contains(node));
            }
        }
    }
}
