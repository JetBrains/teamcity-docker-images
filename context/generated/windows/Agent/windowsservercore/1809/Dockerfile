# Default arguments
ARG dotnetWindowsComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-win-x64.zip'
ARG dotnetWindowsComponent_31='https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-win-x64.zip'
ARG dotnetWindowsComponent_50='https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.17/dotnet-runtime-5.0.17-win-x64.zip'
ARG dotnetWindowsComponentSHA512='d3e8273d451b3bc15cd8f656ffdaaff4d1b0d17059ada578967063f4b9882b74926af61c6e0919f66b5bdeddd66e5047743544ac4768026b60de7a7591e1fab5'
ARG dotnetWindowsComponentSHA512_31='0a9cc67349adaebb8b81754aae5d7c478a043e25661537d149c65b098c13446ec78626f23e7eab4c5a6539485888f4f0a200e539038bae0be9e45aaefc76c05b'
ARG dotnetWindowsComponentSHA512_50='09721d707779168484579d07fc76e5c29ada8c32b9533689f9f066da3aa98d4a615d825b7212aedf4744d017e9cf3ba86d6a570d00826b732bbc5dd6976a8774'
ARG gitWindowsComponent='https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.1/MinGit-2.42.0-64-bit.zip'
ARG gitWindowsComponentSHA256='b945e6df773fd8013f12e26b65b6815122be62a241d3ef4b9ed2d5ae67ae0aa1'
ARG jdkWindowsComponent='https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip'
ARG jdkWindowsComponentMD5SUM='feb7eab99c647a0b4347be9f0a3276de'
ARG mercurialWindowsComponent='https://www.mercurial-scm.org/release/windows/mercurial-5.9.1-x64.msi'
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:EAP-nanoserver-1809'
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
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
    Add-Type -IgnoreWarnings -TypeDefinition "$code" -Language CSharp ; \
    $downloadScript = [Scripts.Web]::DownloadFiles($Env:jdkWindowsComponent + '#MD5#' + $Env:jdkWindowsComponentMD5SUM, 'jdk.zip', $Env:gitWindowsComponent + '#SHA256#' + $Env:gitWindowsComponentSHA256, 'git.zip', $Env:mercurialWindowsComponent, 'hg.msi', $Env:dotnetWindowsComponent + '#SHA512#' + $Env:dotnetWindowsComponentSHA512, 'dotnet.zip', $Env:dotnetWindowsComponent_31 + '#SHA512#' + $Env:dotnetWindowsComponentSHA512_31, 'dotnet_31.zip', $Env:dotnetWindowsComponent_50 + '#SHA512#' + $Env:dotnetWindowsComponentSHA512_50, 'dotnet_50.zip') ; \
    Remove-Item -Force -Recurse $Env:ProgramFiles\dotnet; \
    Expand-Archive dotnet_31.zip -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    Remove-Item -Force dotnet_31.zip; \
    Expand-Archive dotnet_50.zip -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    Remove-Item -Force dotnet_50.zip; \
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

CMD ["pwsh", "./BuildAgent/run-agent.ps1"]

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
