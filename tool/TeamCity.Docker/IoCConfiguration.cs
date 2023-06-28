// ReSharper disable InconsistentNaming
namespace TeamCity.Docker
{
    using System;
    using System.Collections.Generic;
    using System.Threading;
    using Generic;
    using global::Docker.DotNet;
    using IoC;
    using JetBrains.TeamCity.ServiceMessages.Write;
    using JetBrains.TeamCity.ServiceMessages.Write.Special;
    using JetBrains.TeamCity.ServiceMessages.Write.Special.Impl.Updater;
    using Model;
    using static IoC.Lifetime;

    internal class IoCConfiguration: IConfiguration
    {
        public IEnumerable<IToken> Apply(IMutableContainer container)
        {
            if (container == null)
            {
                throw new ArgumentNullException(nameof(container));
            }

            yield return container
                .Bind<CancellationTokenSource>().As(Singleton).To<CancellationTokenSource>()
                .Bind<IFileSystem>().As(Singleton).To<FileSystem>()
                .Bind<IEnvironment>().As(Singleton).To<Environment>()
                .Bind<ILogger>().As(Singleton).Tag("Console").To<ConsoleLogger>()
                .Bind<ILogger>().As(Singleton).Tag("TeamCity").To<TeamCityLogger>()
                .Bind<ILogger>().As(Singleton).To<Logger>(ctx => 
                    new Logger(
                        ctx.Container.Inject<IOptions>(),
                        ctx.Container.Inject<IEnvironment>(),
                        ctx.Container.Inject<ILogger>("Console"),
                        ctx.Container.Inject<ILogger>("TeamCity")))
                .Bind<IDockerClientFactory>().As(Singleton).To<DockerClientFactory>()
                .Bind<IDockerClient>().To(ctx => ctx.Container.Inject<IDockerClientFactory>().Create().Result)
                .Bind<IStreamService>().As(Singleton).To<StreamService>()
                .Bind<IMessageLogger>().As(Singleton).To<MessageLogger>()
                .Bind<IDockerConverter>().As(Singleton).To<DockerConverter>()
                .Bind<IPathService>().As(Singleton).To<PathService>()
                .Bind<IConfigurationExplorer>().As(Singleton).To<ConfigurationExplorer>()
                .Bind<IContentParser>().As(Singleton).To<ContentParser>()
                .Bind<IFactory<IEnumerable<IGraph<IArtifact, Dependency>>, IGraph<IArtifact, Dependency>>>().As(Singleton).To<BuildGraphsFactory>()
                .Bind<IScriptGenerator>().As(Singleton).To<ScriptGenerator>()
                .Bind<IGenerator>().Tag("Readme files").As(Singleton).To<ReadmeFilesGenerator>()
                // -- Generation of Kotlin DSL via C# had been replaced with reusable build configurations.
                // .Bind<IGenerator>().Tag("Kotlin DSL").As(Singleton).To<TeamCityKotlinSettingsGenerator>()
                .Bind<IGenerator>().Tag("Scripts").As(Singleton).To<ScriptsGenerator>()
                .Bind<IFactory<IGraph<IArtifact, Dependency>, IEnumerable<Template>>>().As(Singleton).To<DockerGraphFactory>()
                .Bind<IContextFactory>().As(Singleton).To<ContextFactory>()
                .Bind<IBuildPathProvider>().As(Singleton).To<BuildPathProvider>()
                .Bind<IFactory<NodesDescription, IEnumerable<INode<IArtifact>>>>().As(Singleton).To<NodesNameFactory>()

                // TeamCity messages
                .Bind<IServiceMessageFormatter>().As(Singleton).To<ServiceMessageFormatter>()
                .Bind<IFlowIdGenerator>().As(Singleton).To<FlowIdGenerator>()
                .Bind<IServiceMessageUpdater>().As(Singleton).To(ctx => new TimestampUpdater(() => DateTime.Now) )
                .Bind<ITeamCityServiceMessages>().As(Singleton).To<TeamCityServiceMessages>()
                .Bind<ITeamCityWriter, ITeamCityMessageWriter>().As(Singleton).To(ctx => ctx.Container.Inject<ITeamCityServiceMessages>().CreateWriter())

                .Bind<ICommand<IGenerateOptions>>().As(Singleton).To<GenerateCommand>()
                .Bind<ICommand<IBuildOptions>>().As(Singleton).To<BuildCommand>();
        }
    }
}
