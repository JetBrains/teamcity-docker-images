namespace TeamCity.Docker
{
    using System.Collections.Generic;
    using System.IO;
    using System.Threading.Tasks;
    using IoC;
    using Model;

    internal interface IContextFactory
    {
        [NotNull] Task<Result<Stream>> Create([NotNull] string dockerFilesRootPath, [NotNull] IEnumerable<Dockerfile> dockerFiles);
    }
}