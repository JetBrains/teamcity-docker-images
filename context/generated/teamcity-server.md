## teamcity-server tags

Other tags

- [teamcity-agent](teamcity-agent.md)
- [teamcity-minimal-agent](teamcity-minimal-agent.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [latest](#latest)
- [2023.11.5](#2023115)

#### linux

- 20.04
  - [2023.11.5-linux](#2023115-linux)
  - [2023.11.5-linux-arm64](#2023115-linux-arm64)
- 18.04
  - [2023.11.5-linux-18.04](#2023115-linux-1804)
  - [2023.11.5-linux-arm64-18.04](#2023115-linux-arm64-1804)

#### windows

- 2022
  - [2023.11.5-nanoserver-2022](#2023115-nanoserver-2022)
- 2004
  - [2023.11.5-nanoserver-2004](#2023115-nanoserver-2004)
- 1909
  - [2023.11.5-nanoserver-1909](#2023115-nanoserver-1909)
- 1903
  - [2023.11.5-nanoserver-1903](#2023115-nanoserver-1903)
- 1809
  - [2023.11.5-nanoserver-1809](#2023115-nanoserver-1809)
- 1803
  - [2023.11.5-nanoserver-1803](#2023115-nanoserver-1803)


### latest

Supported platforms: linux 20.04, windows 1809, windows 2022

#### Content

- [2023.11.5-linux](#2023115-linux)
- [2023.11.5-nanoserver-1809](#2023115-nanoserver-1809)
- [2023.11.5-nanoserver-2022](#2023115-nanoserver-2022)

### 2023.11.5

Supported platforms: linux 20.04, windows 1809, windows 2022

#### Content

- [2023.11.5-linux](#2023115-linux)
- [2023.11.5-nanoserver-1809](#2023115-nanoserver-1809)
- [2023.11.5-nanoserver-2022](#2023115-nanoserver-2022)


### 2023.11.5-linux

[Dockerfile](linux/Server/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) 443750a02c28ff2807c80032ee2e8ebc](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-x64.tar.gz)
- Git v.2.45.1
- Git LFS v.2.9.2
- Perforce Helix Core client (p4) [2022.2-2531894](https://www.perforce.com/products/helix-core)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/20.04/Dockerfile" -t teamcity-server:2023.11.5-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2023.11.5-nanoserver-1809

[Dockerfile](windows/Server/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.45.1 Checksum (SHA256) f7ba0e2acdc603cf8893b446f6871c869b7644b88a1116b00d6b30fb30f18c74](https://github.com/git-for-windows/git/releases/download/v2.43.0.windows.1/MinGit-2.43.0-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1809
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1809/Dockerfile" -t teamcity-server:2023.11.5-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2023.11.5-nanoserver-2022

[Dockerfile](windows/Server/nanoserver/2022/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.45.1 Checksum (SHA256) f7ba0e2acdc603cf8893b446f6871c869b7644b88a1116b00d6b30fb30f18c74](https://github.com/git-for-windows/git/releases/download/v2.43.0.windows.1/MinGit-2.43.0-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-ltsc2022
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/2022/Dockerfile" -t teamcity-server:2023.11.5-nanoserver-2022 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2023.11.5-linux-18.04

[Dockerfile](linux/Server/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) 443750a02c28ff2807c80032ee2e8ebc](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-x64.tar.gz)
- Git v.2.41.0
- Git LFS v.2.3.4
- Perforce Helix Core client (p4) [2022.2-2531894](https://www.perforce.com/products/helix-core)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/18.04/Dockerfile" -t teamcity-server:2023.11.5-linux-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2023.11.5-linux-arm64

[Dockerfile](linux/Server/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.17.0.7.7.1 Checksum (MD5) c55e3d0615fac07f948ac3adaed818e9](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-aarch64.tar.gz)
- Git v.2.45.1
- Git LFS v.2.9.2

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/20.04/Dockerfile" -t teamcity-server:2023.11.5-linux-arm64 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2023.11.5-linux-arm64-18.04

[Dockerfile](linux/Server/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.17.0.7.7.1 Checksum (MD5) c55e3d0615fac07f948ac3adaed818e9](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-linux-aarch64.tar.gz)
- Git v.2.41.0
- Git LFS v.2.3.4

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/18.04/Dockerfile" -t teamcity-server:2023.11.5-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2023.11.5-nanoserver-1803

[Dockerfile](windows/Server/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.45.1 Checksum (SHA256) f7ba0e2acdc603cf8893b446f6871c869b7644b88a1116b00d6b30fb30f18c74](https://github.com/git-for-windows/git/releases/download/v2.43.0.windows.1/MinGit-2.43.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1803/Dockerfile" -t teamcity-server:2023.11.5-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2023.11.5-nanoserver-1903

[Dockerfile](windows/Server/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.45.1 Checksum (SHA256) f7ba0e2acdc603cf8893b446f6871c869b7644b88a1116b00d6b30fb30f18c74](https://github.com/git-for-windows/git/releases/download/v2.43.0.windows.1/MinGit-2.43.0-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1903
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1903/Dockerfile" -t teamcity-server:2023.11.5-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2023.11.5-nanoserver-1909

[Dockerfile](windows/Server/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.45.1 Checksum (SHA256) f7ba0e2acdc603cf8893b446f6871c869b7644b88a1116b00d6b30fb30f18c74](https://github.com/git-for-windows/git/releases/download/v2.43.0.windows.1/MinGit-2.43.0-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1909
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1909/Dockerfile" -t teamcity-server:2023.11.5-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2023.11.5-nanoserver-2004

[Dockerfile](windows/Server/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.17.0.7.7.1 Checksum (MD5) feb7eab99c647a0b4347be9f0a3276de](https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.45.1 Checksum (SHA256) f7ba0e2acdc603cf8893b446f6871c869b7644b88a1116b00d6b30fb30f18c74](https://github.com/git-for-windows/git/releases/download/v2.43.0.windows.1/MinGit-2.43.0-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-2004
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/2004/Dockerfile" -t teamcity-server:2023.11.5-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **6 GB**._

