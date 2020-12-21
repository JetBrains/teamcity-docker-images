namespace TeamCity.Docker
{
    using IoC;

    internal interface IDockerConverter
    {
        [CanBeNull] string TryConvertRepoTagToTag([NotNull] string repoTag);

        [CanBeNull] string TryConvertRepoTagToRepositoryName([NotNull] string repoTag);

        [CanBeNull] string TryConvertConvertHashToImageId([NotNull] string hash);

        [NotNull] string ConvertToSize(long value, int decimalPlaces = 0);
    }
}