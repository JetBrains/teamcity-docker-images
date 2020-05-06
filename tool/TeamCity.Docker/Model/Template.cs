using System;
using System.Collections.Generic;
using IoC;

namespace TeamCity.Docker.Model
{
    internal struct Template
    {
        [NotNull] public readonly IEnumerable<string> Lines;
        [NotNull] public readonly IEnumerable<Variant> Variants;

        public Template([NotNull] IReadOnlyCollection<string> lines, [NotNull] IReadOnlyCollection<Variant> variants)
        {
            Lines = lines ?? throw new ArgumentNullException(nameof(lines));
            Variants = variants ?? throw new ArgumentNullException(nameof(variants));
        }
    }
}
