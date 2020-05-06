using System;
using IoC;

namespace TeamCity.Docker.Model
{
    internal struct Variable
    {
        [NotNull] public readonly string Name;
        [NotNull] public readonly string Value;

        public Variable([NotNull] string name, [NotNull] string value)
        {
            Name = name ?? throw new ArgumentNullException(nameof(name));
            Value = value ?? throw new ArgumentNullException(nameof(value));
        }
    }
}
