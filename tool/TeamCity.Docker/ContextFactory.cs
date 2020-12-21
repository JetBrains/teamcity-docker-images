using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using ICSharpCode.SharpZipLib.Tar;
using IoC;
using TeamCity.Docker.Model;

// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    internal class ContextFactory : IContextFactory
    {
        private readonly IOptions _options;
        private readonly ILogger _logger;
        private readonly IPathService _pathService;
        private readonly IFileSystem _fileSystem;
        private readonly IStreamService _streamService;
        private static readonly int Chmod = Convert.ToInt32("100755", 8);

        public ContextFactory(
            [NotNull] IOptions options,
            [NotNull] ILogger logger,
            [NotNull] IPathService pathService,
            [NotNull] IFileSystem fileSystem,
            [NotNull] IStreamService streamService)
        {
            _options = options ?? throw new ArgumentNullException(nameof(options));
            _logger = logger ?? throw new ArgumentNullException(nameof(logger));
            _pathService = pathService ?? throw new ArgumentNullException(nameof(pathService));
            _fileSystem = fileSystem ?? throw new ArgumentNullException(nameof(fileSystem));
            _streamService = streamService ?? throw new ArgumentNullException(nameof(streamService));
        }

        public async Task<Result<Stream>> Create(string dockerFilesRootPath, IEnumerable<Dockerfile> dockerFiles)
        {
            if (dockerFilesRootPath == null)
            {
                throw new ArgumentNullException(nameof(dockerFilesRootPath));
            }

            if (dockerFiles == null)
            {
                throw new ArgumentNullException(nameof(dockerFiles));
            }

            using (_logger.CreateBlock("Context"))
            {
                var context = new MemoryStream();
                using (var archive = new TarOutputStream(context, Encoding.UTF8) {IsStreamOwner = false})
                {
                    var number = 0;
                    if (!string.IsNullOrWhiteSpace(_options.ContextPath))
                    {
                        var path = Path.GetFullPath(_options.ContextPath);
                        if (!_fileSystem.IsDirectoryExist(path))
                        {
                            throw new InvalidOperationException($"The docker build context directory \"{path}\" does not exist.");
                        }

                        foreach (var file in _fileSystem.EnumerateFileSystemEntries(path))
                        {
                            if (!_fileSystem.IsFileExist(file))
                            {
                                continue;
                            }

                            number++;
                            using (var fileStream = _fileSystem.OpenRead(file))
                            {
                                var filePathInArchive = _pathService.Normalize(Path.GetRelativePath(path, file));
                                var result = await AddEntry(archive, filePathInArchive, fileStream);
                                _logger.Details($"{number:0000} \"{filePathInArchive}\" was added."); 
                                if (result == Result.Error)
                                {
                                    return new Result<Stream>(new MemoryStream(), Result.Error);
                                }
                            }
                        }

                        _logger.Log($"{number} files were added to docker build context from the directory \"{_options.ContextPath}\" (\"{path}\").");
                    }
                    else
                    {
                        _logger.Log("The path for docker build context was not defined.", Result.Warning);
                    }

                    number = 0;
                    using (_logger.CreateBlock("Docker files"))
                    {
                        foreach (var dockerfile in dockerFiles)
                        {
                            number++;
                            var dockerFilePathInArchive = _pathService.Normalize(Path.Combine(dockerFilesRootPath, dockerfile.Path));
                            using (var dockerFileStream = new MemoryStream())
                            {
                                using(var writer = new StreamWriter(dockerFileStream, Encoding.UTF8, 0xff, true))
                                {
                                    foreach (var line in dockerfile.Lines)
                                    {
                                        writer.WriteLine(line.Text);
                                    }
                                }

                                dockerFileStream.Position = 0;
                                var result = await AddEntry(archive, dockerFilePathInArchive, dockerFileStream);
                                if (result == Result.Error)
                                {
                                    return new Result<Stream>(new MemoryStream(), Result.Error);
                                }

                                _logger.Log($"{number:0000} \"{dockerFilePathInArchive}\" was added ({dockerFileStream.Length} bytes).");
                            }
                        }
                    }

                    archive.Close();
                    return new Result<Stream>(context);
                }
            }
        }

        private async Task<Result> AddEntry([NotNull] TarOutputStream archive, [NotNull] string filePathInArchive, [NotNull] Stream contentStream)
        {
            if (archive == null)
            {
                throw new ArgumentNullException(nameof(archive));
            }

            if (filePathInArchive == null)
            {
                throw new ArgumentNullException(nameof(filePathInArchive));
            }

            if (contentStream == null)
            {
                throw new ArgumentNullException(nameof(contentStream));
            }

            var entry = TarEntry.CreateTarEntry(filePathInArchive);
            entry.Size = contentStream.Length;
            entry.TarHeader.Mode = Chmod; //chmod 755
            archive.PutNextEntry(entry);
            var result = await _streamService.Copy(contentStream, archive, $"Adding {filePathInArchive}");
            archive.CloseEntry();
            return result;
        }
    }
}
