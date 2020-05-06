using System;
using System.Collections.Generic;
using IoC;

namespace TeamCity.Docker.Generic
{
    internal interface IGraph<TNode, TLink>
    {
        [NotNull] IEnumerable<INode<TNode>> Nodes { get; }

        int NodesCount { get; }

        [NotNull] IEnumerable<ILink<TNode, TLink>> Links { get; }

        int LinksCount { get; }

        bool TryAddNode([NotNull] TNode value, out INode<TNode> node);

        bool TryRemoveNode([NotNull] INode<TNode> node);

        bool TryAddLink([NotNull] INode<TNode> from, [NotNull] TLink value, [NotNull] INode<TNode> to, out ILink<TNode, TLink> link);

        bool TryRemoveLink([NotNull] ILink<TNode, TLink> link);

        IGraph<TNode, TLink> Copy(Predicate<INode<TNode>> filter);
    }
}
