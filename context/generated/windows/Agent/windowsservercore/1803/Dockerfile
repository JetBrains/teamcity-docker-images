# Default arguments
ARG dotnetWindowsComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.413/dotnet-sdk-3.1.413-win-x64.zip'
ARG dotnetWindowsComponentSHA512='1fe3beb2e033c557c1577452f341121c1a8b2520b8bc80c2e1291d382964aeef7bfbc3e7b55c9130b934fb89aa6cbf1354cf625b3b1dada1bde3443b43ca2359'
ARG gitWindowsComponent='https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip'
ARG gitWindowsComponentSHA256='e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95'
ARG jdkWindowsComponent='https://corretto.aws/downloads/resources/11.0.12.7.1/amazon-corretto-11.0.12.7.1-windows-x64-jdk.zip'
ARG jdkWindowsComponentMD5SUM='022bf679bc9c337287859264496c2f38'
ARG mercurialWindowsComponent='https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi'
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:EAP-nanoserver-1803'
ARG windowsservercoreImage='mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1803'

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

COPY scripts/*.cs /scripts/

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
    Add-Type -TypeDefinition "$code" -Language CSharp ; \
    $downloadScript = [Scripts.Web]::DownloadFiles($Env:jdkWindowsComponent + '#MD5#' + $Env:jdkWindowsComponentMD5SUM, 'jdk.zip', $Env:gitWindowsComponent + '#SHA256#' + $Env:gitWindowsComponentSHA256, 'git.zip', $Env:mercurialWindowsComponent, 'hg.msi', $Env:dotnetWindowsComponent + '#SHA512#' + $Env:dotnetWindowsComponentSHA512, 'dotnet.zip') ; \
    Remove-Item -Force -Recurse $Env:ProgramFiles\dotnet; \
    Expand-Archive dotnet.zip -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    Remove-Item -Force dotnet.zip; \
    Get-ChildItem -Path $Env:ProgramFiles\dotnet -Include *.lzma -File -Recurse | foreach { $_.Delete()}; \
    Expand-Archive jdk.zip -DestinationPath $Env:ProgramFiles\Java ; \
    Get-ChildItem $Env:ProgramFiles\Java | Rename-Item -NewName "OpenJDK" ; \
    Remove-Item $Env:ProgramFiles\Java\OpenJDK\lib\src.zip -Force ; \
    Remove-Item -Force jdk.zip ; \
    $gitPath = $Env:ProgramFiles + '\Git'; \
    Expand-Archive git.zip -DestinationPath $gitPath ; \
    Remove-Item -Force git.zip ; \
    # avoid circular dependencies in gitconfig
    $gitConfigFile = $gitPath + '\etc\gitconfig'; \
    $configContent = Get-Content $gitConfigFile; \
    $configContent = $configContent.Replace('path = C:/Program Files/Git/etc/gitconfig', ''); \
    Set-Content $gitConfigFile $configContent; \
    Start-Process msiexec -Wait -ArgumentList /q, /i, hg.msi ; \
    Remove-Item -Force hg.msi

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
