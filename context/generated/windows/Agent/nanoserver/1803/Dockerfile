# Default arguments
ARG dotnetCoreWindowsComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.300/dotnet-sdk-3.1.300-win-x64.zip'
ARG dotnetCoreWindowsComponentVersion='3.1.300'
ARG powershellImage='mcr.microsoft.com/powershell:nanoserver-1803'
ARG teamcityWindowsservercoreImage='teamcity-agent:windowsservercore-1803'

# The list of required arguments
# ARG dotnetCoreWindowsComponentVersion
# ARG jdkWindowsComponent
# ARG nanoserverImage
# ARG powershellImage
# ARG teamcityWindowsservercoreImage



FROM ${powershellImage} AS dotnet

SHELL ["pwsh", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

ARG dotnetCoreWindowsComponentVersion

ENV DOTNET_SDK_VERSION ${dotnetCoreWindowsComponentVersion}

ARG dotnetCoreWindowsComponent

RUN Invoke-WebRequest -OutFile dotnet.zip $Env:dotnetCoreWindowsComponent; \
    Expand-Archive dotnet.zip -DestinationPath $Env:ProgramFiles\dotnet; \
    Remove-Item -Force dotnet.zip; \
    Get-ChildItem -Path $Env:ProgramFiles\dotnet -Include *.lzma -File -Recurse | foreach { $_.Delete()}

ARG teamcityWindowsservercoreImage

FROM ${teamcityWindowsservercoreImage} AS tools

ARG powershellImage

FROM ${powershellImage}

COPY --from=tools ["C:/Program Files/Java/OpenJDK", "C:/Program Files/Java/OpenJDK"]
COPY --from=tools ["C:/Program Files/Git", "C:/Program Files/Git"]
COPY --from=dotnet ["C:/Program Files/dotnet", "C:/Program Files/dotnet"]
COPY --from=tools /BuildAgent /BuildAgent

EXPOSE 9090

VOLUME C:/BuildAgent/conf

CMD pwsh ./BuildAgent/run-agent.ps1

    # Configuration file for TeamCity agent
ENV CONFIG_FILE="C:/BuildAgent/conf/buildAgent.properties" \
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

# In order to set system PATH, ContainerAdministrator must be used
USER ContainerAdministrator
RUN setx /M PATH "%PATH%;%JAVA_HOME%\bin;C:\Program Files\Git\cmd;C:\Program Files\dotnet"
USER ContainerUser

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

# Trigger first run experience by running arbitrary cmd to populate local package cache
RUN dotnet help
