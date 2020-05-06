using System;
using System.Collections.Generic;
using System.Linq;
using CommandLine;

// ReSharper disable ClassNeverInstantiated.Global
// ReSharper disable AutoPropertyCanBeMadeGetOnly.Global

namespace TeamCity.Docker
{
    [Verb("generate", HelpText = "Generate docker and readme files.")]
    internal class GenerateOptions: IGenerateOptions
    {
        [Option('s', "source", Required = false, HelpText = "Path to configuration directory.")]
        public string SourcePath { get; set; } = string.Empty;

        [Option('c', "context", Required = false, HelpText = "Path to the context directory.")]
        public string ContextPath { get; set; } = string.Empty;

        [Option('f', "files", Separator = ';', Required = false, HelpText = "Semicolon separated configuration file.")]
        public IEnumerable<string> ConfigurationFiles { get; set; } = Enumerable.Empty<string>();

        [Option('t', "target", Required = true, HelpText = "Path to directory for generating docker files.")]
        public string TargetPath { get; set; } = string.Empty;

        [Option('d', "dsl", Required = false, HelpText = "Path to directory for teamcity DSL script settings.kts.")]
        public string TeamCityDslPath { get; set; } = string.Empty;

        [Option('b', "builds", Separator = ';', Required = false, HelpText = "Semicolon separated TeamCity build configuration ids.")]
        public IEnumerable<string> TeamCityBuildConfigurationIds { get; set; } = Enumerable.Empty<string>();

        [Option('r', "registry", Required = false, HelpText = "TeamCity docker registry id.")]
        public string TeamCityDockerRegistryId { get; set; } = string.Empty;

        public string DockerEngineEndpoint => throw new NotImplementedException();

        [Option('v', "verbose", Required = false, HelpText = "Add it for detailed output.")]
        public bool VerboseMode { get; set; } = false;
    }
}
