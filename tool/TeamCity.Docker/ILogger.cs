using System;
using IoC;

namespace TeamCity.Docker
{
    internal interface ILogger
    {
        void Log([NotNull] string text, Result result = Result.Success);

        void Details([NotNull] string text, Result result = Result.Success);

        [NotNull] IDisposable CreateBlock([NotNull] string blockName);
    }
}
