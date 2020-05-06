using System;
using System.Collections.Generic;
using IoC;

namespace TeamCity.Docker.Model
{
    internal struct Variant
    {
        [NotNull] public readonly string BuildPath;
        [NotNull] public readonly IEnumerable<Variable> Variables;

        public Variant([NotNull] string buildPath, [NotNull] IReadOnlyCollection<Variable> variables)
        {
            BuildPath = buildPath ?? throw new ArgumentNullException(nameof(buildPath));
            Variables = variables ?? throw new ArgumentNullException(nameof(variables));
        }
    }
}
