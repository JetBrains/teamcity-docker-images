# Default arguments
ARG dotnetWindowsComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-win-x64.zip'
ARG dotnetWindowsComponent_31='https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-win-x64.zip'
ARG dotnetWindowsComponent_50='https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-win-x64.zip'
ARG dotnetWindowsComponentSHA512='d2fa2f0d2b4550ac3408b924ab356add378af1d0f639623f0742e37f57b3f2b525d81f5d5c029303b6d95fed516b04a7b6c3a98f27f770fc8b4e76414cf41660'
ARG dotnetWindowsComponentSHA512_31='7ba766b2f388ab09beee6a465f1eeb6b9a6858c8b6da51dacc79622b110558ef6211a40e715a16b526f2da08216c99143570b8253ff2c5ad874400475d1feb44'
ARG dotnetWindowsComponentSHA512_50='636f22bfbfd98c80c96f2fc3815beb42ee2699cf2a410eeba24ddcc9304bc39594260eca4061b012d4b02b9c4592fa6927343077df053343a9c344a9289658e1'
ARG gitWindowsComponent='https://github.com/git-for-windows/git/releases/download/v2.33.0.windows.2/MinGit-2.33.0.2-64-bit.zip'
ARG gitWindowsComponentSHA256='e28968ddd1c928eec233e0c692a90d6ac41eb7b53a9d7a408c13cb5b613afa95'
ARG jdkWindowsComponent='https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-windows-x64-jdk.zip'
ARG jdkWindowsComponentMD5SUM='a937acacf9cc25ec06f9b240d064ad99'
ARG mercurialWindowsComponent='https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi'
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:local-nanoserver-1809'
ARG windowsservercoreImage='mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019'

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



FROM ${teamcityMinimalAgentImage} AS buildagent

ARG windowsservercoreImage
FROM ${windowsservercoreImage}

COPY scripts/*.cs /scripts/

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
    Expand-Archive $dotnet31Path -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    [io.file]::Delete($dotnet31Path) ; \
    Expand-Archive $dotnet50Path -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    [io.file]::Delete($dotnet50Path) ; \
    Expand-Archive $dotnetPath -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    [io.file]::Delete($dotnetPath) ; \
    Get-ChildItem -Path $Env:ProgramFiles\dotnet -Include *.lzma -File -Recurse | foreach { $_.Delete()}; \
    Expand-Archive $jdkPath -DestinationPath $Env:ProgramFiles\Java ; \
    Get-ChildItem $Env:ProgramFiles\Java | Rename-Item -NewName "OpenJDK" ; \
    Remove-Item $Env:ProgramFiles\Java\OpenJDK\lib\src.zip -Force ; \
    [io.file]::Delete($jdkPath) ; \
    $gitDestPath = $Env:ProgramFiles + '\Git'; \
    Expand-Archive $gitPath -DestinationPath $gitDestPath ; \
    [io.file]::Delete($gitPath) ; \
    # avoid circular dependencies in gitconfig
    $gitConfigFile = $gitDestPath + '\etc\gitconfig'; \
    $configContent = Get-Content $gitConfigFile; \
    $configContent = $configContent.Replace('path = C:/Program Files/Git/etc/gitconfig', ''); \
    Set-Content $gitConfigFile $configContent; \
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
