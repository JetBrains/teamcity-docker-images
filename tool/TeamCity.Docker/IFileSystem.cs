using System.Collections.Generic;
using System.IO;
using IoC;

// ReSharper disable UnusedMember.Global

namespace TeamCity.Docker
{
    internal interface IFileSystem
    {
        [NotNull] string UniqueName { get; }

        bool IsDirectoryExist([NotNull] string path);

        bool IsFileExist([NotNull] string path);

        [NotNull] IEnumerable<string> EnumerateFileSystemEntries([NotNull] string path, [NotNull] string searchPattern = "*.*");

        [NotNull] IEnumerable<string> ReadLines([NotNull] string path);

        [NotNull] string ReadFile([NotNull] string path);

        void WriteFile([NotNull] string path, [NotNull] string content);

        void WriteLines([NotNull] string path, [NotNull] IEnumerable<string> lines);

        [NotNull] Stream OpenRead([NotNull] string path);

        [NotNull] Stream OpenWrite([NotNull] string path);
    }
}