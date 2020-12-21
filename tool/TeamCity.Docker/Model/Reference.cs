namespace TeamCity.Docker.Model
{
    using System;
    using IoC;

    internal class Reference: IArtifact
    {
        [NotNull] public readonly string RepoTag;

        public Reference([NotNull] string repoTag, Weight weight)
        {
            RepoTag = repoTag ?? throw new ArgumentNullException(nameof(repoTag));
            Weight = weight;
        }

        public Weight Weight { get; }

        public override string ToString() => $"Ref[{RepoTag}]({Weight})";
    }
}
