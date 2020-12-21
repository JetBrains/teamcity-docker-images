// ReSharper disable ClassNeverInstantiated.Global
// ReSharper disable AutoPropertyCanBeMadeGetOnly.Global
namespace TeamCity.Docker
{
    using System.Collections.Generic;
    using System.Linq;
    using CommandLine;

    [Verb("build", HelpText = "Build docker images for session.")]
    internal class BuildOptions: IBuildOptions
    {
        [Option('s', "source", Required = false, HelpText = "Path to configuration directory.")]
        public string SourcePath { get; set; } = string.Empty;

        [Option('f', "files", Separator = ';', Required = false, HelpText = "Semicolon separated configuration file.")]
        public IEnumerable<string> ConfigurationFiles { get; set; } = Enumerable.Empty<string>();

        [Option('c', "context", Required = false, HelpText = "Path to the context directory.")]
        public string ContextPath { get; set; } = "";

        [Option('d', "docker", Required = false, HelpText = "The Docker Engine endpoint like tcp://localhost:2375 (defaults: npipe://./pipe/docker_engine fo windows and unix:///var/run/docker.sock for others).")]
        public string DockerEngineEndpoint { get; set; } = string.Empty;

        [Option('v', "verbose", Required = false, HelpText = "Add it for detailed output.")]
        public bool VerboseMode { get; set; } = false;

        [Option('r', "regex", Required = false, HelpText = "Build graph filter regular expression.")]
        public string FilterRegex { get; set; } = string.Empty;
    }
}
