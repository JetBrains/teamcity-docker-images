# Default arguments
ARG dotnetWindowsComponent='https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.406/dotnet-sdk-8.0.406-win-x64.zip'
ARG dotnetWindowsComponentSHA512='88095e181228e9e496574ed6f36582303533369cd253f41abad6c3aaa7d23436736a3fb1dd6c908032b2cfda445f66d50628516395783bef3d5ee9bbb00edcd2'
ARG gitWindowsComponent='https://github.com/git-for-windows/git/releases/download/v2.50.1.windows.1/MinGit-2.50.1-64-bit.zip'
ARG gitWindowsComponentSHA256='6f672aebe9e488a246efd6875f9197dbc0d9a40100e218acc3877cba2b206c45'
ARG jdkWindowsComponent='https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip'
ARG jdkWindowsComponentMD5SUM='20023c16bbc60d518ee7f8ca040a8c58'
ARG mercurialWindowsComponent='https://www.mercurial-scm.org/release/windows/mercurial-6.1.1-x64.msi'
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:EAP-nanoserver-2022'
ARG windowsservercoreImage='mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2022'

# The list of required arguments
# ARG windowsservercoreImage
# ARG dotnetWindowsComponent
# ARG dotnetWindowsComponentSHA512
# ARG jdkWindowsComponent
# ARG jdkWindowsComponentMD5SUM
# ARG gitWindowsComponent
# ARG gitWindowsComponentSHA256
# ARG mercurialWindowsComponentName
# ARG teamcityMinimalAgentImage




FROM ${teamcityMinimalAgentImage} AS buildagent

ARG windowsservercoreImage
FROM ${windowsservercoreImage}

# On some agents, Windows 2022 requires administrator permissions to modify "C:/" folder within ...
# ... PowerShell container.
USER ContainerAdministrator

COPY scripts/*.cs /scripts/

# PowerShell
SHELL ["powershell", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

ARG dotnetWindowsComponent
ARG dotnetWindowsComponentSHA512
ARG jdkWindowsComponent
ARG jdkWindowsComponentMD5SUM
ARG gitWindowsComponent
ARG gitWindowsComponentSHA256
ARG mercurialWindowsComponent

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
    Add-Type -IgnoreWarnings -TypeDefinition "$code" -Language CSharp ; \
    $downloadScript = [Scripts.Web]::DownloadFiles($Env:jdkWindowsComponent + '#MD5#' + $Env:jdkWindowsComponentMD5SUM, 'jdk.zip', $Env:gitWindowsComponent + '#SHA256#' + $Env:gitWindowsComponentSHA256, 'git.zip', $Env:mercurialWindowsComponent, 'hg.msi', $Env:dotnetWindowsComponent + '#SHA512#' + $Env:dotnetWindowsComponentSHA512, 'dotnet.zip') ; \
    Remove-Item -Force -Recurse $Env:ProgramFiles\dotnet; \
# .NET 6.0, .NET Framework 4 is inherited from base image
    Expand-Archive dotnet.zip -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    Remove-Item -Force dotnet.zip; \
    Get-ChildItem -Path $Env:ProgramFiles\dotnet -Include *.lzma -File -Recurse | foreach { $_.Delete()}; \
# JDK
    Expand-Archive jdk.zip -DestinationPath $Env:ProgramFiles\Java ; \
    Get-ChildItem $Env:ProgramFiles\Java | Rename-Item -NewName "OpenJDK" ; \
    Remove-Item $Env:ProgramFiles\Java\OpenJDK\lib\src.zip -Force ; \
    Remove-Item -Force jdk.zip ; \
# Git
    $gitPath = $Env:ProgramFiles + '\Git'; \
    Expand-Archive git.zip -DestinationPath $gitPath ; \
    Remove-Item -Force git.zip ; \
    # avoid circular dependencies in gitconfig
    $gitConfigFile = $gitPath + '\etc\gitconfig'; \
    $configContent = Get-Content $gitConfigFile; \
    $configContent = $configContent.Replace('path = C:/Program Files/Git/etc/gitconfig', ''); \
    Set-Content $gitConfigFile $configContent; \
# Mercirual
    Start-Process msiexec -Wait -ArgumentList /q, /i, hg.msi ; \
    Remove-Item -Force hg.msi

COPY --from=buildagent /BuildAgent /BuildAgent

EXPOSE 9090

VOLUME C:/BuildAgent/conf

USER ContainerUser
CMD ["powershell", "./BuildAgent/run-agent.ps1"]

    # Configuration file for TeamCity agent
ENV CONFIG_FILE="C:\BuildAgent\conf\buildAgent.properties" \
    # Java home directory
    JAVA_HOME="C:\Program Files\Java\OpenJDK" \
    # Opt out of the telemetry feature
    DOTNET_CLI_TELEMETRY_OPTOUT=true \
    # Disable first time experience
    DOTNET_SKIP_FIRST_TIME_EXPERIENCE=true \
    # Configure Kestrel web server to bind to port 80 when present
    ASPNETCORE_URLS=http://+:80 \
    # Enable detection of running in a container
    DOTNET_RUNNING_IN_CONTAINER=true \
    # Enable correct mode for dotnet watch (only mode supported in a container)
    DOTNET_USE_POLLING_FILE_WATCHER=true \
    # Skip extraction of XML docs - generally not useful within an image/container - helps perfomance
    NUGET_XMLDOC_MODE=skip

USER ContainerAdministrator
RUN setx /M PATH ('{0};{1}\bin;C:\Program Files\Git\cmd;C:\Program Files\Mercurial' -f $env:PATH, $env:JAVA_HOME)
# Grant Permissions for ContainerUser (Default Account), OI - Object Inherit, CI - Container Inherit, ...
# ... F - full control, D - delete, /T - apply to subfolders & files
RUN cmd /c icacls.exe "C:\\BuildAgent" /grant:r 'DefaultAccount:(OI)(CI)F' /grant:r 'DefaultAccount:(OI)(CI)D' /T
RUN cmd /c icacls.exe "C:\\BuildAgent" /grant:r 'Users:(OI)(CI)F' /grant:r 'Users:(OI)(CI)D' /T
# Applied permission check for logging purposes
RUN cmd /c icacls.exe C:\\BuildAgent\\*
USER ContainerUser
