// ReSharper disable ClassNeverInstantiated.Global
namespace TeamCity.Docker
{
    using System;
    using System.Runtime.InteropServices;
    using IoC;

    internal class PathService : IPathService
    {
        private readonly IEnvironment _environment;

        public PathService([NotNull] IEnvironment environment) =>
            _environment = environment ?? throw new ArgumentNullException(nameof(environment));

        public string Normalize(string path)
        {
            if (path == null) throw new ArgumentNullException(nameof(path));
            return _environment.IsOSPlatform(OSPlatform.Windows) ? path.Replace('\\', '/') : path;
        }
    }
}
