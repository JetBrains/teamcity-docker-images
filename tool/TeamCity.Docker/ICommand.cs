// ReSharper disable UnusedTypeParameter
namespace TeamCity.Docker
{
    using System.Threading.Tasks;
    using IoC;

    internal interface ICommand<in TOptions>
    {
        [NotNull] Task<Result> Run();
    }
}
