using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using IoC;
using TeamCity.Docker.Model;

// ReSharper disable ConvertIfStatementToConditionalTernaryExpression
// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    internal class ContentParser : IContentParser
    {
        private static readonly Regex ArgRegex = new Regex(@"\s*ARG\s+(?<name>\w+)=(?<value>.*)", RegexOptions.CultureInvariant | RegexOptions.IgnoreCase | RegexOptions.Compiled);
        private static readonly Variable[] EmptyVars = new Variable[0];
        private readonly ILogger _logger;

        public ContentParser([NotNull] ILogger logger) => _logger = logger ?? throw new ArgumentNullException(nameof(logger));

        public IEnumerable<Line> Parse(IEnumerable<string> content, IEnumerable<Variable> variables)
        {
            if (content == null) throw new ArgumentNullException(nameof(content));
            if (variables == null) throw new ArgumentNullException(nameof(variables));
            var lines = new List<Line>();
            var localVars = new List<Variable>();
            foreach (var line in content)
            {
                var vars = (
                    from variable in variables
                    where line.Contains($"{variable.Name}")
                    select new Variable(variable.Name, variable.Value))
                    .Distinct()
                    .ToList();

                var lineWithVars = ApplyVariables(line, vars);
                var argStatement = ArgRegex.Match(lineWithVars);
                if (argStatement.Success)
                {
                    var value = argStatement.Groups["value"].Value;
                    if (string.IsNullOrEmpty(value))
                    {
                        localVars.Add(new Variable(argStatement.Groups["name"].Value, value));
                    }
                }

                var lineWithLocalVars = ApplyVariables(lineWithVars, localVars);
                if (lineWithLocalVars.Contains("${") && lineWithLocalVars.Contains("}"))
                {
                    _logger.Log($"The line \"{line.Trim()}\" may still contain some unresolved variables. May be these variables will be resolved by environment variables.", Result.Warning);
                }

                if (line.TrimStart().StartsWith("#"))
                {
                    lines.Add(new Line(lineWithVars, LineType.Comment, vars));
                }
                else
                {
                    lines.Add(new Line(line, LineType.Text, vars));
                }
            }

            var usedVariables = lines
                .Where(line => line.Type != LineType.Comment)
                .SelectMany(line => line.Variables)
                .Distinct()
                .OrderByDescending(line => line.Name)
                .ToList();

            if (usedVariables.Any())
            {
                lines.Insert(0, new Line("", LineType.Text, EmptyVars));
                foreach (var variable in usedVariables)
                {
                    lines.Insert(0, new Line($"ARG {variable.Name}='{variable.Value}'", LineType.Text, new[] {variable}));
                }

                lines.Insert(0, new Line("# Default arguments", LineType.Comment, EmptyVars));
            }

            return lines;
        }

        private static string ApplyVariables([NotNull] string text, [NotNull] IEnumerable<Variable> vars)
        {
            if (text == null)
            {
                throw new ArgumentNullException(nameof(text));
            }

            if (vars == null)
            {
                throw new ArgumentNullException(nameof(vars));
            }

            return vars.Aggregate(text, (current, value) => current.Replace("${" + value.Name + "}", value.Value));
        }
    }
}
