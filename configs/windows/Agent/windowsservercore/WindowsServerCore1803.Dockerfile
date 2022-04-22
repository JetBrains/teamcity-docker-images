# The list of required arguments
# ARG windowsservercoreImage
# ARG dotnetWindowsComponent
# ARG dotnetWindowsComponentSHA512
# ARG dotnetWindowsComponent_31
# ARG dotnetWindowsComponentSHA512_31
# ARG dotnetWindowsComponent_50
# ARG dotnetWindowsComponentSHA512_50
# ARG jdkWindowsComponent
# ARG jdkWindowsComponentMD5SUM
# ARG gitWindowsComponent
# ARG gitWindowsComponentSHA256
# ARG mercurialWindowsComponentName
# ARG teamcityMinimalAgentImage

# Id teamcity-agent
# Tag ${versionTag}-${tag}
# Tag ${versionTag}-windowsservercore
# Tag ${latestTag}-windowsservercore
# Platform ${windowsPlatform}
# Repo ${repo}
# Weight 16

## ${agentCommentHeader}

# Based on ${teamcityMinimalAgentImage}
FROM ${teamcityMinimalAgentImage} AS buildagent

# Based on ${windowsservercoreImage} 12
ARG windowsservercoreImage
FROM ${windowsservercoreImage}

COPY scripts/*.cs /scripts/

# Install ${powerShellComponentName}
SHELL ["powershell", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

ARG dotnetWindowsComponent
ARG dotnetWindowsComponentSHA512
ARG dotnetWindowsComponent_31
ARG dotnetWindowsComponentSHA512_31
ARG dotnetWindowsComponent_50
ARG dotnetWindowsComponentSHA512_50
ARG jdkWindowsComponent
ARG jdkWindowsComponentMD5SUM
ARG gitWindowsComponent
ARG gitWindowsComponentSHA256
ARG mercurialWindowsComponent

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    $jdkPath = [io.path]::GetTempPath() + 'jdk.zip'; \
    $gitPath = [io.path]::GetTempPath() + 'git.zip'; \
    $hgPath = [io.path]::GetTempPath() + 'hg.msi'; \
    $dotnetPath = [io.path]::GetTempPath() + 'dotnet.zip'; \
    $dotnet31Path = [io.path]::GetTempPath() + 'dotnet_31.zip'; \
    $dotnet50Path = [io.path]::GetTempPath() + 'dotnet_50.zip'; \
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
    Add-Type -IgnoreWarnings -TypeDefinition "$code" -Language CSharp ; \
    $downloadScript = [Scripts.Web]::DownloadFiles($Env:jdkWindowsComponent + '#MD5#' + $Env:jdkWindowsComponentMD5SUM, $jdkPath, $Env:gitWindowsComponent + '#SHA256#' + $Env:gitWindowsComponentSHA256, $gitPath, $Env:mercurialWindowsComponent, $hgPath, $Env:dotnetWindowsComponent + '#SHA512#' + $Env:dotnetWindowsComponentSHA512, $dotnetPath, $Env:dotnetWindowsComponent_31 + '#SHA512#' + $Env:dotnetWindowsComponentSHA512_31, $dotnet31Path, $Env:dotnetWindowsComponent_50 + '#SHA512#' + $Env:dotnetWindowsComponentSHA512_50, $dotnet50Path) ; \
    Remove-Item -Force -Recurse $Env:ProgramFiles\dotnet; \
# Install [${dotnetWindowsComponentName_31}](${dotnetWindowsComponent_31})
    Expand-Archive $dotnet31Path -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    [io.file]::Delete($dotnet31Path) ; \
# Install [${dotnetWindowsComponentName_50}](${dotnetWindowsComponent_50})
    Expand-Archive $dotnet50Path -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    [io.file]::Delete($dotnet50Path) ; \
# Install [${dotnetWindowsComponentName}](${dotnetWindowsComponent})
    Expand-Archive $dotnetPath -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    [io.file]::Delete($dotnetPath) ; \
    Get-ChildItem -Path $Env:ProgramFiles\dotnet -Include *.lzma -File -Recurse | foreach { $_.Delete()}; \
# Install [${jdkWindowsComponentName}](${jdkWindowsComponent})
    Expand-Archive $jdkPath -DestinationPath $Env:ProgramFiles\Java ; \
    Get-ChildItem $Env:ProgramFiles\Java | Rename-Item -NewName "OpenJDK" ; \
    Remove-Item $Env:ProgramFiles\Java\OpenJDK\lib\src.zip -Force ; \
    [io.file]::Delete($jdkPath) ; \
# Install [${gitWindowsComponentName}](${gitWindowsComponent})
    $gitDestPath = $Env:ProgramFiles + '\Git'; \
    Expand-Archive $gitPath -DestinationPath $gitDestPath ; \
    [io.file]::Delete($gitPath) ; \
    # avoid circular dependencies in gitconfig
    $gitConfigFile = $gitDestPath + '\etc\gitconfig'; \
    $configContent = Get-Content $gitConfigFile; \
    $configContent = $configContent.Replace('path = C:/Program Files/Git/etc/gitconfig', ''); \
    Set-Content $gitConfigFile $configContent; \
# Install [${mercurialWindowsComponentName}](${mercurialWindowsComponent})
    Start-Process msiexec -Wait -ArgumentList /q, /i, $hgPath ; \
    [io.file]::Delete($hgPath)

COPY --from=buildagent /BuildAgent /BuildAgent

EXPOSE 9090

VOLUME C:/BuildAgent/conf

CMD ./BuildAgent/run-agent.ps1

    # Configuration file for TeamCity agent
ENV CONFIG_FILE="C:/BuildAgent/conf/buildAgent.properties" \
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
USER ContainerUser