using System;
using System.Buffers;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using IoC;

// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    internal class StreamService : IStreamService
    {
        private readonly ILogger _logger;

        public StreamService([NotNull] ILogger logger) => _logger = logger ?? throw new ArgumentNullException(nameof(logger));

        public async Task<Result> Copy(Stream sourceStream, Stream targetStream, string description)
        {
            if (sourceStream == null)
            {
                throw new ArgumentNullException(nameof(sourceStream));
            }

            if (targetStream == null)
            {
                throw new ArgumentNullException(nameof(targetStream));
            }

            if (description == null)
            {
                throw new ArgumentNullException(nameof(description));
            }

            var buffer = ArrayPool<byte>.Shared.Rent(0xffff);
            while (true)
            {
                try
                {
                    var bytes = await sourceStream.ReadAsync(buffer, 0, buffer.Length);
                    if (bytes <= 0)
                    {
                        break;
                    }

                    targetStream.Write(buffer, 0, bytes);
                }
                catch (Exception ex)
                {
                    _logger.Log($"{description}: {ex.Message}", Result.Error);
                    return Result.Error;
                }
            }

            return Result.Success;
        }

        public void ProcessLines(Stream source, Action<string> handler)
        {
            if (source == null)
            {
                throw new ArgumentNullException(nameof(source));
            }

            if (handler == null)
            {
                throw new ArgumentNullException(nameof(handler));
            }

            using (var streamReader = new StreamReader(source, Encoding.UTF8))
            {
                do
                {
                    var line = streamReader.ReadLine();
                    if (line != null)
                    {
                        handler(line);
                    }
                } while (!streamReader.EndOfStream);
            }
        }
    }
}
