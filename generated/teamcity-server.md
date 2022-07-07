## teamcity-server tags

Other tags

- [teamcity-agent](teamcity-agent.md)
- [teamcity-minimal-agent](teamcity-minimal-agent.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [local](#local)

#### linux

- 20.04
  - [local-linux](#local-linux)
  - [local-linux-arm64-20.04](#local-linux-arm64-2004)
- 18.04
  - [local-linux-18.04](#local-linux-1804)
  - [local-linux-arm64-18.04](#local-linux-arm64-1804)

#### windows

- 2004
  - [local-nanoserver-2004](#local-nanoserver-2004)
- 1909
  - [local-nanoserver-1909](#local-nanoserver-1909)
- 1903
  - [local-nanoserver-1903](#local-nanoserver-1903)
- 1809
  - [local-nanoserver-1809](#local-nanoserver-1809)
- 1803
  - [local-nanoserver-1803](#local-nanoserver-1803)


### local

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [local-linux](#local-linux)
- [local-nanoserver-1809](#local-nanoserver-1809)
- [local-nanoserver-2004](#local-nanoserver-2004)


### local-linux

[Dockerfile](linux/Server/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) 30f9268b8f4c2c2f8c1676611b88aa0d](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-linux-x64.tar.gz)
- Git v.2.37.0
- Git LFS v.2.9.2
- Perforce Helix Core client (p4) [2021.2-2273812](https://www.perforce.com/products/helix-core)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/linux/Server/Ubuntu/20.04/Dockerfile" -t teamcity-server:local-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### local-nanoserver-1809

[Dockerfile](windows/Server/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) a937acacf9cc25ec06f9b240d064ad99](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1809
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/windows/Server/nanoserver/1809/Dockerfile" -t teamcity-server:local-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### local-nanoserver-2004

[Dockerfile](windows/Server/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) a937acacf9cc25ec06f9b240d064ad99](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-2004
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/windows/Server/nanoserver/2004/Dockerfile" -t teamcity-server:local-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### local-linux-18.04

[Dockerfile](linux/Server/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) 30f9268b8f4c2c2f8c1676611b88aa0d](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-linux-x64.tar.gz)
- Git v.2.37.0
- Git LFS v.2.3.4
- Perforce Helix Core client (p4) [2021.2-2273812](https://www.perforce.com/products/helix-core)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/linux/Server/Ubuntu/18.04/Dockerfile" -t teamcity-server:local-linux-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### local-linux-arm64-18.04

[Dockerfile](linux/Server/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto aarch64 v.8.292.10.1 Checksum (MD5) b0b989af7b8635d0dd0724707206b67c](https://corretto.aws/downloads/resources/8.292.10.1/amazon-corretto-8.292.10.1-linux-aarch64.tar.gz)
- Git v.2.37.0
- Git LFS v.2.3.4

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/linux/Server/UbuntuARM/18.04/Dockerfile" -t teamcity-server:local-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### local-linux-arm64-20.04

[Dockerfile](linux/Server/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto aarch64 v.8.292.10.1 Checksum (MD5) b0b989af7b8635d0dd0724707206b67c](https://corretto.aws/downloads/resources/8.292.10.1/amazon-corretto-8.292.10.1-linux-aarch64.tar.gz)
- Git v.2.37.0
- Git LFS v.2.9.2

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/linux/Server/UbuntuARM/20.04/Dockerfile" -t teamcity-server:local-linux-arm64-20.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### local-nanoserver-1803

[Dockerfile](windows/Server/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) a937acacf9cc25ec06f9b240d064ad99](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/windows/Server/nanoserver/1803/Dockerfile" -t teamcity-server:local-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### local-nanoserver-1903

[Dockerfile](windows/Server/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) a937acacf9cc25ec06f9b240d064ad99](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1903
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/windows/Server/nanoserver/1903/Dockerfile" -t teamcity-server:local-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### local-nanoserver-1909

[Dockerfile](windows/Server/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) a937acacf9cc25ec06f9b240d064ad99](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.33.0 Checksum (SHA256) e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95](https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1909
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/windows/Server/nanoserver/1909/Dockerfile" -t teamcity-server:local-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **6 GB**._

