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

# Use ContainerAdministrator to update permissions and PATH
USER ContainerAdministrator

# Create missing directories required for volumes, reset any potentially conflicting ACLs, ...
# ... grant Permissions for ContainerUser (Default Account), OI - Object Inherit, CI - Container Inherit, ...
# ... F - full control, /T - apply to subfolders & files
SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]
RUN setx /M PATH ($env:PATH + ';' + $env:JAVA_HOME + '\bin;C:\Program Files\Git\cmd') ; \
    if ($LASTEXITCODE -ne 0) { throw ('setx failed with exit code ' + $LASTEXITCODE) } ; \
    if (-not (Test-Path 'C:\TeamCity\temp')) { New-Item -Path 'C:\TeamCity\temp' -ItemType Directory | Out-Null } ; \
    if (-not (Test-Path 'C:\TeamCity\logs')) { New-Item -Path 'C:\TeamCity\logs' -ItemType Directory | Out-Null } ; \
    New-Item -Path 'C:\TeamCity\temp\.keep' -ItemType File -Force | Out-Null ; \
    New-Item -Path 'C:\TeamCity\logs\.keep' -ItemType File -Force | Out-Null ; \
    Write-Host 'Resetting ACLs...' ; \
    icacls.exe C:\TeamCity /reset /T ; \
    if ($LASTEXITCODE -ne 0) { throw ('icacls reset failed with exit code ' + $LASTEXITCODE) } ; \
    Write-Host 'Granting permissions...' ; \
    icacls.exe C:\TeamCity /grant:r 'DefaultAccount:(OI)(CI)F' /grant:r 'Users:(OI)(CI)F' /T ; \
    if ($LASTEXITCODE -ne 0) { throw ('icacls grant failed with exit code ' + $LASTEXITCODE) } ; \
    <# Canonicalizing ACLs to prevent issues such as TW-100061 #> \
    Write-Host 'Canonicalizing ACLs...' ; \
    $acl = Get-Acl 'C:\TeamCity'; Set-Acl 'C:\TeamCity' $acl; \
    Get-ChildItem 'C:\TeamCity' -Recurse -Force | ForEach-Object { $a = Get-Acl $_.FullName; Set-Acl $_.FullName $a }; \
    $acl = Get-Acl 'C:\TeamCity'; if (-not $acl.AreAccessRulesCanonical) { throw 'ACLs are not canonical after Set-Acl on C:\TeamCity' }; \
    Write-Host 'Verifying permissions:' ; \
    icacls.exe C:\TeamCity\* ; \
    Write-Host 'Fixing permissions for TeamCity data directory...' ; \
    if (-not (Test-Path 'C:\ProgramData\JetBrains\TeamCity')) { New-Item -Path 'C:\ProgramData\JetBrains\TeamCity' -ItemType Directory -Force | Out-Null } ; \
    Write-Host 'Resetting ACLs for data directory...' ; \
    icacls.exe C:\ProgramData\JetBrains\TeamCity /reset /T ; \
    if ($LASTEXITCODE -ne 0) { throw ('icacls reset failed for data directory with exit code ' + $LASTEXITCODE) } ; \
    Write-Host 'Granting permissions for data directory...' ; \
    icacls.exe C:\ProgramData\JetBrains\TeamCity /grant:r 'DefaultAccount:(OI)(CI)F' /grant:r 'Users:(OI)(CI)F' /T ; \
    if ($LASTEXITCODE -ne 0) { throw ('icacls grant failed for data directory with exit code ' + $LASTEXITCODE) } ; \
    <# Canonicalizing ACLs to prevent issues such as TW-100061 #> \
    Write-Host 'Canonicalizing ACLs for data directory...' ; \
    $acl = Get-Acl 'C:\ProgramData\JetBrains\TeamCity'; Set-Acl 'C:\ProgramData\JetBrains\TeamCity' $acl; \
    Get-ChildItem 'C:\ProgramData\JetBrains\TeamCity' -Recurse -Force | ForEach-Object { $a = Get-Acl $_.FullName; Set-Acl $_.FullName $a }; \
    $acl = Get-Acl 'C:\ProgramData\JetBrains\TeamCity'; if (-not $acl.AreAccessRulesCanonical) { throw 'ACLs are not canonical after Set-Acl on C:\ProgramData\JetBrains\TeamCity' }; \
    Write-Host 'Verifying data directory permissions:' ; \
    icacls.exe 'C:\ProgramData\JetBrains\TeamCity'
SHELL ["cmd", "/S", "/C"]

USER ContainerUser

VOLUME $TEAMCITY_DATA_PATH \
       $TEAMCITY_LOGS \
       $CATALINA_TMPDIR

CMD ["pwsh", "C:/TeamCity/run-server.ps1"]
