## teamcity-agent tags

Other tags

- [teamcity-minimal-agent](teamcity-minimal-agent.md)
- [teamcity-server](teamcity-server.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [EAP](#EAP)
- [EAP-windowsservercore](#EAP-windowsservercore)

#### linux

- 24.04-sudo
  - [EAP-linux-arm64-sudo](#EAP-linux-arm64-sudo)
  - [EAP-linux-sudo](#EAP-linux-sudo)
- 24.04
  - [EAP-linux](#EAP-linux)
  - [EAP-linux-arm64](#EAP-linux-arm64)
- 22.04-sudo
  - [EAP-linux-arm64-sudo](#EAP-linux-arm64-sudo)
  - [EAP-linux-sudo](#EAP-linux-sudo)
- 22.04
  - [EAP-linux](#EAP-linux)
  - [EAP-linux-arm64](#EAP-linux-arm64)
- 20.04-sudo
  - [EAP-linux-arm64-sudo](#EAP-linux-arm64-sudo)
  - [EAP-linux-sudo](#EAP-linux-sudo)
- 20.04
  - [EAP-linux](#EAP-linux)
  - [EAP-linux-arm64](#EAP-linux-arm64)
- 18.04-sudo
  - [EAP-linux-18.04-sudo](#EAP-linux-1804-sudo)
  - [EAP-linux-arm64-18.04-sudo](#EAP-linux-arm64-1804-sudo)
- 18.04
  - [EAP-linux-18.04](#EAP-linux-1804)
  - [EAP-linux-arm64-18.04](#EAP-linux-arm64-1804)

#### windows

- 2022
  - [EAP-nanoserver-2022](#EAP-nanoserver-2022)
  - [EAP-windowsservercore-2022](#EAP-windowsservercore-2022)
- 1909
  - [EAP-nanoserver-1909](#EAP-nanoserver-1909)
  - [EAP-windowsservercore-1909](#EAP-windowsservercore-1909)
- 1903
  - [EAP-nanoserver-1903](#EAP-nanoserver-1903)
  - [EAP-windowsservercore-1903](#EAP-windowsservercore-1903)
- 1809
  - [EAP-nanoserver-1809](#EAP-nanoserver-1809)
  - [EAP-windowsservercore-1809](#EAP-windowsservercore-1809)
- 1803
  - [EAP-nanoserver-1803](#EAP-nanoserver-1803)
  - [EAP-windowsservercore-1803](#EAP-windowsservercore-1803)


### EAP

Supported platforms: linux 20.04, linux 22.04, linux 24.04, windows 1809, windows 2022

#### Content

- [EAP-linux](#EAP-linux)
- [EAP-linux](#EAP-linux)
- [EAP-linux](#EAP-linux)
- [EAP-linux-arm64](#EAP-linux-arm64)
- [EAP-linux-arm64](#EAP-linux-arm64)
- [EAP-linux-arm64](#EAP-linux-arm64)
- [EAP-nanoserver-1809](#EAP-nanoserver-1809)
- [EAP-nanoserver-2022](#EAP-nanoserver-2022)

### EAP-windowsservercore

Supported platforms: windows 1809, windows 2022

#### Content

- [EAP-windowsservercore-1809](#EAP-windowsservercore-1809)
- [EAP-windowsservercore-2022](#EAP-windowsservercore-2022)


# Dockerfile links

* **Linux**. [teamcity-agent:EAP-linux,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/22.04/Dockerfile), [teamcity-agent:EAP-linux,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/20.04/Dockerfile), [teamcity-agent:EAP-linux,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/24.04/Dockerfile), [teamcity-agent:EAP-linux-arm64,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/22.04/Dockerfile), [teamcity-agent:EAP-linux-arm64,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile), [teamcity-agent:EAP-linux-arm64,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/24.04/Dockerfile), [teamcity-agent:EAP-linux-arm64-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/24.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-arm64-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-arm64-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/22.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/24.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/22.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-18.04,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/18.04/Dockerfile), [teamcity-agent:EAP-linux-18.04-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-arm64-18.04,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/18.04/Dockerfile), [teamcity-agent:EAP-linux-arm64-18.04-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/18.04-sudo/Dockerfile)

* **Windows**. [teamcity-agent:EAP-nanoserver-1809,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/nanoserver/1809/Dockerfile), [teamcity-agent:EAP-nanoserver-2022,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/nanoserver/2022/Dockerfile), [teamcity-agent:EAP-windowsservercore-1809,EAP-windowsservercore,-windowsservercore](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/windowsservercore/1809/Dockerfile), [teamcity-agent:EAP-windowsservercore-2022,EAP-windowsservercore,-windowsservercore](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/windowsservercore/2022/Dockerfile), [teamcity-agent:EAP-nanoserver-1803,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/nanoserver/1803/Dockerfile), [teamcity-agent:EAP-nanoserver-1903,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/nanoserver/1903/Dockerfile), [teamcity-agent:EAP-nanoserver-1909,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/nanoserver/1909/Dockerfile), [teamcity-agent:EAP-windowsservercore-1803,EAP-windowsservercore,-windowsservercore](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/windowsservercore/1803/Dockerfile), [teamcity-agent:EAP-windowsservercore-1903,EAP-windowsservercore,-windowsservercore](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/windowsservercore/1903/Dockerfile), [teamcity-agent:EAP-windowsservercore-1909,EAP-windowsservercore,-windowsservercore](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/windowsservercore/1909/Dockerfile)


### EAP-linux

[Dockerfile](linux/Agent/Ubuntu/22.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.7.1
- Git v.2.52.0
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 0fc0499a857f161f7c35775bb3f50ac6f0333f02f5df21d21147d538eb26a9a87282d4ba3707181c46f3c09d22cdc984e77820a5953a773525d6f7b332deb7f2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/22.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux

[Dockerfile](linux/Agent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.7.1
- Git v.2.52.0
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 0fc0499a857f161f7c35775bb3f50ac6f0333f02f5df21d21147d538eb26a9a87282d4ba3707181c46f3c09d22cdc984e77820a5953a773525d6f7b332deb7f2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux

[Dockerfile](linux/Agent/Ubuntu/24.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.7.1
- Git v.2.52.0
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 0fc0499a857f161f7c35775bb3f50ac6f0333f02f5df21d21147d538eb26a9a87282d4ba3707181c46f3c09d22cdc984e77820a5953a773525d6f7b332deb7f2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/24.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-arm64

[Dockerfile](linux/Agent/UbuntuARM/22.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.52.0
- Git LFS 3.7.1
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) ARM64 Checksum (SHA512) c2efcccfd83690482d3314b23a9d9b53d41591795eb50e02857cb495dd1fde132f2c332dc243095463338d2dc6cd362cd7ea7ae3a9ce75b32ab54a517b91def8](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/22.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-arm64

[Dockerfile](linux/Agent/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.52.0
- Git LFS 3.7.1
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) ARM64 Checksum (SHA512) c2efcccfd83690482d3314b23a9d9b53d41591795eb50e02857cb495dd1fde132f2c332dc243095463338d2dc6cd362cd7ea7ae3a9ce75b32ab54a517b91def8](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-arm64

[Dockerfile](linux/Agent/UbuntuARM/24.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.52.0
- Git LFS 3.7.1
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) ARM64 Checksum (SHA512) c2efcccfd83690482d3314b23a9d9b53d41591795eb50e02857cb495dd1fde132f2c332dc243095463338d2dc6cd362cd7ea7ae3a9ce75b32ab54a517b91def8](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/24.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-arm64-sudo

[Dockerfile](linux/Agent/UbuntuARM/24.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.52.0
- Git LFS 3.7.1
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) ARM64 Checksum (SHA512) c2efcccfd83690482d3314b23a9d9b53d41591795eb50e02857cb495dd1fde132f2c332dc243095463338d2dc6cd362cd7ea7ae3a9ce75b32ab54a517b91def8](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/24.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
docker build -f "context/generated/linux/Agent/UbuntuARM/24.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-arm64-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-linux-arm64-sudo

[Dockerfile](linux/Agent/UbuntuARM/20.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.52.0
- Git LFS 3.7.1
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) ARM64 Checksum (SHA512) c2efcccfd83690482d3314b23a9d9b53d41591795eb50e02857cb495dd1fde132f2c332dc243095463338d2dc6cd362cd7ea7ae3a9ce75b32ab54a517b91def8](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/24.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-arm64-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-linux-arm64-sudo

[Dockerfile](linux/Agent/UbuntuARM/22.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.52.0
- Git LFS 3.7.1
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) ARM64 Checksum (SHA512) c2efcccfd83690482d3314b23a9d9b53d41591795eb50e02857cb495dd1fde132f2c332dc243095463338d2dc6cd362cd7ea7ae3a9ce75b32ab54a517b91def8](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/24.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
docker build -f "context/generated/linux/Agent/UbuntuARM/22.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-arm64-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-linux-sudo

[Dockerfile](linux/Agent/Ubuntu/24.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.7.1
- Git v.2.52.0
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 0fc0499a857f161f7c35775bb3f50ac6f0333f02f5df21d21147d538eb26a9a87282d4ba3707181c46f3c09d22cdc984e77820a5953a773525d6f7b332deb7f2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/24.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/24.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-linux-sudo

[Dockerfile](linux/Agent/Ubuntu/20.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.7.1
- Git v.2.52.0
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 0fc0499a857f161f7c35775bb3f50ac6f0333f02f5df21d21147d538eb26a9a87282d4ba3707181c46f3c09d22cdc984e77820a5953a773525d6f7b332deb7f2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/24.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-linux-sudo

[Dockerfile](linux/Agent/Ubuntu/22.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.7.1
- Git v.2.52.0
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 0fc0499a857f161f7c35775bb3f50ac6f0333f02f5df21d21147d538eb26a9a87282d4ba3707181c46f3c09d22cdc984e77820a5953a773525d6f7b332deb7f2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/24.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/24.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/22.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-nanoserver-1809

[Dockerfile](windows/Agent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.52.0 Checksum (SHA256) f42a561840627747ad48e6ece05a14093292d31f3393a401a7f7c780ee7695c2](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1809 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1809/Dockerfile" -t teamcity-agent:EAP-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### EAP-nanoserver-2022

[Dockerfile](windows/Agent/nanoserver/2022/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.52.0 Checksum (SHA256) f42a561840627747ad48e6ece05a14093292d31f3393a401a7f7c780ee7695c2](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:ltsc2022
docker pull mcr.microsoft.com/powershell:nanoserver-ltsc2022
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2022
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2022/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-2022 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2022/Dockerfile" -t teamcity-agent:EAP-windowsservercore-2022 "context"
docker build -f "context/generated/windows/Agent/nanoserver/2022/Dockerfile" -t teamcity-agent:EAP-nanoserver-2022 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### EAP-windowsservercore-1809

[Dockerfile](windows/Agent/windowsservercore/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [Git x64 v.2.52.0 Checksum (SHA256) f42a561840627747ad48e6ece05a14093292d31f3393a401a7f7c780ee7695c2](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.6.2.2](https://www.mercurial-scm.org/release/windows/mercurial-6.2.2-x64.msi)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1809 "context"
```

_The required free space to generate image(s) is about **38 GB**._

### EAP-windowsservercore-2022

[Dockerfile](windows/Agent/windowsservercore/2022/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [Git x64 v.2.52.0 Checksum (SHA256) f42a561840627747ad48e6ece05a14093292d31f3393a401a7f7c780ee7695c2](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.6.2.2](https://www.mercurial-scm.org/release/windows/mercurial-6.2.2-x64.msi)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:ltsc2022
docker pull mcr.microsoft.com/powershell:nanoserver-ltsc2022
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2022
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2022/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-2022 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/2022/Dockerfile" -t teamcity-agent:EAP-windowsservercore-2022 "context"
```

_The required free space to generate image(s) is about **38 GB**._

### EAP-linux-18.04

[Dockerfile](linux/Agent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS v.2.3.4
- Git v.2.41.0
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 0fc0499a857f161f7c35775bb3f50ac6f0333f02f5df21d21147d538eb26a9a87282d4ba3707181c46f3c09d22cdc984e77820a5953a773525d6f7b332deb7f2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:EAP-linux-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-18.04-sudo

[Dockerfile](linux/Agent/Ubuntu/18.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 18a45468ad50c1e0e09201de38c5c8f4](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.7.1
- Git v.2.52.0
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 0fc0499a857f161f7c35775bb3f50ac6f0333f02f5df21d21147d538eb26a9a87282d4ba3707181c46f3c09d22cdc984e77820a5953a773525d6f7b332deb7f2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:EAP-linux-18.04 "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-linux-arm64-18.04

[Dockerfile](linux/Agent/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.41.0
- Git LFS v.2.3.4
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) ARM64 Checksum (SHA512) c2efcccfd83690482d3314b23a9d9b53d41591795eb50e02857cb495dd1fde132f2c332dc243095463338d2dc6cd362cd7ea7ae3a9ce75b32ab54a517b91def8](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/18.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-arm64-18.04-sudo

[Dockerfile](linux/Agent/UbuntuARM/18.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.9.10.1 Checksum (MD5) f8568c459023d0327937e7a6ca9ea5ce](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.52.0
- Git LFS 3.7.1
- Mercurial
- [Docker v.28.5.1](https://docs.docker.com/engine/release-notes/28)
- [Containerd.io 1.7.28-1](https://github.com/containerd/containerd/releases/tag/v1.7.28)
- [.NET SDK v.8.0.415 (LTS) ARM64 Checksum (SHA512) c2efcccfd83690482d3314b23a9d9b53d41591795eb50e02857cb495dd1fde132f2c332dc243095463338d2dc6cd362cd7ea7ae3a9ce75b32ab54a517b91def8](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64-18.04 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/18.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64-18.04 "context"
docker build -f "context/generated/linux/Agent/UbuntuARM/18.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-arm64-18.04-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-nanoserver-1803

[Dockerfile](windows/Agent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1803 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1803/Dockerfile" -t teamcity-agent:EAP-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **36 GB**._

### EAP-nanoserver-1903

[Dockerfile](windows/Agent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.52.0 Checksum (SHA256) f42a561840627747ad48e6ece05a14093292d31f3393a401a7f7c780ee7695c2](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1903 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1903/Dockerfile" -t teamcity-agent:EAP-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### EAP-nanoserver-1909

[Dockerfile](windows/Agent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.52.0 Checksum (SHA256) f42a561840627747ad48e6ece05a14093292d31f3393a401a7f7c780ee7695c2](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1909 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1909/Dockerfile" -t teamcity-agent:EAP-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **40 GB**._

### EAP-windowsservercore-1803

[Dockerfile](windows/Agent/windowsservercore/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [Git x64 v.2.52.0 Checksum (SHA256) f42a561840627747ad48e6ece05a14093292d31f3393a401a7f7c780ee7695c2](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.6.2.2](https://www.mercurial-scm.org/release/windows/mercurial-6.2.2-x64.msi)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1803 "context"
```

_The required free space to generate image(s) is about **34 GB**._

### EAP-windowsservercore-1903

[Dockerfile](windows/Agent/windowsservercore/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [Git x64 v.2.52.0 Checksum (SHA256) f42a561840627747ad48e6ece05a14093292d31f3393a401a7f7c780ee7695c2](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.6.2.2](https://www.mercurial-scm.org/release/windows/mercurial-6.2.2-x64.msi)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1903 "context"
```

_The required free space to generate image(s) is about **38 GB**._

### EAP-windowsservercore-1909

[Dockerfile](windows/Agent/windowsservercore/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.9.10.1 Checksum (MD5) 905e139bfc80a7c05333c22c3075fd87](https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip)
- [Git x64 v.2.52.0 Checksum (SHA256) f42a561840627747ad48e6ece05a14093292d31f3393a401a7f7c780ee7695c2](https://github.com/git-for-windows/git/releases/download/v2.52.0.windows.1/MinGit-2.52.0-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.6.2.2](https://www.mercurial-scm.org/release/windows/mercurial-6.2.2-x64.msi)
- [.NET SDK v.8.0.415 (LTS) x86 Checksum (SHA512) 904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-win-x64.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:EAP-windowsservercore-1909 "context"
```

_The required free space to generate image(s) is about **38 GB**._

