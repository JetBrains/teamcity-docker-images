namespace TeamCity.Docker.Model
{
    using System;
    using IoC;

    internal readonly struct Variable
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
