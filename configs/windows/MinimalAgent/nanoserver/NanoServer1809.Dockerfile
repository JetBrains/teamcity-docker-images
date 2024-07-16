# The list of required arguments
# ARG jdkWindowsComponent
# ARG jdkWindowsComponentMD5SUM
# ARG nanoserverImage
# ARG powershellImage

# Id teamcity-minimal-agent
# Tag ${versionTag}-${tag}
# Tag ${latestTag}
# Tag ${versionTag}
# Platform ${windowsPlatform}
# Repo ${repo}
# Weight 5
# Requires teamcity.agent.jvm.os.name contains Windows 10

## ${agentCommentHeader}

# @AddToolToDoc [${jdkWindowsComponentName}](${jdkWindowsComponent})
# @AddToolToDoc ${powerShellComponentName}

# Based on ${powershellImage} 3
FROM ${powershellImage} AS base

# On some agents, Windows 2019 requires administrator permissions to modify "C:/" folder within ...
# ... PowerShell container.
USER ContainerAdministrator
RUN mkdir C:\\BuildAgent
RUN cmd /c icacls.exe C:\\BuildAgent /grant:r DefaultAccount:(OI)(CI)F /grant:r DefaultAccount:(OI)(CI)D /T
RUN cmd /c icacls.exe C:\\BuildAgent /grant:r Users:(OI)(CI)F /grant:r Users:(OI)(CI)D /T

# Copy BuildAgent distribution under 'ContainerUser' permissions

USER ContainerUser
COPY TeamCity/buildAgent C:/BuildAgent

# Switch back to administrator to modify C:/
USER ContainerAdministrator

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

# Remove to prevent upgrade failures
RUN Remove-Item -Recurse -Force C:/BuildAgent/system/.teamcity-agent


# Workaround for https://github.com/PowerShell/PowerShell-Docker/issues/164
ARG nanoserverImage

# Based on ${nanoserverImage} 2
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

#USER ContainerAdministrator
## Grant Permissions for ContainerUser (Default Account), OI - Object Inherit, CI - Container Inherit, ...
## ... F - full control, D - delete, /T - apply to subfolders & files
#RUN cmd /c icacls.exe C:\\BuildAgent /grant:r DefaultAccount:(OI)(CI)F /grant:r DefaultAccount:(OI)(CI)D /T
#RUN cmd /c icacls.exe C:\\BuildAgent /grant:r Users:(OI)(CI)F /grant:r Users:(OI)(CI)D /T
## Applied permission check for logging purposes
#RUN cmd /c icacls.exe C:\\BuildAgent\\*
#USER ContainerUser

VOLUME C:/BuildAgent/conf
VOLUME C:/BuildAgent/work
VOLUME C:/BuildAgent/temp
VOLUME C:/BuildAgent/logs

CMD ["pwsh", "./BuildAgent/run-agent.ps1"]
