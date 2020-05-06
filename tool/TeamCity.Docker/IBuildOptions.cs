namespace TeamCity.Docker
{
    internal interface IBuildOptions : IOptions
    {
        string FilterRegex { get; }
    }
}
