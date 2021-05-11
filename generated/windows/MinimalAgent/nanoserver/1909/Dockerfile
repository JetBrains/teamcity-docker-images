# Default arguments
ARG jdkWindowsComponent='https://corretto.aws/downloads/resources/8.292.10.1/amazon-corretto-8.292.10.1-windows-x64-jdk.zip'
ARG jdkWindowsComponentMD5SUM='29403acf16a4bb307002820a7797ca64'
ARG jreWindowsComponent='https://corretto.aws/downloads/resources/8.292.10.1/amazon-corretto-8.292.10.1-windows-x64-jre.zip'
ARG jreWindowsComponentMD5SUM='cde580c171448d6a5710834676d37a92'
ARG nanoserverImage='mcr.microsoft.com/windows/nanoserver:1909'
ARG powershellImage='mcr.microsoft.com/powershell:nanoserver-1909'

# The list of required arguments
# ARG jreWindowsComponent
# ARG jreWindowsComponentMD5SUM
# ARG jdkWindowsComponent
# ARG jdkWindowsComponentMD5SUM
# ARG nanoserverImage
# ARG powershellImage



FROM ${powershellImage} AS base

COPY scripts/*.cs /scripts/
SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

# Prepare build agent distribution
COPY TeamCity/buildAgent C:/BuildAgent
COPY run-agent.ps1 /BuildAgent/run-agent.ps1

ARG jreWindowsComponent
ARG jreWindowsComponentMD5SUM

ARG jdkWindowsComponent
ARG jdkWindowsComponentMD5SUM

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    $code = Get-Content -Path "scripts/Web.cs" -Raw ; \
    Add-Type -TypeDefinition "$code" -Language CSharp ; \
    $downloadScript = [Scripts.Web]::DownloadFiles($Env:jreWindowsComponent + '#MD5#' + $Env:jreWindowsComponentMD5SUM, 'jre.zip', $Env:jdkWindowsComponent + '#MD5#' + $Env:jdkWindowsComponentMD5SUM, 'jdk.zip') ; \
    iex $downloadScript ; \
    Expand-Archive jre.zip -DestinationPath $Env:ProgramFiles\Java ; \
    Get-ChildItem $Env:ProgramFiles\Java | Rename-Item -NewName "OpenJDK" ; \
    Remove-Item -Force jre.zip ; \
    Expand-Archive jdk.zip -DestinationPath $Env:Temp\JDK ; \
    Get-ChildItem $Env:Temp\JDK | Rename-Item -NewName "OpenJDK" ; \
    ('jar.exe', 'jcmd.exe', 'jconsole.exe', 'jmap.exe', 'jstack.exe', 'jps.exe') | foreach { \
         Copy-Item $Env:Temp\JDK\OpenJDK\bin\$_ $Env:ProgramFiles\Java\OpenJDK\bin\ \
    } ; \
    Remove-Item -Force -Recurse $Env:Temp\JDK ; \
    Remove-Item -Force jdk.zip ; \
    (Get-Content /BuildAgent/system/.teamcity-agent/unpacked-plugins.xml).replace('/', '\\') | Set-Content /BuildAgent/system/.teamcity-agent/unpacked-plugins.xml

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

ENV JRE_HOME="C:\Program Files\Java\OpenJDK" \
    CONFIG_FILE="C:\BuildAgent\conf\buildAgent.properties"

COPY --chown=ContainerUser --from=base /BuildAgent /BuildAgent

VOLUME C:/BuildAgent/conf
VOLUME C:/BuildAgent/work
VOLUME C:/BuildAgent/temp
VOLUME C:/BuildAgent/logs

CMD pwsh ./BuildAgent/run-agent.ps1
