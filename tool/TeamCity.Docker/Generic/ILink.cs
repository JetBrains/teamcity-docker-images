using IoC;

namespace TeamCity.Docker.Generic
{
    interface ILink<out TNode, out TLink>
    {
        [NotNull] INode<TNode> From { get; }

        [NotNull] TLink Value { get; }

        [NotNull] INode<TNode> To { get; }
    }
}
