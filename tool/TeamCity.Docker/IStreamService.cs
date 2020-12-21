namespace TeamCity.Docker
{
    using System;
    using System.IO;
    using System.Threading.Tasks;
    using IoC;

    internal interface IStreamService
    {
        [NotNull] Task<Result> Copy([NotNull] Stream sourceStream, [NotNull] Stream targetStream, [NotNull] string description = "");

        void ProcessLines([NotNull] Stream source, [NotNull] Action<string> handler);
    }
}
