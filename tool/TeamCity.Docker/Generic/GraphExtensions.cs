namespace TeamCity.Docker.Generic
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using IoC;

    internal static class GraphExtensions
    {
        public static IEnumerable<ILink<TNode, TLink>>[,] ToAdjacencyMatrix<TNode, TLink>([NotNull] this IGraph<TNode, TLink> graph)
        {
            var indices = graph.Nodes.Select((node, index) => new { node, index }).ToDictionary(i => i.node, i => i.index);
            return ToAdjacencyMatrix(graph, node => indices[node]);
        }

        public static IEnumerable<ILink<TNode, TLink>>[,] ToAdjacencyMatrix<TNode, TLink>([NotNull] this IGraph<TNode, TLink> graph, Func<INode<TNode>, int> indexSelector)
        {
            if (graph == null) throw new ArgumentNullException(nameof(graph));

            var matrix = new ICollection<ILink<TNode, TLink>>[graph.NodesCount, graph.NodesCount];
            for (var i = 0; i < graph.NodesCount; i++)
            {
                for (var j = 0; j <= i; j++)
                {
                    matrix[i, j] = new List<ILink<TNode, TLink>>();
                }
            }

            for (var i = 0; i < graph.NodesCount; i++)
            {
                for (var j = i + 1; j < graph.NodesCount; j++)
                {
                    matrix[i, j] = matrix[j, i];
                }
            }

            foreach (var link in graph.Links)
            {
                var i = indexSelector(link.From);
                var j = indexSelector(link.To);
                matrix[i, j].Add(link);
            }

            var result = new IEnumerable<ILink<TNode, TLink>>[graph.NodesCount, graph.NodesCount];
            for (var i = 0; i < graph.NodesCount; i++)
            {
                for (var j = 0; j < graph.NodesCount; j++)
                {
                    result[i, j] = matrix[i, j];
                }
            }

            return result;
        }

        public static IEnumerable<INode<TNode>> FindMinimumCutByStoerWagner<TNode, TLink>([NotNull] this IGraph<TNode, TLink> graph, [NotNull] Func<IEnumerable<ILink<TNode, TLink>>, int> weightSelector, out int bestCost)
        {
            if (graph == null) throw new ArgumentNullException(nameof(graph));
            if (weightSelector == null) throw new ArgumentNullException(nameof(weightSelector));

            var nodes = graph.Nodes.Select((node, index) => new {node, index}).ToList();
            var indices = nodes.ToDictionary(i => i.node, i => i.index);
            var reverseIndices = nodes.ToDictionary(i => i.index, i => i.node);

            var nodesCount = graph.NodesCount;
            var matrix = graph.ToAdjacencyMatrix(node => indices[node]);
            var weightMatrix = new int[nodesCount, nodesCount];
            for (var i = 0; i < nodesCount; i++)
            {
                for (var j = 0; j < nodesCount; j++)
                {
                    weightMatrix[i, j] = weightSelector(matrix[i, j]);
                }
            }

            bestCost = int.MaxValue;
            var bestСut = new List<int>();
            var activeNodes = new List<int>[nodesCount];
            for (var i = 0; i < nodesCount; ++i)
            {
                activeNodes[i] = new List<int>(1) {i};
            }

            var weights = new int[nodesCount];
            var exist = new bool[nodesCount];
            var inActive = new bool[nodesCount];
            Array.Fill(exist, true);
            for (var ph = 0; ph < nodesCount - 1; ph++)
            {
                Array.Fill(inActive, false);
                Array.Fill(weights, 0);
                var previous = 0;
                for (var it = 0; it < nodesCount - ph; ++it)
                {
                    var selected = -1;
                    for (var i = 0; i < nodesCount; ++i)
                    {
                        if (exist[i] && !inActive[i] && (selected == -1 || weights[i] > weights[selected]))
                        {
                            selected = i;
                        }
                    }

                    if (it == nodesCount - ph - 1)
                    {
                        if (weights[selected] < bestCost)
                        {
                            bestCost = weights[selected];
                            bestСut = activeNodes[selected];
                        }

                        activeNodes[previous].AddRange(activeNodes[selected]);

                        for (var i = 0; i < nodesCount; ++i)
                        {
                            weightMatrix[i, previous] += weightMatrix[selected, i];
                            weightMatrix[previous, i] = weightMatrix[i, previous];
                        }

                        exist[selected] = false;
                    }
                    else
                    {
                        inActive[selected] = true;
                        for (var i = 0; i < nodesCount; ++i)
                        {
                            weights[i] += weightMatrix[selected, i];
                        }

                        previous = selected;
                    }
                }
            }

            return bestСut.Select(i => reverseIndices[i]);
        }
    }
}
