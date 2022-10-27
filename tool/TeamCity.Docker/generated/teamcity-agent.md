## teamcity-agent tags

Other tags

- [teamcity-minimal-agent](teamcity-minimal-agent.md)
- [teamcity-server](teamcity-server.md)

#### ${linuxPlatform}

- 20.04-sudo
  - [${versionTag}-linux-arm64-sudo](#${versionTag}-linux-arm64-sudo)
  - [${versionTag}-linux-sudo](#${versionTag}-linux-sudo)
- 20.04
  - [${versionTag}-linux](#${versionTag}-linux)
  - [${versionTag}-linux-arm64](#${versionTag}-linux-arm64)
- 18.04-sudo
  - [${versionTag}-linux-18.04-sudo](#${versionTag}-linux-1804-sudo)
  - [${versionTag}-linux-arm64-18.04-sudo](#${versionTag}-linux-arm64-1804-sudo)
- 18.04
  - [${versionTag}-linux-18.04](#${versionTag}-linux-1804)
  - [${versionTag}-linux-arm64-18.04](#${versionTag}-linux-arm64-1804)

#### windows

- 2004
  - [${versionTag}-nanoserver-2004](#${versionTag}-nanoserver-2004)
  - [${versionTag}-windowsservercore-2004](#${versionTag}-windowsservercore-2004)
- 1909
  - [${versionTag}-nanoserver-1909](#${versionTag}-nanoserver-1909)
  - [${versionTag}-windowsservercore-1909](#${versionTag}-windowsservercore-1909)
- 1903
  - [${versionTag}-nanoserver-1903](#${versionTag}-nanoserver-1903)
  - [${versionTag}-windowsservercore-1903](#${versionTag}-windowsservercore-1903)
- 1809
  - [${versionTag}-nanoserver-1809](#${versionTag}-nanoserver-1809)
  - [${versionTag}-windowsservercore-1809](#${versionTag}-windowsservercore-1809)
- 1803
  - [${versionTag}-nanoserver-1803](#${versionTag}-nanoserver-1803)
  - [${versionTag}-windowsservercore-1803](#${versionTag}-windowsservercore-1803)



### ${versionTag}-linux

[Dockerfile](linux/Agent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- ${gitLinuxComponentName}
- ${gitLFSLinuxComponentName}
- Mercurial
- ${p4Name}
- ${dockerLinuxComponentName}, ${containerdIoLinuxComponentName}
- [Docker Compose v.${dockerComposeLinuxComponentVersion}](https://github.com/docker/compose/releases/tag/${dockerComposeLinuxComponentVersion})
- [${dotnetLinuxComponentName_31}](${dotnetLinuxComponent_31})
- [${dotnetLinuxComponentName_50}](${dotnetLinuxComponent_50})
- [${dotnetLinuxComponentName}](${dotnetLinuxComponent})

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:${versionTag}-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:${versionTag}-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### ${versionTag}-linux-arm64

[Dockerfile](linux/Agent/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- ${gitLinuxComponentName}
- ${gitLFSLinuxComponentName}
- Mercurial
- ${dockerLinuxComponentName}, ${containerdIoLinuxComponentName}
- [Docker Compose v.${dockerComposeLinuxComponentVersion}](https://github.com/docker/compose/releases/tag/${dockerComposeLinuxComponentVersion})
- [.NET Runtime v.3.1.21 arm64 Checksum (SHA512) 80971125650a2fa0163e39a2de98bc2e871c295b723559e6081a3ab59d99195aa5b794450f8182c5eb4e7e472ca1c13340ef1cc8a5588114c494bbb5981f19c4](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-linux-arm64.tar.gz)
- [.NET Runtime v.5.0.12 arm65 Checksum (SHA512) a8089fad8d21a4b582aa6c3d7162d56a21fee697fd400f050a772f67c2ace5e4196d1c4261d3e861d6dc2e5439666f112c406104d6271e5ab60cda80ef2ffc64](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-linux-arm64.tar.gz)
- [.NET SDK v.6.0.100 arm64 Checksum (SHA512) e5983c1c599d6dc7c3c7496b9698e47c68247f04a5d0d1e3162969d071471297bce1c2fd3a1f9fb88645006c327ae79f880dcbdd8eefc9166fd717331f2716e7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-linux-arm64.tar.gz)

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile" -t teamcity-minimal-agent:${versionTag}-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/UbuntuARM/20.04/Dockerfile" -t teamcity-agent:${versionTag}-linux-arm64 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### ${versionTag}-linux-arm64-sudo

[Dockerfile](linux/Agent/UbuntuARM/20.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile" -t teamcity-minimal-agent:${versionTag}-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/UbuntuARM/20.04/Dockerfile" -t teamcity-agent:${versionTag}-linux-arm64 "context"
docker build -f "generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile" -t teamcity-agent:${versionTag}-linux-arm64-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### ${versionTag}-linux-sudo

[Dockerfile](linux/Agent/Ubuntu/20.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:${versionTag}-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:${versionTag}-linux "context"
docker build -f "generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile" -t teamcity-agent:${versionTag}-linux-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### ${versionTag}-nanoserver-1809

[Dockerfile](windows/Agent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
- [.NET SDK v.6.0.100 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-1809 "context"
docker build -f "generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-1809 "context"
docker build -f "generated/windows/Agent/nanoserver/1809/Dockerfile" -t teamcity-agent:${versionTag}-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### ${versionTag}-nanoserver-2004

[Dockerfile](windows/Agent/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
- [.NET SDK v.6.0.100 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:2004
docker pull mcr.microsoft.com/powershell:nanoserver-2004
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-2004
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-2004 "context"
docker build -f "generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-2004 "context"
docker build -f "generated/windows/Agent/nanoserver/2004/Dockerfile" -t teamcity-agent:${versionTag}-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### ${versionTag}-windowsservercore-1809

[Dockerfile](windows/Agent/windowsservercore/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime v.3.1.21 x86 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-win-x64.zip)
- [.NET Runtime v.5.0.12 x86 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-win-x64.zip)
- [.NET SDK v.6.0.100 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
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
docker build -f "generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-1809 "context"
docker build -f "generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-1809 "context"
```

_The required free space to generate image(s) is about **38 GB**._

### ${versionTag}-windowsservercore-2004

[Dockerfile](windows/Agent/windowsservercore/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime v.3.1.21 x86 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-win-x64.zip)
- [.NET Runtime v.5.0.12 x86 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-win-x64.zip)
- [.NET SDK v.6.0.100 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
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
docker build -f "generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-2004 "context"
docker build -f "generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-2004 "context"
```

_The required free space to generate image(s) is about **38 GB**._

### ${versionTag}-linux-18.04

[Dockerfile](linux/Agent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- Git v.2.38.0
- Git LFS v.2.3.4
- Mercurial
- ${p4Name}
- ${dockerLinuxComponentName}, ${containerdIoLinuxComponentName}
- [Docker Compose v.${dockerComposeLinuxComponentVersion}](https://github.com/docker/compose/releases/tag/${dockerComposeLinuxComponentVersion})
- [${dotnetLinuxComponentName_31}](${dotnetLinuxComponent_31})
- [${dotnetLinuxComponentName_50}](${dotnetLinuxComponent_50})
- [${dotnetLinuxComponentName}](${dotnetLinuxComponent})

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:${versionTag}-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:${versionTag}-linux-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### ${versionTag}-linux-18.04-sudo

[Dockerfile](linux/Agent/Ubuntu/18.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.
The docker image is not available and may be created manually.

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:${versionTag}-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:${versionTag}-linux-18.04 "context"
docker build -f "generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile" -t teamcity-agent:${versionTag}-linux-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### ${versionTag}-linux-arm64-18.04

[Dockerfile](linux/Agent/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- Git v.2.38.0
- Git LFS v.2.3.4
- Mercurial
- ${dockerLinuxComponentName}, ${containerdIoLinuxComponentName}
- [Docker Compose v.${dockerComposeLinuxComponentVersion}](https://github.com/docker/compose/releases/tag/${dockerComposeLinuxComponentVersion})
- [.NET Runtime v.3.1.21 arm64 Checksum (SHA512) 80971125650a2fa0163e39a2de98bc2e871c295b723559e6081a3ab59d99195aa5b794450f8182c5eb4e7e472ca1c13340ef1cc8a5588114c494bbb5981f19c4](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-linux-arm64.tar.gz)
- [.NET Runtime v.5.0.12 arm65 Checksum (SHA512) a8089fad8d21a4b582aa6c3d7162d56a21fee697fd400f050a772f67c2ace5e4196d1c4261d3e861d6dc2e5439666f112c406104d6271e5ab60cda80ef2ffc64](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-linux-arm64.tar.gz)
- [.NET SDK v.6.0.100 arm64 Checksum (SHA512) e5983c1c599d6dc7c3c7496b9698e47c68247f04a5d0d1e3162969d071471297bce1c2fd3a1f9fb88645006c327ae79f880dcbdd8eefc9166fd717331f2716e7](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-linux-arm64.tar.gz)

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/UbuntuARM/18.04/Dockerfile" -t teamcity-minimal-agent:${versionTag}-linux-arm64-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/UbuntuARM/18.04/Dockerfile" -t teamcity-agent:${versionTag}-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### ${versionTag}-linux-arm64-18.04-sudo

[Dockerfile](linux/Agent/UbuntuARM/18.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.
The docker image is not available and may be created manually.

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/UbuntuARM/18.04/Dockerfile" -t teamcity-minimal-agent:${versionTag}-linux-arm64-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "generated/linux/Agent/UbuntuARM/18.04/Dockerfile" -t teamcity-agent:${versionTag}-linux-arm64-18.04 "context"
docker build -f "generated/linux/Agent/UbuntuARM/18.04-sudo/Dockerfile" -t teamcity-agent:${versionTag}-linux-arm64-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### ${versionTag}-nanoserver-1803

[Dockerfile](windows/Agent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK v.6.0.100 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-1803 "context"
docker build -f "generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-1803 "context"
docker build -f "generated/windows/Agent/nanoserver/1803/Dockerfile" -t teamcity-agent:${versionTag}-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **36 GB**._

### ${versionTag}-nanoserver-1903

[Dockerfile](windows/Agent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
- [.NET SDK v.6.0.100 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-1903 "context"
docker build -f "generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-1903 "context"
docker build -f "generated/windows/Agent/nanoserver/1903/Dockerfile" -t teamcity-agent:${versionTag}-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### ${versionTag}-nanoserver-1909

[Dockerfile](windows/Agent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)
- [.NET SDK v.6.0.100 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-1909 "context"
docker build -f "generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-1909 "context"
docker build -f "generated/windows/Agent/nanoserver/1909/Dockerfile" -t teamcity-agent:${versionTag}-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### ${versionTag}-windowsservercore-1803

[Dockerfile](windows/Agent/windowsservercore/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime v.3.1.21 x86 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-win-x64.zip)
- [.NET Runtime v.5.0.12 x86 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-win-x64.zip)
- [.NET SDK v.6.0.100 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
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
docker build -f "generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-1803 "context"
docker build -f "generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-1803 "context"
```

_The required free space to generate image(s) is about **34 GB**._

### ${versionTag}-windowsservercore-1903

[Dockerfile](windows/Agent/windowsservercore/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime v.3.1.21 x86 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-win-x64.zip)
- [.NET Runtime v.5.0.12 x86 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-win-x64.zip)
- [.NET SDK v.6.0.100 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
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
docker build -f "generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-1903 "context"
docker build -f "generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-1903 "context"
```

_The required free space to generate image(s) is about **38 GB**._

### ${versionTag}-windowsservercore-1909

[Dockerfile](windows/Agent/windowsservercore/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET Runtime v.3.1.21 x86 Checksum (SHA512) 7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44](https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-win-x64.zip)
- [.NET Runtime v.5.0.12 x86 Checksum (SHA512) 636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1](https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-win-x64.zip)
- [.NET SDK v.6.0.100 x86 Checksum (SHA512) d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
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
docker build -f "generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-1909 "context"
docker build -f "generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-1909 "context"
```

_The required free space to generate image(s) is about **38 GB**._

