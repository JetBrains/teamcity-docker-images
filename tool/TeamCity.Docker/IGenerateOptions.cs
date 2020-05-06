using System.Collections.Generic;
using IoC;

namespace TeamCity.Docker
{
    internal interface IGenerateOptions: IOptions
    {
        [NotNull] string TargetPath { get; }

        [NotNull] string TeamCityDslPath { get; }

        [NotNull] IEnumerable<string> TeamCityBuildConfigurationIds { get; }

        [NotNull] string TeamCityDockerRegistryId { get; }
    }
}
