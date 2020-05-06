using System.Collections.Generic;
using System.Runtime.InteropServices;
using IoC;

// ReSharper disable InconsistentNaming

namespace TeamCity.Docker
{
    internal interface IEnvironment
    {
        IReadOnlyDictionary<string, string> Variables { get; }

        bool IsOSPlatform(OSPlatform platform);

        bool HasEnvironmentVariable([NotNull] string name);
    }
}