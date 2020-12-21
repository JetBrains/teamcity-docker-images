namespace TeamCity.Docker
{
    using System.Collections.Generic;
    using IoC;
    using Model;

    internal interface IConfigurationExplorer
    {
        Result<IEnumerable<Template>> Explore([NotNull] string sourcePath, [NotNull] IEnumerable<string> configurationFiles);
    }
}