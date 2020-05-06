using System;
using CommandLine;
using IoC;

// ReSharper disable ClassNeverInstantiated.Global
// ReSharper disable AccessToDisposedClosure

namespace TeamCity.Docker
{
    public class Program
    {
        public static int Main(string[] args) =>
            (int)Parser.Default.ParseArguments<GenerateOptions, BuildOptions>(args)
                .MapResult(
                    (GenerateOptions options) => Run<IGenerateOptions>(options),
                    (BuildOptions options) => Run<IBuildOptions>(options),
                    error => Result.Error);

        private static Result Run<TOptions>([NotNull] TOptions options)
            where TOptions: IOptions
        {
            if (options == null)
            {
                throw new ArgumentNullException(nameof(options));
            }

            using (var container = Container
                .Create()
                .Using<IoCConfiguration>()
                .Bind<TOptions, IOptions>().As(Lifetime.Singleton).To(ctx => options)
                .Container)
            {
                try
                {
                    var runTask = container.Resolve<ICommand<TOptions>>().Run();
                    runTask.Wait();
                    return runTask.Result;
                }
                catch (Exception ex)
                {
                    container.Resolve<ILogger>().Log(ex);
                    return Result.Error;
                }
            }
        }
    }
}
