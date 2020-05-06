using System.Collections.Generic;
using System.Linq;
using Shouldly;
using TeamCity.Docker.Generic;
using Xunit;
// ReSharper disable PrivateFieldCanBeConvertedToLocalVariable

namespace TeamCity.Docker.Tests
{
    public class GraphExtensionsTests
    {
        private static readonly IEnumerable<ILink<int, string>> Empty = new ILink<int, string>[0];
        private readonly Graph<int, string> _graph;
        private readonly INode<int> _node0;
        private readonly INode<int> _node1;
        private readonly INode<int> _node2;
        private readonly INode<int> _node3;
        private readonly INode<int> _node4;
        private readonly ILink<int, string> _link01;
        private readonly ILink<int, string> _link04;
        private readonly ILink<int, string> _link12;
        private readonly ILink<int, string> _link13;
        private readonly ILink<int, string> _link14;
        private readonly ILink<int, string> _link23;
        private readonly ILink<int, string> _link34;

        public GraphExtensionsTests()
        {
            // https://neerc.ifmo.ru/wiki/index.php?title=%D0%9C%D0%B0%D1%82%D1%80%D0%B8%D1%86%D0%B0_%D1%81%D0%BC%D0%B5%D0%B6%D0%BD%D0%BE%D1%81%D1%82%D0%B8_%D0%B3%D1%80%D0%B0%D1%84%D0%B0
            _graph = new Graph<int, string>();
            _graph.TryAddNode(0, out _node0);
            _graph.TryAddNode(1, out _node1);
            _graph.TryAddNode(2, out _node2);
            _graph.TryAddNode(3, out _node3);
            _graph.TryAddNode(4, out _node4);

            _graph.TryAddLink(_node0, "0-1", _node1, out _link01);
            _graph.TryAddLink(_node0, "0-4", _node4, out _link04);

            _graph.TryAddLink(_node1, "1-2", _node2, out _link12);
            _graph.TryAddLink(_node1, "1-3", _node3, out _link13);
            _graph.TryAddLink(_node1, "1-4", _node4, out _link14);

            _graph.TryAddLink(_node2, "2-3", _node3, out _link23);

            _graph.TryAddLink(_node3, "3-4", _node4, out _link34);


        }

        [Fact]
        public void ShouldConvertToAdjacencyMatrix()
        {
            // Given
            _graph.TryAddLink(_node1, "1-0", _node0, out var link10);

            // When
            var matrix = _graph.ToAdjacencyMatrix();

            // Then
            matrix[0, 0].ShouldBe(Empty);
            matrix[0, 1].ShouldBe(new [] { _link01, link10 } );
            matrix[0, 2].ShouldBe(Empty);
            matrix[0, 3].ShouldBe(Empty);
            matrix[0, 4].ShouldBe(new[] { _link04 });

            matrix[1, 0].ShouldBe(new[] { _link01, link10 });
            matrix[1, 1].ShouldBe(Empty);
            matrix[1, 2].ShouldBe(new[] { _link12 });
            matrix[1, 3].ShouldBe(new[] { _link13 });
            matrix[1, 4].ShouldBe(new[] { _link14 });

            matrix[2, 0].ShouldBe(Empty);
            matrix[2, 1].ShouldBe(new[] { _link12 });
            matrix[2, 2].ShouldBe(Empty);
            matrix[2, 3].ShouldBe(new[] { _link23 });
            matrix[2, 4].ShouldBe(Empty);

            matrix[3, 0].ShouldBe(Empty);
            matrix[3, 1].ShouldBe(new[] { _link13 });
            matrix[3, 2].ShouldBe(new[] { _link23 });
            matrix[3, 3].ShouldBe(Empty);
            matrix[3, 4].ShouldBe(new[] { _link34 });

            matrix[4, 0].ShouldBe(new[] { _link04 });
            matrix[4, 1].ShouldBe(new[] { _link14 });
            matrix[4, 2].ShouldBe(Empty);
            matrix[4, 3].ShouldBe(new[] { _link34 });
            matrix[4, 4].ShouldBe(Empty);
        }

        [Fact]
        public void ShouldFindMinimumCutByStoerWagner()
        {
            // Given

            // When
            var bestToCut = _graph.FindMinimumCutByStoerWagner(i => i.Any() ? 1 : 0, out var bestCost);

            // Then
            bestCost.ShouldBe(2);
            bestToCut.ShouldBe(new [] { _node2 });
        }
    }
}
