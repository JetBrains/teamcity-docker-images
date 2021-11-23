# Default arguments
ARG dotnetLatestWindowsComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.401/dotnet-sdk-5.0.401-win-x64.zip'
ARG dotnetLatestWindowsComponentSHA512='9838f0286afe3724f490c6686564dae3ab27c9fe0e2c41a48d8aaeaabd07d9a219e207206d88311241c66dde8c79a9dae7ec1f8103303aeaf6943a3a9f6d34e5'
ARG teamcityAgentImage='jetbrains/teamcity-agent:2021.2.1-nanoserver-1809'

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
