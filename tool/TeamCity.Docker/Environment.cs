// ReSharper disable InconsistentNaming
// ReSharper disable ClassNeverInstantiated.Global
namespace TeamCity.Docker
{
    using System;
    using System.Collections;
    using System.Collections.Generic;
    using System.Collections.ObjectModel;
    using System.Linq;
    using System.Runtime.InteropServices;

    internal class Environment : IEnvironment
    {
        public IReadOnlyDictionary<string, string> Variables
            => new ReadOnlyDictionary<string, string>(
                System.Environment.GetEnvironmentVariables()
                    .OfType<DictionaryEntry>()
                    .ToDictionary(i => (string)i.Key, i => (string)i.Value));

        public bool IsOSPlatform(OSPlatform platform) => RuntimeInformation.IsOSPlatform(platform);

        public bool HasEnvironmentVariable(string name)
        {
            if (name == null)
            {
                throw new ArgumentNullException(nameof(name));
            }

            return !string.IsNullOrEmpty(System.Environment.GetEnvironmentVariable(name));
        }
    }
}
