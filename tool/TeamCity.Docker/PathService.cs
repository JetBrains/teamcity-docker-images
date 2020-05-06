using System;
using System.Runtime.InteropServices;
using IoC;

// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    internal class PathService : IPathService
    {
        private readonly IEnvironment _environment;

        public PathService([NotNull] IEnvironment environment) =>
            _environment = environment ?? throw new ArgumentNullException(nameof(environment));

        public string Normalize(string path)
        {
            if (path == null)
            {
                throw new ArgumentNullException(nameof(path));
            }

            return _environment.IsOSPlatform(OSPlatform.Windows) ? path.Replace('\\', '/') : path;
        }
    }
}
