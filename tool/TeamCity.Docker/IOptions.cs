using System.Collections.Generic;
using IoC;

namespace TeamCity.Docker
{
    internal interface IOptions
    {
        [NotNull] string SourcePath { get; }

        [NotNull] string DockerEngineEndpoint { get; }

        [NotNull] string ContextPath { get; }

        [NotNull] IEnumerable<string> ConfigurationFiles { get; }

        bool VerboseMode { get; }
    }
}
