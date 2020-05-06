using System.Linq;
using TeamCity.Docker.Generic;
using TeamCity.Docker.Model;

namespace TeamCity.Docker
{
    internal class GraphNameFactory: IFactory<string, IGraph<IArtifact, Dependency>>
    {
        public Result<string> Create(IGraph<IArtifact, Dependency> buildGraph)
        {
            var generalTags = buildGraph.Nodes
                .Select(i => i.Value)
                .OfType<Image>()
                .SelectMany(i => i.File.Tags)
                .GroupBy(i => i)
                .Select(i => new { tag = i.Key, count = i.Count() })
                .Where(i => i.count > 1)
                .OrderByDescending(i => i.count)
                .Select(i => i.tag);

            return new Result<string>(string.Join(" ", generalTags));
        }
    }
}
