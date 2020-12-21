namespace TeamCity.Docker
{
    using System.Collections.Generic;
    using IoC;
    using Model;

    internal interface IContentParser
    {
        [NotNull] IEnumerable<Line> Parse([NotNull] IEnumerable<string> content, [NotNull] IEnumerable<Variable> variables);
    }
}