# Default arguments
ARG jdkWindowsComponent='https://corretto.aws/downloads/resources/17.0.7.7.1/amazon-corretto-17.0.7.7.1-windows-x64-jdk.zip'
ARG nanoserverImage='mcr.microsoft.com/powershell:nanoserver-1803'
ARG powershellImage='mcr.microsoft.com/powershell:nanoserver-1803'

# The list of required arguments
# ARG jdkWindowsComponent
# ARG nanoserverImage
# ARG powershellImage




# PoweShell
FROM ${powershellImage} AS base

SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

# Prepare build agent distribution
COPY TeamCity/buildAgent C:/BuildAgent
COPY run-agent.ps1 /BuildAgent/run-agent.ps1

# JDK
ARG jdkWindowsComponent

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    Invoke-WebRequest $Env:jdkWindowsComponent -OutFile jdk.zip; \
    Expand-Archive jdk.zip -DestinationPath $Env:ProgramFiles\Java ; \
    Get-ChildItem $Env:ProgramFiles\Java | Rename-Item -NewName "OpenJDK" ; \
    Remove-Item -Force jdk.zip ; \
    if (Test-Path '/BuildAgent/system/.teamcity-agent/unpacked-plugins.xml') { (Get-Content '/BuildAgent/system/.teamcity-agent/unpacked-plugins.xml').replace('/', '\\') | Set-Content '/BuildAgent/system/.teamcity-agent/unpacked-plugins.xml' }

ARG nanoserverImage

FROM ${nanoserverImage}

COPY --from=base ["C:/Program Files/Java/OpenJDK", "C:/Program Files/Java/OpenJDK"]

ENV JAVA_HOME="C:\Program Files\Java\OpenJDK" \
    CONFIG_FILE="C:\BuildAgent\conf\buildAgent.properties"

COPY --chown=ContainerUser --from=base /BuildAgent /BuildAgent

VOLUME C:/BuildAgent/conf
VOLUME C:/BuildAgent/work
VOLUME C:/BuildAgent/temp
VOLUME C:/BuildAgent/logs

ENV LOCALAPPDATA="C:\Users\ContainerUser\AppData\Local" \
    # set a fixed location for the Module analysis cache
    PSModuleAnalysisCachePath="C:\Users\ContainerUser\AppData\Local\Microsoft\Windows\PowerShell\docker\ModuleAnalysisCache"

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

CMD ["pwsh", "./BuildAgent/run-agent.ps1"]
