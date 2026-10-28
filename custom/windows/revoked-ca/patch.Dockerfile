# check=skip=InvalidDefaultArgInFrom
#
# Patches a published TeamCity Windows image (Server 2019 / 1809 and 2022) against CVE-2026-111943:
# 1. Replaces Corretto and, if installed, MinGit (it ships its own CA bundle),
# 2. Adds the compromised intermediates to the Windows 'Disallowed' store.
#
# @param baseImage       image to patch, e.g. 'jetbrains/teamcity-agent:2026.1-windowsservercore-2022'
# @param powershellImage PowerShell image with the same OS version, e.g. 'mcr.microsoft.com/powershell:nanoserver-ltsc2022'
#

ARG baseImage
ARG powershellImage

FROM ${powershellImage} AS download

USER ContainerAdministrator
SHELL ["pwsh", "-NoProfile", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

ARG jdkWindowsComponent='https://corretto.aws/downloads/resources/21.0.13.0.11/amazon-corretto-21.0.13.0.11-windows-x64-jdk.zip'
ARG jdkWindowsComponentMD5SUM='4ddf720246e751c6a54d806da23b9c9c'
ARG gitWindowsComponent='https://github.com/git-for-windows/git/releases/download/v2.56.0.windows.4/MinGit-2.56.0.4-64-bit.zip'
ARG gitWindowsComponentSHA256='e3bf78351fe777fba47ddee5950082f8a98bb0f02604fdc7ba3ee2cb68ffa877'

RUN Invoke-WebRequest -UseBasicParsing -Uri $Env:jdkWindowsComponent -OutFile C:\jdk.zip ; \
    if ((Get-FileHash C:\jdk.zip -Algorithm MD5).Hash -ne $Env:jdkWindowsComponentMD5SUM) { throw 'JDK checksum mismatch' } ; \
    Expand-Archive C:\jdk.zip -DestinationPath C:\jdk-tmp ; \
    New-Item -ItemType Directory C:\patch | Out-Null ; \
    Get-ChildItem C:\jdk-tmp | Select-Object -First 1 | Move-Item -Destination C:\patch\OpenJDK ; \
    Remove-Item C:\patch\OpenJDK\lib\src.zip -Force -ErrorAction SilentlyContinue ; \
    Remove-Item C:\jdk.zip, C:\jdk-tmp -Recurse -Force

RUN Invoke-WebRequest -UseBasicParsing -Uri $Env:gitWindowsComponent -OutFile C:\git.zip ; \
    if ((Get-FileHash C:\git.zip -Algorithm SHA256).Hash -ne $Env:gitWindowsComponentSHA256) { throw 'MinGit checksum mismatch' } ; \
    Expand-Archive C:\git.zip -DestinationPath C:\patch\Git ; \
    # Avoid circular dependencies in gitconfig, same as in the original images
    (Get-Content C:\patch\Git\etc\gitconfig) -replace 'path = C:/Program Files/Git/etc/gitconfig', '' | Set-Content C:\patch\Git\etc\gitconfig ; \
    Remove-Item C:\git.zip -Force

COPY ["windows/revoked-ca/disallow-compromised-ca.ps1", "windows/revoked-ca/verify-compromised-ca-absence.ps1", "C:/patch/revoked-ca/"]
COPY ["windows/revoked-ca/disallowed", "C:/patch/revoked-ca/disallowed"]


FROM ${baseImage}

SHELL ["cmd", "/S", "/C"]
USER ContainerAdministrator

COPY --from=download ["C:/patch", "C:/patch"]

# Corretto always, MinGit only in images that already have Git (Server, Agent).
# Windows trusts the compromised intermediates through their roots, so block them in the 'Disallowed' store
# (entries the base image already has are kept). The build fails if any CA store still trusts them.
RUN rmdir /S /Q "C:\Program Files\Java\OpenJDK" && \
    move "C:\patch\OpenJDK" "C:\Program Files\Java\OpenJDK" && \
    (if exist "C:\Program Files\Git\cmd\git.exe" (rmdir /S /Q "C:\Program Files\Git" && move "C:\patch\Git" "C:\Program Files\Git")) && \
    (if exist "C:\Program Files\PowerShell\pwsh.exe" ("C:\Program Files\PowerShell\pwsh.exe" -NoProfile -ExecutionPolicy Bypass -File C:\patch\revoked-ca\disallow-compromised-ca.ps1) else (powershell -NoProfile -ExecutionPolicy Bypass -File C:\patch\revoked-ca\disallow-compromised-ca.ps1)) && \
    (if exist "C:\Program Files\PowerShell\pwsh.exe" ("C:\Program Files\PowerShell\pwsh.exe" -NoProfile -ExecutionPolicy Bypass -File C:\patch\revoked-ca\verify-compromised-ca-absence.ps1) else (powershell -NoProfile -ExecutionPolicy Bypass -File C:\patch\revoked-ca\verify-compromised-ca-absence.ps1)) && \
    rmdir /S /Q C:\patch

# Switch back to the regular user, as in the original images
USER ContainerUser
