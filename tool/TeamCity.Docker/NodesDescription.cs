using System;
using IoC;

namespace TeamCity.Docker
{
    internal struct NodesDescription
    {
        [NotNull] public readonly string Name;
        [NotNull] public readonly string Platform;
        [NotNull] public readonly string Description;

        public NodesDescription([NotNull] string name, [NotNull] string platform, [NotNull] string description)
        {
            Name = name ?? throw new ArgumentNullException(nameof(name));
            Platform = platform ?? throw new ArgumentNullException(nameof(platform));
            Description = description ?? throw new ArgumentNullException(nameof(description));
        }
    }
}
