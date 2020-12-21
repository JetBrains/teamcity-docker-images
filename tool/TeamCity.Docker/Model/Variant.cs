namespace TeamCity.Docker.Model
{
    using System;
    using System.Collections.Generic;
    using IoC;

    internal readonly struct Variant
    {
        [NotNull] public readonly string BuildPath;
        [NotNull] public readonly string ConfigFile;
        [NotNull] public readonly IEnumerable<Variable> Variables;

        public Variant([NotNull] string buildPath, [NotNull] string configFile, [NotNull] IReadOnlyCollection<Variable> variables)
        {
            BuildPath = buildPath ?? throw new ArgumentNullException(nameof(buildPath));
            ConfigFile = configFile ?? throw new ArgumentNullException(nameof(configFile));
            Variables = variables ?? throw new ArgumentNullException(nameof(variables));
        }
    }
}
