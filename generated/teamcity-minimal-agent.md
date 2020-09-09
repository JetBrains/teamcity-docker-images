## teamcity-minimal-agent tags

Other tags

- [teamcity-server](teamcity-server.md)
- [teamcity-agent](teamcity-agent.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [latest](#latest)
- [2020.1.4](#202014)

#### linux

- 18.04
  - [2020.1.4-linux](#202014-linux)

#### windows

- 1909
  - [2020.1.4-nanoserver-1909](#202014-nanoserver-1909)
- 1903
  - [2020.1.4-nanoserver-1903](#202014-nanoserver-1903)
- 1809
  - [2020.1.4-nanoserver-1809](#202014-nanoserver-1809)
- 1803
  - [2020.1.4-nanoserver-1803](#202014-nanoserver-1803)


### latest

Supported platforms: linux 18.04, windows 1809, windows 1903

#### Content

- [2020.1.4-linux](#202014-linux)
- [2020.1.4-nanoserver-1809](#202014-nanoserver-1809)
- [2020.1.4-nanoserver-1903](#202014-nanoserver-1903)

### 2020.1.4

Supported platforms: linux 18.04, windows 1809, windows 1903

#### Content

- [2020.1.4-linux](#202014-linux)
- [2020.1.4-nanoserver-1809](#202014-nanoserver-1809)
- [2020.1.4-nanoserver-1903](#202014-nanoserver-1903)


### 2020.1.4-linux

[Dockerfile](linux/MinimalAgent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.1](https://corretto.aws/downloads/resources/8.252.09.1/amazon-corretto-8.252.09.1-linux-x64.tar.gz)

Container platform: linux

Docker pull command:

```
docker pull jetbrains//teamcity-minimal-agent:2020.1.4-linux
```

Docker build commands:

```
docker build -f "generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:2020.1.4-linux "context"
```

Base images:

```
docker pull ubuntu:18.04
```

_The required free space to generate image(s) is about **1 GB**._
### 2020.1.4-nanoserver-1809

[Dockerfile](windows/MinimalAgent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JRE <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jre.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-minimal-agent:2020.1.4-nanoserver-1809
```

Docker build commands:

```
docker build -f "generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:2020.1.4-nanoserver-1809 "context"
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
```

_The required free space to generate image(s) is about **8 GB**._
### 2020.1.4-nanoserver-1903

[Dockerfile](windows/MinimalAgent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JRE <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jre.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-minimal-agent:2020.1.4-nanoserver-1903
```

Docker build commands:

```
docker build -f "generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:2020.1.4-nanoserver-1903 "context"
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
```

_The required free space to generate image(s) is about **8 GB**._
### 2020.1.4-nanoserver-1803

[Dockerfile](windows/MinimalAgent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JRE <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jre.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)

Container platform: windows

Docker build commands:

```
docker build -f "generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:2020.1.4-nanoserver-1803 "context"
```

Base images:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
```

_The required free space to generate image(s) is about **6 GB**._
### 2020.1.4-nanoserver-1909

[Dockerfile](windows/MinimalAgent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JRE <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jre.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker build -f "generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:2020.1.4-nanoserver-1909 "context"
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
```

_The required free space to generate image(s) is about **8 GB**._
