using System;
using System.Collections.Generic;
using IoC;

namespace TeamCity.Docker.Model
{
    internal struct Dockerfile
    {
        [NotNull] public readonly string Path;
        [NotNull] public readonly string ImageId;
        [NotNull] public readonly string Platform;
        [NotNull] public readonly IEnumerable<string> Tags;
        [NotNull] public readonly IEnumerable<string> Components;
        [NotNull] public readonly IEnumerable<string> Repositories;
        [NotNull] public readonly IReadOnlyCollection<string> Comments;
        [NotNull] public readonly IReadOnlyCollection<Reference> References;
        public readonly Weight Weight;
        [NotNull] public readonly IEnumerable<Line> Lines;

        public Dockerfile(
            [NotNull] string path,
            [NotNull] string imageId,
            [NotNull] string platform,
            [NotNull] IReadOnlyCollection<string> tags,
            [NotNull] IReadOnlyCollection<string> components,
            [NotNull] IReadOnlyCollection<string> repositories,
            [NotNull] IReadOnlyCollection<string> comments,
            [NotNull] IReadOnlyCollection<Reference> references,
            Weight weight,
            [NotNull] IReadOnlyCollection<Line> lines)
        {
            Path = path ?? throw new ArgumentNullException(nameof(path));
            ImageId = imageId ?? throw new ArgumentNullException(nameof(imageId));
            Platform = platform ?? throw new ArgumentNullException(nameof(platform));
            Tags = tags ?? throw new ArgumentNullException(nameof(tags));
            Components = components ?? throw new ArgumentNullException(nameof(components));
            Repositories = repositories ?? throw new ArgumentNullException(nameof(repositories));
            Comments = comments ?? throw new ArgumentNullException(nameof(comments));
            References = references ?? throw new ArgumentNullException(nameof(references));
            Weight = weight;
            Lines = lines ?? throw new ArgumentNullException(nameof(lines));
        }

        public override string ToString() => $"{ImageId}:{string.Join(",", Tags)}";
    }
}
