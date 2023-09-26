# Default arguments
ARG containerdIoLinuxComponentVersion='1.4.12-1'
ARG dockerComposeLinuxComponentVersion='1.28.5'
ARG dockerLinuxComponentVersion='5:20.10.12~3-0~ubuntu'
ARG dotnetLibs='libc6 libgcc1 libgssapi-krb5-2 libicu60 libssl1.1 libstdc++6 zlib1g'
ARG dotnetLinuxARM64Component='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-arm64.tar.gz'
ARG dotnetLinuxARM64Component_70='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/7.0.401/dotnet-sdk-7.0.401-linux-arm64.tar.gz'
ARG dotnetLinuxARM64ComponentSHA512='7f05a9774d79e694da5a6115d9916abf87a65e40bd6bdaa5dca1f705795436bc8e764242f7045207386a86732ef5519f60bdb516a3860e4860bca7ee91a21759'
ARG dotnetLinuxARM64ComponentSHA512_70='ea222cb8e68744b55184a2a8eb24272f3ae51e4b30d8237e4c80018b1b0316b4f1dcdbfd8fdef18fc4735218d24d175f4e9701f685c4a9b9f0a22f587cc22c56'
ARG gitLFSLinuxComponentVersion='2.3.4-1'
ARG gitLinuxComponentVersion='1:2.41.0-0ppa1~ubuntu18.04.1'
ARG repo=''
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:EAP-linux-arm64-18.04'

# The list of required arguments
# ARG dotnetLinuxARM64Component
# ARG dotnetLinuxARM64ComponentSHA512
# ARG dotnetLinuxARM64Component_70
# ARG dotnetLinuxARM64ComponentSHA512_70
# ARG teamcityMinimalAgentImage
# ARG dotnetLibs
# ARG gitLinuxComponentVersion
# ARG gitLFSLinuxComponentVersion
# ARG dockerComposeLinuxComponentVersion
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
ARG dotnetLinuxARM64Component_70
ARG dotnetLinuxARM64ComponentSHA512_70
ARG dotnetLibs
ARG gitLinuxComponentVersion
ARG gitLFSLinuxComponentVersion
ARG dockerComposeLinuxComponentVersion
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
    apt-get install -y  docker-ce=${dockerLinuxComponentVersion}-$(lsb_release -cs) \
    docker-ce-cli=${dockerLinuxComponentVersion}-$(lsb_release -cs) \
    containerd.io:arm64=${containerdIoLinuxComponentVersion} \
    systemd && \
    systemctl disable docker && \
    sed -i -e 's/\r$//' /services/run-docker.sh && \
# Docker-Compose
    curl -SL "https://github.com/docker/compose/releases/download/${dockerComposeLinuxComponentVersion}/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose && \
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
# .NET 7.0
    curl -SL ${dotnetLinuxARM64Component_70} --output /tmp/dotnet.tar.gz && \
    echo "Downloaded .NET 7.0 (Linux ARM64) checksum: $(sha512sum tmp/dotnet.tar.gz)" && \
    echo "${dotnetLinuxARM64ComponentSHA512_70} */tmp/dotnet.tar.gz" | sha512sum -c -; \
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

