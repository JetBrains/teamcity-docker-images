using System.Collections.Generic;
using IoC;
using TeamCity.Docker.Model;

namespace TeamCity.Docker
{
    internal interface IConfigurationExplorer
    {
        Result<IEnumerable<Template>> Explore([NotNull] string sourcePath, [NotNull] IEnumerable<string> configurationFiles);
    }
}