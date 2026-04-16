// ReSharper disable ClassNeverInstantiated.Global
namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using System.Collections.Immutable;
    using System.IO;
    using System.Linq;
    using System.Text.RegularExpressions;
    using IoC;
    using Model;

    /// <summary>
    /// Locates configuration files for Docker Images.
    /// </summary>
    internal class ConfigurationExplorer : IConfigurationExplorer
    {
        // Retrieves image version from the path, e.g. "configs/linux/Agent/Ubuntu/22.04/Ubuntu.Dockerfile" -> "22.04"
        private static readonly Regex VersionFromPathRegex = new Regex(@"(\d+\.\d+)", RegexOptions.Compiled);

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

        /// <summary>
        /// Generates "variants" - objects based on template Dockerfiles.
        /// </summary>
        /// <param name="sourcePath">path to folder with templates</param>
        /// <param name="additionalVars">Parameters for the substitution within Dockerfile template.</param>
        /// <returns>Tempalte objects</returns>
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
                    // ReSharper disable once IdentifierTypo
                    var dockerignoreTemplatePath = Path.Combine(dockerfileTemplateDir, Path.GetFileNameWithoutExtension(dockerfileTemplatePath) + ".Dockerignore");
                    var variants = new List<Variant>();
                    var configCounter = 0;
                    
                    // Get all configuration files for particular OS (e.g. Ubuntu/20.04/..., Ubuntu/18.04/, ...
                    foreach (var configFile in _fileSystem.EnumerateFileSystemEntries(dockerfileTemplateDir, dockerfileTemplatePath + ".config"))
                    {
                        var buildPath = Path.GetDirectoryName(Path.GetRelativePath(sourcePath, configFile)) ?? "";
                        using (_logger.CreateBlock($"{templateCounter:000}.{++configCounter:000} {Path.GetRelativePath(sourcePath, configFile)}"))
                        {
                            var vars = UpdateVariables(additionalVars, GetVariables(configFile));
                            variants.Add(new Variant(buildPath, configFile, vars.Select(i => new Variable(i.Key, i.Value)).ToList()));
                        }
                    }


                    // Derive version suffixes for linuxVersion from directory paths
                    variants = DeriveLinuxVersionFromPath(variants);

                    var ignore = new List<string>();
                    if (_fileSystem.IsFileExist(dockerignoreTemplatePath))
                    {
                        // Add .Dockerignore files
                        ignore.AddRange(_fileSystem.ReadLines(dockerignoreTemplatePath));
                    }

                    yield return new Template(_fileSystem.ReadLines(dockerfileTemplate).ToImmutableList(), variants.AsReadOnly(), ignore.AsReadOnly());
                }
            }
        }


        /// <summary>
        /// Derives version suffixes for linuxVersion from config file directory paths.
        /// The latest (highest) version gets no suffix; older versions get their version appended.
        /// Also prepends '-' separator to non-empty linuxVersion values.
        /// </summary>
        private static List<Variant> DeriveLinuxVersionFromPath(List<Variant> variants)
        {
            // Extract version from each variant's config file directory
            var versioned = variants.Select(v =>
            {
                var dirName = Path.GetFileName(Path.GetDirectoryName(v.ConfigFile) ?? "");
                var match = VersionFromPathRegex.Match(dirName);
                return (Variant: v, Version: match.Success ? match.Value : "");
            }).ToList();

            // Find the latest (highest) version
            var latestVersion = versioned
                .Where(v => !string.IsNullOrEmpty(v.Version))
                .Select(v => v.Version)
                .OrderByDescending(v => v, StringComparer.Ordinal)
                .FirstOrDefault() ?? "";

            var result = new List<Variant>();
            foreach (var (variant, version) in versioned)
            {
                var varsList = variant.Variables.ToList();
                var lvIndex = varsList.FindIndex(v => v.Name == "linuxVersion");

                if (lvIndex >= 0)
                {
                    var currentValue = varsList[lvIndex].Value;

                    // For non-latest variants, append version suffix (skip if already present).
                    // The '-' prefix is already handled by UpdateVariables.
                    if (!string.IsNullOrEmpty(version) && version != latestVersion
                        && !currentValue.Contains(version))
                    {
                        currentValue = string.IsNullOrEmpty(currentValue)
                            ? "-" + version
                            : currentValue + "-" + version;
                    }

                    varsList[lvIndex] = new Variable("linuxVersion", currentValue);
                    result.Add(new Variant(variant.BuildPath, variant.ConfigFile, varsList));
                }
                else
                {
                    result.Add(variant);
                }
            }

            return result;
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

            // Auto-prepend '-' separator to linuxVersion when non-empty,
            // so config files can use clean values like "arm64" instead of "-arm64".
            // Must happen before cross-reference resolution below.
            if (result.TryGetValue("linuxVersion", out var lv) && !string.IsNullOrEmpty(lv) && !lv.StartsWith("-"))
            {
                result["linuxVersion"] = "-" + lv;
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