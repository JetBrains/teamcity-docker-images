# Custom TeamCity Agent Images
The folder includes Dockerfiles that you can utilize to create custom TeamCity Agent images.

# Content
<!-- TOC -->
* [1. .NET SDK](#1-net-sdk)
    * [1.1. Building Images](#11-building-images)
* [1.2 .NET End of Support Dates](#12-net-end-of-support-dates)
* [2. Podman](#2-podman)
    * [2.1 Building Images](#21-building-images)
    * [2.2 Execution](#22-execution)
        * [2.2.1 Rootless Podman in Docker (no '--privileged')](#221-rootless-podman-in-docker-no---privileged)
        * [2.2.2 Rootful Podman in Docker ('--privileged')](#222-rootful-podman-in-docker---privileged)
    * [2.3 Podman - troubleshooting](#23-podman---troubleshooting)
        * [2.3.1 Inability to execute images with rootful Podman](#231-inability-to-execute-images-with-rootful-podman)
<!-- TOC -->

# 1. .NET SDK
| OS      | Arch    | .NET SDK                                                                | Dockerfile                                                 | `dotnetSdkVersion` | `dotnetSdkChecksum`                                                                                                                |
|---------|---------|-------------------------------------------------------------------------|------------------------------------------------------------|--------------------|------------------------------------------------------------------------------------------------------------------------------------|
| Linux   | `AMD64` | [.NET Core 3.1](https://dotnet.microsoft.com/en-us/download/dotnet/3.1) | [link](linux/agent/amd/custom.dotnet.sdk.amd.Dockerfile)   | `3.1.426`          | `6c3f9541557feb5d5b93f5c10b28264878948e8540f2b8bb7fb966c32bd38191e6b310dcb5f87a4a8f7c67a7046fa932cde3cce9dc8341c1365ae6c9fcc481ec` |
| Linux   | `ARM64` | [.NET Core 3.1](https://dotnet.microsoft.com/en-us/download/dotnet/3.1) | [link](linux/agent/arm/custom.dotnet.sdk.arm.Dockerfile)   | `3.1.426`          | `ff311df0db488f3b5cc03c7f6724f8442de7e60fa0a503ec8f536361ce7a357ad26d09d2499d68c50ebdfa751a5520bba4aaa77a38b191c892d5a018561ce422` |
| Windows | `AMD64` | [.NET Core 3.1](https://dotnet.microsoft.com/en-us/download/dotnet/3.1) | [link](windows/agent/custom.dotnet.sdk.win.amd.Dockerfile) | `3.1.426`          | `ca5c60898318d2cf9786013edd45508f44fba45c2a8814752ba53094ca7b78b3d94874e765655e310b4efd2b604d42807ef6e16c6281d877495d513bfb5c1261` |
| Linux   | `AMD64` | [.NET 5.0](https://dotnet.microsoft.com/en-us/download/dotnet/5.0)      | [link](linux/agent/amd/custom.dotnet.sdk.amd.Dockerfile)   | `5.0.408`          | `a9c4784930a977abbc42aff1337dda06ec588c1ec4769a59f9fcab4d5df4fc9efe65f8e61e5433db078f67a94ea2dfe870c32c482a50d4c16283ffacacff4261` |
| Linux   | `ARM64` | [.NET 5.0](https://dotnet.microsoft.com/en-us/download/dotnet/5.0)      | [link](linux/agent/arm/custom.dotnet.sdk.arm.Dockerfile)   | `5.0.408`          | `50f23d7aca91051d8b7c37f1a76b1eb51e6fe73e017d98558d757a6b9699e4237d401ce81515c1601b8c21eb62fee4e0b4f0bbed8967eefa3ceba75fc242f01b` |
| Windows | `AMD64` | [.NET 5.0](https://dotnet.microsoft.com/en-us/download/dotnet/5.0)      | [link](windows/agent/custom.dotnet.sdk.win.amd.Dockerfile) | `5.0.408`          | `3845485401695b325d9afee67e33c6b3a45902476e408dd74ebc8815ad2c4f4b5d70a6b993e87ff587d0d9b0e5a3d66eaf3dd6bf715b0012ffee70501a716485` |
| Linux   | `AMD64` | [.NET 7.0](https://dotnet.microsoft.com/en-us/download/dotnet/7.0)      | [link](linux/agent/amd/custom.dotnet.sdk.amd.Dockerfile)   | `7.0.401`          | `2544f58c7409b1fd8fe2c7f600f6d2b6a1929318071f16789bd6abf6deea00bd496dd6ba7f2573bbf17c891c4f56a372a073e57712acfd3e80ea3eb1b3f9c3d0` |
| Linux   | `ARM64` | [.NET 7.0](https://dotnet.microsoft.com/en-us/download/dotnet/7.0)      | [link](linux/agent/arm/custom.dotnet.sdk.arm.Dockerfile)   | `7.0.401`          | `7c6ba2047998c906353f8e8d7fa73589867f46cbc2d4ece6cc7ee4ca3402b6a18717089b98002c7d15e16ca6fd5b11e42037b5fb0e25aff39075d67d8be49e25` |
| Windows | `AMD64` | [.NET 7.0](https://dotnet.microsoft.com/en-us/download/dotnet/7.0)      | [link](windows/agent/custom.dotnet.sdk.win.amd.Dockerfile) | `7.0.401`          | `02a4ecc05d0b9dfa0c9e32f8a3d288f329e7338b2430fcbc1276ae356f9d8e14920f91382f3f141842bf1e6e6cd331e532b301edc71c26de9d9e5ad2371afbe0` |


The .NET SDK version bundled within TeamCity Docker Images is aligned with [Microsoft's Long Term Support (LTS) release](https://dotnet.microsoft.com/en-us/platform/support/policy/dotnet-core)
at the moment of a TeamCity release. Since it is sometimes necessary to use STS (which can be newer than LTS) or
older versions, we provide examples of building images with custom .NET SDK versions inside.

The folder contains Dockerfiles that simplify this process, allowing you to easily replace any .NET SDK version within the image with a pre-defined one. These Dockerfiles can also be used as templates for installing any custom .NET version.

## 1.1. Building Images
The table above references multiple versions of .NET framework. To build a custom image, specify the required SDK version (`dotnetSdkVersion`) and a checksum for it (`dotnetSdkChecksum`):
```
docker build \
    --build-arg teamCityAgentImage=<teamcity agent image> \
    --build-arg dotnetSdkVersion=<dotnetSdkVersion> \
     --build-arg dotnetSdkChecksum=<dotnetSdkChecksum>  \
               -f "linux/agent/arm/custom.dotnet.sdk.arm.Dockerfile" -t teamcity-agent:custom-dotnet-version .
```

Example for Linux:
```
# 1. Build Image
docker build \
    --build-arg teamCityAgentImage=jetbrains/teamcity-agent:2023.05.4-linux-arm64 \
    --build-arg dotnetSdkVersion=7.0.401 \
     --build-arg dotnetSdkChecksum=7c6ba2047998c906353f8e8d7fa73589867f46cbc2d4ece6cc7ee4ca3402b6a18717089b98002c7d15e16ca6fd5b11e42037b5fb0e25aff39075d67d8be49e25  \
               -f "linux/agent/arm/custom.dotnet.sdk.arm.Dockerfile" -t teamcity-agent:linux-arm-dotnet-7-0 .

# 2. Verify .NET SDK version within the new image
docker exec <container ID> dotnet --version
7.0.401

```

Example for Windows:
```
# 1. Build Image
docker build `
    --build-arg teamCityAgentImage=jetbrains/teamcity-agent:2023.05.4-windowsservercore  `
    --build-arg dotnetSdkVersion=7.0.401 `
     --build-arg dotnetSdkChecksum=02a4ecc05d0b9dfa0c9e32f8a3d288f329e7338b2430fcbc1276ae356f9d8e14920f91382f3f141842bf1e6e6cd331e532b301edc71c26de9d9e5ad2371afbe0  `
               -f "windows/agent/cusotm.dotnet.sdk.win.amd.Dockerfile" -t teamcity-agent:windows-custom-dotnet-7 .
               
# 2. Verify .NET SDK version within the new image
docker run teamcity-agent:windows-custom-dotnet-7 dotnet --version     
7.0.401
```

# 1.2 .NET End of Support Dates
In the [.NET and .NET Core Support Policy](https://dotnet.microsoft.com/en-us/platform/support/policy/dotnet-core) article, Microsoft states the following end of support dates for .NET:
* **.NET Core 3.1** -  December 13th, 2022;
* **.NET 5.0** -  May 10th, 2022;
* **.NET 6** (LTS) - November 12, 2024;
* **.NET 7.0** (STS) - May 14, 2024;

We strongly encourage replacing your current .NET versions to newer ones if the support for your current version is nearing its end.

# 2. Podman
This section provides instructions for building and executing TeamCity Docker Images with Podman, suitable for use in both rootless and rootful modes.

Please, note that the latest version of Podman for Ubuntu 20.04 is `Podman 3.4.2`, as indicated by the [libcontainers](https://download.opensuse.org/repositories/devel:/kubic:/libcontainers:/stable/xUbuntu_20.04/amd64/).

In order to use Podman as a default container runtime in TeamCity, please, set `teamcity.container.wrapper.use.podman=true`.

## 2.1 Building Images
Rootless:
```
$ docker build \
    --build-arg teamCityAgentImage=jetbrains/teamcity-agent:2023.05.4 \
    -f linux/agent/podman.Dockerfile \
    -t jebrains/teamcity-agent:2023.05.4-podman .
```

Rootful (based on `sudo` image):
```
$ docker build \
--build-arg teamCityAgentImage=jetbrains/teamcity-agent:2023.05.4-linux-sudo\
-f linux/agent/podman.Dockerfile \
-t jebrains/teamcity-agent:2023.05.4-podman-sudo .
```
Please, ensure the OS/Arch of Docker image matching the expected host (see: [2.3.1 Inability to execute images with rootful Podman](#231-inability-to-execute-images-with-rootful-podman)).

## 2.2 Execution
### 2.2.1 Rootless Podman in Docker (no '--privileged')
The ability to run Podman-in-Docker in Rootless mode is achieved via the combination of extending the capabilities
of container and `buildserver` user within it.

Capabilities:
* `sys_admin` - root access for Podman in order to mount required file systems;
* `mknod` - creation of `/dev` devices, such as `fuse-overlayfs`;

Security options:
* `unconfined`, `disable` - responsible for disabling of SElinux for container file mount permissions;

Storage options:
* `--device=/dev/fuse` - use [FUSE](https://www.kernel.org/doc/html/next/filesystems/fuse.html) for Podman container storage;
```
$ docker run --cap-add=sys_admin \
                --cap-add mknod \
                --device=/dev/fuse \
                --security-opt seccomp=unconfined \
                --security-opt label=disable \
                -e SERVER_URL="<server url>" \
                -v <agent conf>:/data/teamcity_agent/conf \
                jebrains/teamcity-agent:2023.05.4-podman  \
                podman run ubi8-minimal echo hello
```

### 2.2.2 Rootful Podman in Docker ('--privileged')
Rootful Podman can be launched from non-sudo images using `--privileged` flag.
```
$ docker run -itd --privileged \
    -u 0 \
    -e SERVER_URL="<server url>" \
    -v <agent conf>:/data/teamcity_agent/conf \
    jebrains/teamcity-agent:2023.05.4-podman-sudo \
    podman run ubi8-minimal echo hello
```

## 2.3 Podman - troubleshooting
### 2.3.1 Inability to execute images with rootful Podman
**Problem**: When running _rootful Podman-in-Docker_ on a platform whose host platform does not match the detected one,
container execution becomes wouldn't work. This problem arises because overlayFS doesn't function correctly,
causing issues with _CRUN_ and container storage

```
docker run --privileged -u 0 docker.io/jebrains/teamcity-agent:2023.05.4-sudo-with-podman-sudo podman run ubi8-minimal echo hello
...
Error: writing blob: adding layer with blob "sha256:395bceae1ad3587036e94ca53ad1a297204f1ffa8f3af10c5a96c3c13b8aec8d": Error processing tar file(exit status 1): Error while loading /: Permission denied

 Resolved "ubi8" as an alias (/etc/containers/registries.conf.d/000-shortnames.conf)
...
11:18:52   Error: writing blob: adding layer with blob "sha256:f992cb38fce665360a4d07f6f78db864a1f6e20a7ad304219f7f81d7fe608d97": Error processing tar file(exit status 1): Error while loading /: Permission denied
...
Failed to re-execute libcrun via memory file descriptor
```

**Solution**: build TeamCity Agent Image with _Podman_ using Agent image, whose OS/Arch matches the target host (`arm64` / `amd64`).