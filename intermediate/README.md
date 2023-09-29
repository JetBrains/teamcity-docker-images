# Intermediate Dockerfiles

The folder contains files that will be available intermittently for a specific period or during a transitional phase. 
Specifically, it contains files for images that will feature a different version of the tool compared to the mainline 
image.


Dockerfiles will replace .NET SDK version within the image with pre-defined one. Please, note these Dockerfiles might 
be used as templates for the installation of any custom .NET version.

How to build:
```
docker build \
    --build-arg teamCityArmImage=<teamcity agent image> \
    --build-arg dotnetSdkVersion=<dotnetSdkVersion> \
     --build-arg dotnetSdkChecksum=<dotnetSdkChecksum>  \
               -f "linux/agent/arm/custom.dotnet.sdk.arm.Dockerfile" -t teamcity-agent:custom-dotnet-version .
```

TODO: Add LTS / STS status as of date of last release.

| OS    | Arch    | .NET          | `dotnetSdkVersion` | `dotnetSdkChecksum`                                                                                                                | Dockerfile                                               |
|-------|---------|---------------|--------------------|------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------|
| Linux | `AMD64` | .NET Core 3.1 | `3.1.426`          | `6c3f9541557feb5d5b93f5c10b28264878948e8540f2b8bb7fb966c32bd38191e6b310dcb5f87a4a8f7c67a7046fa932cde3cce9dc8341c1365ae6c9fcc481ec` | [link](linux/agent/amd/custom.dotnet.sdk.amd.Dockerfile) |
| Linux | `ARM64` | .NET Core 3.1 | `3.1.426`          | `ff311df0db488f3b5cc03c7f6724f8442de7e60fa0a503ec8f536361ce7a357ad26d09d2499d68c50ebdfa751a5520bba4aaa77a38b191c892d5a018561ce422` | [link](linux/agent/arm/custom.dotnet.sdk.arm.Dockerfile) |
| Linux | `AMD64` | .NET 5.0      | `5.0.408`          | `a9c4784930a977abbc42aff1337dda06ec588c1ec4769a59f9fcab4d5df4fc9efe65f8e61e5433db078f67a94ea2dfe870c32c482a50d4c16283ffacacff4261` | [link](linux/agent/amd/custom.dotnet.sdk.amd.Dockerfile) |
| Linux | `ARM64` | .NET 5.0      | `5.0.408`          | `50f23d7aca91051d8b7c37f1a76b1eb51e6fe73e017d98558d757a6b9699e4237d401ce81515c1601b8c21eb62fee4e0b4f0bbed8967eefa3ceba75fc242f01b` | [link](linux/agent/arm/custom.dotnet.sdk.arm.Dockerfile) |
| Linux | `AMD64` | .NET 7.0      | `7.0.401`          | `2544f58c7409b1fd8fe2c7f600f6d2b6a1929318071f16789bd6abf6deea00bd496dd6ba7f2573bbf17c891c4f56a372a073e57712acfd3e80ea3eb1b3f9c3d0` | [link](linux/agent/amd/custom.dotnet.sdk.amd.Dockerfile) |
| Linux | `ARM64` | .NET 7.0      | `7.0.401`          | `7c6ba2047998c906353f8e8d7fa73589867f46cbc2d4ece6cc7ee4ca3402b6a18717089b98002c7d15e16ca6fd5b11e42037b5fb0e25aff39075d67d8be49e25` | [link](linux/agent/arm/custom.dotnet.sdk.arm.Dockerfile) |

Example:
```
docker build \
    --build-arg teamCityArmImage=<teamcity agent image> \
    --build-arg dotnetSdkVersion=7.0.401 \
     --build-arg dotnetSdkChecksum=7c6ba2047998c906353f8e8d7fa73589867f46cbc2d4ece6cc7ee4ca3402b6a18717089b98002c7d15e16ca6fd5b11e42037b5fb0e25aff39075d67d8be49e25  \
               -f "linux/agent/arm/custom.dotnet.sdk.arm.Dockerfile" -t teamcity-agent:EAP-linux-dotnet-7-0-arm .
```
