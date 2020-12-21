using System;
using System.Collections.Generic;
using IoC;

namespace TeamCity.Docker.Model
{
    internal readonly struct Line
    {
        [NotNull] public readonly string Text;
        public readonly LineType Type;
        [NotNull] public readonly IEnumerable<Variable> Variables;

        public Line([NotNull] string text, LineType type, [NotNull] IReadOnlyCollection<Variable> variables)
        {
            Text = text ?? throw new ArgumentNullException(nameof(text));
            Type = type;
            Variables = variables ?? throw new ArgumentNullException(nameof(variables));
        }
    }
}
