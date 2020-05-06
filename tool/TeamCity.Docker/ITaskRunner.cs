using System;
using System.Threading.Tasks;
using IoC;

namespace TeamCity.Docker
{
    internal interface ITaskRunner<out TState> : ILogger
        where TState : IDisposable
    {
        [NotNull] Task<Result> Run([NotNull] Func<TState, Task> handler);

        [NotNull] Task<Result<T>> Run<T>(Func<TState, Task<T>> handler);
    }
}