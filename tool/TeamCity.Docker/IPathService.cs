namespace TeamCity.Docker
{
    using IoC;

    internal interface IPathService
    {
        [NotNull] string Normalize([NotNull] string path);
    }
}