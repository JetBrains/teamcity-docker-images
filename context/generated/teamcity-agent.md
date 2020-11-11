## teamcity-agent tags

Other tags

- [teamcity-minimal-agent](teamcity-minimal-agent.md)
- [teamcity-server](teamcity-server.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [latest](#latest)
- [2020.2](#20202)
- [latest-windowsservercore](#latest-windowsservercore)
- [2020.2-windowsservercore](#20202-windowsservercore)

#### linux

- 20.04-sudo
  - [2020.2-linux-sudo](#20202-linux-sudo)
- 20.04
  - [2020.2-linux](#20202-linux)
- 18.04-sudo
  - [2020.2-linux-18.04-sudo](#20202-linux-1804-sudo)
- 18.04
  - [2020.2-linux-18.04](#20202-linux-1804)

#### windows

- 2004
  - [2020.2-nanoserver-2004](#20202-nanoserver-2004)
  - [2020.2-windowsservercore-2004](#20202-windowsservercore-2004)
- 1909
  - [2020.2-nanoserver-1909](#20202-nanoserver-1909)
  - [2020.2-windowsservercore-1909](#20202-windowsservercore-1909)
- 1903
  - [2020.2-nanoserver-1903](#20202-nanoserver-1903)
  - [2020.2-windowsservercore-1903](#20202-windowsservercore-1903)
- 1809
  - [2020.2-nanoserver-1809](#20202-nanoserver-1809)
  - [2020.2-windowsservercore-1809](#20202-windowsservercore-1809)
- 1803
  - [2020.2-nanoserver-1803](#20202-nanoserver-1803)
  - [2020.2-windowsservercore-1803](#20202-windowsservercore-1803)


### latest

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2020.2-linux](#20202-linux)
- [2020.2-nanoserver-1809](#20202-nanoserver-1809)
- [2020.2-nanoserver-2004](#20202-nanoserver-2004)

### 2020.2

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2020.2-linux](#20202-linux)
- [2020.2-nanoserver-1809](#20202-nanoserver-1809)
- [2020.2-nanoserver-2004](#20202-nanoserver-2004)

### latest-windowsservercore

Supported platforms: windows 1809, windows 2004

#### Content

- [2020.2-windowsservercore-1809](#20202-windowsservercore-1809)
- [2020.2-windowsservercore-2004](#20202-windowsservercore-2004)

### 2020.2-windowsservercore

Supported platforms: windows 1809, windows 2004

#### Content

- [2020.2-windowsservercore-1809](#20202-windowsservercore-1809)
- [2020.2-windowsservercore-2004](#20202-windowsservercore-2004)


### 2020.2-linux

[Dockerfile](linux/Agent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- Git v.2.25.1
- Mercurial
- [.NET SDK x64 v.3.1.404](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.404/dotnet-sdk-3.1.404-linux-x64.tar.gz)
- [.NET SDK x64 v.5.0.100](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:2020.2-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:2020.2-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### 2020.2-linux-sudo

[Dockerfile](linux/Agent/Ubuntu/20.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [Docker v.19.03.13](https://github.com/docker/docker-ce/releases/tag/v19.03.13)
- [Docker Compose v.1.24.1](https://github.com/docker/compose/releases/tag/1.24.1)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:2020.2-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:2020.2-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile" -t teamcity-agent:2020.2-linux-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### 2020.2-nanoserver-1809

[Dockerfile](windows/Agent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.404](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.404/dotnet-sdk-3.1.404-win-x64.zip)
- [.NET SDK x64 v.5.0.100](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:2020.2-nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:2020.2-windowsservercore-1809 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1809/Dockerfile" -t teamcity-agent:2020.2-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### 2020.2-nanoserver-2004

[Dockerfile](windows/Agent/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.404](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.404/dotnet-sdk-3.1.404-win-x64.zip)
- [.NET SDK x64 v.5.0.100](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:2004
docker pull mcr.microsoft.com/powershell:nanoserver-2004
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-2004
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:2020.2-nanoserver-2004 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:2020.2-windowsservercore-2004 "context"
docker build -f "context/generated/windows/Agent/nanoserver/2004/Dockerfile" -t teamcity-agent:2020.2-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### 2020.2-windowsservercore-1809

[Dockerfile](windows/Agent/windowsservercore/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.404](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.404/dotnet-sdk-3.1.404-win-x64.zip)
- [.NET SDK x64 v.5.0.100](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [Mercurial x64 v.5.5.1](https://www.mercurial-scm.org/release/windows/mercurial-5.5.1-x64.msi)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:2020.2-nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:2020.2-windowsservercore-1809 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### 2020.2-windowsservercore-2004

[Dockerfile](windows/Agent/windowsservercore/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.404](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.404/dotnet-sdk-3.1.404-win-x64.zip)
- [.NET SDK x64 v.5.0.100](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [Mercurial x64 v.5.5.1](https://www.mercurial-scm.org/release/windows/mercurial-5.5.1-x64.msi)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:2004
docker pull mcr.microsoft.com/powershell:nanoserver-2004
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-2004
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:2020.2-nanoserver-2004 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:2020.2-windowsservercore-2004 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### 2020.2-linux-18.04

[Dockerfile](linux/Agent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- Git v.2.17.1
- Mercurial
- [.NET SDK x64 v.3.1.404](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.404/dotnet-sdk-3.1.404-linux-x64.tar.gz)
- [.NET SDK x64 v.5.0.100](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:2020.2-linux-18.04 "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:2020.2-linux-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### 2020.2-linux-18.04-sudo

[Dockerfile](linux/Agent/Ubuntu/18.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.
The docker image is not available and may be created manually.

Installed components:

- [Docker v.19.03.13](https://github.com/docker/docker-ce/releases/tag/v19.03.13)
- [Docker Compose v.1.24.1](https://github.com/docker/compose/releases/tag/1.24.1)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:2020.2-linux-18.04 "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:2020.2-linux-18.04 "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile" -t teamcity-agent:2020.2-linux-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### 2020.2-nanoserver-1803

[Dockerfile](windows/Agent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [${dotnetCoreWindowsComponentName}](${dotnetCoreWindowsComponent})

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:2020.2-nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:2020.2-windowsservercore-1803 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1803/Dockerfile" -t teamcity-agent:2020.2-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### 2020.2-nanoserver-1903

[Dockerfile](windows/Agent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.404](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.404/dotnet-sdk-3.1.404-win-x64.zip)
- [.NET SDK x64 v.5.0.100](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:2020.2-nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:2020.2-windowsservercore-1903 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1903/Dockerfile" -t teamcity-agent:2020.2-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### 2020.2-nanoserver-1909

[Dockerfile](windows/Agent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.404](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.404/dotnet-sdk-3.1.404-win-x64.zip)
- [.NET SDK x64 v.5.0.100](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:2020.2-nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:2020.2-windowsservercore-1909 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1909/Dockerfile" -t teamcity-agent:2020.2-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### 2020.2-windowsservercore-1803

[Dockerfile](windows/Agent/windowsservercore/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.404](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.404/dotnet-sdk-3.1.404-win-x64.zip)
- [.NET SDK x64 v.5.0.100](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [Mercurial x64 v.5.5.1](https://www.mercurial-scm.org/release/windows/mercurial-5.5.1-x64.msi)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:2020.2-nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:2020.2-windowsservercore-1803 "context"
```

_The required free space to generate image(s) is about **31 GB**._

### 2020.2-windowsservercore-1903

[Dockerfile](windows/Agent/windowsservercore/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.404](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.404/dotnet-sdk-3.1.404-win-x64.zip)
- [.NET SDK x64 v.5.0.100](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [Mercurial x64 v.5.5.1](https://www.mercurial-scm.org/release/windows/mercurial-5.5.1-x64.msi)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:2020.2-nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:2020.2-windowsservercore-1903 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### 2020.2-windowsservercore-1909

[Dockerfile](windows/Agent/windowsservercore/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.404](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.404/dotnet-sdk-3.1.404-win-x64.zip)
- [.NET SDK x64 v.5.0.100](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [Mercurial x64 v.5.5.1](https://www.mercurial-scm.org/release/windows/mercurial-5.5.1-x64.msi)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:2020.2-nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:2020.2-windowsservercore-1909 "context"
```

_The required free space to generate image(s) is about **33 GB**._

