# The list of required arguments
# ARG powershellImage
# ARG jdkServerWindowsComponent
# ARG jdkServerWindowsComponentMD5SUM
# ARG gitWindowsComponent
# ARG gitWindowsComponentSHA256
# ARG windowsBuild
# ARG powershellImage

# Id teamcity-server
# Tag ${versionTag}-${tag}
# Tag ${latestTag}
# Tag ${versionTag}
# Platform ${windowsPlatform}
# Repo ${repo}
# Weight 3

## ${serverCommentHeader}

# Based on ${powershellImage} 3
# Install ${powerShellComponentName}
FROM ${powershellImage} AS base

COPY scripts/*.cs /scripts/
SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

# Install [${jdkServerWindowsComponentName}](${jdkServerWindowsComponent})
ARG jdkServerWindowsComponent
ARG jdkServerWindowsComponentMD5SUM

# Install [${gitWindowsComponentName}](${gitWindowsComponent})
ARG gitWindowsComponent
ARG gitWindowsComponentSHA256

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    $jdkPath = [io.path]::GetTempPath() + 'jdk.zip'; \
    $gitPath = [io.path]::GetTempPath() + 'git.zip'; \
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
    Add-Type -IgnoreWarnings -TypeDefinition "$code" -Language CSharp ; \
    $downloadScript = [Scripts.Web]::DownloadFiles($Env:jdkServerWindowsComponent + '#MD5#' + $Env:jdkServerWindowsComponentMD5SUM, $jdkPath, $Env:gitWindowsComponent + '#SHA256#' + $Env:gitWindowsComponentSHA256, $gitPath) ; \
    iex $downloadScript ; \
    Expand-Archive $jdkPath -DestinationPath $Env:ProgramFiles\Java ; \
    Get-ChildItem $Env:ProgramFiles\Java | Rename-Item -NewName "OpenJDK" ; \
    [io.file]::Delete($jdkPath) ; \
    Remove-Item $Env:ProgramFiles\Java\OpenJDK\lib\src.zip -Force ; \
    Expand-Archive $gitPath -DestinationPath $Env:ProgramFiles\Git ; \
    [io.file]::Delete($jdkPath) ; \
    # https://youtrack.jetbrains.com/issue/TW-73017
    (Get-Content 'C:\Program Files\Git\etc\gitconfig') -replace 'path = C:/Program Files/Git/etc/gitconfig', '' | Set-Content 'C:\Program Files\Git\etc\gitconfig'

# Prepare TeamCity server distribution
ARG windowsBuild

COPY TeamCity /TeamCity
RUN $distributionTypePath = 'C:/TeamCity/webapps/ROOT/WEB-INF/DistributionType.txt' ; \
    [io.file]::WriteAllText($distributionTypePath, '') ; \
    New-Item $distributionTypePath -type file -force -value "docker-windows-$Env:windowsBuild" | Out-Null
COPY run-server.ps1 /TeamCity/run-server.ps1

# Workaround for https://github.com/PowerShell/PowerShell-Docker/issues/164
ARG nanoserverImage

FROM ${nanoserverImage}

ENV ProgramFiles="C:\Program Files" \
    # set a fixed location for the Module analysis cache
    PSModuleAnalysisCachePath="C:\Users\ContainerUser\AppData\Local\Microsoft\Windows\PowerShell\docker\ModuleAnalysisCache" \
    # Persist %PSCORE% ENV variable for user convenience
    PSCORE="$ProgramFiles\PowerShell\pwsh.exe"

COPY --from=base ["C:/Program Files/PowerShell", "C:/Program Files/PowerShell"]

# In order to set system PATH, ContainerAdministrator must be used
USER ContainerAdministrator
RUN setx /M PATH "%PATH%;%ProgramFiles%\PowerShell"
USER ContainerUser

# intialize powershell module cache
RUN pwsh -NoLogo -NoProfile -Command " \
    $stopTime = (get-date).AddMinutes(15); \
    $ErrorActionPreference = 'Stop' ; \
    $ProgressPreference = 'SilentlyContinue' ; \
    while(!(Test-Path -Path $env:PSModuleAnalysisCachePath)) {  \
        Write-Host "'Waiting for $env:PSModuleAnalysisCachePath'" ; \
        if((get-date) -gt $stopTime) { throw 'timout expired'} \
        Start-Sleep -Seconds 6 ; \
    }"

COPY --from=base ["C:/Program Files/Java/OpenJDK", "C:/Program Files/Java/OpenJDK"]
COPY --from=base ["C:/Program Files/Git", "C:/Program Files/Git"]

ENV JRE_HOME="C:\Program Files\Java\OpenJDK" \
    TEAMCITY_DIST="C:\TeamCity" \
    CATALINA_TMPDIR="C:\TeamCity\temp" \
    TEAMCITY_LOGS="C:\TeamCity\logs" \
    TEAMCITY_DATA_PATH="C:\ProgramData\JetBrains\TeamCity" \
    TEAMCITY_SERVER_MEM_OPTS="-Xmx2g -XX:ReservedCodeCacheSize=350m"

EXPOSE 8111

COPY --from=base $TEAMCITY_DIST $TEAMCITY_DIST

VOLUME $TEAMCITY_DATA_PATH \
       $TEAMCITY_LOGS \
       $CATALINA_TMPDIR

CMD pwsh C:/TeamCity/run-server.ps1

# In order to set system PATH, ContainerAdministrator must be used
USER ContainerAdministrator
RUN setx /M PATH "%PATH%;%JRE_HOME%\bin;C:\Program Files\Git\cmd"
USER ContainerUser