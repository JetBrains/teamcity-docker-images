# Custom TeamCity Agent Images

The folder includes Dockerfiles that you can utilize to create custom TeamCity Agent images.

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
| Linux   | `AMD64` | [.NET 8.0](https://dotnet.microsoft.com/en-us/download/dotnet/8.0)      | [link](linux/agent/amd/custom.dotnet.sdk.amd.Dockerfile)   | `8.0.415`          | `0fc0499a857f161f7c35775bb3f50ac6f0333f02f5df21d21147d538eb26a9a87282d4ba3707181c46f3c09d22cdc984e77820a5953a773525d6f7b332deb7f2` |
| Linux   | `ARM64` | [.NET 8.0](https://dotnet.microsoft.com/en-us/download/dotnet/8.0)      | [link](linux/agent/arm/custom.dotnet.sdk.arm.Dockerfile)   | `8.0.415`          | `c2efcccfd83690482d3314b23a9d9b53d41591795eb50e02857cb495dd1fde132f2c332dc243095463338d2dc6cd362cd7ea7ae3a9ce75b32ab54a517b91def8` |
| Windows | `AMD64` | [.NET 8.0](https://dotnet.microsoft.com/en-us/download/dotnet/8.0)      | [link](windows/agent/custom.dotnet.sdk.win.amd.Dockerfile) | `8.0.415`          | `904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c` |


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
               -f "windows/agent/custom.dotnet.sdk.win.amd.Dockerfile" -t teamcity-agent:windows-custom-dotnet-7 .
               
# 2. Verify .NET SDK version within the new image
docker run teamcity-agent:windows-custom-dotnet-7 dotnet --version     
7.0.401
```

# 1.2 .NET End of Support Dates

In the [.NET and .NET Core Support Policy](https://dotnet.microsoft.com/en-us/platform/support/policy/dotnet-core) article, Microsoft states the following end-of-support dates for .NET:

| .NET Version    | Support Type | End of Support       |
|-----------------|--------------|----------------------|
| .NET Core 3.1   |              | December 13th, 2022  |
| .NET 5.0        |              | May 10th, 2022       |
| .NET 6          | LTS          | November 12, 2024    |
| .NET 7.0        | STS          | May 14, 2024         |
| .NET 8.0        | LTS          | November 10, 2026    |

We strongly encourage replacing your current .NET versions to newer ones if the support for your current version is nearing its end.


# 2. Patching published images: compromised CAs (CVE-2026-91403, CVE-2026-114820)

Images that were published before the fix still trust the compromised CAs. The Dockerfiles below patch an already published
image without rebuilding it.

Supported: Linux Ubuntu 24.04 (`AMD64`, `ARM64`) and Windows Server 2019 (`1809`) / 2022 images of Server, Agent and Minimal Agent.

| OS      | Dockerfile                                  | Updates                                                         |
|---------|---------------------------------------------|-----------------------------------------------------------------|
| Linux   | [link](linux/revoked-ca/patch.Dockerfile)   | OS CA (latest from the Ubuntu archive, verified by fingerprint), Corretto |
| Windows | [link](windows/revoked-ca/patch.Dockerfile) | Corretto, MinGit (Server, Agent), Windows `Disallowed` store    |

## 2.1. Linux

Set `imageUser` to `tcuser` for the Server image (default: `buildagent`). Override the `jdkLinux*` build arguments to use another
Corretto version.

Git and Docker read the OS CA bundle (`/etc/ssl/certs/ca-certificates.crt`), so the CA update covers them and they are not
reinstalled. Custom CAs added through `/usr/local/share/ca-certificates/` are kept: the bundle is regenerated with them, minus
the compromised roots.

CA bundles configured outside the OS store are not patched. If you use any of the following, regenerate the bundle so it no
longer contains the compromised roots:
* Git: `http.sslCAInfo` or `GIT_SSL_CAINFO`
* `SSL_CERT_FILE` or `CURL_CA_BUNDLE`
* Docker: `/etc/docker/certs.d/`
* a custom Java truststore (`javax.net.ssl.trustStore`)
```
docker build \
    --build-arg baseImage=jetbrains/teamcity-agent:2026.2-linux \
    -f "linux/revoked-ca/patch.Dockerfile" -t teamcity-agent:2026.2-linux-patched .

docker build \
    --build-arg baseImage=jetbrains/teamcity-server:2026.2-linux \
    --build-arg imageUser=tcuser \
    -f "linux/revoked-ca/patch.Dockerfile" -t teamcity-server:2026.2-linux-patched .
```

## 2.2. Windows

Run the build from this directory. `powershellImage` must match the OS version of the image being patched.

Windows still trusts the compromised roots, so the patch blocks the intermediates through the `Disallowed` store. The
certificates are in [windows/revoked-ca/disallowed](windows/revoked-ca/disallowed), each named after its SHA-256. The build
fails if any CA store in the image still trusts the compromised CAs.

MCR base images from 2026-10-26 on already ship the updated `Disallowed` list
([microsoft/Windows-Containers#659](https://github.com/microsoft/Windows-Containers/issues/659)).
```
docker build `
    --build-arg baseImage=jetbrains/teamcity-agent:2026.2-windowsservercore-2022 `
    --build-arg powershellImage=mcr.microsoft.com/powershell:nanoserver-ltsc2022 `
    -f "windows/revoked-ca/patch.Dockerfile" -t teamcity-agent:2026.2-windowsservercore-2022-patched .

# Windows Server 2019
docker build `
    --build-arg baseImage=jetbrains/teamcity-agent:2026.2-windowsservercore-1809 `
    --build-arg powershellImage=mcr.microsoft.com/powershell:nanoserver-1809 `
    -f "windows/revoked-ca/patch.Dockerfile" -t teamcity-agent:2026.2-windowsservercore-1809-patched .
```

## 2.3. Verifying the image

The check scripts run inside the image and exit with `1` if any check fails. They do not modify the image.

Linux: [verify-compromised-ca-absence.sh](linux/revoked-ca/verify-compromised-ca-absence.sh) checks the OS CA bundle and the Corretto truststore.
```
docker run --rm -v "$PWD/linux/revoked-ca:/check:ro" --entrypoint sh teamcity-agent:2026.2-linux-patched /check/verify-compromised-ca-absence.sh
```

Windows: [verify-compromised-ca-absence.ps1](windows/revoked-ca/verify-compromised-ca-absence.ps1) checks the Corretto truststore, the MinGit CA bundle and whether the Windows
`Disallowed` store blocks all compromised intermediates. Use `powershell` instead of `pwsh` for `windowsservercore` images.
```
docker run --rm -v "${PWD}\windows\revoked-ca:C:\check" --entrypoint powershell `
    teamcity-agent:2026.2-windowsservercore-2022-patched -NoProfile -File C:\check\verify-compromised-ca-absence.ps1
```
To check a running agent, paste the script into a PowerShell build step.

The scripts only check the stores shipped with the image. CA bundles configured outside of them (see [2.1](#21-linux)) need to be
checked separately.
