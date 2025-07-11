## teamcity-agent tags

Other tags

- [teamcity-minimal-agent](teamcity-minimal-agent.md)
- [teamcity-server](teamcity-server.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [EAP](#EAP)
- [EAP-windowsservercore](#EAP-windowsservercore)

#### linux

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

Supported platforms: linux 20.04, linux 22.04, windows 1809, windows 2022

#### Content

- [EAP-linux](#EAP-linux)
- [EAP-linux](#EAP-linux)
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

* **Linux**. [teamcity-agent:EAP-linux,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/20.04/Dockerfile), [teamcity-agent:EAP-linux,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/22.04/Dockerfile), [teamcity-agent:EAP-linux-arm64,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile), [teamcity-agent:EAP-linux-arm64,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/22.04/Dockerfile), [teamcity-agent:EAP-linux-arm64-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/22.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-arm64-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/22.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-18.04,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/18.04/Dockerfile), [teamcity-agent:EAP-linux-18.04-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile), [teamcity-agent:EAP-linux-arm64-18.04,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/18.04/Dockerfile), [teamcity-agent:EAP-linux-arm64-18.04-sudo](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/linux/Agent/UbuntuARM/18.04-sudo/Dockerfile)

* **Windows**. [teamcity-agent:EAP-nanoserver-1809,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/nanoserver/1809/Dockerfile), [teamcity-agent:EAP-nanoserver-2022,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/nanoserver/2022/Dockerfile), [teamcity-agent:EAP-windowsservercore-1809,EAP-windowsservercore,-windowsservercore](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/windowsservercore/1809/Dockerfile), [teamcity-agent:EAP-windowsservercore-2022,EAP-windowsservercore,-windowsservercore](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/windowsservercore/2022/Dockerfile), [teamcity-agent:EAP-nanoserver-1803,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/nanoserver/1803/Dockerfile), [teamcity-agent:EAP-nanoserver-1903,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/nanoserver/1903/Dockerfile), [teamcity-agent:EAP-nanoserver-1909,EAP](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/nanoserver/1909/Dockerfile), [teamcity-agent:EAP-windowsservercore-1803,EAP-windowsservercore,-windowsservercore](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/windowsservercore/1803/Dockerfile), [teamcity-agent:EAP-windowsservercore-1903,EAP-windowsservercore,-windowsservercore](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/windowsservercore/1903/Dockerfile), [teamcity-agent:EAP-windowsservercore-1909,EAP-windowsservercore,-windowsservercore](https://github.com/JetBrains/teamcity-docker-images/tree/master/context/generated/windows/Agent/windowsservercore/1909/Dockerfile)


### EAP-linux

[Dockerfile](linux/Agent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.6.1
- Git v.2.50.1
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) d6fdcfebd0df46959f7857cfb3beac7de6c8843515ece28b24802765fd9cfb6c7e9701b320134cb4907322937ab89cae914ddc21bf48b9b6313e9a9af5c1f24a](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/22.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux

[Dockerfile](linux/Agent/Ubuntu/22.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.6.1
- Git v.2.50.1
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) d6fdcfebd0df46959f7857cfb3beac7de6c8843515ece28b24802765fd9cfb6c7e9701b320134cb4907322937ab89cae914ddc21bf48b9b6313e9a9af5c1f24a](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/22.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/22.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-arm64

[Dockerfile](linux/Agent/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.6.7.1 Checksum (MD5) 14e42338d8e0b52a69edaede9892ec23](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.50.1
- Git LFS 3.6.1
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) ARM64 Checksum (SHA512) 9b939f09fbda8a080b1266914ca02c4d60a95e85fa6a1344c378d394697de6935eb7d941dd9a3aeb977ada3aab561c614a5fe9b973824899cb02aa74e9c09988](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/22.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-arm64

[Dockerfile](linux/Agent/UbuntuARM/22.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.6.7.1 Checksum (MD5) 14e42338d8e0b52a69edaede9892ec23](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.50.1
- Git LFS 3.6.1
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) ARM64 Checksum (SHA512) 9b939f09fbda8a080b1266914ca02c4d60a95e85fa6a1344c378d394697de6935eb7d941dd9a3aeb977ada3aab561c614a5fe9b973824899cb02aa74e9c09988](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/22.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/22.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
```

_The required free space to generate image(s) is about **2 GB**._

### EAP-linux-arm64-sudo

[Dockerfile](linux/Agent/UbuntuARM/22.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.6.7.1 Checksum (MD5) 14e42338d8e0b52a69edaede9892ec23](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.50.1
- Git LFS 3.6.1
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) ARM64 Checksum (SHA512) 9b939f09fbda8a080b1266914ca02c4d60a95e85fa6a1344c378d394697de6935eb7d941dd9a3aeb977ada3aab561c614a5fe9b973824899cb02aa74e9c09988](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/22.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/22.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
docker build -f "context/generated/linux/Agent/UbuntuARM/22.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-arm64-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-linux-arm64-sudo

[Dockerfile](linux/Agent/UbuntuARM/20.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.6.7.1 Checksum (MD5) 14e42338d8e0b52a69edaede9892ec23](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.50.1
- Git LFS 3.6.1
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) ARM64 Checksum (SHA512) 9b939f09fbda8a080b1266914ca02c4d60a95e85fa6a1344c378d394697de6935eb7d941dd9a3aeb977ada3aab561c614a5fe9b973824899cb02aa74e9c09988](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-arm64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/22.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/22.04/Dockerfile" -t teamcity-agent:EAP-linux-arm64 "context"
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-arm64-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-linux-sudo

[Dockerfile](linux/Agent/Ubuntu/22.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.6.1
- Git v.2.50.1
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) d6fdcfebd0df46959f7857cfb3beac7de6c8843515ece28b24802765fd9cfb6c7e9701b320134cb4907322937ab89cae914ddc21bf48b9b6313e9a9af5c1f24a](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/22.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/22.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/22.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-linux-sudo

[Dockerfile](linux/Agent/Ubuntu/20.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.6.1
- Git v.2.50.1
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) d6fdcfebd0df46959f7857cfb3beac7de6c8843515ece28b24802765fd9cfb6c7e9701b320134cb4907322937ab89cae914ddc21bf48b9b6313e9a9af5c1f24a](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-x64.tar.gz)
- Perforce Helix Core client (p4) [2024.2](https://www.perforce.com/downloads/perforce)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/22.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/22.04/Dockerfile" -t teamcity-agent:EAP-linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile" -t teamcity-agent:EAP-linux-sudo "context"
```

_The required free space to generate image(s) is about **3 GB**._

### EAP-nanoserver-1809

[Dockerfile](windows/Agent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.50.1 Checksum (SHA256) 6f672aebe9e488a246efd6875f9197dbc0d9a40100e218acc3877cba2b206c45](https://github.com/git-for-windows/git/releases/download/v2.50.1.windows.1/MinGit-2.50.1-64-bit.zip)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) 88095e181228e9e496574ed6f36582303533369cd253f41abad6c3aaa7d23436736a3fb1dd6c908032b2cfda445f66d50628516395783bef3d5ee9bbb00edcd2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-win-x64.zip)

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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.50.1 Checksum (SHA256) 6f672aebe9e488a246efd6875f9197dbc0d9a40100e218acc3877cba2b206c45](https://github.com/git-for-windows/git/releases/download/v2.50.1.windows.1/MinGit-2.50.1-64-bit.zip)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) 88095e181228e9e496574ed6f36582303533369cd253f41abad6c3aaa7d23436736a3fb1dd6c908032b2cfda445f66d50628516395783bef3d5ee9bbb00edcd2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-win-x64.zip)

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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.50.1 Checksum (SHA256) 6f672aebe9e488a246efd6875f9197dbc0d9a40100e218acc3877cba2b206c45](https://github.com/git-for-windows/git/releases/download/v2.50.1.windows.1/MinGit-2.50.1-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.6.1.1](https://www.mercurial-scm.org/release/windows/mercurial-6.1.1-x64.msi)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) 88095e181228e9e496574ed6f36582303533369cd253f41abad6c3aaa7d23436736a3fb1dd6c908032b2cfda445f66d50628516395783bef3d5ee9bbb00edcd2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-win-x64.zip)

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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.50.1 Checksum (SHA256) 6f672aebe9e488a246efd6875f9197dbc0d9a40100e218acc3877cba2b206c45](https://github.com/git-for-windows/git/releases/download/v2.50.1.windows.1/MinGit-2.50.1-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.6.1.1](https://www.mercurial-scm.org/release/windows/mercurial-6.1.1-x64.msi)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) 88095e181228e9e496574ed6f36582303533369cd253f41abad6c3aaa7d23436736a3fb1dd6c908032b2cfda445f66d50628516395783bef3d5ee9bbb00edcd2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-win-x64.zip)

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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS v.2.3.4
- Git v.2.41.0
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) d6fdcfebd0df46959f7857cfb3beac7de6c8843515ece28b24802765fd9cfb6c7e9701b320134cb4907322937ab89cae914ddc21bf48b9b6313e9a9af5c1f24a](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-x64.tar.gz)
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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 49cefdfc07e785430013f8dfd24a3fd7](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-x64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git LFS 3.6.1
- Git v.2.50.1
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) d6fdcfebd0df46959f7857cfb3beac7de6c8843515ece28b24802765fd9cfb6c7e9701b320134cb4907322937ab89cae914ddc21bf48b9b6313e9a9af5c1f24a](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-x64.tar.gz)
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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.6.7.1 Checksum (MD5) 14e42338d8e0b52a69edaede9892ec23](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.41.0
- Git LFS v.2.3.4
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) ARM64 Checksum (SHA512) 9b939f09fbda8a080b1266914ca02c4d60a95e85fa6a1344c378d394697de6935eb7d941dd9a3aeb977ada3aab561c614a5fe9b973824899cb02aa74e9c09988](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-arm64.tar.gz)

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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto aarch64 v.21.0.6.7.1 Checksum (MD5) 14e42338d8e0b52a69edaede9892ec23](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-linux-aarch64.tar.gz)
- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- Git v.2.50.1
- Git LFS 3.6.1
- Mercurial
- [Docker v.27.5.1](https://docs.docker.com/engine/release-notes/27)
- [Containerd.io v1.6.28-2](https://github.com/containerd/containerd/releases/tag/v1.6.28)
- [.NET SDK v.8.0.406 (LTS) ARM64 Checksum (SHA512) 9b939f09fbda8a080b1266914ca02c4d60a95e85fa6a1344c378d394697de6935eb7d941dd9a3aeb977ada3aab561c614a5fe9b973824899cb02aa74e9c09988](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-linux-arm64.tar.gz)

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

- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) 88095e181228e9e496574ed6f36582303533369cd253f41abad6c3aaa7d23436736a3fb1dd6c908032b2cfda445f66d50628516395783bef3d5ee9bbb00edcd2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-win-x64.zip)
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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.50.1 Checksum (SHA256) 6f672aebe9e488a246efd6875f9197dbc0d9a40100e218acc3877cba2b206c45](https://github.com/git-for-windows/git/releases/download/v2.50.1.windows.1/MinGit-2.50.1-64-bit.zip)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) 88095e181228e9e496574ed6f36582303533369cd253f41abad6c3aaa7d23436736a3fb1dd6c908032b2cfda445f66d50628516395783bef3d5ee9bbb00edcd2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-win-x64.zip)

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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Git x64 v.2.50.1 Checksum (SHA256) 6f672aebe9e488a246efd6875f9197dbc0d9a40100e218acc3877cba2b206c45](https://github.com/git-for-windows/git/releases/download/v2.50.1.windows.1/MinGit-2.50.1-64-bit.zip)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) 88095e181228e9e496574ed6f36582303533369cd253f41abad6c3aaa7d23436736a3fb1dd6c908032b2cfda445f66d50628516395783bef3d5ee9bbb00edcd2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-win-x64.zip)

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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.50.1 Checksum (SHA256) 6f672aebe9e488a246efd6875f9197dbc0d9a40100e218acc3877cba2b206c45](https://github.com/git-for-windows/git/releases/download/v2.50.1.windows.1/MinGit-2.50.1-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.6.1.1](https://www.mercurial-scm.org/release/windows/mercurial-6.1.1-x64.msi)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) 88095e181228e9e496574ed6f36582303533369cd253f41abad6c3aaa7d23436736a3fb1dd6c908032b2cfda445f66d50628516395783bef3d5ee9bbb00edcd2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-win-x64.zip)

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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.50.1 Checksum (SHA256) 6f672aebe9e488a246efd6875f9197dbc0d9a40100e218acc3877cba2b206c45](https://github.com/git-for-windows/git/releases/download/v2.50.1.windows.1/MinGit-2.50.1-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.6.1.1](https://www.mercurial-scm.org/release/windows/mercurial-6.1.1-x64.msi)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) 88095e181228e9e496574ed6f36582303533369cd253f41abad6c3aaa7d23436736a3fb1dd6c908032b2cfda445f66d50628516395783bef3d5ee9bbb00edcd2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-win-x64.zip)

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

- [JDK <img align="center" height="18" src="/docs/media/corretto.png"> Amazon Corretto x64 v.21.0.6.7.1 Checksum (MD5) 20023c16bbc60d518ee7f8ca040a8c58](https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip)
- [Git x64 v.2.50.1 Checksum (SHA256) 6f672aebe9e488a246efd6875f9197dbc0d9a40100e218acc3877cba2b206c45](https://github.com/git-for-windows/git/releases/download/v2.50.1.windows.1/MinGit-2.50.1-64-bit.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [Mercurial x64 v.6.1.1](https://www.mercurial-scm.org/release/windows/mercurial-6.1.1-x64.msi)
- [.NET SDK v.8.0.406 (LTS) x86 Checksum (SHA512) 88095e181228e9e496574ed6f36582303533369cd253f41abad6c3aaa7d23436736a3fb1dd6c908032b2cfda445f66d50628516395783bef3d5ee9bbb00edcd2](https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-win-x64.zip)

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

