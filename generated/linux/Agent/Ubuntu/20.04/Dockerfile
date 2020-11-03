# Default arguments
ARG dotnetCoreLinuxComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.402/dotnet-sdk-3.1.402-linux-x64.tar.gz'
ARG dotnetCoreLinuxComponentVersion='3.1.402'
ARG dotnetLibs='libc6 libgcc1 libgssapi-krb5-2 libicu66 libssl1.1 libstdc++6 zlib1g'
ARG gitLinuxComponentVersion='1:2.25.1-1ubuntu3'
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:local-linux'

# The list of required arguments
# ARG dotnetCoreLinuxComponentVersion
# ARG dotnetCoreLinuxComponent
# ARG teamcityMinimalAgentImage
# ARG dotnetLibs
# ARG gitLinuxComponentVersion



FROM ${teamcityMinimalAgentImage}

USER root

ARG dotnetCoreLinuxComponentVersion

    # Opt out of the telemetry feature
ENV DOTNET_CLI_TELEMETRY_OPTOUT=true \
    # Disable first time experience
    DOTNET_SKIP_FIRST_TIME_EXPERIENCE=true \
    # Configure Kestrel web server to bind to port 80 when present
    ASPNETCORE_URLS=http://+:80 \
    # Enable detection of running in a container
    DOTNET_RUNNING_IN_CONTAINER=true \
    # Enable correct mode for dotnet watch (only mode supported in a container)
    DOTNET_USE_POLLING_FILE_WATCHER=true \
    # Skip extraction of XML docs - generally not useful within an image/container - helps perfomance
    NUGET_XMLDOC_MODE=skip \
    GIT_SSH_VARIANT=ssh \
    DOTNET_SDK_VERSION=${dotnetCoreLinuxComponentVersion}

ARG dotnetCoreLinuxComponent
ARG dotnetLibs
ARG gitLinuxComponentVersion

RUN apt-get update && \
    apt-get install -y git=${gitLinuxComponentVersion} mercurial apt-transport-https software-properties-common && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    apt-get install -y --no-install-recommends ${dotnetLibs} && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    curl -SL ${dotnetCoreLinuxComponent} --output dotnet.tar.gz && \
    mkdir -p /usr/share/dotnet && \
    tar -zxf dotnet.tar.gz -C /usr/share/dotnet && \
    rm dotnet.tar.gz && \
    find /usr/share/dotnet -name "*.lzma" -type f -delete && \
    ln -s /usr/share/dotnet/dotnet /usr/bin/dotnet

# Trigger .NET CLI first run experience by running arbitrary cmd to populate local package cache
RUN dotnet help

USER buildagent

