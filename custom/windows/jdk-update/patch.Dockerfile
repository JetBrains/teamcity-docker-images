# check=skip=InvalidDefaultArgInFrom
#
# Patches a published TeamCity Windows image (Server 2019 / 1809 and 2022) against CVE-2026-114938
# ("Java4Shell") by replacing the bundled Amazon Corretto JDK with a fixed build (21.0.13.1.2).
#
# @param baseImage       image to patch, e.g. 'jetbrains/teamcity-agent:2026.2-windowsservercore-2022'
# @param powershellImage PowerShell image with the same OS version, e.g. 'mcr.microsoft.com/powershell:nanoserver-ltsc2022'
# @param jdkVersion      Corretto build to install; drives both the download URL and the post-install check
#

ARG baseImage
ARG powershellImage

FROM ${powershellImage} AS download

USER ContainerAdministrator
SHELL ["pwsh", "-NoProfile", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

ARG jdkVersion=21.0.13.1.2
ARG jdkWindowsComponent="https://corretto.aws/downloads/resources/${jdkVersion}/amazon-corretto-${jdkVersion}-windows-x64-jdk.zip"
ARG jdkWindowsComponentMD5SUM='346402f739b8ab2e9346f3e179abb873'

RUN Invoke-WebRequest -UseBasicParsing -Uri $Env:jdkWindowsComponent -OutFile C:\jdk.zip ; \
    if ((Get-FileHash C:\jdk.zip -Algorithm MD5).Hash -ne $Env:jdkWindowsComponentMD5SUM) { throw 'JDK checksum mismatch' } ; \
    Expand-Archive C:\jdk.zip -DestinationPath C:\jdk-tmp ; \
    New-Item -ItemType Directory C:\patch | Out-Null ; \
    Get-ChildItem C:\jdk-tmp | Select-Object -First 1 | Move-Item -Destination C:\patch\OpenJDK ; \
    Remove-Item C:\patch\OpenJDK\lib\src.zip -Force -ErrorAction SilentlyContinue ; \
    Remove-Item C:\jdk.zip, C:\jdk-tmp -Recurse -Force


FROM ${baseImage}

SHELL ["cmd", "/S", "/C"]
USER ContainerAdministrator

ARG jdkVersion=21.0.13.1.2

COPY --from=download ["C:/patch", "C:/patch"]

RUN rmdir /S /Q "C:\Program Files\Java\OpenJDK" && \
    move "C:\patch\OpenJDK" "C:\Program Files\Java\OpenJDK" && \
    rmdir /S /Q C:\patch && \
    "C:\Program Files\Java\OpenJDK\bin\java.exe" -version 2>&1 | findstr /C:"%jdkVersion%"

# Switch back to the regular user, as in the original images
USER ContainerUser
