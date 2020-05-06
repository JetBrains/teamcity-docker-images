using Docker.DotNet.Models;
using IoC;

namespace TeamCity.Docker
{
    internal interface IMessageLogger
    {
        Result Log([NotNull] JSONMessage message);

        Result Log([NotNull] string jsonMessage);
    }
}
