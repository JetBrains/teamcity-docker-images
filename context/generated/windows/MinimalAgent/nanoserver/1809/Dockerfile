# Default arguments
ARG jdkWindowsComponent='https://corretto.aws/downloads/resources/21.0.6.7.1/amazon-corretto-21.0.6.7.1-windows-x64-jdk.zip'
ARG jdkWindowsComponentMD5SUM='20023c16bbc60d518ee7f8ca040a8c58'
ARG nanoserverImage='mcr.microsoft.com/windows/nanoserver:1809'
ARG powershellImage='mcr.microsoft.com/powershell:nanoserver-1809'

# The list of required arguments
# ARG jdkWindowsComponent
# ARG jdkWindowsComponentMD5SUM
# ARG nanoserverImage
# ARG powershellImage




FROM ${powershellImage} AS base

# On some agents, Windows 2019 requires administrator permissions to modify "C:/" folder within ...
# ... PowerShell container.
USER ContainerAdministrator

# Prepare build agent distribution
RUN mkdir C:\\BuildAgent
COPY TeamCity/buildAgent C:/BuildAgent

COPY scripts/*.cs /scripts/
SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

COPY run-agent.ps1 /BuildAgent/run-agent.ps1

# JDK
ARG jdkWindowsComponent
ARG jdkWindowsComponentMD5SUM

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
    Add-Type -IgnoreWarnings -TypeDefinition "$code" -Language CSharp ; \
    $downloadScript = [Scripts.Web]::DownloadFiles($Env:jdkWindowsComponent + '#MD5#' + $Env:jdkWindowsComponentMD5SUM, 'jdk.zip') ; \
    iex $downloadScript ; \
    Expand-Archive jdk.zip -DestinationPath $Env:ProgramFiles\Java ; \
    Get-ChildItem $Env:ProgramFiles\Java | Rename-Item -NewName "OpenJDK" ; \
    Remove-Item -Force jdk.zip ; \
    if (Test-Path '/BuildAgent/system/.teamcity-agent/unpacked-plugins.xml') { (Get-Content '/BuildAgent/system/.teamcity-agent/unpacked-plugins.xml').replace('/', '\\') | Set-Content '/BuildAgent/system/.teamcity-agent/unpacked-plugins.xml' }

# Workaround for https://github.com/PowerShell/PowerShell-Docker/issues/164
ARG nanoserverImage

FROM ${nanoserverImage}

ENV ProgramFiles="C:\Program Files" \
    # set a fixed location for the Module analysis cache
    PSModuleAnalysisCachePath="C:\Users\ContainerUser\AppData\Local\Microsoft\Windows\PowerShell\docker\ModuleAnalysisCache" \
    # Persist %PSCORE% ENV variable for user convenience
    PSCORE="$ProgramFiles\PowerShell\pwsh.exe"

# PowerShell
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

ENV JAVA_HOME="C:\Program Files\Java\OpenJDK" \
    CONFIG_FILE="C:\BuildAgent\conf\buildAgent.properties"

COPY --chown=ContainerUser --from=base /BuildAgent /BuildAgent

USER ContainerAdministrator
# Grant Permissions for ContainerUser (Default Account), OI - Object Inherit, CI - Container Inherit, ...
# ... F - full control, D - delete, /T - apply to subfolders & files
RUN cmd /c icacls.exe C:\\BuildAgent /grant:r DefaultAccount:(OI)(CI)F /grant:r DefaultAccount:(OI)(CI)D /T
RUN cmd /c icacls.exe C:\\BuildAgent /grant:r Users:(OI)(CI)F /grant:r Users:(OI)(CI)D /T
# Applied permission check for logging purposes
RUN cmd /c icacls.exe C:\\BuildAgent\\*
USER ContainerUser

VOLUME C:/BuildAgent/conf
VOLUME C:/BuildAgent/work
VOLUME C:/BuildAgent/temp
VOLUME C:/BuildAgent/logs

CMD ["pwsh", "./BuildAgent/run-agent.ps1"]
