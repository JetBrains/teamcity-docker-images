## teamcity-server tags

Other tags

- [teamcity-agent](teamcity-agent.md)
- [teamcity-minimal-agent](teamcity-minimal-agent.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [latest](#latest)
- [2026.1.6](#202616)

#### linux

- 24.04
  - [2026.1.6-linux](#202616-linux)
  - [2026.1.6-linux-arm64](#202616-linux-arm64)
- 22.04
  - [2026.1.6-linux-22.04](#202616-linux-2204)
  - [2026.1.6-linux-arm64-22.04](#202616-linux-arm64-2204)
- 20.04
  - [2026.1.6-linux-20.04](#202616-linux-2004)
  - [2026.1.6-linux-arm64-20.04](#202616-linux-arm64-2004)
- 18.04
  - [2026.1.6-linux-18.04](#202616-linux-1804)
  - [2026.1.6-linux-arm64-18.04](#202616-linux-arm64-1804)

#### windows

- 2022
  - [2026.1.6-nanoserver-2022](#202616-nanoserver-2022)
- 1909
  - [2026.1.6-nanoserver-1909](#202616-nanoserver-1909)
- 1903
  - [2026.1.6-nanoserver-1903](#202616-nanoserver-1903)
- 1809
  - [2026.1.6-nanoserver-1809](#202616-nanoserver-1809)
- 1803
  - [2026.1.6-nanoserver-1803](#202616-nanoserver-1803)


### latest

Supported platforms: linux 20.04, linux 22.04, linux 24.04, windows 1809, windows 2022

#### Content

- [2026.1.6-linux](#202616-linux)
- [2026.1.6-linux-20.04](#202616-linux-2004)
- [2026.1.6-linux-22.04](#202616-linux-2204)
- [2026.1.6-nanoserver-1809](#202616-nanoserver-1809)
- [2026.1.6-nanoserver-2022](#202616-nanoserver-2022)

### 2026.1.6

Supported platforms: linux 20.04, linux 22.04, linux 24.04, windows 1809, windows 2022

#### Content

- [2026.1.6-linux](#202616-linux)
- [2026.1.6-linux-20.04](#202616-linux-2004)
- [2026.1.6-linux-22.04](#202616-linux-2204)
- [2026.1.6-nanoserver-1809](#202616-nanoserver-1809)
- [2026.1.6-nanoserver-2022](#202616-nanoserver-2022)


# Dockerfile links

* **Linux**. [teamcity-server:2026.1.6-linux,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/Ubuntu/24.04/Dockerfile), [teamcity-server:2026.1.6-linux-20.04,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/Ubuntu/20.04/Dockerfile), [teamcity-server:2026.1.6-linux-22.04,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/Ubuntu/22.04/Dockerfile), [teamcity-server:2026.1.6-linux-18.04,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/Ubuntu/18.04/Dockerfile), [teamcity-server:2026.1.6-linux-arm64,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/UbuntuARM/24.04/Dockerfile), [teamcity-server:2026.1.6-linux-arm64-18.04,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/UbuntuARM/18.04/Dockerfile), [teamcity-server:2026.1.6-linux-arm64-20.04,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/UbuntuARM/20.04/Dockerfile), [teamcity-server:2026.1.6-linux-arm64-22.04,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Server/UbuntuARM/22.04/Dockerfile)

* **Windows**. [teamcity-server:2026.1.6-nanoserver-1809,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Server/nanoserver/1809/Dockerfile), [teamcity-server:2026.1.6-nanoserver-2022,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Server/nanoserver/2022/Dockerfile), [teamcity-server:2026.1.6-nanoserver-1803,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Server/nanoserver/1803/Dockerfile), [teamcity-server:2026.1.6-nanoserver-1903,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Server/nanoserver/1903/Dockerfile), [teamcity-server:2026.1.6-nanoserver-1909,latest,2026.1.6](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Server/nanoserver/1909/Dockerfile)


### 2026.1.6-linux

[Dockerfile](linux/Server/Ubuntu/24.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.13.1.2 Checksum (MD5) 9e596431ec718c529ee9238f983eb5eb](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-linux-x64.tar.gz)
- Git v.2.56.1
- Git LFS 3.7.1
- Perforce Helix Core client (p4) [2026.1](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/24.04/Dockerfile" -t teamcity-server:2026.1.6-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2026.1.6-linux-20.04

[Dockerfile](linux/Server/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.13.1.2 Checksum (MD5) 9e596431ec718c529ee9238f983eb5eb](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-linux-x64.tar.gz)
- Git v.2.56.1
- Git LFS 3.7.1
- Perforce Helix Core client (p4) [2026.1](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/20.04/Dockerfile" -t teamcity-server:2026.1.6-linux-20.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2026.1.6-linux-22.04

[Dockerfile](linux/Server/Ubuntu/22.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.13.1.2 Checksum (MD5) 9e596431ec718c529ee9238f983eb5eb](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-linux-x64.tar.gz)
- Git v.2.56.1
- Git LFS 3.7.1
- Perforce Helix Core client (p4) [2026.1](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/22.04/Dockerfile" -t teamcity-server:2026.1.6-linux-22.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2026.1.6-nanoserver-1809

[Dockerfile](windows/Server/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.13.1.2 Checksum (MD5) 346402f739b8ab2e9346f3e179abb873](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.56.1 Checksum (SHA256) 4a92850b6e917da4fd010669b614b55dccf5b0ec333a534cc4be4700dfefdafd](https://github.com/git-for-windows/git/releases/download/v2.56.1.windows.1/MinGit-2.56.1-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1809
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1809/Dockerfile" -t teamcity-server:2026.1.6-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2026.1.6-nanoserver-2022

[Dockerfile](windows/Server/nanoserver/2022/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.13.1.2 Checksum (MD5) 346402f739b8ab2e9346f3e179abb873](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.56.1 Checksum (SHA256) 4a92850b6e917da4fd010669b614b55dccf5b0ec333a534cc4be4700dfefdafd](https://github.com/git-for-windows/git/releases/download/v2.56.1.windows.1/MinGit-2.56.1-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-ltsc2022
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/2022/Dockerfile" -t teamcity-server:2026.1.6-nanoserver-2022 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2026.1.6-linux-18.04

[Dockerfile](linux/Server/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.13.1.2 Checksum (MD5) 9e596431ec718c529ee9238f983eb5eb](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-linux-x64.tar.gz)
- Git v.2.41.0
- Git LFS v.2.3.4
- Perforce Helix Core client (p4) [2026.1](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/18.04/Dockerfile" -t teamcity-server:2026.1.6-linux-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2026.1.6-linux-arm64

[Dockerfile](linux/Server/UbuntuARM/24.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.13.1.2 Checksum (MD5) 994f8379ee74343f061616f39972f15e](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-linux-aarch64.tar.gz)
- Git v.2.56.1
- Git LFS 3.7.1

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/24.04/Dockerfile" -t teamcity-server:2026.1.6-linux-arm64 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2026.1.6-linux-arm64-18.04

[Dockerfile](linux/Server/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.13.1.2 Checksum (MD5) 994f8379ee74343f061616f39972f15e](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-linux-aarch64.tar.gz)
- Git v.2.41.0
- Git LFS v.2.3.4

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/18.04/Dockerfile" -t teamcity-server:2026.1.6-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2026.1.6-linux-arm64-20.04

[Dockerfile](linux/Server/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.13.1.2 Checksum (MD5) 994f8379ee74343f061616f39972f15e](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-linux-aarch64.tar.gz)
- Git v.2.56.1
- Git LFS 3.7.1

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/20.04/Dockerfile" -t teamcity-server:2026.1.6-linux-arm64-20.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2026.1.6-linux-arm64-22.04

[Dockerfile](linux/Server/UbuntuARM/22.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.13.1.2 Checksum (MD5) 994f8379ee74343f061616f39972f15e](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-linux-aarch64.tar.gz)
- Git v.2.56.1
- Git LFS 3.7.1

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/22.04/Dockerfile" -t teamcity-server:2026.1.6-linux-arm64-22.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2026.1.6-nanoserver-1803

[Dockerfile](windows/Server/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.13.1.2 Checksum (MD5) 346402f739b8ab2e9346f3e179abb873](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-windows-x64-jdk.zip)
- [Git x64 v.2.56.1 Checksum (SHA256) 4a92850b6e917da4fd010669b614b55dccf5b0ec333a534cc4be4700dfefdafd](https://github.com/git-for-windows/git/releases/download/v2.56.1.windows.1/MinGit-2.56.1-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1803/Dockerfile" -t teamcity-server:2026.1.6-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2026.1.6-nanoserver-1903

[Dockerfile](windows/Server/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.13.1.2 Checksum (MD5) 346402f739b8ab2e9346f3e179abb873](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.56.1 Checksum (SHA256) 4a92850b6e917da4fd010669b614b55dccf5b0ec333a534cc4be4700dfefdafd](https://github.com/git-for-windows/git/releases/download/v2.56.1.windows.1/MinGit-2.56.1-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1903
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1903/Dockerfile" -t teamcity-server:2026.1.6-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2026.1.6-nanoserver-1909

[Dockerfile](windows/Server/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.13.1.2 Checksum (MD5) 346402f739b8ab2e9346f3e179abb873](https://corretto.aws/downloads/resources/21.0.13.1.2/amazon-corretto-21.0.13.1.2-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.56.1 Checksum (SHA256) 4a92850b6e917da4fd010669b614b55dccf5b0ec333a534cc4be4700dfefdafd](https://github.com/git-for-windows/git/releases/download/v2.56.1.windows.1/MinGit-2.56.1-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1909
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1909/Dockerfile" -t teamcity-server:2026.1.6-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **6 GB**._

