using IoC;

namespace TeamCity.Docker
{
    internal interface IFactory<T, in TState>
    {
        Result<T> Create([NotNull] TState state);
    }
}
