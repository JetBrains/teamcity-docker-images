namespace TeamCity.Docker
{
    using IoC;

    internal interface IGenerateOptions: IOptions
    {
        [NotNull] string TargetPath { get; }

        [NotNull] string TeamCityDslPath { get; }

        [NotNull] string TeamCityBuildConfigurationId { get; }

        [NotNull] string TeamCityDockerRegistryId { get; }
    }
}
