namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using Generic;
    using IoC;
    using Model;

    internal interface IScriptGenerator
    {
        [NotNull] IEnumerable<string> GenerateScript([NotNull] IGraph<IArtifact, Dependency> graph, [NotNull] INode<IArtifact> node, [NotNull] Func<IArtifact, bool> artifactSelector);
    }
}
