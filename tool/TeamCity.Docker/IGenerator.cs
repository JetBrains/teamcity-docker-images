using TeamCity.Docker.Generic;
using TeamCity.Docker.Model;

namespace TeamCity.Docker
{
    internal interface IGenerator
    {
        void Generate(IGraph<IArtifact, Dependency> graph);
    }
}