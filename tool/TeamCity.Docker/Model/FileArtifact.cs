using System.Collections.Generic;

namespace TeamCity.Docker.Model
{
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
