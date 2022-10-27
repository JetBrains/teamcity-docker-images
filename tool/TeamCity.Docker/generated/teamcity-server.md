## teamcity-server tags

Other tags

- [teamcity-agent](teamcity-agent.md)
- [teamcity-minimal-agent](teamcity-minimal-agent.md)

#### ${linuxPlatform}

- 20.04
  - [${versionTag}-linux](#${versionTag}-linux)
  - [${versionTag}-linux-arm64](#${versionTag}-linux-arm64)
- 18.04
  - [${versionTag}-linux-18.04](#${versionTag}-linux-1804)
  - [${versionTag}-linux-arm64-18.04](#${versionTag}-linux-arm64-1804)

#### windows

- 2004
  - [${versionTag}-nanoserver-2004](#${versionTag}-nanoserver-2004)
- 1909
  - [${versionTag}-nanoserver-1909](#${versionTag}-nanoserver-1909)
- 1903
  - [${versionTag}-nanoserver-1903](#${versionTag}-nanoserver-1903)
- 1809
  - [${versionTag}-nanoserver-1809](#${versionTag}-nanoserver-1809)
- 1803
  - [${versionTag}-nanoserver-1803](#${versionTag}-nanoserver-1803)



### ${versionTag}-linux

[Dockerfile](linux/Server/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [${jdkServerLinuxComponentName}](${jdkServerLinuxComponent})
- ${gitLinuxComponentName}
- ${gitLFSLinuxComponentName}
- ${p4Name}

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/linux/Server/Ubuntu/20.04/Dockerfile" -t teamcity-server:${versionTag}-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### ${versionTag}-nanoserver-1809

[Dockerfile](windows/Server/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1809
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/windows/Server/nanoserver/1809/Dockerfile" -t teamcity-server:${versionTag}-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### ${versionTag}-nanoserver-2004

[Dockerfile](windows/Server/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-2004
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/windows/Server/nanoserver/2004/Dockerfile" -t teamcity-server:${versionTag}-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### ${versionTag}-linux-18.04

[Dockerfile](linux/Server/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [${jdkServerLinuxComponentName}](${jdkServerLinuxComponent})
- Git v.2.38.0
- Git LFS v.2.3.4
- ${p4Name}

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/linux/Server/Ubuntu/18.04/Dockerfile" -t teamcity-server:${versionTag}-linux-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### ${versionTag}-linux-arm64

[Dockerfile](linux/Server/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto aarch64 v.11.0.16.9.1 Checksum (MD5) fd96ceb7be9522eaf545b36a88a3e96a](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-linux-aarch64.tar.gz)
- ${gitLinuxComponentName}
- ${gitLFSLinuxComponentName}

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/linux/Server/UbuntuARM/20.04/Dockerfile" -t teamcity-server:${versionTag}-linux-arm64 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### ${versionTag}-linux-arm64-18.04

[Dockerfile](linux/Server/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto aarch64 v.11.0.16.9.1 Checksum (MD5) fd96ceb7be9522eaf545b36a88a3e96a](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-linux-aarch64.tar.gz)
- Git v.2.38.0
- Git LFS v.2.3.4

Container platform: ${linuxPlatform}

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/linux/Server/UbuntuARM/18.04/Dockerfile" -t teamcity-server:${versionTag}-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### ${versionTag}-nanoserver-1803

[Dockerfile](windows/Server/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/windows/Server/nanoserver/1803/Dockerfile" -t teamcity-server:${versionTag}-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### ${versionTag}-nanoserver-1903

[Dockerfile](windows/Server/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1903
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/windows/Server/nanoserver/1903/Dockerfile" -t teamcity-server:${versionTag}-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### ${versionTag}-nanoserver-1909

[Dockerfile](windows/Server/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1909
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/windows/Server/nanoserver/1909/Dockerfile" -t teamcity-server:${versionTag}-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **6 GB**._

