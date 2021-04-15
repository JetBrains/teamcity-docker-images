# Default arguments
ARG dotnetLatestWindowsComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.102/dotnet-sdk-5.0.102-win-x64.zip'
ARG dotnetLatestWindowsComponentSHA512='118056d7c60d9591b0a803fb4f8941b6fa5166553d1deac625279330b05599073231ee4c4ecdc3f179d57261290b6c62ac7f34d5f89c8b06274e2346a069f79b'
ARG teamcityAgentImage='jetbrains/teamcity-agent:2020.2.4-nanoserver-1809'

# The list of required arguments
# ARG dotnetLatestWindowsComponent
# ARG dotnetLatestWindowsComponentSHA512
# ARG teamcityAgentImage



FROM ${teamcityAgentImage}

# COPY scripts/*.cs /scripts/
SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

COPY scripts/*.cs /scripts/

ARG dotnetLatestWindowsComponent
ARG dotnetLatestWindowsComponentSHA512

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
    Add-Type -TypeDefinition "$code" -Language CSharp ; \
    $downloadScript = [Scripts.Web]::DownloadFiles($Env:dotnetLatestWindowsComponent + '#SHA512#' + $Env:dotnetLatestWindowsComponentSHA512, 'dotnetLatest.zip') ; \
    Expand-Archive dotnetLatest.zip -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    Remove-Item -Force dotnetLatest.zip; \
    Get-ChildItem -Path $Env:ProgramFiles\dotnet -Include *.lzma -File -Recurse | foreach { $_.Delete()};

USER ContainerUser

# Trigger first run experience by running arbitrary cmd to populate local package cache
RUN dotnet help

CMD pwsh ./BuildAgent/run-agent.ps1
