using System.Collections.Generic;
using IoC;
using TeamCity.Docker.Model;

namespace TeamCity.Docker
{
    internal interface IContentParser
    {
        [NotNull] IEnumerable<Line> Parse([NotNull] IEnumerable<string> content, [NotNull] IEnumerable<Variable> variables);
    }
}