using System.Collections.Generic;
using IoC;
using TeamCity.Docker.Generic;
using TeamCity.Docker.Model;

namespace TeamCity.Docker
{
    internal interface IBuildPathProvider
    {
        [NotNull] IEnumerable<INode<IArtifact>> GetPath([NotNull] IGraph<IArtifact, Dependency> buildGraph);

        [NotNull] IEnumerable<INode<IArtifact>> GetPath([NotNull] IGraph<IArtifact, Dependency> buildGraph, [NotNull] INode<IArtifact> leafNode);
    }
}