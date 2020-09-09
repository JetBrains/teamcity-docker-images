# Default arguments
ARG gitWindowsComponent='https://repo.labs.intellij.net/cache/https/github.com/git-for-windows/git/releases/download/v2.19.1.windows.1/MinGit-2.19.1-64-bit.zip'
ARG jdkWindowsComponent='https://repo.labs.intellij.net/cache/https/corretto.aws/downloads/resources/8.252.09.2/amazon-corretto-8.252.09.2-windows-x64-jdk.zip'
ARG mercurialWindowsComponent='https://repo.labs.intellij.net/cache/https/www.mercurial-scm.org/release/windows/mercurial-5.5.1-x64.msi'
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:2020.1.4-nanoserver-1903'
ARG windowsservercoreImage='mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903'

# The list of required arguments
# ARG windowsservercoreImage
# ARG dotnetCoreWindowsComponentVersion
# ARG jdkWindowsComponent
# ARG gitWindowsComponent
# ARG mercurialWindowsComponentName
# ARG teamcityMinimalAgentImage



FROM ${windowsservercoreImage} AS tools

SHELL ["powershell", "-Command", "$ErrorActionPreference = 'Stop'; $ProgressPreference = 'SilentlyContinue';"]

ARG jdkWindowsComponent

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    Invoke-WebRequest $Env:jdkWindowsComponent -OutFile jdk.zip; \
    Expand-Archive jdk.zip -DestinationPath $Env:ProgramFiles\Java ; \
    Get-ChildItem $Env:ProgramFiles\Java | Rename-Item -NewName "OpenJDK" ; \
    Remove-Item $Env:ProgramFiles\Java\OpenJDK\demo -Force -Recurse ; \
    Remove-Item $Env:ProgramFiles\Java\OpenJDK\sample -Force -Recurse ; \
    Remove-Item $Env:ProgramFiles\Java\OpenJDK\src.zip -Force ; \
    Remove-Item -Force jdk.zip

ARG gitWindowsComponent

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    Invoke-WebRequest $Env:gitWindowsComponent -OutFile git.zip; \
    Expand-Archive git.zip -DestinationPath $Env:ProgramFiles\Git ; \
    Remove-Item -Force git.zip

ARG mercurialWindowsComponent

RUN [Net.ServicePointManager]::SecurityProtocol = 'tls12, tls11, tls' ; \
    Invoke-WebRequest $Env:mercurialWindowsComponent -OutFile hg.msi; \
    Start-Process msiexec -Wait -ArgumentList /q, /i, hg.msi ; \
    Remove-Item -Force hg.msi

ARG teamcityMinimalAgentImage

FROM ${teamcityMinimalAgentImage} AS buildagent

ARG windowsservercoreImage

FROM ${windowsservercoreImage}

COPY --from=tools ["C:/Program Files/Java/OpenJDK", "C:/Program Files/Java/OpenJDK"]
COPY --from=tools ["C:/Program Files/Git", "C:/Program Files/Git"]
COPY --from=tools ["C:/Program Files/Mercurial", "C:/Program Files/Mercurial"]
COPY --from=buildagent /BuildAgent /BuildAgent

EXPOSE 9090

VOLUME C:/BuildAgent/conf

CMD ./BuildAgent/run-agent.ps1

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

USER ContainerAdministrator
RUN setx /M PATH ('{0};{1}\bin;C:\Program Files\Git\cmd;C:\Program Files\Mercurial' -f $env:PATH, $env:JAVA_HOME)
USER ContainerUser
