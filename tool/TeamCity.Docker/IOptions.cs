namespace TeamCity.Docker
{
    using System.Collections.Generic;
    using IoC;

    internal interface IOptions
    {
        [NotNull] string SourcePath { get; }

        [NotNull] string DockerEngineEndpoint { get; }

        [NotNull] string ContextPath { get; }

        [NotNull] IEnumerable<string> ConfigurationFiles { get; }

        bool VerboseMode { get; }
    }
}
