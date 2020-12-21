namespace TeamCity.Docker
{
    using System.Threading.Tasks;
    using global::Docker.DotNet;
    using IoC;

    internal interface IDockerClientFactory
    {
        [NotNull] Task<IDockerClient> Create();
    }
}
