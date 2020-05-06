namespace TeamCity.Docker.Model
{
    internal class Image: IArtifact
    {
        public readonly Dockerfile File;

        public Image(Dockerfile file)
        {
            File = file;
        }

        public Weight Weight => File.Weight;

        public override string ToString() => $"Local[{File}]({Weight})";
    }
}
