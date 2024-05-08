namespace TeamCity.Docker.Model
{
    using System;
    using System.Collections.Generic;
    using IoC;

    /// <summary>
    /// Describes "Variant" (configuration) of Docker Image - path to generated Dockerfile, path to its configuration ...
    /// ... file, parameters (variables) included into the Dockerfile.
    /// </summary>
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
