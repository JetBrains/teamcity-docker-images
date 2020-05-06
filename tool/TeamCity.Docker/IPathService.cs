using IoC;

namespace TeamCity.Docker
{
    internal interface IPathService
    {
        [NotNull] string Normalize([NotNull] string path);
    }
}