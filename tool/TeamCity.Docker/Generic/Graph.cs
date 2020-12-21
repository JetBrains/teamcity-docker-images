namespace TeamCity.Docker.Generic
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using IoC;

    internal class Graph<TNode, TLink> : IGraph<TNode, TLink>
    {
        [NotNull] private readonly IEqualityComparer<TNode> _nodeComparer;
        [NotNull] private readonly IEqualityComparer<TLink> _linkComparer;
        [NotNull] private readonly HashSet<INode<TNode>> _nodes;
        [NotNull] private readonly HashSet<ILink<TNode, TLink>> _links;

        public Graph()
            :this(EqualityComparer<TNode>.Default, EqualityComparer<TLink>.Default)
        { }

        // ReSharper disable once MemberCanBePrivate.Global
        public Graph(
            [NotNull] IEqualityComparer<TNode> nodeComparer,
            [NotNull] IEqualityComparer<TLink> linkComparer)
        {
            _nodeComparer = nodeComparer ?? throw new ArgumentNullException(nameof(nodeComparer));
            _linkComparer = linkComparer ?? throw new ArgumentNullException(nameof(linkComparer));
            _nodes = new HashSet<INode<TNode>>();
            _links = new HashSet<ILink<TNode, TLink>>();
        }

        public IEnumerable<INode<TNode>> Nodes => _nodes;

        public int NodesCount => _nodes.Count;

        public IEnumerable<ILink<TNode, TLink>> Links => _links;

        public int LinksCount => _links.Count;
        
        public bool TryAddNode(TNode value, out INode<TNode> node)
        {
            if (value == null) throw new ArgumentNullException(nameof(value));
            var newNode = new Node(value, _nodeComparer);
            return TryAddNode(newNode, out node);
        }

        public bool TryRemoveNode(INode<TNode> node)
        {
            if (node == null)  throw new ArgumentNullException(nameof(node));
            return _nodes.Remove(node);
        }

        public bool TryAddLink(INode<TNode> from, TLink value, INode<TNode> to, out ILink<TNode, TLink> link)
        {
            if (from == null) throw new ArgumentNullException(nameof(from));
            if (value == null) throw new ArgumentNullException(nameof(value));
            if (to == null) throw new ArgumentNullException(nameof(to));

            TryAddNode(from, out from);
            TryAddNode(to, out to);
            var newLink = link = new Link(from, value, to, _linkComparer);
            var success = _links.Add(newLink);
            if (!success)
            {
                link = _links.Single(i => i.Equals(newLink));
            }

            return success;
        }

        public bool TryRemoveLink(ILink<TNode, TLink> link)
        {
            if (link == null) throw new ArgumentNullException(nameof(link));
            return _links.Remove(link);
        }

        public IGraph<TNode, TLink> Copy(Predicate<INode<TNode>> filter)
        {
            var clone = new Graph<TNode, TLink>(_nodeComparer, _linkComparer);
            var newNodes = Nodes
                .Where(node => filter(node))
                .Select(node =>
                {
                    clone.TryAddNode(node.Value, out var newNode);
                    return new { node, newNode};
                })
                .ToDictionary(i => i.node, i => i.newNode);

            foreach (var link in Links)
            {
                if (newNodes.TryGetValue(link.From, out _) && newNodes.TryGetValue(link.To, out _))
                {
                    clone.TryAddLink(newNodes[link.From], link.Value, newNodes[link.To], out var _);
                }
            }

            return clone;
        }

        public override string ToString()
        {
            var dict = new Dictionary<INode<TNode>, int>();
            var sb = new StringBuilder();
            sb.AppendLine("digraph g {");
            foreach (var node in _nodes)
            {
                sb.AppendLine($"{GetId(dict, node)} [label=\"{node.Value}\"];");
            }

            foreach (var link in _links)
            {
                sb.AppendLine($"{GetId(dict, link.From)} -> {GetId(dict, link.To)};");
            }

            sb.AppendLine("}");
            return sb.ToString();
        }

        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj))
            {
                return false;
            }

            if (ReferenceEquals(this, obj))
            {
                return true;
            }

            if (obj.GetType() != GetType())
            {
                return false;
            }

            var other = (Graph<TNode, TLink>) obj;
            return _nodes.SetEquals(other._nodes) && _links.SetEquals(other._links);
        }

        public override int GetHashCode()
        {
            unchecked
            {
                return _nodes.Sum(node => node.GetHashCode()) + _links.Sum(link => link.GetHashCode());
            }
        }

        private bool TryAddNode(INode<TNode> newNode, out INode<TNode> node)
        {
            var success = _nodes.Add(newNode);
            node = success ? newNode : _nodes.Single(i => i.Equals(newNode));
            return success;
        }

        private static int GetId(IDictionary<INode<TNode>, int> dictionary, INode<TNode> node)
        {
            if(dictionary.TryGetValue(node, out var id))
            {
                return id;
            }

            id = dictionary.Any() ? dictionary.Values.Max() + 1 : 0;
            dictionary.Add(node, id);
            return id;
        }

        private class Node: INode<TNode>
        {
            [NotNull] private readonly IEqualityComparer<TNode> _nodeComparer;

            public Node(
                TNode value,
                [NotNull] IEqualityComparer<TNode> nodeComparer)
            {
                if (value == null)
                {
                    throw new ArgumentNullException(nameof(value));
                }

                _nodeComparer = nodeComparer ?? throw new ArgumentNullException(nameof(nodeComparer));
                Value = value;
            }

            public TNode Value { get; }

            public override bool Equals(object obj)
            {
                if (ReferenceEquals(null, obj))
                {
                    return false;
                }

                if (ReferenceEquals(this, obj))
                {
                    return true;
                }

                if (obj.GetType() != GetType())
                {
                    return false;
                }

                var other = (Node) obj;
                return _nodeComparer.Equals(Value, other.Value);
            }

            public override int GetHashCode() => _nodeComparer.GetHashCode(Value);

            public override string ToString() => Value.ToString();
        }
        private class Link: ILink<TNode, TLink>
        {
            private readonly IEqualityComparer<TLink> _linkComparer;

            public Link(
                [NotNull] INode<TNode> from,
                [NotNull] TLink value,
                [NotNull] INode<TNode> to,
                [NotNull] IEqualityComparer<TLink> linkComparer)
            {
                if (value == null)
                {
                    throw new ArgumentNullException(nameof(value));
                }

                _linkComparer = linkComparer ?? throw new ArgumentNullException(nameof(linkComparer));
                From = from ?? throw new ArgumentNullException(nameof(from));
                To = to ?? throw new ArgumentNullException(nameof(to));
                Value = value;
            }

            public INode<TNode> From { get; }

            public TLink Value { get; }

            public INode<TNode> To { get; }

            public override bool Equals(object obj)
            {
                if (ReferenceEquals(null, obj))
                {
                    return false;
                }

                if (ReferenceEquals(this, obj))
                {
                    return true;
                }

                if (obj.GetType() != GetType())
                {
                    return false;
                }

                var other = (Link) obj;
                return From.Equals(other.From) && _linkComparer.Equals(Value, other.Value) && To.Equals(other.To);
            }

            public override int GetHashCode()
            {
                unchecked
                {
                    var hashCode = From.GetHashCode();
                    hashCode = (hashCode * 397) ^ _linkComparer.GetHashCode(Value);
                    hashCode = (hashCode * 397) ^ To.GetHashCode();
                    return hashCode;
                }
            }

            public override string ToString() => $"{From} -- {Value} --> {To}";
        }
    }
}