using System.Threading.Tasks;
using Docker.DotNet;
using IoC;

namespace TeamCity.Docker
{
    internal interface IDockerClientFactory
    {
        [NotNull] Task<IDockerClient> Create();
    }
}
