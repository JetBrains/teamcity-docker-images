namespace TeamCity.Docker
{
    using System;
    using IoC;

    internal class Logger: ILogger
    {
        [NotNull] private readonly IOptions _options;
        private readonly ILogger _logger;

        public Logger(
            [NotNull] IOptions options,
            [NotNull] IEnvironment environment,
            [NotNull] ILogger consoleLogger,
            [NotNull] ILogger teamCityLogger)
        {
            if (environment == null)
            {
                throw new ArgumentNullException(nameof(environment));
            }

            if (consoleLogger == null)
            {
                throw new ArgumentNullException(nameof(consoleLogger));
            }

            if (teamCityLogger == null)
            {
                throw new ArgumentNullException(nameof(teamCityLogger));
            }

            _options = options;

            _logger = environment.HasEnvironmentVariable("TEAMCITY_VERSION") ? teamCityLogger : consoleLogger;
        }

        public void Log(string text, Result result = Result.Success)
        {
            if (text == null)
            {
                throw new ArgumentNullException(nameof(text));
            }

            _logger.Log(text, result);
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

            return _logger.CreateBlock(blockName);
        }
    }
}
