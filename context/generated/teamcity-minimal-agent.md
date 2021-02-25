## teamcity-minimal-agent tags

Other tags

- [teamcity-agent](teamcity-agent.md)
- [teamcity-server](teamcity-server.md)

#### multi-architecture

When running an image with multi-architecture support, docker will automatically select an image variant which matches your OS and architecture.

- [latest](#latest)
- [2020.2.3](#202023)

#### linux

- 20.04
  - [2020.2.3-linux](#202023-linux)
- 18.04
  - [2020.2.3-linux-18.04](#202023-linux-1804)

#### windows

- 2004
  - [2020.2.3-nanoserver-2004](#202023-nanoserver-2004)
- 1909
  - [2020.2.3-nanoserver-1909](#202023-nanoserver-1909)
- 1903
  - [2020.2.3-nanoserver-1903](#202023-nanoserver-1903)
- 1809
  - [2020.2.3-nanoserver-1809](#202023-nanoserver-1809)
- 1803
  - [2020.2.3-nanoserver-1803](#202023-nanoserver-1803)


### latest

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2020.2.3-linux](#202023-linux)
- [2020.2.3-nanoserver-1809](#202023-nanoserver-1809)
- [2020.2.3-nanoserver-2004](#202023-nanoserver-2004)

### 2020.2.3

Supported platforms: linux 20.04, windows 1809, windows 2004

#### Content

- [2020.2.3-linux](#202023-linux)
- [2020.2.3-nanoserver-1809](#202023-nanoserver-1809)
- [2020.2.3-nanoserver-2004](#202023-nanoserver-2004)


### 2020.2.3-linux

[Dockerfile](linux/MinimalAgent/Ubuntu/20.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 9b9889f6907274aee4814d134beb22d9](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile" -t teamcity-minimal-agent:2020.2.3-linux "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2020.2.3-nanoserver-1809

[Dockerfile](windows/MinimalAgent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JRE <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 9f63d24e072072b2e7ec94ddb3f594e2](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jre.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:2020.2.3-nanoserver-1809 "context"
```

_The required free space to generate image(s) is about **8 GB**._

### 2020.2.3-nanoserver-2004

[Dockerfile](windows/MinimalAgent/nanoserver/2004/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-minimal-agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

Installed components:

- [JRE <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 9f63d24e072072b2e7ec94ddb3f594e2](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jre.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:2004
docker pull mcr.microsoft.com/powershell:nanoserver-2004
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:2020.2.3-nanoserver-2004 "context"
```

_The required free space to generate image(s) is about **8 GB**._

### 2020.2.3-linux-18.04

[Dockerfile](linux/MinimalAgent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 9b9889f6907274aee4814d134beb22d9](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-linux-x64.tar.gz)

Container platform: linux

Docker build commands:

```
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:2020.2.3-linux-18.04 "context"
```

_The required free space to generate image(s) is about **1 GB**._

### 2020.2.3-nanoserver-1803

[Dockerfile](windows/MinimalAgent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JRE <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 9f63d24e072072b2e7ec94ddb3f594e2](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jre.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:2020.2.3-nanoserver-1803 "context"
```

_The required free space to generate image(s) is about **6 GB**._

### 2020.2.3-nanoserver-1903

[Dockerfile](windows/MinimalAgent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JRE <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 9f63d24e072072b2e7ec94ddb3f594e2](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jre.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:2020.2.3-nanoserver-1903 "context"
```

_The required free space to generate image(s) is about **8 GB**._

### 2020.2.3-nanoserver-1909

[Dockerfile](windows/MinimalAgent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [JRE <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 9f63d24e072072b2e7ec94ddb3f594e2](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jre.zip)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.272.10.3 Checksum (MD5) 244b50667ef3b040191ae4083e3438e7](https://corretto.aws/downloads/resources/8.272.10.3/amazon-corretto-8.272.10.3-windows-x64-jdk.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container platform: windows

Docker build commands:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:2020.2.3-nanoserver-1909 "context"
```

_The required free space to generate image(s) is about **8 GB**._

