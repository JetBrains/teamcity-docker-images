## teamcity-server tags

Other tags

- [teamcity-agent](teamcity-agent.md)
- [teamcity-minimal-agent](teamcity-minimal-agent.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [latest](#latest)
- [2022.10.2](#2022102)

#### linux

- 20.04
  - [2022.10.2-linux](#2022102-linux)
  - [2022.10.2-linux-arm64](#2022102-linux-arm64)
- 18.04
  - [2022.10.2-linux-18.04](#2022102-linux-1804)
  - [2022.10.2-linux-arm64-18.04](#2022102-linux-arm64-1804)

#### windows

- 2004
  - [2022.10.2-nanoserver-2004](#2022102-nanoserver-2004)
- 1909
  - [2022.10.2-nanoserver-1909](#2022102-nanoserver-1909)
- 1903
  - [2022.10.2-nanoserver-1903](#2022102-nanoserver-1903)
- 1809
  - [2022.10.2-nanoserver-1809](#2022102-nanoserver-1809)
- 1803
  - [2022.10.2-nanoserver-1803](#2022102-nanoserver-1803)


### latest

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2022.10.2-linux](#2022102-linux)
- [2022.10.2-nanoserver-1809](#2022102-nanoserver-1809)
- [2022.10.2-nanoserver-2004](#2022102-nanoserver-2004)

### 2022.10.2

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2022.10.2-linux](#2022102-linux)
- [2022.10.2-nanoserver-1809](#2022102-nanoserver-1809)
- [2022.10.2-nanoserver-2004](#2022102-nanoserver-2004)


### 2022.10.2-linux

[Dockerfile](linux/Server/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) 6ff46b39cbaec4218fd49c6c64077c43](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-linux-x64.tar.gz)
- Git v.2.39.2
- Git LFS v.2.9.2
- Perforce Helix Core client (p4) [2022.2-2407422](https://www.perforce.com/products/helix-core)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/20.04/Dockerfile" -t teamcity-server:2022.10.2-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2022.10.2-nanoserver-1809

[Dockerfile](windows/Server/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.39.2 Checksum (SHA256) a53b90a42d9a5e3ac992f525b5805c4dbb8a013b09a32edfdcf9a551fd8cfe2d](https://github.com/git-for-windows/git/releases/download/v2.39.2.windows.1/MinGit-2.39.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1809
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1809/Dockerfile" -t teamcity-server:2022.10.2-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2022.10.2-nanoserver-2004

[Dockerfile](windows/Server/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.39.2 Checksum (SHA256) a53b90a42d9a5e3ac992f525b5805c4dbb8a013b09a32edfdcf9a551fd8cfe2d](https://github.com/git-for-windows/git/releases/download/v2.39.2.windows.1/MinGit-2.39.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-2004
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/2004/Dockerfile" -t teamcity-server:2022.10.2-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2022.10.2-linux-18.04

[Dockerfile](linux/Server/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) 6ff46b39cbaec4218fd49c6c64077c43](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-linux-x64.tar.gz)
- Git v.2.39.2
- Git LFS v.2.3.4
- Perforce Helix Core client (p4) [2022.2-2407422](https://www.perforce.com/products/helix-core)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/18.04/Dockerfile" -t teamcity-server:2022.10.2-linux-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2022.10.2-linux-arm64

[Dockerfile](linux/Server/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto aarch64 v.11.0.16.9.1 Checksum (MD5) fd96ceb7be9522eaf545b36a88a3e96a](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-linux-aarch64.tar.gz)
- Git v.2.39.2
- Git LFS v.2.9.2

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/20.04/Dockerfile" -t teamcity-server:2022.10.2-linux-arm64 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2022.10.2-linux-arm64-18.04

[Dockerfile](linux/Server/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto aarch64 v.11.0.16.9.1 Checksum (MD5) fd96ceb7be9522eaf545b36a88a3e96a](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-linux-aarch64.tar.gz)
- Git v.2.39.2
- Git LFS v.2.3.4

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/18.04/Dockerfile" -t teamcity-server:2022.10.2-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2022.10.2-nanoserver-1803

[Dockerfile](windows/Server/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.39.2 Checksum (SHA256) a53b90a42d9a5e3ac992f525b5805c4dbb8a013b09a32edfdcf9a551fd8cfe2d](https://github.com/git-for-windows/git/releases/download/v2.39.2.windows.1/MinGit-2.39.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1803/Dockerfile" -t teamcity-server:2022.10.2-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2022.10.2-nanoserver-1903

[Dockerfile](windows/Server/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.39.2 Checksum (SHA256) a53b90a42d9a5e3ac992f525b5805c4dbb8a013b09a32edfdcf9a551fd8cfe2d](https://github.com/git-for-windows/git/releases/download/v2.39.2.windows.1/MinGit-2.39.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1903
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1903/Dockerfile" -t teamcity-server:2022.10.2-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2022.10.2-nanoserver-1909

[Dockerfile](windows/Server/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.16.9.1 Checksum (MD5) e46d240031e3a58f6bfbd1f67044da61](https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip)
- [Git x64 v.2.39.2 Checksum (SHA256) a53b90a42d9a5e3ac992f525b5805c4dbb8a013b09a32edfdcf9a551fd8cfe2d](https://github.com/git-for-windows/git/releases/download/v2.39.2.windows.1/MinGit-2.39.2-64-bit.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1909
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1909/Dockerfile" -t teamcity-server:2022.10.2-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **6 GB**._

