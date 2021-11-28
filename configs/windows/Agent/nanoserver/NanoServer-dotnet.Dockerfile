# The list of required arguments
# ARG dotnetLatestWindowsComponent
# ARG dotnetLatestWindowsComponentSHA512
# ARG teamcityAgentImage

# Id teamcity-agent
# Tag ${tag}
# Platform ${windowsPlatform}
# Repo ${repo}
# Weight 1

## ${agentCommentHeader}
## This image can be built manually. It contains a set of .NET SDK.

# Based on ${teamcityAgentImage} 5
FROM ${teamcityAgentImage}

# COPY scripts/*.cs /scripts/
SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

COPY scripts/*.cs /scripts/

ARG dotnetLatestWindowsComponent
ARG dotnetLatestWindowsComponentSHA512

# Install [${dotnetLatestWindowsComponentName}](${dotnetLatestWindowsComponent})
RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
    Add-Type -IgnoreWarnings -TypeDefinition "$code" -Language CSharp ; \
    $downloadScript = [Scripts.Web]::DownloadFiles($Env:dotnetLatestWindowsComponent + '#SHA512#' + $Env:dotnetLatestWindowsComponentSHA512, 'dotnetLatest.zip') ; \
# Install [${dotnetLatestWindowsComponentName}](${dotnetLatestWindowsComponent})
    Expand-Archive dotnetLatest.zip -Force -DestinationPath $Env:ProgramFiles\dotnet; \
    Remove-Item -Force dotnetLatest.zip; \
    Get-ChildItem -Path $Env:ProgramFiles\dotnet -Include *.lzma -File -Recurse | foreach { $_.Delete()};

USER ContainerUser

# Trigger first run experience by running arbitrary cmd to populate local package cache
RUN dotnet help

CMD pwsh ./BuildAgent/run-agent.ps1