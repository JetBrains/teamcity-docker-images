#
# Patches a published TeamCity Windows image (Server 2019 / 1809 and 2022) against CVE-2026-111943.
# Replaces Corretto and, if installed, MinGit (it ships its own CA bundle).
# The Windows CA store comes from the base image and needs a rebuild on a fixed base image instead.
#
# @param baseImage       image to patch, e.g. 'jetbrains/teamcity-agent:2026.1-windowsservercore-2022'
# @param powershellImage PowerShell image with the same OS version, e.g. 'mcr.microsoft.com/powershell:nanoserver-ltsc2022'
#

ARG baseImage
ARG powershellImage

FROM ${powershellImage} AS download

USER ContainerAdministrator
SHELL ["pwsh", "-NoProfile", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

ARG jdkWindowsComponent='https://corretto.aws/downloads/resources/21.0.13.0.10/amazon-corretto-21.0.13.0.10-windows-x64-jdk.zip'
ARG jdkWindowsComponentMD5SUM='a73908091ac0cf1f5ee2af0bd9e92bd9'
ARG gitWindowsComponent='https://github.com/git-for-windows/git/releases/download/v2.56.0.windows.3/MinGit-2.56.0.3-64-bit.zip'
ARG gitWindowsComponentSHA256='2e5619966e588fde5f8122c5557cad50c0fbafcaed736689f40ec1dfb1d78ea7'

RUN Invoke-WebRequest -UseBasicParsing -Uri $Env:jdkWindowsComponent -OutFile C:\jdk.zip ; \
    if ((Get-FileHash C:\jdk.zip -Algorithm MD5).Hash -ne $Env:jdkWindowsComponentMD5SUM) { throw 'JDK checksum mismatch' } ; \
    Expand-Archive C:\jdk.zip -DestinationPath C:\jdk-tmp ; \
    Get-ChildItem C:\jdk-tmp | Select-Object -First 1 | Move-Item -Destination C:\OpenJDK ; \
    Remove-Item C:\OpenJDK\lib\src.zip -Force -ErrorAction SilentlyContinue ; \
    Remove-Item C:\jdk.zip, C:\jdk-tmp -Recurse -Force

RUN Invoke-WebRequest -UseBasicParsing -Uri $Env:gitWindowsComponent -OutFile C:\git.zip ; \
    if ((Get-FileHash C:\git.zip -Algorithm SHA256).Hash -ne $Env:gitWindowsComponentSHA256) { throw 'MinGit checksum mismatch' } ; \
    Expand-Archive C:\git.zip -DestinationPath C:\Git ; \
    # Avoid circular dependencies in gitconfig, same as in the original images
    (Get-Content C:\Git\etc\gitconfig) -replace 'path = C:/Program Files/Git/etc/gitconfig', '' | Set-Content C:\Git\etc\gitconfig ; \
    Remove-Item C:\git.zip -Force


FROM ${baseImage}

# The base image may have set another SHELL (e.g. PowerShell in the Agent image)
SHELL ["cmd", "/S", "/C"]
USER ContainerAdministrator

COPY --from=download ["C:/OpenJDK", "C:/OpenJDK-patch"]
COPY --from=download ["C:/Git", "C:/Git-patch"]

# One layer for the swap: Corretto always, MinGit only in images that already have Git (Server, Agent)
RUN rmdir /S /Q "C:\Program Files\Java\OpenJDK" && \
    move "C:\OpenJDK-patch" "C:\Program Files\Java\OpenJDK" && \
    if exist "C:\Program Files\Git\cmd\git.exe" (rmdir /S /Q "C:\Program Files\Git" && move "C:\Git-patch" "C:\Program Files\Git") else (rmdir /S /Q "C:\Git-patch") && \
    "C:\Program Files\Java\OpenJDK\bin\java.exe" -version

# Switch back to the regular user, as in the original images
USER ContainerUser
