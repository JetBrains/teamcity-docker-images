using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.IO;
using System.Linq;
using IoC;
using TeamCity.Docker.Model;

// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    internal class ConfigurationExplorer : IConfigurationExplorer
    {
        [NotNull] private readonly ILogger _logger;
        [NotNull] private readonly IFileSystem _fileSystem;

        public ConfigurationExplorer(
            [NotNull] ILogger logger,
            [NotNull] IFileSystem fileSystem)
        {
            _logger = logger ?? throw new ArgumentNullException(nameof(logger));
            _fileSystem = fileSystem ?? throw new ArgumentNullException(nameof(fileSystem));
        }

        public Result<IEnumerable<Template>> Explore(string sourcePath, IEnumerable<string> configurationFiles)
        {
            if (sourcePath == null)
            {
                throw new ArgumentNullException(nameof(sourcePath));
            }

            if (configurationFiles == null)
            {
                throw new ArgumentNullException(nameof(configurationFiles));
            }

            var additionalVars = new Dictionary<string, string>();
            using (_logger.CreateBlock("Explore"))
            {
                foreach (var configurationFile in configurationFiles)
                {
                    if (!_fileSystem.IsFileExist(configurationFile))
                    {
                        _logger.Log($"The configuration file \"{configurationFile}\"  (\"{Path.GetFullPath(configurationFile)}\") does not exist.", Result.Error);
                        return new Result<IEnumerable<Template>>(Enumerable.Empty<Template>());
                    }

                    using (_logger.CreateBlock(configurationFile))
                    {
                        additionalVars = UpdateVariables(additionalVars, GetVariables(configurationFile));
                    }
                }

                _logger.Log($"The configuration path is \"{sourcePath}\" (\"{Path.GetFullPath(sourcePath)}\")");
            }

            return new Result<IEnumerable<Template>>(GetConfigurations(sourcePath, additionalVars));
        }

        private IEnumerable<Template> GetConfigurations([NotNull] string sourcePath, [NotNull] IReadOnlyDictionary<string, string> additionalVars)
        {
            if (sourcePath == null)
            {
                throw new ArgumentNullException(nameof(sourcePath));
            }

            if (additionalVars == null)
            {
                throw new ArgumentNullException(nameof(additionalVars));
            }

            var templateCounter = 0;
            foreach (var dockerfileTemplate in _fileSystem.EnumerateFileSystemEntries(sourcePath, "*.Dockerfile"))
            {
                var dockerfileTemplateRelative = Path.GetRelativePath(sourcePath, dockerfileTemplate);
                using (_logger.CreateBlock($"{++templateCounter:000} {dockerfileTemplateRelative}"))
                {
                    var dockerfileTemplateDir = Path.GetDirectoryName(dockerfileTemplate) ?? ".";
                    var dockerfileTemplatePath = Path.GetFileName(dockerfileTemplate);
                    var variants = new List<Variant>();
                    var configCounter = 0;
                    foreach (var configFile in _fileSystem.EnumerateFileSystemEntries(dockerfileTemplateDir, dockerfileTemplatePath + ".config"))
                    {
                        var buildPath = Path.GetDirectoryName(Path.GetRelativePath(sourcePath, configFile)) ?? "";
                        using (_logger.CreateBlock($"{templateCounter:000}.{++configCounter:000} {Path.GetRelativePath(sourcePath, configFile)}"))
                        {
                            var vars = UpdateVariables(GetVariables(configFile), additionalVars);
                            variants.Add(new Variant(buildPath, vars.Select(i => new Variable(i.Key, i.Value)).ToList()));
                        }
                    }

                    yield return new Template(_fileSystem.ReadLines(dockerfileTemplate).ToImmutableList(), variants);
                }
            }
        }

        private IReadOnlyDictionary<string, string> GetVariables([NotNull] string configFile)
        {
            if (configFile == null)
            {
                throw new ArgumentNullException(nameof(configFile));
            }

            var vars = new Dictionary<string, string>();
            foreach (var line in _fileSystem.ReadLines(configFile))
            {
                var text = line.Trim();
                if (text.StartsWith('#') || text.Length < 3)
                {
                    continue;
                }

                var eq = text.IndexOf('=');
                if (eq < 1)
                {
                    continue;
                }

                var key = text.Substring(0, eq);
                var val = text.Substring(eq + 1);
                vars[key] = val;
                _logger.Details($"SET {key}={val}");
            }

            return vars;
        }

        private Dictionary<string, string> UpdateVariables([NotNull] IReadOnlyDictionary<string, string> variables, [NotNull] IReadOnlyDictionary<string, string> newVariables)
        {
            if (variables == null)
            {
                throw new ArgumentNullException(nameof(variables));
            }

            if (newVariables == null)
            {
                throw new ArgumentNullException(nameof(newVariables));
            }

            var result = new Dictionary<string, string>(variables);
            foreach (var (key, value) in newVariables)
            {
                if (result.ContainsKey(key))
                {
                    _logger.Details($"UPDATE {key}={value}");
                    result[key] = value;
                }
                else
                {
                    _logger.Details($"SET {key}={value}");
                    result.Add(key, value);
                }
            }

            var replacements = new Dictionary<string, string>();
            var iterations = 0;
            do
            {
                replacements.Clear();
                foreach (var (newKey, newValue) in result)
                {
                    foreach (var (key, value) in result)
                    {
                        var replacement = "${" + newKey + "}";
                        if (value.Contains(replacement))
                        {
                            replacements[key] = value.Replace(replacement, newValue);
                        }
                    }
                }

                foreach (var (key, value) in replacements)
                {
                    if (result.ContainsKey(key))
                    {
                        _logger.Details($"UPDATE {key}={value}");
                        result[key] = value;
                    }
                    else
                    {
                        _logger.Details($"SET {key}={value}");
                        result.Add(key, value);
                    }
                }
            } while (replacements.Count > 0 && iterations++ < 100);

            return result;
        }
    }
}