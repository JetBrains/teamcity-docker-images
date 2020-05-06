using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;
using IoC;
using TeamCity.Docker.Model;

namespace TeamCity.Docker
{
    internal interface IContextFactory
    {
        [NotNull] Task<Result<Stream>> Create([NotNull] string dockerFilesRootPath, [NotNull] IEnumerable<Dockerfile> dockerFiles);
    }
}