using System.Threading.Tasks;
using IoC;

// ReSharper disable UnusedTypeParameter

namespace TeamCity.Docker
{
    internal interface ICommand<in TOptions>
    {
        [NotNull] Task<Result> Run();
    }
}
