﻿namespace TeamCity.Docker.Model
{
    using System;
    using System.Collections.Generic;
    using IoC;

    internal readonly struct Template
    {
        [NotNull] public readonly IEnumerable<string> Lines;
        [NotNull] public readonly IEnumerable<Variant> Variants;
        [NotNull] public readonly IReadOnlyCollection<string> Ignore;

        /// <summary>
        /// Creates object describing configuration for Dockerfile.
        /// </summary>
        /// <param name="lines">Content of the template Dockerfile.</param>
        /// <param name="variants">Different configuration options (.config files)</param>
        /// <param name="ignore">Content of .Dockerignore</param>
        public Template([NotNull] IReadOnlyCollection<string> lines, [NotNull] IReadOnlyCollection<Variant> variants, [NotNull] IReadOnlyCollection<string> ignore)
        {
            Lines = lines ?? throw new ArgumentNullException(nameof(lines));
            Variants = variants ?? throw new ArgumentNullException(nameof(variants));
            Ignore = ignore;
        }
    }
}
