## teamcity-agent tags

Other tags

- [teamcity-minimal-agent](teamcity-minimal-agent.md)
- [teamcity-server](teamcity-server.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [latest](#latest)
- [2021.2.2](#202122)
- [latest-windowsservercore](#latest-windowsservercore)
- [2021.2.2-windowsservercore](#202122-windowsservercore)

#### linux

- 20.04-sudo
  - [2021.2.2-linux-sudo](#202122-linux-sudo)
- 20.04
  - [2021.2.2-linux](#202122-linux)
- 18.04-sudo
  - [2021.2.2-linux-18.04-sudo](#202122-linux-1804-sudo)
- 18.04
  - [2021.2.2-linux-18.04](#202122-linux-1804)

#### windows

- 2004
  - [2021.2.2-nanoserver-2004](#202122-nanoserver-2004)
  - [2021.2.2-windowsservercore-2004](#202122-windowsservercore-2004)
- 1909
  - [2021.2.2-nanoserver-1909](#202122-nanoserver-1909)
  - [2021.2.2-windowsservercore-1909](#202122-windowsservercore-1909)
- 1903
  - [2021.2.2-nanoserver-1903](#202122-nanoserver-1903)
  - [2021.2.2-windowsservercore-1903](#202122-windowsservercore-1903)
- 1809
  - [2021.2.2-nanoserver-1809](#202122-nanoserver-1809)
  - [2021.2.2-windowsservercore-1809](#202122-windowsservercore-1809)
- 1803
  - [2021.2.2-nanoserver-1803](#202122-nanoserver-1803)
  - [2021.2.2-windowsservercore-1803](#202122-windowsservercore-1803)


### latest

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2021.2.2-linux](#202122-linux)
- [2021.2.2-nanoserver-1809](#202122-nanoserver-1809)
- [2021.2.2-nanoserver-2004](#202122-nanoserver-2004)

### 2021.2.2

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2021.2.2-linux](#202122-linux)
- [2021.2.2-nanoserver-1809](#202122-nanoserver-1809)
- [2021.2.2-nanoserver-2004](#202122-nanoserver-2004)

### latest-windowsservercore

Supported platforms: windows 1809, windows 2004

#### Content

- [2021.2.2-windowsservercore-1809](#202122-windowsservercore-1809)
- [2021.2.2-windowsservercore-2004](#202122-windowsservercore-2004)

### 2021.2.2-windowsservercore

Supported platforms: windows 1809, windows 2004

#### Content

- [2021.2.2-windowsservercore-1809](#202122-windowsservercore-1809)
- [2021.2.2-windowsservercore-2004](#202122-windowsservercore-2004)


### 2021.2.2-linux

[Dockerfile](linux/Agent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- Git v.2.25.1
- Mercurial
- Perforce Helix Core client (p4) [2021.2-2220431](https://www.perforce.com/products/helix-core)
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12), [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET Runtime x64 v.3.1.21 Checksum (SHA512) cc4b2fef46e94df88bf0fc11cb15439e79bd48da524561dffde80d3cd6db218133468ad2f6785803cf0c13f000d95ff71eb258cec76dd8eb809676ec1cb38fac](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-linux-x64.tar.gz)
- [.NET Runtime x64 v.5.0.12 Checksum (SHA512) 32b5f86db3b1d4c21e3cf616d22f0e4a7374385dac0cf03cdebf3520dcf846460d9677ec1829a180920740a0237d64f6eaa2421d036a67f4fe9fb15d4f6b1db9](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-linux-x64.tar.gz)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) cb0d174a79d6294c302261b645dba6a479da8f7cf6c1fe15ae6998bc09c5e0baec810822f9e0104e84b0efd51fdc0333306cb2a0a6fcdbaf515a8ad8cf1af25b](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:2021.2.2-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:2021.2.2-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### 2021.2.2-linux-sudo

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
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:2021.2.2-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:2021.2.2-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile" -t teamcity-agent:2021.2.2-linux-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### 2021.2.2-nanoserver-1809

[Dockerfile](windows/Agent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.12.7.1 Checksum (MD5) 022bf679bc9c337287859264496c2f38](https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:2021.2.2-nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:2021.2.2-windowsservercore-1809 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1809/Dockerfile" -t teamcity-agent:2021.2.2-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### 2021.2.2-nanoserver-2004

[Dockerfile](windows/Agent/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.12.7.1 Checksum (MD5) 022bf679bc9c337287859264496c2f38](https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:2004
docker pull mcr.microsoft.com/powershell:nanoserver-2004
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-2004
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:2021.2.2-nanoserver-2004 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:2021.2.2-windowsservercore-2004 "context"
docker build -f "context/generated/windows/Agent/nanoserver/2004/Dockerfile" -t teamcity-agent:2021.2.2-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### 2021.2.2-windowsservercore-1809

[Dockerfile](windows/Agent/windowsservercore/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime x64 v.3.1.21 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-win-x64.zip)
- [.NET Runtime x64 v.5.0.12 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-win-x64.zip)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.12.7.1 Checksum (MD5) 022bf679bc9c337287859264496c2f38](https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
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
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:2021.2.2-nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:2021.2.2-windowsservercore-1809 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### 2021.2.2-windowsservercore-2004

[Dockerfile](windows/Agent/windowsservercore/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime x64 v.3.1.21 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-win-x64.zip)
- [.NET Runtime x64 v.5.0.12 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-win-x64.zip)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.12.7.1 Checksum (MD5) 022bf679bc9c337287859264496c2f38](https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
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
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:2021.2.2-nanoserver-2004 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:2021.2.2-windowsservercore-2004 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### 2021.2.2-linux-18.04

[Dockerfile](linux/Agent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- Git v.2.17.1
- Mercurial
- Perforce Helix Core client (p4) [2021.2-2220431](https://www.perforce.com/products/helix-core)
- [Docker v.5:20.10.12](https://github.com/docker/cli/releases/tag/v20.10.12), [Containerd.io v1.4.12-1](https://ubuntu.pkgs.org/20.04/docker-ce-stable-amd64/containerd.io_1.4.12-1_amd64.deb.html)
- [Docker Compose v.1.28.5](https://github.com/docker/compose/releases/tag/1.28.5)
- [.NET Runtime x64 v.3.1.21 Checksum (SHA512) cc4b2fef46e94df88bf0fc11cb15439e79bd48da524561dffde80d3cd6db218133468ad2f6785803cf0c13f000d95ff71eb258cec76dd8eb809676ec1cb38fac](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-linux-x64.tar.gz)
- [.NET Runtime x64 v.5.0.12 Checksum (SHA512) 32b5f86db3b1d4c21e3cf616d22f0e4a7374385dac0cf03cdebf3520dcf846460d9677ec1829a180920740a0237d64f6eaa2421d036a67f4fe9fb15d4f6b1db9](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-linux-x64.tar.gz)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) cb0d174a79d6294c302261b645dba6a479da8f7cf6c1fe15ae6998bc09c5e0baec810822f9e0104e84b0efd51fdc0333306cb2a0a6fcdbaf515a8ad8cf1af25b](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:2021.2.2-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:2021.2.2-linux-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### 2021.2.2-linux-18.04-sudo

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
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:2021.2.2-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:2021.2.2-linux-18.04 "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile" -t teamcity-agent:2021.2.2-linux-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### 2021.2.2-nanoserver-1803

[Dockerfile](windows/Agent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:2021.2.2-nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:2021.2.2-windowsservercore-1803 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1803/Dockerfile" -t teamcity-agent:2021.2.2-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### 2021.2.2-nanoserver-1903

[Dockerfile](windows/Agent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.12.7.1 Checksum (MD5) 022bf679bc9c337287859264496c2f38](https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:2021.2.2-nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:2021.2.2-windowsservercore-1903 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1903/Dockerfile" -t teamcity-agent:2021.2.2-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### 2021.2.2-nanoserver-1909

[Dockerfile](windows/Agent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.12.7.1 Checksum (MD5) 022bf679bc9c337287859264496c2f38](https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:2021.2.2-nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:2021.2.2-windowsservercore-1909 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1909/Dockerfile" -t teamcity-agent:2021.2.2-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **35 GB**._

### 2021.2.2-windowsservercore-1803

[Dockerfile](windows/Agent/windowsservercore/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime x64 v.3.1.21 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-win-x64.zip)
- [.NET Runtime x64 v.5.0.12 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-win-x64.zip)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.12.7.1 Checksum (MD5) 022bf679bc9c337287859264496c2f38](https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
- [Mercurial x64 v.5.9.1](https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:2021.2.2-nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:2021.2.2-windowsservercore-1803 "context"
```

_The required free space to generate image(s) is about **31 GB**._

### 2021.2.2-windowsservercore-1903

[Dockerfile](windows/Agent/windowsservercore/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime x64 v.3.1.21 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-win-x64.zip)
- [.NET Runtime x64 v.5.0.12 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-win-x64.zip)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.12.7.1 Checksum (MD5) 022bf679bc9c337287859264496c2f38](https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
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
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:2021.2.2-nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:2021.2.2-windowsservercore-1903 "context"
```

_The required free space to generate image(s) is about **33 GB**._

### 2021.2.2-windowsservercore-1909

[Dockerfile](windows/Agent/windowsservercore/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime x64 v.3.1.21 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-win-x64.zip)
- [.NET Runtime x64 v.5.0.12 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-win-x64.zip)
- [.NET SDK x64 v.6.0.100 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.12.7.1 Checksum (MD5) 022bf679bc9c337287859264496c2f38](https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
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
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:2021.2.2-nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:2021.2.2-windowsservercore-1909 "context"
```

_The required free space to generate image(s) is about **33 GB**._

