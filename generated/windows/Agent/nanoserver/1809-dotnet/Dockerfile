# Default arguments
ARG dotnetLatestWindowsComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.203/dotnet-sdk-5.0.203-win-x64.zip'
ARG dotnetLatestWindowsComponentSHA512='762ad53d66b893cb2cdf61540794a4a1e20b127e371f57f912ad8ebd4102aabf32366ebaabfe90aa362c1fae0bec0aa7ac6af35c6c0153fb913cd4c532149238'
ARG teamcityAgentImage='jetbrains/teamcity-agent:2020.2.1-nanoserver-1809'

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
