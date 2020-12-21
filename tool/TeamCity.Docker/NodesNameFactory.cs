namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using Generic;
    using Model;

    // ReSharper disable once ClassNeverInstantiated.Global
    internal class NodesNameFactory: IFactory<NodesDescription, IEnumerable<INode<IArtifact>>>
    {
        public Result<NodesDescription> Create(IEnumerable<INode<IArtifact>> nodes)
        {
            var descriptions = nodes.Select(i => i.Value)
                .OfType<Image>()
                .Select(i => new NodesDescription($"{i.File.Platform} {i.File.Description}".Trim(), i.File.Platform, i.File.Description))
                .Where(i => !string.IsNullOrWhiteSpace(i.Name))
                .GroupBy(i => i, s => s, (s, enumerable) => Tuple.Create(s, enumerable.Count()))
                .OrderByDescending(i => i.Item2)
                .Select(i => i.Item1)
                .ToList();

            return descriptions.Any() ? new Result<NodesDescription>(descriptions.First()) : new Result<NodesDescription>(default(NodesDescription), Result.Error);
        }
    }
}
