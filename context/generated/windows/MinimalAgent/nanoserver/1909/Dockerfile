# Default arguments
ARG jdkWindowsComponent='https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-windows-x64-jdk.zip'
ARG jdkWindowsComponentMD5SUM='905e139bfc80a7c05333c22c3075fd87'
ARG nanoserverImage='mcr.microsoft.com/windows/nanoserver:1909'
ARG powershellImage='mcr.microsoft.com/powershell:nanoserver-1909'

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
# Create missing directories required for volumes, reset any potentially conflicting ACLs, ...
# ... grant Permissions for ContainerUser (Default Account), OI - Object Inherit, CI - Container Inherit, ...
# ... F - full control, /T - apply to subfolders & files
RUN if not exist C:\BuildAgent\logs md C:\BuildAgent\logs && \
    if not exist C:\BuildAgent\work md C:\BuildAgent\work && \
    if not exist C:\BuildAgent\temp md C:\BuildAgent\temp && \
    type nul > C:\BuildAgent\logs\.keep && \
    type nul > C:\BuildAgent\work\.keep && \
    type nul > C:\BuildAgent\temp\.keep && \
    if exist C:\BuildAgent\conf\buildAgent.properties del C:\BuildAgent\conf\buildAgent.properties

# Reset and grant permissions in PowerShell for proper error handling
SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]
RUN Write-Host 'Resetting ACLs...' ; \
    icacls.exe C:\BuildAgent /reset /T ; \
    if ($LASTEXITCODE -ne 0) { throw ('icacls reset failed with exit code ' + $LASTEXITCODE) } ; \
    Write-Host 'Granting permissions...' ; \
    icacls.exe C:\BuildAgent /grant:r 'DefaultAccount:(OI)(CI)F' /grant:r 'Users:(OI)(CI)F' /T ; \
    if ($LASTEXITCODE -ne 0) { throw ('icacls grant failed with exit code ' + $LASTEXITCODE) } ; \
    Write-Host 'Verifying permissions:' ; \
    icacls.exe C:\BuildAgent\conf ; \
    icacls.exe C:\BuildAgent\*
SHELL ["cmd", "/S", "/C"]

USER ContainerUser

VOLUME C:/BuildAgent/conf
VOLUME C:/BuildAgent/work
VOLUME C:/BuildAgent/temp
VOLUME C:/BuildAgent/logs

CMD ["pwsh", "./BuildAgent/run-agent.ps1"]
