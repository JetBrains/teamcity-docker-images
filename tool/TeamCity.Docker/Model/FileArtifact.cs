namespace TeamCity.Docker.Model
{
    using System.Collections.Generic;

    internal class FileArtifact: IArtifact
    {
        public readonly string Path;
        public readonly IEnumerable<string> Lines;

        public FileArtifact(string path, IEnumerable<string> lines)
        {
            Path = path;
            Lines = lines;
        }

        public Weight Weight => Weight.Empty;

        public override string ToString() => Path;
    }
}
