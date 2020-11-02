## teamcity-agent tags

Other tags

- [teamcity-server](teamcity-server.md)
- [teamcity-minimal-agent](teamcity-minimal-agent.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [EAP](#EAP)
- [EAP-windowsservercore](#EAP-windowsservercore)

#### linux

- 20.04-sudo
  - [EAP-linux-sudo](#EAP-linux-sudo)
- 20.04
  - [EAP-linux](#EAP-linux)
- 18.04-sudo
  - [EAP-linux-18.04-sudo](#EAP-linux-1804-sudo)
- 18.04
  - [EAP-linux-18.04](#EAP-linux-1804)

#### windows

- 2004
  - [EAP-nanoserver-2004](#EAP-nanoserver-2004)
  - [EAP-windowsservercore-2004](#EAP-windowsservercore-2004)
- 1909
  - [EAP-nanoserver-1909](#EAP-nanoserver-1909)
  - [EAP-windowsservercore-1909](#EAP-windowsservercore-1909)
- 1903
  - [EAP-nanoserver-1903](#EAP-nanoserver-1903)
  - [EAP-windowsservercore-1903](#EAP-windowsservercore-1903)
- 1809
  - [EAP-nanoserver-1809](#EAP-nanoserver-1809)
  - [EAP-windowsservercore-1809](#EAP-windowsservercore-1809)
- 1803
  - [EAP-nanoserver-1803](#EAP-nanoserver-1803)
  - [EAP-windowsservercore-1803](#EAP-windowsservercore-1803)


### EAP

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [EAP-linux](#EAP-linux)
- [EAP-nanoserver-1809](#EAP-nanoserver-1809)
- [EAP-nanoserver-2004](#EAP-nanoserver-2004)

### EAP-windowsservercore

Supported platforms: windows 1809, windows 2004

#### Content

- [EAP-windowsservercore-1809](#EAP-windowsservercore-1809)
- [EAP-windowsservercore-2004](#EAP-windowsservercore-2004)


### EAP-linux

[Dockerfile](linux/Agent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- Git v.2.25.1
- Mercurial
- Docker Engine
- Docker Compose
- [.NET SDK x64 v.3.1.402](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.402/dotnet-sdk-3.1.402-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-sudo

[Dockerfile](linux/Agent/Ubuntu/20.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-nanoserver-1809

[Dockerfile](windows/Agent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [.NET SDK x64 v.3.1.402](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.402/dotnet-sdk-3.1.402-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1809 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1809/Dockerfile" -t teamcity-agent:EAP-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### EAP-nanoserver-2004

[Dockerfile](windows/Agent/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [.NET SDK x64 v.3.1.402](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.402/dotnet-sdk-3.1.402-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:2004
docker pull mcr.microsoft.com/powershell:nanoserver-2004
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-2004
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-2004 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:EAP-windowsservercore-2004 "context"
docker build -f "context/generated/windows/Agent/nanoserver/2004/Dockerfile" -t teamcity-agent:EAP-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### EAP-windowsservercore-1809

[Dockerfile](windows/Agent/windowsservercore/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
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
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1809 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### EAP-windowsservercore-2004

[Dockerfile](windows/Agent/windowsservercore/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
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
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-2004 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:EAP-windowsservercore-2004 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### EAP-linux-18.04

[Dockerfile](linux/Agent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- Git v.2.17.1
- Mercurial
- Docker Engine
- Docker Compose
- [.NET SDK x64 v.3.1.402](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.402/dotnet-sdk-3.1.402-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-18.04 "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:EAP-linux-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-18.04-sudo

[Dockerfile](linux/Agent/Ubuntu/18.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user.
The docker image is not available and may be created manually.

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-18.04 "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:EAP-linux-18.04 "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-nanoserver-1803

[Dockerfile](windows/Agent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.402](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.402/dotnet-sdk-3.1.402-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1803 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1803/Dockerfile" -t teamcity-agent:EAP-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### EAP-nanoserver-1903

[Dockerfile](windows/Agent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [.NET SDK x64 v.3.1.402](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.402/dotnet-sdk-3.1.402-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1903 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1903/Dockerfile" -t teamcity-agent:EAP-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### EAP-nanoserver-1909

[Dockerfile](windows/Agent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [.NET SDK x64 v.3.1.402](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.402/dotnet-sdk-3.1.402-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1909 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1909/Dockerfile" -t teamcity-agent:EAP-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### EAP-windowsservercore-1803

[Dockerfile](windows/Agent/windowsservercore/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
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
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1803 "context"
```

_The required free space to generate image(s) is about **31 GB**._

### EAP-windowsservercore-1903

[Dockerfile](windows/Agent/windowsservercore/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
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
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1903 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### EAP-windowsservercore-1909

[Dockerfile](windows/Agent/windowsservercore/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
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
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1909 "context"
```

_The required free space to generate image(s) is about **33 GB**._

