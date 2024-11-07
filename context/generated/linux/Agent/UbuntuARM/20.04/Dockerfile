# Default arguments
ARG containerdIoLinuxComponentVersion='1.6.28-2'
ARG dockerLinuxComponentVersion='5:24.0.9-1~ubuntu'
ARG dotnetLibs='libc6 libgcc1 libgssapi-krb5-2 libicu66 libssl1.1 libstdc++6 zlib1g'
ARG dotnetLinuxARM64Component='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-arm64.tar.gz'
ARG dotnetLinuxARM64ComponentSHA512='7f05a9774d79e694da5a6115d9916abf87a65e40bd6bdaa5dca1f705795436bc8e764242f7045207386a86732ef5519f60bdb516a3860e4860bca7ee91a21759'
ARG gitLFSLinuxComponentVersion='3.0.2-1'
ARG gitLinuxComponentVersion='1:2.47.0-0ppa2~ubuntu22.04.1'
ARG repo='https://hub.docker.com/r/jetbrains/'
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:EAP-linux-arm64'

# The list of required arguments
# ARG dotnetLinuxARM64Component
# ARG dotnetLinuxARM64ComponentSHA512
# ARG teamcityMinimalAgentImage
# ARG dotnetLibs
# ARG gitLinuxComponentVersion
# ARG gitLFSLinuxComponentVersion
# ARG dockerLinuxComponentVersion






FROM ${teamcityMinimalAgentImage}

USER root

COPY run-docker.sh /services/run-docker.sh

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

ARG dotnetLinuxARM64Component
ARG dotnetLinuxARM64ComponentSHA512
ARG dotnetLibs
ARG gitLinuxComponentVersion
ARG gitLFSLinuxComponentVersion
ARG dockerLinuxComponentVersion
ARG containerdIoLinuxComponentVersion

RUN apt-get update && \
    apt-get install -y mercurial apt-transport-https software-properties-common && \
    add-apt-repository ppa:git-core/ppa -y && \
    apt-get install -y git=${gitLinuxComponentVersion} git-lfs=${gitLFSLinuxComponentVersion} && \
    git lfs install --system && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
# Docker
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add - && \
    add-apt-repository "deb [arch=arm64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" && \
    apt-cache policy docker-ce && \
    apt-get update && \
    # docker-ce, docker-ce-cli package name format: "26.0.0-1~ubuntu.20.04~focal"
    apt-get install -y docker-ce=${dockerLinuxComponentVersion}.$(lsb_release -rs)~$(lsb_release -cs) \
      docker-ce-cli=${dockerLinuxComponentVersion}.$(lsb_release -rs)~$(lsb_release -cs) \
      containerd.io:arm64=${containerdIoLinuxComponentVersion} \
      systemd && \
    systemctl disable docker && \
    sed -i -e 's/\r$//' /services/run-docker.sh && \
# .NET Libraries
    apt-get install -y --no-install-recommends ${dotnetLibs} && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    mkdir -p /usr/share/dotnet && \
# .NET 6.0
    curl -SL ${dotnetLinuxARM64Component} --output /tmp/dotnet.tar.gz && \
    echo "Downloaded .NET 6.0 (Linux ARM64) checksum: $(sha512sum tmp/dotnet.tar.gz)" && \
    echo "${dotnetLinuxARM64ComponentSHA512} */tmp/dotnet.tar.gz" | sha512sum -c -; \
    tar -zxf /tmp/dotnet.tar.gz -C /usr/share/dotnet && \
    rm /tmp/dotnet.tar.gz && \
    find /usr/share/dotnet -name "*.lzma" -type f -delete && \
# Trigger .NET CLI first run experience by running arbitrary cmd to populate local package cache \
    ln -s /usr/share/dotnet/dotnet /usr/bin/dotnet && \
    dotnet help && \
    dotnet --info && \
# Other
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    chown -R buildagent:buildagent /services && \
    usermod -aG docker buildagent

# A better fix for TW-52939 Dockerfile build fails because of aufs
VOLUME /var/lib/docker

USER buildagent

