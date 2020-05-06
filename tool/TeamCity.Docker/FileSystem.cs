using System;
using System.Collections.Generic;
using System.IO;
// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    internal class FileSystem : IFileSystem
    {
        public string UniqueName =>
            Guid.NewGuid().ToString().Replace("-", string.Empty).Replace("{", string.Empty);

        public bool IsDirectoryExist(string path)
        {
            if (path == null)
            {
                throw new ArgumentNullException(nameof(path));
            }

            return Directory.Exists(path);
        }

        public bool IsFileExist(string path)
        {
            if (path == null)
            {
                throw new ArgumentNullException(nameof(path));
            }

            return File.Exists(path);
        }

        public IEnumerable<string> EnumerateFileSystemEntries(string path, string searchPattern)
        {
            if (path == null)
            {
                throw new ArgumentNullException(nameof(path));
            }

            if (searchPattern == null)
            {
                throw new ArgumentNullException(nameof(searchPattern));
            }

            return Directory.EnumerateFileSystemEntries(path, searchPattern, SearchOption.AllDirectories);
        }

        public IEnumerable<string> ReadLines(string path)
        {
            if (path == null)
            {
                throw new ArgumentNullException(nameof(path));
            }

            return File.ReadLines(path);
        }

        public string ReadFile(string path)
        {
            if (path == null)
            {
                throw new ArgumentNullException(nameof(path));
            }

            return File.ReadAllText(path);
        }

        public void WriteFile(string path, string content)
        {
            if (path == null)
            {
                throw new ArgumentNullException(nameof(path));
            }

            if (content == null)
            {
                throw new ArgumentNullException(nameof(content));
            }

            Directory.CreateDirectory(Path.GetDirectoryName(path));
            File.WriteAllText(path, content);
        }

        public void WriteLines(string path, IEnumerable<string> lines)
        {
            if (path == null)
            {
                throw new ArgumentNullException(nameof(path));
            }

            if (lines == null)
            {
                throw new ArgumentNullException(nameof(lines));
            }

            Directory.CreateDirectory(Path.GetDirectoryName(path));
            File.WriteAllLines(path, lines);
        }

        public Stream OpenRead(string path)
        {
            if (path == null)
            {
                throw new ArgumentNullException(nameof(path));
            }

            return File.OpenRead(path);
        }

        public Stream OpenWrite(string path)
        {
            if (path == null)
            {
                throw new ArgumentNullException(nameof(path));
            }

            return File.OpenWrite(path);
        }
    }
}
