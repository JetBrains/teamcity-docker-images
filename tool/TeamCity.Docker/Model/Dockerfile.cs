namespace TeamCity.Docker.Model
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using IoC;

    internal readonly struct Dockerfile: IComparable<Dockerfile>
    {
        [NotNull] public readonly string Path;
        [NotNull] public readonly string ImageId;
        [NotNull] public readonly string Platform;
        [NotNull] public readonly string Description;
        [NotNull] public readonly IEnumerable<string> Tags;
        [NotNull] public readonly IEnumerable<string> Components;
        [NotNull] public readonly IEnumerable<string> Repositories;
        [NotNull] public readonly IReadOnlyCollection<string> Comments;
        [NotNull] public readonly IReadOnlyCollection<Reference> References;
        public readonly Weight Weight;
        [NotNull] public readonly IEnumerable<Line> Lines;
        [NotNull] public readonly IReadOnlyCollection<string> Ignores;

        public Dockerfile(
            [NotNull] string path,
            [NotNull] string imageId,
            [NotNull] string platform,
            [NotNull] string description,
            [NotNull] IReadOnlyCollection<string> tags,
            [NotNull] IReadOnlyCollection<string> components,
            [NotNull] IReadOnlyCollection<string> repositories,
            [NotNull] IReadOnlyCollection<string> comments,
            [NotNull] IReadOnlyCollection<Reference> references,
            Weight weight,
            [NotNull] IReadOnlyCollection<Line> lines,
            [NotNull] IReadOnlyCollection<string> ignore)
        {
            Path = path ?? throw new ArgumentNullException(nameof(path));
            ImageId = imageId ?? throw new ArgumentNullException(nameof(imageId));
            Platform = platform ?? throw new ArgumentNullException(nameof(platform));
            Description = description ?? throw new ArgumentNullException(nameof(description));
            Tags = tags ?? throw new ArgumentNullException(nameof(tags));
            Components = components ?? throw new ArgumentNullException(nameof(components));
            Repositories = repositories ?? throw new ArgumentNullException(nameof(repositories));
            Comments = comments ?? throw new ArgumentNullException(nameof(comments));
            References = references ?? throw new ArgumentNullException(nameof(references));
            Weight = weight;
            Lines = lines ?? throw new ArgumentNullException(nameof(lines));
            Ignores = ignore ?? throw new ArgumentNullException(nameof(ignore));
        }

        public int CompareTo(Dockerfile other) =>
            string.Compare(OrderKey(), other.OrderKey(), StringComparison.Ordinal);

        public override string ToString() => $"{ImageId}:{string.Join(",", Tags)}";

        private string OrderKey()
        {
            var repoCount = Repositories.Count(i => !string.IsNullOrWhiteSpace(i));
            var repoFlag = repoCount > 0 ? "1" : "2";
            return $"{repoFlag}-{Platform}-{this}";
        }
    }
}
