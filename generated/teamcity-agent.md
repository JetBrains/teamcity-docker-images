## teamcity-agent tags

Other tags

- [teamcity-minimal-agent](teamcity-minimal-agent.md)
- [teamcity-server](teamcity-server.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [local](#local)
- [local-windowsservercore](#local-windowsservercore)

#### linux

- 20.04-sudo
  - [local-linux-sudo](#local-linux-sudo)
- 20.04-dotnet
  - [linux-dotnet](#linux-dotnet)
- 20.04
  - [local-linux](#local-linux)
- 18.04-sudo
  - [local-linux-18.04-sudo](#local-linux-1804-sudo)
- 18.04
  - [local-linux-18.04](#local-linux-1804)

#### windows

- 2004
  - [local-nanoserver-2004](#local-nanoserver-2004)
  - [local-windowsservercore-2004](#local-windowsservercore-2004)
- 1909
  - [local-nanoserver-1909](#local-nanoserver-1909)
  - [local-windowsservercore-1909](#local-windowsservercore-1909)
- 1903
  - [local-nanoserver-1903](#local-nanoserver-1903)
  - [local-windowsservercore-1903](#local-windowsservercore-1903)
- 1809-dotnet
  - [nanoserver-dotnet](#nanoserver-dotnet)
- 1809
  - [local-nanoserver-1809](#local-nanoserver-1809)
  - [local-windowsservercore-1809](#local-windowsservercore-1809)
- 1803
  - [local-nanoserver-1803](#local-nanoserver-1803)
  - [local-windowsservercore-1803](#local-windowsservercore-1803)


### local

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [local-linux](#local-linux)
- [local-nanoserver-1809](#local-nanoserver-1809)
- [local-nanoserver-2004](#local-nanoserver-2004)

### local-windowsservercore

Supported platforms: windows 1809, windows 2004

#### Content

- [local-windowsservercore-1809](#local-windowsservercore-1809)
- [local-windowsservercore-2004](#local-windowsservercore-2004)


### local-linux

[Dockerfile](linux/Agent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- Git v.2.25.1
- Mercurial
- [Docker v.19.03.13](https://github.com/docker/docker-ce/releases/tag/v19.03.13)
- [Docker Compose v.1.24.1](https://github.com/docker/compose/releases/tag/1.24.1)
- [.NET SDK x64 v.3.1.405 Checksum (SHA512) 924ec0ab3f126d340ef37fe90263a91f31218995716d1ad5a817bdc6ef71e4d8e87a91edeeb785f5dff3912cc08fe87615718986bb5540ff23e9edf2302e38dd](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:local-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:local-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### local-linux-sudo

[Dockerfile](linux/Agent/Ubuntu/20.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:local-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:local-linux "context"
docker build -f "generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile" -t teamcity-agent:local-linux-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### local-nanoserver-1809

[Dockerfile](windows/Agent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.405 Checksum (SHA512) b58731cecaa4a468a160b20cd1080fe4cea7840db69101cadc80ba6d0af5b4a5a0ab28cadd2da49086e5137edea3a70144bc46ae4508618af644a82a32897260](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:local-nanoserver-1809 "context"
docker build -f "generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:local-windowsservercore-1809 "context"
docker build -f "generated/windows/Agent/nanoserver/1809/Dockerfile" -t teamcity-agent:local-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### local-nanoserver-2004

[Dockerfile](windows/Agent/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.405 Checksum (SHA512) b58731cecaa4a468a160b20cd1080fe4cea7840db69101cadc80ba6d0af5b4a5a0ab28cadd2da49086e5137edea3a70144bc46ae4508618af644a82a32897260](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:2004
docker pull mcr.microsoft.com/powershell:nanoserver-2004
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-2004
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:local-nanoserver-2004 "context"
docker build -f "generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:local-windowsservercore-2004 "context"
docker build -f "generated/windows/Agent/nanoserver/2004/Dockerfile" -t teamcity-agent:local-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### local-windowsservercore-1809

[Dockerfile](windows/Agent/windowsservercore/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.405 Checksum (SHA512) b58731cecaa4a468a160b20cd1080fe4cea7840db69101cadc80ba6d0af5b4a5a0ab28cadd2da49086e5137edea3a70144bc46ae4508618af644a82a32897260](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
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
docker build -f "generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:local-nanoserver-1809 "context"
docker build -f "generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:local-windowsservercore-1809 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### local-windowsservercore-2004

[Dockerfile](windows/Agent/windowsservercore/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.405 Checksum (SHA512) b58731cecaa4a468a160b20cd1080fe4cea7840db69101cadc80ba6d0af5b4a5a0ab28cadd2da49086e5137edea3a70144bc46ae4508618af644a82a32897260](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
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
docker build -f "generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:local-nanoserver-2004 "context"
docker build -f "generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:local-windowsservercore-2004 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### linux-dotnet

[Dockerfile](linux/Agent/Ubuntu/20.04-dotnet/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image can be built manually. It contains a set of .NET SDK. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.
The docker image is not available and may be created manually.

Installed components:

- [.NET SDK x64 v.5.0.102 Checksum (SHA512) 0ce2d5365ca39808fb71baec4584d4ec786491c3735543dc93244604ea97e242377d0987cd8b1e529258dee68f203b5780559201e7ea6d84487d6d8d433329b3](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.102/dotnet-sdk-5.0.102-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull jetbrains/teamcity-agent:2020.2-linux-sudo
echo TeamCity > context/.dockerignore
docker build -f "generated/linux/Agent/Ubuntu/20.04-dotnet/Dockerfile" -t teamcity-agent:linux-dotnet "context"
```

_The required free space to generate image(s) is about **1 GB**._

### local-linux-18.04

[Dockerfile](linux/Agent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- Git v.2.17.1
- Mercurial
- [Docker v.19.03.13](https://github.com/docker/docker-ce/releases/tag/v19.03.13)
- [Docker Compose v.1.24.1](https://github.com/docker/compose/releases/tag/1.24.1)
- [.NET SDK x64 v.3.1.405 Checksum (SHA512) 924ec0ab3f126d340ef37fe90263a91f31218995716d1ad5a817bdc6ef71e4d8e87a91edeeb785f5dff3912cc08fe87615718986bb5540ff23e9edf2302e38dd](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:local-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:local-linux-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### local-linux-18.04-sudo

[Dockerfile](linux/Agent/Ubuntu/18.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.
The docker image is not available and may be created manually.

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:local-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:local-linux-18.04 "context"
docker build -f "generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile" -t teamcity-agent:local-linux-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### local-nanoserver-1803

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
docker build -f "generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:local-nanoserver-1803 "context"
docker build -f "generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:local-windowsservercore-1803 "context"
docker build -f "generated/windows/Agent/nanoserver/1803/Dockerfile" -t teamcity-agent:local-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### local-nanoserver-1903

[Dockerfile](windows/Agent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.405 Checksum (SHA512) b58731cecaa4a468a160b20cd1080fe4cea7840db69101cadc80ba6d0af5b4a5a0ab28cadd2da49086e5137edea3a70144bc46ae4508618af644a82a32897260](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:local-nanoserver-1903 "context"
docker build -f "generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:local-windowsservercore-1903 "context"
docker build -f "generated/windows/Agent/nanoserver/1903/Dockerfile" -t teamcity-agent:local-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### local-nanoserver-1909

[Dockerfile](windows/Agent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [Git x64 v.2.29.1](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.405 Checksum (SHA512) b58731cecaa4a468a160b20cd1080fe4cea7840db69101cadc80ba6d0af5b4a5a0ab28cadd2da49086e5137edea3a70144bc46ae4508618af644a82a32897260](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:local-nanoserver-1909 "context"
docker build -f "generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:local-windowsservercore-1909 "context"
docker build -f "generated/windows/Agent/nanoserver/1909/Dockerfile" -t teamcity-agent:local-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### local-windowsservercore-1803

[Dockerfile](windows/Agent/windowsservercore/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.405 Checksum (SHA512) b58731cecaa4a468a160b20cd1080fe4cea7840db69101cadc80ba6d0af5b4a5a0ab28cadd2da49086e5137edea3a70144bc46ae4508618af644a82a32897260](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
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
docker build -f "generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:local-nanoserver-1803 "context"
docker build -f "generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:local-windowsservercore-1803 "context"
```

_The required free space to generate image(s) is about **31 GB**._

### local-windowsservercore-1903

[Dockerfile](windows/Agent/windowsservercore/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.405 Checksum (SHA512) b58731cecaa4a468a160b20cd1080fe4cea7840db69101cadc80ba6d0af5b4a5a0ab28cadd2da49086e5137edea3a70144bc46ae4508618af644a82a32897260](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
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
docker build -f "generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:local-nanoserver-1903 "context"
docker build -f "generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:local-windowsservercore-1903 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### local-windowsservercore-1909

[Dockerfile](windows/Agent/windowsservercore/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.405 Checksum (SHA512) b58731cecaa4a468a160b20cd1080fe4cea7840db69101cadc80ba6d0af5b4a5a0ab28cadd2da49086e5137edea3a70144bc46ae4508618af644a82a32897260](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
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
docker build -f "generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:local-nanoserver-1909 "context"
docker build -f "generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:local-windowsservercore-1909 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### nanoserver-dotnet

[Dockerfile](windows/Agent/nanoserver/1809-dotnet/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image can be built manually. It contains a set of .NET SDK.
The docker image is not available and may be created manually.

Installed components:

- [.NET SDK x64 v.5.0.102 Checksum (SHA512) 118056d7c60d9591b0a803fb4f8941b6fa5166553d1deac625279330b05599073231ee4c4ecdc3f179d57261290b6c62ac7f34d5f89c8b06274e2346a069f79b](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.102/dotnet-sdk-5.0.102-win-x64.zip)
- [.NET SDK x64 v.5.0.102 Checksum (SHA512) 118056d7c60d9591b0a803fb4f8941b6fa5166553d1deac625279330b05599073231ee4c4ecdc3f179d57261290b6c62ac7f34d5f89c8b06274e2346a069f79b](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.102/dotnet-sdk-5.0.102-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull jetbrains/teamcity-agent:2020.2-nanoserver-1809
echo TeamCity > context/.dockerignore
docker build -f "generated/windows/Agent/nanoserver/1809-dotnet/Dockerfile" -t teamcity-agent:nanoserver-dotnet "context"
```

_The required free space to generate image(s) is about **6 GB**._

