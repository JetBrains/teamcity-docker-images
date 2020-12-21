using System;
using System.Collections.Generic;
using TeamCity.Docker.Generic;
using TeamCity.Docker.Model;

namespace TeamCity.Docker
{
    internal interface IScriptGenerator
    {
        IEnumerable<string> GenerateScript(IGraph<IArtifact, Dependency> graph, INode<IArtifact> node, Func<IArtifact, bool> artifactSelector);
    }
}
