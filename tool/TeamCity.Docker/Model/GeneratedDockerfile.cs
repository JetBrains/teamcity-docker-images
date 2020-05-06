using System.Collections.Generic;

namespace TeamCity.Docker.Model
{
    internal class GeneratedDockerfile: IArtifact
    {
        public readonly string Path;
        public readonly IEnumerable<Line> Lines;

        public GeneratedDockerfile(string path, IEnumerable<Line> lines)
        {
            Path = path;
            Lines = lines;
        }

        public Weight Weight => Weight.Empty;

        public override string ToString() => Path;
    }
}
