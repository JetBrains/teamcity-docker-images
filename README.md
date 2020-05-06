# TeamCity docker images

* [Minimal agent](generated/teamcity-minimal-agent.md)
* [Agent](generated/teamcity-agent.md)
* [Server](generated/teamcity-server.md)

## Docker images on [<img align="center" height="18" src="/logo/docker_hub.png">](https://hub.docker.com/search?q=JetBrains%2FTeamCity&type=image)

These are official [JetBrains TeamCity](jetbrains.com/teamcity/) docker images suitable for production use and evaluation purposes.

The [TeamCity build agent](https://www.jetbrains.com/help/teamcity/build-agent.html) connects to the TeamCity server and spawns the actual build processes. You can use the [jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server) image to run a TeamCity server.

- [Minimal agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent) - this minimal image adds just a TeamCity agent without any tools like VCS clients, etc. It is suitable for simple builds and can serve as a base for your custom images. For Java or .NET development we recommend using the default build agent image [jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent).

- [Agent](https://hub.docker.com/r/jetbrains/teamcity-agent) - this image adds a TeamCity agent suitable for Java development. It is based on [jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent) but gives you more benefits, e.g.

  - client-side checkout if you use <img align="center" height="18" src="/logo/git.png"> or <img align="center" height="18" src="/logo/mercurial.png"> Mercurial
  - more bundled build tools
  - 'docker-in-docker' on Linux

- [Server](https://hub.docker.com/r/jetbrains/teamcity-server)

## Creating your own Docker Images based on [JetBrains TeamCity](jetbrains.com/teamcity/) docker images

Before the beginning make sure you have got:

- [<img align="center" height="18" src="/logo/docker_hub.png">](https://hub.docker.com/search?q=&type=edition&offering=community) installed.

Create a docker file based on some of official [JetBrains TeamCity](jetbrains.com/teamcity/) docker images, for instance the docker file _Dockerfile_ with [<img align="center" height="18" src="/logo/dotnetcore.png">](https://dotnet.microsoft.com/download) SDK 2.1 installed may contain the following lines:

```
FROM jetbrains/teamcity-agent:linux

RUN apt-get install -y sudo apt-get install dotnet-sdk-2.1
```

## Manually building TeamCity Docker Images

Before the beginning make sure you have got:

- [<img align="center" height="18" src="/logo/docker_hub.png">](https://hub.docker.com/search?q=&type=edition&offering=community) installed.
- [<img align="center" height="18" src="/logo/dotnetcore.png">](https://dotnet.microsoft.com/download) SDK installed.

### Build images

- Download the [TeamCity distributive](https://www.jetbrains.com/teamcity/download/download-thanks.html?platform=linux) like a _TeamCity-*.tar.gz_ and unpack it to directory _context_, thus all TeamCity files should be in the directory _/context/TeamCity_.

- Run a script like _net30_windows_build.cmd_ depending on <img align="center" height="18" src="/logo/dotnetcore.png"> SDK version installed and your operating system. For Linux use _*.sh_ scripts. For instance the version <img align="center" height="18" src="/logo/dotnetcore.png"> SDK v.3 scripts are:
  - to build windows docker images run the script _net30_windows_build.cmd_
  - to build linux docker images run the script _net30_linux_build.cmd_

- These script builds a set of required docker images by localy. You can add the command line argument - a regular expression to filter images to build, for instance _net30_linux_build.cmd 18.04_ will build a set of images with the tag _18.04_ only.

### Generate docker files

Run a script like _net30_generate.cmd_ depending on <img align="center" height="18" src="/logo/dotnetcore.png"> SDK version installed and your operating system. _net30_generate.cmd_ script generates docker files, readme and Kotlin script for TeamCity build configurations.

