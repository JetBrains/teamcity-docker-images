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
- Perforce Helix Core client (p4) [2021.1-2156517](https://www.perforce.com/products/helix-core)
- [Docker v.19.03.14](https://github.com/docker/docker-ce/releases/tag/v19.03.14), [Containerd.io v1.4.9-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.9-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 63d24f1039f68abc46bf40a521f19720ca74a4d89a2b99d91dfd6216b43a81d74f672f74708efa6f6320058aa49bf13995638e3b8057efcfc84a2877527d56b6](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-linux-x64.tar.gz)

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
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.11.9.1 Checksum (MD5) fb4dafb0db003ed28bb977c6e64fd04c](https://corretto.aws/downloads/resources/11.0.11.9.1/amazon-corretto-11.0.11.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.29.1 Checksum (SHA256) 6aab2a1acce11a765d4dff2b9b5c8fe0feda5ff4067f16a696893786a3bf94c3](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 044ab6ccf316ac18882ecef98f9ec0a9afd8cf8509991a4f82a5778eefae5ad503bf5da915fd567d8ab17ab0a9d93318c59a8c20a8a45ab255eb25247bbe51a7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-win-x64.zip)

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
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.11.9.1 Checksum (MD5) fb4dafb0db003ed28bb977c6e64fd04c](https://corretto.aws/downloads/resources/11.0.11.9.1/amazon-corretto-11.0.11.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.29.1 Checksum (SHA256) 6aab2a1acce11a765d4dff2b9b5c8fe0feda5ff4067f16a696893786a3bf94c3](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 044ab6ccf316ac18882ecef98f9ec0a9afd8cf8509991a4f82a5778eefae5ad503bf5da915fd567d8ab17ab0a9d93318c59a8c20a8a45ab255eb25247bbe51a7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-win-x64.zip)

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
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 044ab6ccf316ac18882ecef98f9ec0a9afd8cf8509991a4f82a5778eefae5ad503bf5da915fd567d8ab17ab0a9d93318c59a8c20a8a45ab255eb25247bbe51a7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.11.9.1 Checksum (MD5) fb4dafb0db003ed28bb977c6e64fd04c](https://corretto.aws/downloads/resources/11.0.11.9.1/amazon-corretto-11.0.11.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.29.1 Checksum (SHA256) 6aab2a1acce11a765d4dff2b9b5c8fe0feda5ff4067f16a696893786a3bf94c3](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
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
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 044ab6ccf316ac18882ecef98f9ec0a9afd8cf8509991a4f82a5778eefae5ad503bf5da915fd567d8ab17ab0a9d93318c59a8c20a8a45ab255eb25247bbe51a7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.11.9.1 Checksum (MD5) fb4dafb0db003ed28bb977c6e64fd04c](https://corretto.aws/downloads/resources/11.0.11.9.1/amazon-corretto-11.0.11.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.29.1 Checksum (SHA256) 6aab2a1acce11a765d4dff2b9b5c8fe0feda5ff4067f16a696893786a3bf94c3](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
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

- [.NET SDK x64 v.5.0.203 Checksum (SHA512) 49d8f0414806a9c938192ed13e7707ac2609ca6c2dc408d616e56e98fc0a954b1aa3f569858f7ba38fb79b2ee36dc1920c7f08d1ba4f93da501542b1c8a1320f](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.203/dotnet-sdk-5.0.203-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull jetbrains/teamcity-agent:2020.2.1-linux-sudo
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
- Perforce Helix Core client (p4) [2021.1-2156517](https://www.perforce.com/products/helix-core)
- [Docker v.19.03.14](https://github.com/docker/docker-ce/releases/tag/v19.03.14), [Containerd.io v1.4.9-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.9-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 63d24f1039f68abc46bf40a521f19720ca74a4d89a2b99d91dfd6216b43a81d74f672f74708efa6f6320058aa49bf13995638e3b8057efcfc84a2877527d56b6](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-linux-x64.tar.gz)

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
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 044ab6ccf316ac18882ecef98f9ec0a9afd8cf8509991a4f82a5778eefae5ad503bf5da915fd567d8ab17ab0a9d93318c59a8c20a8a45ab255eb25247bbe51a7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-win-x64.zip)

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
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.11.9.1 Checksum (MD5) fb4dafb0db003ed28bb977c6e64fd04c](https://corretto.aws/downloads/resources/11.0.11.9.1/amazon-corretto-11.0.11.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.29.1 Checksum (SHA256) 6aab2a1acce11a765d4dff2b9b5c8fe0feda5ff4067f16a696893786a3bf94c3](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 044ab6ccf316ac18882ecef98f9ec0a9afd8cf8509991a4f82a5778eefae5ad503bf5da915fd567d8ab17ab0a9d93318c59a8c20a8a45ab255eb25247bbe51a7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-win-x64.zip)

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
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.11.9.1 Checksum (MD5) fb4dafb0db003ed28bb977c6e64fd04c](https://corretto.aws/downloads/resources/11.0.11.9.1/amazon-corretto-11.0.11.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.29.1 Checksum (SHA256) 6aab2a1acce11a765d4dff2b9b5c8fe0feda5ff4067f16a696893786a3bf94c3](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 044ab6ccf316ac18882ecef98f9ec0a9afd8cf8509991a4f82a5778eefae5ad503bf5da915fd567d8ab17ab0a9d93318c59a8c20a8a45ab255eb25247bbe51a7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-win-x64.zip)

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
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 044ab6ccf316ac18882ecef98f9ec0a9afd8cf8509991a4f82a5778eefae5ad503bf5da915fd567d8ab17ab0a9d93318c59a8c20a8a45ab255eb25247bbe51a7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.11.9.1 Checksum (MD5) fb4dafb0db003ed28bb977c6e64fd04c](https://corretto.aws/downloads/resources/11.0.11.9.1/amazon-corretto-11.0.11.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.29.1 Checksum (SHA256) 6aab2a1acce11a765d4dff2b9b5c8fe0feda5ff4067f16a696893786a3bf94c3](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
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
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 044ab6ccf316ac18882ecef98f9ec0a9afd8cf8509991a4f82a5778eefae5ad503bf5da915fd567d8ab17ab0a9d93318c59a8c20a8a45ab255eb25247bbe51a7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.11.9.1 Checksum (MD5) fb4dafb0db003ed28bb977c6e64fd04c](https://corretto.aws/downloads/resources/11.0.11.9.1/amazon-corretto-11.0.11.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.29.1 Checksum (SHA256) 6aab2a1acce11a765d4dff2b9b5c8fe0feda5ff4067f16a696893786a3bf94c3](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
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
- [.NET SDK x64 v.3.1.409 Checksum (SHA512) 044ab6ccf316ac18882ecef98f9ec0a9afd8cf8509991a4f82a5778eefae5ad503bf5da915fd567d8ab17ab0a9d93318c59a8c20a8a45ab255eb25247bbe51a7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.409/dotnet-sdk-3.1.409-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.11.9.1 Checksum (MD5) fb4dafb0db003ed28bb977c6e64fd04c](https://corretto.aws/downloads/resources/11.0.11.9.1/amazon-corretto-11.0.11.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.29.1 Checksum (SHA256) 6aab2a1acce11a765d4dff2b9b5c8fe0feda5ff4067f16a696893786a3bf94c3](https://github.com/git-for-windows/git/releases/download/v2.29.1.windows.1/MinGit-2.29.1-64-bit.zip)
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

- [.NET SDK x64 v.5.0.203 Checksum (SHA512) 762ad53d66b893cb2cdf61540794a4a1e20b127e371f57f912ad8ebd4102aabf32366ebaabfe90aa362c1fae0bec0aa7ac6af35c6c0153fb913cd4c532149238](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.203/dotnet-sdk-5.0.203-win-x64.zip)
- [.NET SDK x64 v.5.0.203 Checksum (SHA512) 762ad53d66b893cb2cdf61540794a4a1e20b127e371f57f912ad8ebd4102aabf32366ebaabfe90aa362c1fae0bec0aa7ac6af35c6c0153fb913cd4c532149238](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.203/dotnet-sdk-5.0.203-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull jetbrains/teamcity-agent:2020.2.1-nanoserver-1809
echo TeamCity > context/.dockerignore
docker build -f "generated/windows/Agent/nanoserver/1809-dotnet/Dockerfile" -t teamcity-agent:nanoserver-dotnet "context"
```

_The required free space to generate image(s) is about **6 GB**._

