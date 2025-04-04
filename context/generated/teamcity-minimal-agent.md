## teamcity-minimal-agent tags

Other tags

- [teamcity-agent](teamcity-agent.md)
- [teamcity-server](teamcity-server.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [EAP](#EAP)

#### linux

- 22.04
  - [EAP-linux](#EAP-linux)
  - [EAP-linux-arm64](#EAP-linux-arm64)
- 20.04
  - [EAP-linux](#EAP-linux)
  - [EAP-linux-arm64](#EAP-linux-arm64)
- 18.04
  - [EAP-linux-18.04](#EAP-linux-1804)
  - [EAP-linux-arm64-18.04](#EAP-linux-arm64-1804)

#### windows

- 2022
  - [EAP-nanoserver-2022](#EAP-nanoserver-2022)
- 1909
  - [EAP-nanoserver-1909](#EAP-nanoserver-1909)
- 1903
  - [EAP-nanoserver-1903](#EAP-nanoserver-1903)
- 1809
  - [EAP-nanoserver-1809](#EAP-nanoserver-1809)
- 1803
  - [EAP-nanoserver-1803](#EAP-nanoserver-1803)


### EAP

Supported platforms: linux 20.04, linux 22.04, windows 1809, windows 2022

#### Content

- [EAP-linux](#EAP-linux)
- [EAP-linux](#EAP-linux)
- [EAP-nanoserver-1809](#EAP-nanoserver-1809)
- [EAP-nanoserver-2022](#EAP-nanoserver-2022)


# Dockerfile links

* **Linux**. [teamcity-minimal-agent:EAP-linux,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile), [teamcity-minimal-agent:EAP-linux,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/MinimalAgent/Ubuntu/22.04/Dockerfile), [teamcity-minimal-agent:EAP-linux-18.04,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile), [teamcity-minimal-agent:EAP-linux-arm64,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile), [teamcity-minimal-agent:EAP-linux-arm64,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/MinimalAgent/UbuntuARM/22.04/Dockerfile), [teamcity-minimal-agent:EAP-linux-arm64-18.04,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/MinimalAgent/UbuntuARM/18.04/Dockerfile)

* **Windows**. [teamcity-minimal-agent:EAP-nanoserver-1809,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile), [teamcity-minimal-agent:EAP-nanoserver-2022,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/MinimalAgent/nanoserver/2022/Dockerfile), [teamcity-minimal-agent:EAP-nanoserver-1803,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile), [teamcity-minimal-agent:EAP-nanoserver-1903,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile), [teamcity-minimal-agent:EAP-nanoserver-1909,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile)


### EAP-linux

[Dockerfile](linux/MinimalAgent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### EAP-linux

[Dockerfile](linux/MinimalAgent/Ubuntu/22.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/22.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### EAP-nanoserver-1809

[Dockerfile](windows/MinimalAgent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **10 GB**._

### EAP-nanoserver-2022

[Dockerfile](windows/MinimalAgent/nanoserver/2022/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:ltsc2022
docker pull mcr.microsoft.com/powershell:nanoserver-ltsc2022
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2022/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-2022 "context"
```

_The required free space to generate image(s) is about **10 GB**._

### EAP-linux-18.04

[Dockerfile](linux/MinimalAgent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### EAP-linux-arm64

[Dockerfile](linux/MinimalAgent/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.6.7.1 Checksum (MD5) 14e42338d8e0b52a69edaede9892ec23](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### EAP-linux-arm64

[Dockerfile](linux/MinimalAgent/UbuntuARM/22.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.6.7.1 Checksum (MD5) 14e42338d8e0b52a69edaede9892ec23](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/22.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### EAP-linux-arm64-18.04

[Dockerfile](linux/MinimalAgent/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.6.7.1 Checksum (MD5) 14e42338d8e0b52a69edaede9892ec23](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### EAP-nanoserver-1803

[Dockerfile](windows/MinimalAgent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### EAP-nanoserver-1903

[Dockerfile](windows/MinimalAgent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **10 GB**._

### EAP-nanoserver-1909

[Dockerfile](windows/MinimalAgent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **10 GB**._

