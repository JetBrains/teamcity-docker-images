namespace TeamCity.Docker.Generic
{
    using IoC;

    internal interface INode<out TNode>
    {
        [NotNull] TNode Value { get; }
    }
}
