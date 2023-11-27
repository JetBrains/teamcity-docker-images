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
# Requires teamcity.agent.jvm.os.name contains Windows 10

## ${serverCommentHeader}

# @AddToolToDoc [${jdkServerWindowsComponentName}](${jdkServerWindowsComponent})
# @AddToolToDoc ${powerShellComponentName}
# @AddToolToDoc [${gitWindowsComponentName}](${gitWindowsComponent})

# Based on ${powershellImage} 3
# PowerShell
FROM ${powershellImage} AS base

# On some agents, Windows 2022 requires administrator permissions to modify "C:/" folder within ...
# ... PowerShell container.
USER ContainerAdministrator

COPY scripts/*.cs /scripts/
SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

# JDK
ARG jdkServerWindowsComponent
ARG jdkServerWindowsComponentMD5SUM

# Git
ARG gitWindowsComponent
ARG gitWindowsComponentSHA256

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
    # Use basic parsing to prevent errors in Windows Server 2022
    $Global:ProgressPreference = 'SilentlyContinue' ; \
    $Global:UseBasicParsing = $true ; \
    # Download actual target files
    Add-Type -IgnoreWarnings -TypeDefinition "$code" -Language CSharp ; \
    $downloadScript = [Scripts.Web]::DownloadFiles($Env:jdkServerWindowsComponent + '#MD5#' + $Env:jdkServerWindowsComponentMD5SUM, 'jdk.zip', $Env:gitWindowsComponent + '#SHA256#' + $Env:gitWindowsComponentSHA256, 'git.zip') ; \
    iex $downloadScript ; \
    Expand-Archive jdk.zip -DestinationPath $Env:ProgramFiles\Java ; \
    Get-ChildItem $Env:ProgramFiles\Java | Rename-Item -NewName "OpenJDK" ; \
    Remove-Item -Force jdk.zip ; \
    Remove-Item $Env:ProgramFiles\Java\OpenJDK\lib\src.zip -Force ; \
    Expand-Archive git.zip -DestinationPath $Env:ProgramFiles\Git ; \
    # https://youtrack.jetbrains.com/issue/TW-73017
    (Get-Content 'C:\Program Files\Git\etc\gitconfig') -replace 'path = C:/Program Files/Git/etc/gitconfig', '' | Set-Content 'C:\Program Files\Git\etc\gitconfig' ; \
    Remove-Item -Force git.zip

# Prepare TeamCity server distribution
ARG windowsBuild

COPY TeamCity /TeamCity
RUN New-Item C:/TeamCity/webapps/ROOT/WEB-INF/DistributionType.txt -type file -force -value "docker-windows-$Env:windowsBuild" | Out-Null
COPY run-server.ps1 /TeamCity/run-server.ps1

USER ContainerUser

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

ENV JAVA_HOME="C:\Program Files\Java\OpenJDK" \
    TEAMCITY_DIST="C:\TeamCity" \
    TEAMCITY_ENV=container \
    CATALINA_TMPDIR="C:\TeamCity\temp" \
    TEAMCITY_LOGS="C:\TeamCity\logs" \
    TEAMCITY_DATA_PATH="C:\ProgramData\JetBrains\TeamCity" \
    TEAMCITY_SERVER_MEM_OPTS="-Xmx2g -XX:ReservedCodeCacheSize=640m"

EXPOSE 8111

COPY --from=base $TEAMCITY_DIST $TEAMCITY_DIST

VOLUME $TEAMCITY_DATA_PATH \
       $TEAMCITY_LOGS \
       $CATALINA_TMPDIR

CMD ["pwsh", "C:/TeamCity/run-server.ps1"]

# In order to set system PATH, ContainerAdministrator must be used
USER ContainerAdministrator
RUN setx /M PATH "%PATH%;%JAVA_HOME%\bin;C:\Program Files\Git\cmd"
# Grant Permissions for ContainerUser (Default Account), OI - Object Inherit, CI - Container Inherit, F - full control
RUN cmd /c icacls.exe C:\\TeamCity\\* /grant:r DefaultAccount:(OI)(CI)F
RUN cmd /c icacls.exe C:\\TeamCity\\* /grant:r Users:(OI)(CI)F
USER ContainerUser