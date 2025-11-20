## teamcity-server tags

Other tags

- [teamcity-agent](teamcity-agent.md)
- [teamcity-minimal-agent](teamcity-minimal-agent.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [latest](#latest)
- [2025.11](#202511)

#### linux

- 24.04
  - [2025.11-linux](#202511-linux)
  - [2025.11-linux-arm64](#202511-linux-arm64)
- 22.04
  - [2025.11-linux](#202511-linux)
  - [2025.11-linux-arm64](#202511-linux-arm64)
- 20.04
  - [2025.11-linux](#202511-linux)
  - [2025.11-linux-arm64](#202511-linux-arm64)
- 18.04
  - [2025.11-linux-18.04](#202511-linux-1804)
  - [2025.11-linux-arm64-18.04](#202511-linux-arm64-1804)

#### windows

- 2022
  - [2025.11-nanoserver-2022](#202511-nanoserver-2022)
- 1909
  - [2025.11-nanoserver-1909](#202511-nanoserver-1909)
- 1903
  - [2025.11-nanoserver-1903](#202511-nanoserver-1903)
- 1809
  - [2025.11-nanoserver-1809](#202511-nanoserver-1809)
- 1803
  - [2025.11-nanoserver-1803](#202511-nanoserver-1803)


### latest

Supported platforms: linux 20.04, linux 22.04, linux 24.04, windows 1809, windows 2022

#### Content

- [2025.11-linux](#202511-linux)
- [2025.11-linux](#202511-linux)
- [2025.11-linux](#202511-linux)
- [2025.11-nanoserver-1809](#202511-nanoserver-1809)
- [2025.11-nanoserver-2022](#202511-nanoserver-2022)

### 2025.11

Supported platforms: linux 20.04, linux 22.04, linux 24.04, windows 1809, windows 2022

#### Content

- [2025.11-linux](#202511-linux)
- [2025.11-linux](#202511-linux)
- [2025.11-linux](#202511-linux)
- [2025.11-nanoserver-1809](#202511-nanoserver-1809)
- [2025.11-nanoserver-2022](#202511-nanoserver-2022)


# Dockerfile links

* **Linux**. [teamcity-server:2025.11-linux,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/Ubuntu/20.04/Dockerfile), [teamcity-server:2025.11-linux,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/Ubuntu/24.04/Dockerfile), [teamcity-server:2025.11-linux,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/Ubuntu/22.04/Dockerfile), [teamcity-server:2025.11-linux-18.04,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/Ubuntu/18.04/Dockerfile), [teamcity-server:2025.11-linux-arm64,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/UbuntuARM/20.04/Dockerfile), [teamcity-server:2025.11-linux-arm64,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/UbuntuARM/24.04/Dockerfile), [teamcity-server:2025.11-linux-arm64,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/UbuntuARM/22.04/Dockerfile), [teamcity-server:2025.11-linux-arm64-18.04,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/UbuntuARM/18.04/Dockerfile)

* **Windows**. [teamcity-server:2025.11-nanoserver-1809,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Server/nanoserver/1809/Dockerfile), [teamcity-server:2025.11-nanoserver-2022,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Server/nanoserver/2022/Dockerfile), [teamcity-server:2025.11-nanoserver-1803,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Server/nanoserver/1803/Dockerfile), [teamcity-server:2025.11-nanoserver-1903,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Server/nanoserver/1903/Dockerfile), [teamcity-server:2025.11-nanoserver-1909,latest,2025.11](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Server/nanoserver/1909/Dockerfile)


### 2025.11-linux

[Dockerfile](linux/Server/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- Git v.2.52.0
- Git LFS 3.7.1
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/20.04/Dockerfile" -t teamcity-server:2025.11-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2025.11-linux

[Dockerfile](linux/Server/Ubuntu/24.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- Git v.2.52.0
- Git LFS 3.7.1
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/24.04/Dockerfile" -t teamcity-server:2025.11-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2025.11-linux

[Dockerfile](linux/Server/Ubuntu/22.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- Git v.2.52.0
- Git LFS 3.7.1
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/22.04/Dockerfile" -t teamcity-server:2025.11-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2025.11-nanoserver-1809

[Dockerfile](windows/Server/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.52.0 Checksum (SHA256) 8f0a7bc389c0bccc9daf6107cff4efb176348e34b8d787f02a36679a5588e072](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1809
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1809/Dockerfile" -t teamcity-server:2025.11-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2025.11-nanoserver-2022

[Dockerfile](windows/Server/nanoserver/2022/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.52.0 Checksum (SHA256) 8f0a7bc389c0bccc9daf6107cff4efb176348e34b8d787f02a36679a5588e072](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-ltsc2022
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/2022/Dockerfile" -t teamcity-server:2025.11-nanoserver-2022 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2025.11-linux-18.04

[Dockerfile](linux/Server/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- Git v.2.41.0
- Git LFS v.2.3.4
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/18.04/Dockerfile" -t teamcity-server:2025.11-linux-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2025.11-linux-arm64

[Dockerfile](linux/Server/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- Git v.2.52.0
- Git LFS 3.7.1

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/20.04/Dockerfile" -t teamcity-server:2025.11-linux-arm64 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2025.11-linux-arm64

[Dockerfile](linux/Server/UbuntuARM/24.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- Git v.2.52.0
- Git LFS 3.7.1

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/24.04/Dockerfile" -t teamcity-server:2025.11-linux-arm64 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2025.11-linux-arm64

[Dockerfile](linux/Server/UbuntuARM/22.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- Git v.2.52.0
- Git LFS 3.7.1

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/22.04/Dockerfile" -t teamcity-server:2025.11-linux-arm64 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2025.11-linux-arm64-18.04

[Dockerfile](linux/Server/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- Git v.2.41.0
- Git LFS v.2.3.4

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/18.04/Dockerfile" -t teamcity-server:2025.11-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2025.11-nanoserver-1803

[Dockerfile](windows/Server/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [Git x64 v.2.52.0 Checksum (SHA256) 8f0a7bc389c0bccc9daf6107cff4efb176348e34b8d787f02a36679a5588e072](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1803/Dockerfile" -t teamcity-server:2025.11-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2025.11-nanoserver-1903

[Dockerfile](windows/Server/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.52.0 Checksum (SHA256) 8f0a7bc389c0bccc9daf6107cff4efb176348e34b8d787f02a36679a5588e072](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1903
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1903/Dockerfile" -t teamcity-server:2025.11-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2025.11-nanoserver-1909

[Dockerfile](windows/Server/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.52.0 Checksum (SHA256) 8f0a7bc389c0bccc9daf6107cff4efb176348e34b8d787f02a36679a5588e072](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1909
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1909/Dockerfile" -t teamcity-server:2025.11-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **6 GB**._
