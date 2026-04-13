# Default arguments
ARG dotnetWindowsComponent='https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-win-x64.zip'
ARG dotnetWindowsComponentSHA512='904ed90eaa83083584d108a17f671113dd88bbe4485130bf818c8f3b12a717457b2cf29db7d3e66fbf959265bed851def1a890ec9a1349c8d0ff2ec08af65c7c'
ARG gitWindowsComponent='https://github.com/git-for-windows/git/releases/download/v2.53.0.windows.1/MinGit-2.53.0-64-bit.zip'
ARG gitWindowsComponentSHA256='82b562c918ec87b2ef5316ed79bb199e3a25719bb871a0f10294acf21ebd08cd'
ARG jdkWindowsComponent='https://corretto.aws/downloads/resources/21.0.10.7.1/amazon-corretto-21.0.10.7.1-windows-x64-jdk.zip'
ARG jdkWindowsComponentMD5SUM='403888fc1d84a8d7a823ad7ff3ecc589'
ARG mercurialWindowsComponent='https://www.mercurial-scm.org/release/windows/mercurial-6.2.2-x64.msi'
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
# Create missing directories required for volumes, reset any potentially conflicting ACLs, ...
# ... grant Permissions for ContainerUser (Default Account), OI - Object Inherit, CI - Container Inherit, ...
# ... F - full control, /T - apply to subfolders & files
RUN setx /M PATH ('{0};{1}\bin;C:\Program Files\Git\cmd;C:\Program Files\Mercurial' -f $env:PATH, $env:JAVA_HOME) ; \
    New-Item -ItemType Directory -Force -Path C:\BuildAgent\logs, C:\BuildAgent\work, C:\BuildAgent\conf | Out-Null ; \
    New-Item -ItemType File -Force -Path C:\BuildAgent\logs\.keep, C:\BuildAgent\work\.keep, C:\BuildAgent\conf\.keep | Out-Null ; \
    if (Test-Path 'C:\BuildAgent\conf\buildAgent.properties') { Remove-Item -Force 'C:\BuildAgent\conf\buildAgent.properties' } ; \
    icacls.exe C:\BuildAgent /reset /T ; \
    icacls.exe C:\BuildAgent /grant:r 'DefaultAccount:(OI)(CI)F' /grant:r 'Users:(OI)(CI)F' /T ; \
    <# Canonicalizing ACLs to prevent issues such as TW-100061 #> \
    $acl = Get-Acl 'C:\BuildAgent'; Set-Acl 'C:\BuildAgent' $acl; \
    Get-ChildItem 'C:\BuildAgent' -Recurse -Force | ForEach-Object { $a = Get-Acl $_.FullName; Set-Acl $_.FullName $a }; \
    $acl = Get-Acl 'C:\BuildAgent'; if (-not $acl.AreAccessRulesCanonical) { throw 'ACLs are not canonical after Set-Acl on C:\BuildAgent' }; \
    icacls.exe 'C:\BuildAgent\*'

USER ContainerUser


# NB! The legacy builder discards permissions changes after the volune has been initialized => `icacls` has to be executed earlier
VOLUME C:/BuildAgent/conf
