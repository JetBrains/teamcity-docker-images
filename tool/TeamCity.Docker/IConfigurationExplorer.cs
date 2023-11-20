namespace TeamCity.Docker
{
    using System.Collections.Generic;
    using IoC;
    using Model;

    /// <summary>
    /// Describes the contract for locating template files for Docker images.
    /// </summary>
    internal interface IConfigurationExplorer
    {
        /// <summary>
        /// Locates configuration files within the given path.
        /// </summary>
        /// <param name="sourcePath">path to directory with the templates of Dockerfiles (configs)</param>
        /// <param name="configurationFiles">list of files with configuration properties within source path (.config files)</param>
        /// <returns>list of templates (templates, config values)</returns>
        Result<IEnumerable<Template>> Explore([NotNull] string sourcePath, [NotNull] IEnumerable<string> configurationFiles);
    }
}