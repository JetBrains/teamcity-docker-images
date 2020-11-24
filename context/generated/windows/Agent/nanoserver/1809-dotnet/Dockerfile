# Default arguments
ARG dotnetLatestWindowsComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.100/dotnet-sdk-5.0.100-win-x64.zip'
ARG teamcityAgentImage='jetbrains/teamcity-agent:2020.2-nanoserver-1809'

# The list of required arguments
# ARG dotnetLatestWindowsComponent
# ARG teamcityAgentImage



FROM ${teamcityAgentImage}

# COPY scripts/*.cs /scripts/
SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

COPY scripts/*.cs /scripts/

ARG dotnetLatestWindowsComponent

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
    Add-Type -TypeDefinition "$code" -Language CSharp ; \
    $downloadScript = [Scripts.Web]::DownloadFiles($Env:dotnetLatestWindowsComponent, 'dotnetLatest.zip') ; \
    Expand-Archive dotnetLatest.zip -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    Remove-Item -Force dotnetLatest.zip; \
    Get-ChildItem -Path $Env:ProgramFiles\dotnet -Include *.lzma -File -Recurse | foreach { $_.Delete()};

USER ContainerUser

# Trigger first run experience by running arbitrary cmd to populate local package cache
RUN dotnet help

CMD pwsh ./BuildAgent/run-agent.ps1
