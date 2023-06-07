## teamcity-minimal-agent tags

Other tags

- [teamcity-agent](teamcity-agent.md)
- [teamcity-server](teamcity-server.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [latest](#latest)
- [2022.04.5](#2022045)

#### linux

- 20.04
  - [2022.04.5-linux](#2022045-linux)
  - [2022.04.5-linux-arm64-20.04](#2022045-linux-arm64-2004)
- 18.04
  - [2022.04.5-linux-18.04](#2022045-linux-1804)
  - [2022.04.5-linux-arm64-18.04](#2022045-linux-arm64-1804)

#### windows

- 2004
  - [2022.04.5-nanoserver-2004](#2022045-nanoserver-2004)
- 1909
  - [2022.04.5-nanoserver-1909](#2022045-nanoserver-1909)
- 1903
  - [2022.04.5-nanoserver-1903](#2022045-nanoserver-1903)
- 1809
  - [2022.04.5-nanoserver-1809](#2022045-nanoserver-1809)
- 1803
  - [2022.04.5-nanoserver-1803](#2022045-nanoserver-1803)


### latest

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2022.04.5-linux](#2022045-linux)
- [2022.04.5-nanoserver-1809](#2022045-nanoserver-1809)
- [2022.04.5-nanoserver-2004](#2022045-nanoserver-2004)

### 2022.04.5

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2022.04.5-linux](#2022045-linux)
- [2022.04.5-nanoserver-1809](#2022045-nanoserver-1809)
- [2022.04.5-nanoserver-2004](#2022045-nanoserver-2004)


### 2022.04.5-linux

[Dockerfile](linux/MinimalAgent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) 30f9268b8f4c2c2f8c1676611b88aa0d](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:2022.04.5-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2022.04.5-nanoserver-1809

[Dockerfile](windows/MinimalAgent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) a937acacf9cc25ec06f9b240d064ad99](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:2022.04.5-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **10 GB**._

### 2022.04.5-nanoserver-2004

[Dockerfile](windows/MinimalAgent/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) a937acacf9cc25ec06f9b240d064ad99](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:2004
docker pull mcr.microsoft.com/powershell:nanoserver-2004
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:2022.04.5-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **10 GB**._

### 2022.04.5-linux-18.04

[Dockerfile](linux/MinimalAgent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) 30f9268b8f4c2c2f8c1676611b88aa0d](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:2022.04.5-linux-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2022.04.5-linux-arm64-18.04

[Dockerfile](linux/MinimalAgent/UbuntuARM/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto aarch64 v.8.292.10.1 Checksum (MD5) b0b989af7b8635d0dd0724707206b67c](https://corretto.aws/downloads/resources/8.292.10.1/amazon-corretto-8.292.10.1-linux-aarch64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/18.04/Dockerfile" -t teamcity-minimal-agent:2022.04.5-linux-arm64-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2022.04.5-linux-arm64-20.04

[Dockerfile](linux/MinimalAgent/UbuntuARM/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto aarch64 v.8.292.10.1 Checksum (MD5) b0b989af7b8635d0dd0724707206b67c](https://corretto.aws/downloads/resources/8.292.10.1/amazon-corretto-8.292.10.1-linux-aarch64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile" -t teamcity-minimal-agent:2022.04.5-linux-arm64-20.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2022.04.5-nanoserver-1803

[Dockerfile](windows/MinimalAgent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) a937acacf9cc25ec06f9b240d064ad99](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-windows-x64-jdk.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:2022.04.5-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2022.04.5-nanoserver-1903

[Dockerfile](windows/MinimalAgent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) a937acacf9cc25ec06f9b240d064ad99](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:2022.04.5-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **10 GB**._

### 2022.04.5-nanoserver-1909

[Dockerfile](windows/MinimalAgent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.15.9.1 Checksum (MD5) a937acacf9cc25ec06f9b240d064ad99](https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:2022.04.5-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **10 GB**._

