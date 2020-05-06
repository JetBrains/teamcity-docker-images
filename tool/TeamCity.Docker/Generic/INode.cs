using IoC;

namespace TeamCity.Docker.Generic
{
    internal interface INode<out TNode>
    {
        [NotNull] TNode Value { get; }
    }
}
