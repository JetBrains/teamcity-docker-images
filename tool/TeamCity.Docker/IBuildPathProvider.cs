namespace TeamCity.Docker
{
    using System.Collections.Generic;
    using Generic;
    using IoC;
    using Model;

    internal interface IBuildPathProvider
    {
        [NotNull] IEnumerable<INode<IArtifact>> GetPath([NotNull] IGraph<IArtifact, Dependency> buildGraph);

        [NotNull] IEnumerable<INode<IArtifact>> GetPath([NotNull] IGraph<IArtifact, Dependency> buildGraph, [NotNull] INode<IArtifact> leafNode);
    }
}