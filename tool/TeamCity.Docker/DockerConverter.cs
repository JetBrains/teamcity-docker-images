using System;
using IoC;

// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    internal class DockerConverter : IDockerConverter
    {
        private const string EmptyId = "   <missing>";
        static readonly string[] SizeSuffixes = { "bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB" };

        public string TryConvertRepoTagToTag([NotNull] string repoTag)
        {
            if (repoTag == null)
            {
                throw new ArgumentNullException(nameof(repoTag));
            }

            var ind = repoTag.IndexOf(":", StringComparison.Ordinal);
            if (ind > 2)
            {
                return repoTag.Substring(ind + 1);
            }

            return null;
        }

        public string TryConvertRepoTagToRepositoryName([NotNull] string repoTag)
        {
            if (repoTag == null)
            {
                throw new ArgumentNullException(nameof(repoTag));
            }

            var ind = repoTag.IndexOf(":", StringComparison.Ordinal);
            if (ind > 2)
            {
                return repoTag.Substring(0, ind);
            }

            return null;
        }

        public string TryConvertConvertHashToImageId([NotNull] string hash)
        {
            if (hash == null)
            {
                throw new ArgumentNullException(nameof(hash));
            }

            var ind = hash.IndexOf(":", StringComparison.Ordinal);
            if (ind > 2 && hash.Length > ind + 13)
            {
                return hash.Substring(ind + 1, 12);
            }

            return EmptyId;
        }

        public string ConvertToSize(long value, int decimalPlaces = 0)
        {
            if (value < 0)
            {
                throw new ArgumentException("Bytes should not be negative", nameof(value));
            }

            var mag = (int)Math.Max(0, Math.Log(value, 1024));
            var adjustedSize = Math.Round(value / Math.Pow(1024, mag), decimalPlaces);
            return $"{adjustedSize} {SizeSuffixes[mag]}";
        }
    }
}
