# The list of required arguments
# ARG nanoserverImage
# ARG powershellImage
# ARG teamcityWindowsservercoreImage

# Id teamcity-agent
# Tag ${versionTag}-${tag}
# Tag ${latestTag}
# Tag ${versionTag}
# Platform ${windowsPlatform}
# Repo ${repo}
# Weight 2

## ${agentCommentHeader}

# @AddToolToDoc [${jdkWindowsComponentName}](${jdkWindowsComponent})
# @AddToolToDoc ${powerShellComponentName}
# @AddToolToDoc [${gitWindowsComponentName}](${gitWindowsComponent})
# @AddToolToDoc [${dotnetWindowsComponentName}](${dotnetWindowsComponent})

# Based on ${powershellImage} 3
FROM ${powershellImage} AS dotnet

# On some agents, Windows 2022 requires administrator permissions to modify "C:/" folder within ...
# ... PowerShell container.
USER ContainerAdministrator

COPY scripts/*.cs /scripts/

SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

# Based on ${teamcityWindowsservercoreImage}
ARG teamcityWindowsservercoreImage
FROM ${teamcityWindowsservercoreImage} AS tools

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
COPY --from=dotnet ["C:/Program Files/PowerShell", "C:/Program Files/PowerShell"]

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

# JDK
COPY --from=tools ["C:/Program Files/Java/OpenJDK", "C:/Program Files/Java/OpenJDK"]
# Git
COPY --from=tools ["C:/Program Files/Git", "C:/Program Files/Git"]
# .NET
COPY --from=tools ["C:/Program Files/dotnet", "C:/Program Files/dotnet"]
COPY --from=tools /BuildAgent /BuildAgent

EXPOSE 9090


# Configuration file for TeamCity agent
ENV CONFIG_FILE="C:\BuildAgent\conf\buildAgent.properties" \
    # Java home directory
    JAVA_HOME="C:\Program Files\Java\OpenJDK" \
    # Opt out of the telemetry feature
    DOTNET_CLI_TELEMETRY_OPTOUT=true \
    # Disable first time experience
    DOTNET_SKIP_FIRST_TIME_EXPERIENCE=true \
    # Configure Kestrel web server to bind to port 80 when present
    ASPNETCORE_URLS=http://+:80 \
    # Enable detection of running in a container
    DOTNET_RUNNING_IN_CONTAINER=true \
    # Enable correct mode for dotnet watch (only mode supported in a container)
    DOTNET_USE_POLLING_FILE_WATCHER=true \
    # Skip extraction of XML docs - generally not useful within an image/container - helps perfomance
    NUGET_XMLDOC_MODE=skip

# Use ContainerAdministrator to update permissions and PATH
USER ContainerAdministrator
# Create missing directories required for volumes, reset any potentially conflicting ACLs, ...
# ... grant Permissions for ContainerUser (Default Account), OI - Object Inherit, CI - Container Inherit, ...
# ... F - full control, /T - apply to subfolders & files
RUN setx /M PATH "%PATH%;%JAVA_HOME%\bin;C:\Program Files\Git\cmd;C:\Program Files\dotnet" && \
    if not exist C:\BuildAgent\logs md C:\BuildAgent\logs && \
    if not exist C:\BuildAgent\work md C:\BuildAgent\work && \
    type nul > C:\BuildAgent\logs\.keep && \
    type nul > C:\BuildAgent\work\.keep && \
    xcopy /E /I /Y C:\\BuildAgent\\conf C:\\BuildAgent\\conf_tmp && \
    rd /s /q C:\\BuildAgent\\conf && \
    md C:\\BuildAgent\\conf && \
    xcopy /E /I /Y C:\\BuildAgent\\conf_tmp C:\\BuildAgent\\conf && \
    rd /s /q C:\\BuildAgent\\conf_tmp && \
    if exist C:\BuildAgent\conf\buildAgent.properties del C:\BuildAgent\conf\buildAgent.properties

# Reset and grant permissions in PowerShell for proper error handling
SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]
RUN Write-Host 'Resetting ACLs...' ; \
    icacls.exe C:\BuildAgent /reset /T ; \
    if ($LASTEXITCODE -ne 0) { throw ('icacls reset failed with exit code ' + $LASTEXITCODE) } ; \
    Write-Host 'Granting permissions...' ; \
    icacls.exe C:\BuildAgent /grant:r '*S-1-5-32-545:(OI)(CI)F' /grant:r '*S-1-5-93-2-2:(OI)(CI)F' /T ; \
    if ($LASTEXITCODE -ne 0) { throw ('icacls grant failed with exit code ' + $LASTEXITCODE) } ; \
    Write-Host 'Canonicalizing ACLs...' ; \
    $acl = Get-Acl 'C:\BuildAgent'; Set-Acl 'C:\BuildAgent' $acl; \
    Get-ChildItem 'C:\BuildAgent' -Recurse -Force | ForEach-Object { $a = Get-Acl $_.FullName; Set-Acl $_.FullName $a }; \
    Write-Host 'Verifying permissions:' ; \
    icacls.exe C:\BuildAgent\conf ; \
    icacls.exe C:\BuildAgent\*
SHELL ["cmd", "/S", "/C"]

USER ContainerUser

# NB! The legacy builder discards permissions changes after the volume has been initialized => `icacls` has to be executed earlier
VOLUME C:/BuildAgent/conf

# Trigger first run experience by running arbitrary cmd to populate local package cache
RUN dotnet help

CMD ["pwsh", "./BuildAgent/run-agent.ps1"]