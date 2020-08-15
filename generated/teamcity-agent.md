## teamcity-agent tags

Other tags

- [teamcity-minimal-agent](teamcity-minimal-agent.md)
- [teamcity-server](teamcity-server.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [EAP](#EAP)
- [EAP-windowsservercore](#EAP-windowsservercore)

#### linux

- 18.04-sudo
  - [EAP-linux-sudo](#EAP-linux-sudo)
- 18.04
  - [EAP-linux](#EAP-linux)

#### windows

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

Supported platforms: linux 18.04, windows 1809, windows 1903

#### Content

- [EAP-linux](#EAP-linux)
- [EAP-nanoserver-1809](#EAP-nanoserver-1809)
- [EAP-nanoserver-1903](#EAP-nanoserver-1903)

### EAP-windowsservercore

Supported platforms: windows 1809, windows 1903

#### Content

- [EAP-windowsservercore-1809](#EAP-windowsservercore-1809)
- [EAP-windowsservercore-1903](#EAP-windowsservercore-1903)


### EAP-linux

[Dockerfile](linux/Agent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- Git
- Mercurial
- [.NET SDK x64 v.3.1.300](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.300/dotnet-sdk-3.1.300-linux-x64.tar.gz)

Container platform: linux

Docker pull command:

```
docker pull jetbrains//teamcity-agent:EAP-linux
```

Docker build commands:

```
docker build -f "generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux ""
docker build -f "generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:EAP-linux ""
```

Base images:

```
docker pull ubuntu:18.04
```

_The required free space to generate image(s) is about **2 GB**._
### EAP-linux-sudo

[Dockerfile](linux/Agent/Ubuntu/18.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__builduser__* user.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Container platform: linux

Docker pull command:

```
docker pull jetbrains//teamcity-agent:EAP-linux-sudo
```

Docker build commands:

```
docker build -f "generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux ""
docker build -f "generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:EAP-linux ""
docker build -f "generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-sudo ""
```

Base images:

```
docker pull ubuntu:18.04
```

_The required free space to generate image(s) is about **3 GB**._
### EAP-nanoserver-1809

[Dockerfile](windows/Agent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [.NET SDK x64 v.3.1.300](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.300/dotnet-sdk-3.1.300-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-agent:EAP-nanoserver-1809
```

Docker build commands:

```
docker build -f "generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1809 ""
docker build -f "generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1809 ""
docker build -f "generated/windows/Agent/nanoserver/1809/Dockerfile" -t teamcity-agent:EAP-nanoserver-1809 ""
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
```

_The required free space to generate image(s) is about **31 GB**._
### EAP-nanoserver-1903

[Dockerfile](windows/Agent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [.NET SDK x64 v.3.1.300](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.300/dotnet-sdk-3.1.300-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-agent:EAP-nanoserver-1903
```

Docker build commands:

```
docker build -f "generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1903 ""
docker build -f "generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1903 ""
docker build -f "generated/windows/Agent/nanoserver/1903/Dockerfile" -t teamcity-agent:EAP-nanoserver-1903 ""
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
```

_The required free space to generate image(s) is about **31 GB**._
### EAP-windowsservercore-1809

[Dockerfile](windows/Agent/windowsservercore/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)

Container platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-agent:EAP-windowsservercore-1809
```

Docker build commands:

```
docker build -f "generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1809 ""
docker build -f "generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1809 ""
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
```

_The required free space to generate image(s) is about **29 GB**._
### EAP-windowsservercore-1903

[Dockerfile](windows/Agent/windowsservercore/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)

Container platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-agent:EAP-windowsservercore-1903
```

Docker build commands:

```
docker build -f "generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1903 ""
docker build -f "generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1903 ""
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
```

_The required free space to generate image(s) is about **29 GB**._
### EAP-nanoserver-1803

[Dockerfile](windows/Agent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.300](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.300/dotnet-sdk-3.1.300-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker build -f "generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1803 ""
docker build -f "generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1803 ""
docker build -f "generated/windows/Agent/nanoserver/1803/Dockerfile" -t teamcity-agent:EAP-nanoserver-1803 ""
```

Base images:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
```

_The required free space to generate image(s) is about **30 GB**._
### EAP-nanoserver-1909

[Dockerfile](windows/Agent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [.NET SDK x64 v.3.1.300](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.300/dotnet-sdk-3.1.300-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker build -f "generated/windows/Agent/nanoserver/1909/Dockerfile" -t teamcity-agent:EAP-nanoserver-1909 ""
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull EAP-teamcity-agent:windowsservercore-1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
```

_The required free space to generate image(s) is about **5 GB**._
### EAP-windowsservercore-1803

[Dockerfile](windows/Agent/windowsservercore/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker build -f "generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1803 ""
docker build -f "generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1803 ""
```

Base images:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
```

_The required free space to generate image(s) is about **28 GB**._
### EAP-windowsservercore-1909

[Dockerfile](windows/Agent/windowsservercore/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker build -f "generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1909 ""
docker build -f "generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1909 ""
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
```

_The required free space to generate image(s) is about **29 GB**._
