namespace TeamCity.Docker
{
    using IoC;

    internal interface IFactory<T, in TState>
    {
        Result<T> Create([NotNull] TState state);
    }
}
