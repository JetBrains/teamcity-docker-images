### Tags

- multi-arch
  - 2020.1
  - eap
  - latest
- linux
  - [latest-18.04, 2020.1-18.04, eap-18.04, latest-linux, 2020.1-linux, eap-linux](#latest-1804-20201-1804-eap-1804-latest-linux-20201-linux-eap-linux)
  - [latest-18.04-sudo, 2020.1-18.04-sudo, eap-18.04-sudo](#latest-1804-sudo-20201-1804-sudo-eap-1804-sudo)
- windows
  - [latest-nanoserver-1809, 2020.1-nanoserver-1809, eap-nanoserver-1809](#latest-nanoserver-1809-20201-nanoserver-1809-eap-nanoserver-1809)
  - [latest-nanoserver-1903, 2020.1-nanoserver-1903, eap-nanoserver-1903](#latest-nanoserver-1903-20201-nanoserver-1903-eap-nanoserver-1903)
  - [latest-windowsservercore-1809, 2020.1-windowsservercore-1809, eap-windowsservercore-1809](#latest-windowsservercore-1809-20201-windowsservercore-1809-eap-windowsservercore-1809)
  - [latest-windowsservercore-1903, 2020.1-windowsservercore-1903, eap-windowsservercore-1903](#latest-windowsservercore-1903-20201-windowsservercore-1903-eap-windowsservercore-1903)
  - [latest-nanoserver-1803, 2020.1-nanoserver-1803, eap-nanoserver-1803](#latest-nanoserver-1803-20201-nanoserver-1803-eap-nanoserver-1803)
  - [latest-nanoserver-1909, 2020.1-nanoserver-1909, eap-nanoserver-1909](#latest-nanoserver-1909-20201-nanoserver-1909-eap-nanoserver-1909)
  - [latest-windowsservercore-1803, 2020.1-windowsservercore-1803, eap-windowsservercore-1803](#latest-windowsservercore-1803-20201-windowsservercore-1803-eap-windowsservercore-1803)
  - [latest-windowsservercore-1909, 2020.1-windowsservercore-1909, eap-windowsservercore-1909](#latest-windowsservercore-1909-20201-windowsservercore-1909-eap-windowsservercore-1909)

### latest-18.04, 2020.1-18.04, eap-18.04, latest-linux, 2020.1-linux, eap-linux

[Dockerfile](linux/Agent/Ubuntu/18.04/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- Git
- Mercurial
- [.NET SDK x64 v.3.1.300](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.300/dotnet-sdk-3.1.300-linux-x64.tar.gz)

Container Platform: linux

Docker pull command:

```
docker pull jetbrains//teamcity-agent:18.04
```

Docker build commands:

```
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:18.04 -t teamcity-minimal-agent:linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:18.04 -t teamcity-agent:linux "context"
```

Base images:

```
docker pull ubuntu:18.04
```

_The required free space to generate image(s) is about **2 GB**._
### latest-18.04-sudo, 2020.1-18.04-sudo, eap-18.04-sudo

[Dockerfile](linux/Agent/Ubuntu/18.04-sudo/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Container Platform: linux

Docker pull command:

```
docker pull jetbrains//teamcity-agent:18.04-sudo
```

Docker build commands:

```
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile" -t teamcity-minimal-agent:18.04 -t teamcity-minimal-agent:linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04/Dockerfile" -t teamcity-agent:18.04 -t teamcity-agent:linux "context"
docker build -f "context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile" -t teamcity-agent:18.04-sudo "context"
```

Base images:

```
docker pull ubuntu:18.04
```

_The required free space to generate image(s) is about **3 GB**._
### latest-nanoserver-1809, 2020.1-nanoserver-1809, eap-nanoserver-1809

[Dockerfile](windows/Agent/nanoserver/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [.NET SDK x64 v.3.1.300](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.300/dotnet-sdk-3.1.300-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container Platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-agent:nanoserver-1809
```

Docker build commands:

```
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:windowsservercore-1809 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1809/Dockerfile" -t teamcity-agent:nanoserver-1809 "context"
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
```

_The required free space to generate image(s) is about **25 GB**._
### latest-nanoserver-1903, 2020.1-nanoserver-1903, eap-nanoserver-1903

[Dockerfile](windows/Agent/nanoserver/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [.NET SDK x64 v.3.1.300](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.300/dotnet-sdk-3.1.300-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container Platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-agent:nanoserver-1903
```

Docker build commands:

```
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:windowsservercore-1903 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1903/Dockerfile" -t teamcity-agent:nanoserver-1903 "context"
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
```

_The required free space to generate image(s) is about **25 GB**._
### latest-windowsservercore-1809, 2020.1-windowsservercore-1809, eap-windowsservercore-1809

[Dockerfile](windows/Agent/windowsservercore/1809/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)
- [Mercurial x64 v.4.7.2](https://bitbucket.org/tortoisehg/files/downloads/mercurial-4.7.2-x64.msi)

Container Platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-agent:windowsservercore-1809
```

Docker build commands:

```
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:windowsservercore-1809 "context"
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
```

_The required free space to generate image(s) is about **24 GB**._
### latest-windowsservercore-1903, 2020.1-windowsservercore-1903, eap-windowsservercore-1903

[Dockerfile](windows/Agent/windowsservercore/1903/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The docker image is available on:

- [https://hub.docker.com/r/jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent)

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)
- [Mercurial x64 v.4.7.2](https://bitbucket.org/tortoisehg/files/downloads/mercurial-4.7.2-x64.msi)

Container Platform: windows

Docker pull command:

```
docker pull jetbrains//teamcity-agent:windowsservercore-1903
```

Docker build commands:

```
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:windowsservercore-1903 "context"
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
```

_The required free space to generate image(s) is about **24 GB**._
### latest-nanoserver-1803, 2020.1-nanoserver-1803, eap-nanoserver-1803

[Dockerfile](windows/Agent/nanoserver/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [.NET SDK x64 v.3.1.300](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.300/dotnet-sdk-3.1.300-win-x64.zip)

Container Platform: windows

Docker build commands:

```
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:windowsservercore-1803 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1803/Dockerfile" -t teamcity-agent:nanoserver-1803 "context"
```

Base images:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
```

_The required free space to generate image(s) is about **24 GB**._
### latest-nanoserver-1909, 2020.1-nanoserver-1909, eap-nanoserver-1909

[Dockerfile](windows/Agent/nanoserver/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [.NET SDK x64 v.3.1.300](https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.300/dotnet-sdk-3.1.300-win-x64.zip)
- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)

Container Platform: windows

Docker build commands:

```
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:windowsservercore-1909 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1909/Dockerfile" -t teamcity-agent:nanoserver-1909 "context"
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
```

_The required free space to generate image(s) is about **25 GB**._
### latest-windowsservercore-1803, 2020.1-windowsservercore-1803, eap-windowsservercore-1803

[Dockerfile](windows/Agent/windowsservercore/1803/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)
- [Mercurial x64 v.4.7.2](https://bitbucket.org/tortoisehg/files/downloads/mercurial-4.7.2-x64.msi)

Container Platform: windows

Docker build commands:

```
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1803/Dockerfile" -t teamcity-minimal-agent:nanoserver-1803 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1803/Dockerfile" -t teamcity-agent:windowsservercore-1803 "context"
```

Base images:

```
docker pull mcr.microsoft.com/powershell:nanoserver-1803
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803
```

_The required free space to generate image(s) is about **23 GB**._
### latest-windowsservercore-1909, 2020.1-windowsservercore-1909, eap-windowsservercore-1909

[Dockerfile](windows/Agent/windowsservercore/1909/Dockerfile)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.
The docker image is not available and may be created manually.

Installed components:

- [PowerShell](https://github.com/PowerShell/PowerShell#get-powershell)
- [JDK <img align="center" height="18" src="/logo/corretto.png"> Amazon Corretto x64 v.8.252.09.2](https://corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip)
- [Git x64 v.2.19.1](https://github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip)
- [Mercurial x64 v.4.7.2](https://bitbucket.org/tortoisehg/files/downloads/mercurial-4.7.2-x64.msi)

Container Platform: windows

Docker build commands:

```
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:nanoserver-1909 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:windowsservercore-1909 "context"
```

Base images:

```
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
```

_The required free space to generate image(s) is about **24 GB**._
