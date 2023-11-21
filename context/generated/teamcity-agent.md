## teamcity-agent tags

Other tags

- [teamcity-minimal-agent](teamcity-minimal-agent.md)
- [teamcity-server](teamcity-server.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [EAP](#EAP)
- [EAP-windowsservercore](#EAP-windowsservercore)

#### linux

- 20.04-sudo
  - [EAP-linux-arm64-sudo](#EAP-linux-arm64-sudo)
  - [EAP-linux-sudo](#EAP-linux-sudo)
- 20.04
  - [EAP-linux](#EAP-linux)
  - [EAP-linux-arm64](#EAP-linux-arm64)
- 18.04-sudo
  - [EAP-linux-18.04-sudo](#EAP-linux-1804-sudo)
  - [EAP-linux-arm64-18.04-sudo](#EAP-linux-arm64-1804-sudo)
- 18.04
  - [EAP-linux-18.04](#EAP-linux-1804)
  - [EAP-linux-arm64-18.04](#EAP-linux-arm64-1804)

#### windows

- 2022
  - [EAP-nanoserver-2022](#EAP-nanoserver-2022)
  - [EAP-windowsservercore-2022](#EAP-windowsservercore-2022)
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

Supported platforms: linux 20.04, windows 1809, windows 2022

#### Content

- [EAP-linux](#EAP-linux)
- [EAP-linux-arm64](#EAP-linux-arm64)
- [EAP-nanoserver-1809](#EAP-nanoserver-1809)
- [EAP-nanoserver-2022](#EAP-nanoserver-2022)

### EAP-windowsservercore

Supported platforms: windows 1809, windows 2022

#### Content

- [EAP-windowsservercore-1809](#EAP-windowsservercore-1809)
- [EAP-windowsservercore-2022](#EAP-windowsservercore-2022)


### EAP-linux

[Dockerfile](linux/Agent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) 443750a02c28ff2807c80032ee2e8ebc](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) 443750a02c28ff2807c80032ee2e8ebc](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS v.2.9.2
- Git v.2.42.0
- Mercurial
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) ee0a77d54e6d4917be7310ff0abb3bad5525bfb4beb1db0c215e65f64eb46511f5f12d6c7ff465a1d4ab38577e6a1950fde479ee94839c50e627020328a702de](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2022.2-2468771](https://www.perforce.com/products/helix-core)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-arm64

[Dockerfile](linux/Agent/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.17.0.7.7.1 Checksum (MD5) c55e3d0615fac07f948ac3adaed818e9](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.42.0
- Git LFS v.2.9.2
- Mercurial
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12)
- [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET SDK v.6.0.413 (LTS) ARM64 Checksum (SHA512) 7f05a9774d79e694da5a6115d9916abf87a65e40bd6bdaa5dca1f705795436bc8e764242f7045207386a86732ef5519f60bdb516a3860e4860bca7ee91a21759](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-arm64-sudo

[Dockerfile](linux/Agent/UbuntuARM/20.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.17.0.7.7.1 Checksum (MD5) c55e3d0615fac07f948ac3adaed818e9](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.42.0
- Git LFS v.2.9.2
- Mercurial
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12)
- [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET SDK v.6.0.413 (LTS) ARM64 Checksum (SHA512) 7f05a9774d79e694da5a6115d9916abf87a65e40bd6bdaa5dca1f705795436bc8e764242f7045207386a86732ef5519f60bdb516a3860e4860bca7ee91a21759](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-arm64-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-linux-sudo

[Dockerfile](linux/Agent/Ubuntu/20.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) 443750a02c28ff2807c80032ee2e8ebc](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS v.2.9.2
- Git v.2.42.0
- Mercurial
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) ee0a77d54e6d4917be7310ff0abb3bad5525bfb4beb1db0c215e65f64eb46511f5f12d6c7ff465a1d4ab38577e6a1950fde479ee94839c50e627020328a702de](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2022.2-2468771](https://www.perforce.com/products/helix-core)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) a9e1bbb52484ad0667b258451ebb6b47ce6c7b788c015aee8a86c5e0c4dcf4ee8c82d796921869d64c92bb2afef2c7ceea09cfe255d8519d48f2471a098c361e](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

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

_The required free space to generate image(s) is about **40 GB**._

### EAP-nanoserver-2022

[Dockerfile](windows/Agent/nanoserver/2022/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) a9e1bbb52484ad0667b258451ebb6b47ce6c7b788c015aee8a86c5e0c4dcf4ee8c82d796921869d64c92bb2afef2c7ceea09cfe255d8519d48f2471a098c361e](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:ltsc2022
docker pull mcr.microsoft.com/powershell:nanoserver-ltsc2022
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2022
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2022/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-2022 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2022/Dockerfile" -t teamcity-agent:EAP-windowsservercore-2022 "context"
docker build -f "context/generated/windows/Agent/nanoserver/2022/Dockerfile" -t teamcity-agent:EAP-nanoserver-2022 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### EAP-windowsservercore-1809

[Dockerfile](windows/Agent/windowsservercore/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.5.9.1](https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) a9e1bbb52484ad0667b258451ebb6b47ce6c7b788c015aee8a86c5e0c4dcf4ee8c82d796921869d64c92bb2afef2c7ceea09cfe255d8519d48f2471a098c361e](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

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

_The required free space to generate image(s) is about **38 GB**._

### EAP-windowsservercore-2022

[Dockerfile](windows/Agent/windowsservercore/2022/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.5.9.1](https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) a9e1bbb52484ad0667b258451ebb6b47ce6c7b788c015aee8a86c5e0c4dcf4ee8c82d796921869d64c92bb2afef2c7ceea09cfe255d8519d48f2471a098c361e](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:ltsc2022
docker pull mcr.microsoft.com/powershell:nanoserver-ltsc2022
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2022
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2022/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-2022 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2022/Dockerfile" -t teamcity-agent:EAP-windowsservercore-2022 "context"
```

_The required free space to generate image(s) is about **38 GB**._

### EAP-linux-18.04

[Dockerfile](linux/Agent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) 443750a02c28ff2807c80032ee2e8ebc](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) 443750a02c28ff2807c80032ee2e8ebc](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS v.2.3.4
- Git v.2.41.0
- Mercurial
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) ee0a77d54e6d4917be7310ff0abb3bad5525bfb4beb1db0c215e65f64eb46511f5f12d6c7ff465a1d4ab38577e6a1950fde479ee94839c50e627020328a702de](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2022.2-2468771](https://www.perforce.com/products/helix-core)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:EAP-linux-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-18.04-sudo

[Dockerfile](linux/Agent/Ubuntu/18.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) 443750a02c28ff2807c80032ee2e8ebc](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS v.2.9.2
- Git v.2.42.0
- Mercurial
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) ee0a77d54e6d4917be7310ff0abb3bad5525bfb4beb1db0c215e65f64eb46511f5f12d6c7ff465a1d4ab38577e6a1950fde479ee94839c50e627020328a702de](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2022.2-2468771](https://www.perforce.com/products/helix-core)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:EAP-linux-18.04 "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-linux-arm64-18.04

[Dockerfile](linux/Agent/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.17.0.7.7.1 Checksum (MD5) c55e3d0615fac07f948ac3adaed818e9](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.41.0
- Git LFS v.2.3.4
- Mercurial
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12)
- [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET SDK v.6.0.413 (LTS) ARM64 Checksum (SHA512) 7f05a9774d79e694da5a6115d9916abf87a65e40bd6bdaa5dca1f705795436bc8e764242f7045207386a86732ef5519f60bdb516a3860e4860bca7ee91a21759](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/18.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-arm64-18.04-sudo

[Dockerfile](linux/Agent/UbuntuARM/18.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.17.0.7.7.1 Checksum (MD5) c55e3d0615fac07f948ac3adaed818e9](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.42.0
- Git LFS v.2.9.2
- Mercurial
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12)
- [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET SDK v.6.0.413 (LTS) ARM64 Checksum (SHA512) 7f05a9774d79e694da5a6115d9916abf87a65e40bd6bdaa5dca1f705795436bc8e764242f7045207386a86732ef5519f60bdb516a3860e4860bca7ee91a21759](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/18.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64-18.04 "context"
docker build -f "context/generated/linux/Agent/UbuntuARM/18.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-arm64-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-nanoserver-1803

[Dockerfile](windows/Agent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) a9e1bbb52484ad0667b258451ebb6b47ce6c7b788c015aee8a86c5e0c4dcf4ee8c82d796921869d64c92bb2afef2c7ceea09cfe255d8519d48f2471a098c361e](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

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

_The required free space to generate image(s) is about **36 GB**._

### EAP-nanoserver-1903

[Dockerfile](windows/Agent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) a9e1bbb52484ad0667b258451ebb6b47ce6c7b788c015aee8a86c5e0c4dcf4ee8c82d796921869d64c92bb2afef2c7ceea09cfe255d8519d48f2471a098c361e](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

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

_The required free space to generate image(s) is about **40 GB**._

### EAP-nanoserver-1909

[Dockerfile](windows/Agent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) a9e1bbb52484ad0667b258451ebb6b47ce6c7b788c015aee8a86c5e0c4dcf4ee8c82d796921869d64c92bb2afef2c7ceea09cfe255d8519d48f2471a098c361e](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

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

_The required free space to generate image(s) is about **40 GB**._

### EAP-windowsservercore-1803

[Dockerfile](windows/Agent/windowsservercore/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.5.9.1](https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) a9e1bbb52484ad0667b258451ebb6b47ce6c7b788c015aee8a86c5e0c4dcf4ee8c82d796921869d64c92bb2afef2c7ceea09cfe255d8519d48f2471a098c361e](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

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

_The required free space to generate image(s) is about **34 GB**._

### EAP-windowsservercore-1903

[Dockerfile](windows/Agent/windowsservercore/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.5.9.1](https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) a9e1bbb52484ad0667b258451ebb6b47ce6c7b788c015aee8a86c5e0c4dcf4ee8c82d796921869d64c92bb2afef2c7ceea09cfe255d8519d48f2471a098c361e](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

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

_The required free space to generate image(s) is about **38 GB**._

### EAP-windowsservercore-1909

[Dockerfile](windows/Agent/windowsservercore/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.5.9.1](https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi)
- [.NET SDK v.6.0.413 (LTS) x86 Checksum (SHA512) a9e1bbb52484ad0667b258451ebb6b47ce6c7b788c015aee8a86c5e0c4dcf4ee8c82d796921869d64c92bb2afef2c7ceea09cfe255d8519d48f2471a098c361e](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

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

_The required free space to generate image(s) is about **38 GB**._

