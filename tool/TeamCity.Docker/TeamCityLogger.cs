// ReSharper disable ClassNeverInstantiated.Global
namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using IoC;
    using JetBrains.TeamCity.ServiceMessages.Write.Special;

    internal class TeamCityLogger: ILogger, IDisposable
    {
        [NotNull] private readonly IOptions _options;
        private readonly Stack<ITeamCityWriter>_teamCityWriters = new();

        public TeamCityLogger(
            [NotNull] ITeamCityWriter teamCityWriter,
            [NotNull] IOptions options)
        {
            _options = options ?? throw new ArgumentNullException(nameof(options));
            _teamCityWriters.Push(teamCityWriter ?? throw new ArgumentNullException(nameof(teamCityWriter)));
        }

        private ITeamCityWriter TeamCityWriter => _teamCityWriters.Peek();

        public void Log(string text, Result result = Result.Success)
        {
            if (text == null)
            {
                throw new ArgumentNullException(nameof(text));
            }

            switch (result)
            {
                case Result.Success:
                    TeamCityWriter.WriteMessage(text);
                    break;

                case Result.Error:
                    TeamCityWriter.WriteError(text);
                    break;

                case Result.Warning:
                    TeamCityWriter.WriteWarning(text);
                    break;

                default:
                    throw new ArgumentOutOfRangeException(nameof(result), result, null);
            }
        }

        public void Details(string text, Result result = Result.Success)
        {
            if (_options.VerboseMode)
            {
                Log(text, result);
            }
        }

        public IDisposable CreateBlock(string blockName)
        {
            if (blockName == null)
            {
                throw new ArgumentNullException(nameof(blockName));
            }

            _teamCityWriters.Push(TeamCityWriter.OpenBlock(blockName));
            return this;
        }

        public void Dispose()
        {
            if (_teamCityWriters.Count > 1)
            {
                _teamCityWriters.Pop().Dispose();
            }
        }
    }
}
