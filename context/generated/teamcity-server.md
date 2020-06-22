### Tags

#### multi-arch

- latest
  - [2020.1.1-linux](#202011-linux)
  - [2020.1.1-nanoserver-1809](#202011-nanoserver-1809)
  - [2020.1.1-nanoserver-1903](#202011-nanoserver-1903)
- 2020.1.1
  - [2020.1.1-linux](#202011-linux)
  - [2020.1.1-nanoserver-1809](#202011-nanoserver-1809)
  - [2020.1.1-nanoserver-1903](#202011-nanoserver-1903)

#### linux

- 18.04
  - [2020.1.1-linux](#202011-linux)

#### windows

- 1909
  - [2020.1.1-nanoserver-1909](#202011-nanoserver-1909)
- 1903
  - [2020.1.1-nanoserver-1903](#202011-nanoserver-1903)
- 1809
  - [2020.1.1-nanoserver-1809](#202011-nanoserver-1809)
- 1803
  - [2020.1.1-nanoserver-1803](#202011-nanoserver-1803)


### 2020.1.1-linux

[Dockerfile](linux/Server/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.7.10.1](https://corretto.aws/downloads/resources/11.0.7.10.1/amazon-corretto-11.0.7.10.1-linux-x64.tar.gz)

Container Platform: linux

Docker pull command:

```
docker pull jetbrains//teamcity-server:2020.1.1-linux
```

Docker build commands:

```
docker build -f "context/generated/linux/Server/Ubuntu/18.04/Dockerfile" -t teamcity-server:2020.1.1-linux "context"
```

Base images:

```
docker pull ubuntu:18.04
```

_The required free space to generate image(s) is about **1 GB**._
### 2020.1.1-nanoserver-1809

[Dockerfile](windows/Server/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.7.10.1](https://corretto.aws/downloads/resources/11.0.7.10.1/amazon-corretto-11.0.7.10.1-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)

Container Platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-server:2020.1.1-nanoserver-1809
```

Docker build commands:

```
docker build -f "context/generated/windows/Server/nanoserver/1809/Dockerfile" -t teamcity-server:2020.1.1-nanoserver-1809 "context"
```

Base images:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1809
```

_The required free space to generate image(s) is about **3 GB**._
### 2020.1.1-nanoserver-1903

[Dockerfile](windows/Server/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-server](https://hub.docker.com/r/jetbrains/teamcity-server)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.7.10.1](https://corretto.aws/downloads/resources/11.0.7.10.1/amazon-corretto-11.0.7.10.1-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)

Container Platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-server:2020.1.1-nanoserver-1903
```

Docker build commands:

```
docker build -f "context/generated/windows/Server/nanoserver/1903/Dockerfile" -t teamcity-server:2020.1.1-nanoserver-1903 "context"
```

Base images:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1903
```

_The required free space to generate image(s) is about **3 GB**._
### 2020.1.1-nanoserver-1803

[Dockerfile](windows/Server/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.7.10.1](https://corretto.aws/downloads/resources/11.0.7.10.1/amazon-corretto-11.0.7.10.1-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)

Container Platform: windows

Docker build commands:

```
docker build -f "context/generated/windows/Server/nanoserver/1803/Dockerfile" -t teamcity-server:2020.1.1-nanoserver-1803 "context"
```

Base images:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
```

_The required free space to generate image(s) is about **3 GB**._
### 2020.1.1-nanoserver-1909

[Dockerfile](windows/Server/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.11.0.7.10.1](https://corretto.aws/downloads/resources/11.0.7.10.1/amazon-corretto-11.0.7.10.1-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)

Container Platform: windows

Docker build commands:

```
docker build -f "context/generated/windows/Server/nanoserver/1909/Dockerfile" -t teamcity-server:2020.1.1-nanoserver-1909 "context"
```

Base images:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1909
```

_The required free space to generate image(s) is about **3 GB**._
