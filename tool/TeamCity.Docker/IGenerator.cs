namespace TeamCity.Docker
{
    using Generic;
    using IoC;
    using Model;

    internal interface IGenerator
    {
        void Generate([NotNull] IGraph<IArtifact, Dependency> graph);
    }
}