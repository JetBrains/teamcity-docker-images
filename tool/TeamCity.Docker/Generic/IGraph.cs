namespace TeamCity.Docker.Generic
{
    using System;
    using System.Collections.Generic;
    using System.Diagnostics.CodeAnalysis;

    [SuppressMessage("ReSharper", "UnusedMember.Global")]
    [SuppressMessage("ReSharper", "UnusedMethodReturnValue.Global")]
    internal interface IGraph<TNode, TLink>
    {
        [IoC.NotNull] IEnumerable<INode<TNode>> Nodes { get; }

        int NodesCount { get; }

        [IoC.NotNull] IEnumerable<ILink<TNode, TLink>> Links { get; }

        bool TryAddNode([IoC.NotNull] TNode value, out INode<TNode> node);

        bool TryRemoveNode([IoC.NotNull] INode<TNode> node);

        bool TryAddLink([IoC.NotNull] INode<TNode> from, [IoC.NotNull] TLink value, [IoC.NotNull] INode<TNode> to, out ILink<TNode, TLink> link);

        bool TryRemoveLink([IoC.NotNull] ILink<TNode, TLink> link);

        IGraph<TNode, TLink> Copy(Predicate<INode<TNode>> filter);
    }
}
