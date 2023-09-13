## teamcity-agent tags

Other tags

- [teamcity-minimal-agent](teamcity-minimal-agent.md)
- [teamcity-server](teamcity-server.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [latest](#latest)
- [2023.05.4](#2023054)
- [latest-windowsservercore](#latest-windowsservercore)
- [2023.05.4-windowsservercore](#2023054-windowsservercore)

#### linux

- 20.04-sudo
  - [2023.05.4-linux-arm64-sudo](#2023054-linux-arm64-sudo)
  - [2023.05.4-linux-sudo](#2023054-linux-sudo)
- 20.04
  - [2023.05.4-linux](#2023054-linux)
  - [2023.05.4-linux-arm64](#2023054-linux-arm64)
- 18.04-sudo
  - [2023.05.4-linux-18.04-sudo](#2023054-linux-1804-sudo)
  - [2023.05.4-linux-arm64-18.04-sudo](#2023054-linux-arm64-1804-sudo)
- 18.04
  - [2023.05.4-linux-18.04](#2023054-linux-1804)
  - [2023.05.4-linux-arm64-18.04](#2023054-linux-arm64-1804)

#### windows

- 2004
  - [2023.05.4-nanoserver-2004](#2023054-nanoserver-2004)
  - [2023.05.4-windowsservercore-2004](#2023054-windowsservercore-2004)
- 1909
  - [2023.05.4-nanoserver-1909](#2023054-nanoserver-1909)
  - [2023.05.4-windowsservercore-1909](#2023054-windowsservercore-1909)
- 1903
  - [2023.05.4-nanoserver-1903](#2023054-nanoserver-1903)
  - [2023.05.4-windowsservercore-1903](#2023054-windowsservercore-1903)
- 1809
  - [2023.05.4-nanoserver-1809](#2023054-nanoserver-1809)
  - [2023.05.4-windowsservercore-1809](#2023054-windowsservercore-1809)
- 1803
  - [2023.05.4-nanoserver-1803](#2023054-nanoserver-1803)
  - [2023.05.4-windowsservercore-1803](#2023054-windowsservercore-1803)


### latest

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2023.05.4-linux](#2023054-linux)
- [2023.05.4-linux-arm64](#2023054-linux-arm64)
- [2023.05.4-nanoserver-1809](#2023054-nanoserver-1809)
- [2023.05.4-nanoserver-2004](#2023054-nanoserver-2004)

### 2023.05.4

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2023.05.4-linux](#2023054-linux)
- [2023.05.4-linux-arm64](#2023054-linux-arm64)
- [2023.05.4-nanoserver-1809](#2023054-nanoserver-1809)
- [2023.05.4-nanoserver-2004](#2023054-nanoserver-2004)

### latest-windowsservercore

Supported platforms: windows 1809, windows 2004

#### Content

- [2023.05.4-windowsservercore-1809](#2023054-windowsservercore-1809)
- [2023.05.4-windowsservercore-2004](#2023054-windowsservercore-2004)

### 2023.05.4-windowsservercore

Supported platforms: windows 1809, windows 2004

#### Content

- [2023.05.4-windowsservercore-1809](#2023054-windowsservercore-1809)
- [2023.05.4-windowsservercore-2004](#2023054-windowsservercore-2004)


### 2023.05.4-linux

[Dockerfile](linux/Agent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- Git v.2.42.0
- Git LFS v.2.9.2
- Mercurial
- Perforce Helix Core client (p4) [2022.2-2468771](https://www.perforce.com/products/helix-core)
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12), [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET Runtime v.3.1.32 x86 Checksum (SHA512) 56c5e045844f5474a9a12b42e4a22c851985fac5690e227ce62b529d644c4faeaafdfe255de2f1e86a90c0c114e7de66ce4de1692fbf66357ac4d35341f933c3](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-linux-x64.tar.gz)
- [.NET Runtime v.5.0.17 x86 Checksum (SHA512) f47eec03d2b6777e78e2a993306727fa6871cf7f6e295ed70f684745a9f9fc960f0068750007519733426db52afa10e0bcd1c58879019d5d8f1f5f295868e4f3](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.17/dotnet-runtime-5.0.17-linux-x64.tar.gz)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) 4390ba65e592c4f770b247f11228bbd2ed70aab30fbe6613c2bf7ec0c7c54d09b5a47ff160d4424dacc59661613fd4c9460d62f141a64def02a7228dd160f916](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:2023.05.4-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:2023.05.4-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### 2023.05.4-linux-arm64

[Dockerfile](linux/Agent/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- Git v.2.42.0
- Git LFS v.2.9.2
- Mercurial
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12), [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET Runtime v.3.1.32 arm64 Checksum (SHA512) ff311df0db488f3b5cc03c7f6724f8442de7e60fa0a503ec8f536361ce7a357ad26d09d2499d68c50ebdfa751a5520bba4aaa77a38b191c892d5a018561ce422](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-linux-arm64.tar.gz)
- [.NET Runtime v.5.0.17 arm65 Checksum (SHA512) 99cb11871924d3abedcc9c8079c54bc0c550203c7cbe4e349ed70d4355f40e4620b68d90b797e6461d898c06bed6953580e2cd4ad01483e5de107ca5a3409610](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.17/dotnet-runtime-5.0.17-linux-arm64.tar.gz)
- [.NET SDK v.6.0.413 arm64 Checksum (SHA512) 7f05a9774d79e694da5a6115d9916abf87a65e40bd6bdaa5dca1f705795436bc8e764242f7045207386a86732ef5519f60bdb516a3860e4860bca7ee91a21759](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile" -t teamcity-minimal-agent:2023.05.4-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile" -t teamcity-agent:2023.05.4-linux-arm64 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### 2023.05.4-linux-arm64-sudo

[Dockerfile](linux/Agent/UbuntuARM/20.04-sudo/Dockerfile)

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
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile" -t teamcity-minimal-agent:2023.05.4-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile" -t teamcity-agent:2023.05.4-linux-arm64 "context"
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile" -t teamcity-agent:2023.05.4-linux-arm64-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### 2023.05.4-linux-sudo

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
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:2023.05.4-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:2023.05.4-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile" -t teamcity-agent:2023.05.4-linux-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### 2023.05.4-nanoserver-1809

[Dockerfile](windows/Agent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:2023.05.4-nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:2023.05.4-windowsservercore-1809 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1809/Dockerfile" -t teamcity-agent:2023.05.4-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### 2023.05.4-nanoserver-2004

[Dockerfile](windows/Agent/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:2004
docker pull mcr.microsoft.com/powershell:nanoserver-2004
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-2004
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:2023.05.4-nanoserver-2004 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:2023.05.4-windowsservercore-2004 "context"
docker build -f "context/generated/windows/Agent/nanoserver/2004/Dockerfile" -t teamcity-agent:2023.05.4-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### 2023.05.4-windowsservercore-1809

[Dockerfile](windows/Agent/windowsservercore/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime v.3.1.32 x86 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-win-x64.zip)
- [.NET Runtime v.5.0.17 x86 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.17/dotnet-runtime-5.0.17-win-x64.zip)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [Mercurial x64 v.5.9.1](https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:2023.05.4-nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:2023.05.4-windowsservercore-1809 "context"
```

_The required free space to generate image(s) is about **38 GB**._

### 2023.05.4-windowsservercore-2004

[Dockerfile](windows/Agent/windowsservercore/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime v.3.1.32 x86 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-win-x64.zip)
- [.NET Runtime v.5.0.17 x86 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.17/dotnet-runtime-5.0.17-win-x64.zip)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [Mercurial x64 v.5.9.1](https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:2004
docker pull mcr.microsoft.com/powershell:nanoserver-2004
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-2004
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:2023.05.4-nanoserver-2004 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:2023.05.4-windowsservercore-2004 "context"
```

_The required free space to generate image(s) is about **38 GB**._

### 2023.05.4-linux-18.04

[Dockerfile](linux/Agent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- Git v.2.41.0
- Git LFS v.2.3.4
- Mercurial
- Perforce Helix Core client (p4) [2022.2-2468771](https://www.perforce.com/products/helix-core)
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12), [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET Runtime v.3.1.32 x86 Checksum (SHA512) 56c5e045844f5474a9a12b42e4a22c851985fac5690e227ce62b529d644c4faeaafdfe255de2f1e86a90c0c114e7de66ce4de1692fbf66357ac4d35341f933c3](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-linux-x64.tar.gz)
- [.NET Runtime v.5.0.17 x86 Checksum (SHA512) f47eec03d2b6777e78e2a993306727fa6871cf7f6e295ed70f684745a9f9fc960f0068750007519733426db52afa10e0bcd1c58879019d5d8f1f5f295868e4f3](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.17/dotnet-runtime-5.0.17-linux-x64.tar.gz)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) 4390ba65e592c4f770b247f11228bbd2ed70aab30fbe6613c2bf7ec0c7c54d09b5a47ff160d4424dacc59661613fd4c9460d62f141a64def02a7228dd160f916](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:2023.05.4-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:2023.05.4-linux-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### 2023.05.4-linux-18.04-sudo

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
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:2023.05.4-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:2023.05.4-linux-18.04 "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile" -t teamcity-agent:2023.05.4-linux-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### 2023.05.4-linux-arm64-18.04

[Dockerfile](linux/Agent/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- Git v.2.41.0
- Git LFS v.2.3.4
- Mercurial
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12), [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET Runtime v.3.1.32 arm64 Checksum (SHA512) ff311df0db488f3b5cc03c7f6724f8442de7e60fa0a503ec8f536361ce7a357ad26d09d2499d68c50ebdfa751a5520bba4aaa77a38b191c892d5a018561ce422](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-linux-arm64.tar.gz)
- [.NET Runtime v.5.0.17 arm65 Checksum (SHA512) 99cb11871924d3abedcc9c8079c54bc0c550203c7cbe4e349ed70d4355f40e4620b68d90b797e6461d898c06bed6953580e2cd4ad01483e5de107ca5a3409610](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.17/dotnet-runtime-5.0.17-linux-arm64.tar.gz)
- [.NET SDK v.6.0.413 arm64 Checksum (SHA512) 7f05a9774d79e694da5a6115d9916abf87a65e40bd6bdaa5dca1f705795436bc8e764242f7045207386a86732ef5519f60bdb516a3860e4860bca7ee91a21759](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/18.04/Dockerfile" -t teamcity-minimal-agent:2023.05.4-linux-arm64-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/18.04/Dockerfile" -t teamcity-agent:2023.05.4-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### 2023.05.4-linux-arm64-18.04-sudo

[Dockerfile](linux/Agent/UbuntuARM/18.04-sudo/Dockerfile)

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
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/18.04/Dockerfile" -t teamcity-minimal-agent:2023.05.4-linux-arm64-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/18.04/Dockerfile" -t teamcity-agent:2023.05.4-linux-arm64-18.04 "context"
docker build -f "context/generated/linux/Agent/UbuntuARM/18.04-sudo/Dockerfile" -t teamcity-agent:2023.05.4-linux-arm64-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### 2023.05.4-nanoserver-1803

[Dockerfile](windows/Agent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:2023.05.4-nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:2023.05.4-windowsservercore-1803 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1803/Dockerfile" -t teamcity-agent:2023.05.4-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **36 GB**._

### 2023.05.4-nanoserver-1903

[Dockerfile](windows/Agent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:2023.05.4-nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:2023.05.4-windowsservercore-1903 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1903/Dockerfile" -t teamcity-agent:2023.05.4-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### 2023.05.4-nanoserver-1909

[Dockerfile](windows/Agent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:2023.05.4-nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:2023.05.4-windowsservercore-1909 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1909/Dockerfile" -t teamcity-agent:2023.05.4-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### 2023.05.4-windowsservercore-1803

[Dockerfile](windows/Agent/windowsservercore/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime v.3.1.32 x86 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-win-x64.zip)
- [.NET Runtime v.5.0.17 x86 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.17/dotnet-runtime-5.0.17-win-x64.zip)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [Mercurial x64 v.5.9.1](https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:2023.05.4-nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:2023.05.4-windowsservercore-1803 "context"
```

_The required free space to generate image(s) is about **34 GB**._

### 2023.05.4-windowsservercore-1903

[Dockerfile](windows/Agent/windowsservercore/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime v.3.1.32 x86 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-win-x64.zip)
- [.NET Runtime v.5.0.17 x86 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.17/dotnet-runtime-5.0.17-win-x64.zip)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [Mercurial x64 v.5.9.1](https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:2023.05.4-nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:2023.05.4-windowsservercore-1903 "context"
```

_The required free space to generate image(s) is about **38 GB**._

### 2023.05.4-windowsservercore-1909

[Dockerfile](windows/Agent/windowsservercore/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime v.3.1.32 x86 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-win-x64.zip)
- [.NET Runtime v.5.0.17 x86 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.17/dotnet-runtime-5.0.17-win-x64.zip)
- [.NET SDK v.6.0.413 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.42.0 Checksum (SHA256) b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip)
- [Mercurial x64 v.5.9.1](https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:2023.05.4-nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:2023.05.4-windowsservercore-1909 "context"
```

_The required free space to generate image(s) is about **38 GB**._

