# Default arguments
ARG gitWindowsComponent='https://github.com/git-for-windows/git/releases/download/v2.39.2.windows.1/MinGit-2.39.2-64-bit.zip'
ARG gitWindowsComponentSHA256='a53b90a42d9a5e3ac992f525b5805c4dbb8a013b09a32edfdcf9a551fd8cfe2d'
ARG jdkServerWindowsComponent='https://corretto.aws/downloads/resources/11.0.16.9.1/amazon-corretto-11.0.16.9.1-windows-x64-jdk.zip'
ARG jdkServerWindowsComponentMD5SUM='e46d240031e3a58f6bfbd1f67044da61'
ARG nanoserverImage='mcr.microsoft.com/windows/nanoserver:1903'
ARG powershellImage='mcr.microsoft.com/powershell:nanoserver-1903'
ARG windowsBuild='1903'

# The list of required arguments
# ARG powershellImage
# ARG jdkServerWindowsComponent
# ARG jdkServerWindowsComponentMD5SUM
# ARG gitWindowsComponent
# ARG gitWindowsComponentSHA256
# ARG windowsBuild
# ARG powershellImage



FROM ${powershellImage} AS base

COPY scripts/*.cs /scripts/
SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

ARG jdkServerWindowsComponent
ARG jdkServerWindowsComponentMD5SUM

ARG gitWindowsComponent
ARG gitWindowsComponentSHA256

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
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
    TEAMCITY_SERVER_MEM_OPTS="-Xmx2g -XX:ReservedCodeCacheSize=640m"

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
