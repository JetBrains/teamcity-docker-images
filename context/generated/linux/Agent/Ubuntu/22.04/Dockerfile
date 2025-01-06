# Default arguments
ARG containerdIoLinuxComponentVersion='1.6.28-2'
ARG dockerLinuxComponentVersion='5:27.3.1-1~ubuntu'
ARG dotnetLibs='libc6 libgcc1 libgssapi-krb5-2 libicu70 libssl3 libstdc++6 zlib1g'
ARG dotnetLinuxComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-x64.tar.gz'
ARG dotnetLinuxComponentSHA512='ee0a77d54e6d4917be7310ff0abb3bad5525bfb4beb1db0c215e65f64eb46511f5f12d6c7ff465a1d4ab38577e6a1950fde479ee94839c50e627020328a702de'
ARG gitLFSLinuxComponentVersion='3.0.2-1'
ARG gitLinuxComponentVersion='1:2.47.1-0ppa1~ubuntu22.04.1'
ARG p4Version='2022.2-2693782'
ARG repo='https://hub.docker.com/r/jetbrains/'
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:2024.12-linux'

# The list of required arguments
# ARG dotnetLinuxComponent
# ARG dotnetLinuxComponentSHA512
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

ARG dotnetLinuxComponent
ARG dotnetLinuxComponentSHA512
ARG dotnetLibs
ARG gitLinuxComponentVersion
ARG gitLFSLinuxComponentVersion
ARG dockerLinuxComponentVersion
ARG containerdIoLinuxComponentVersion
ARG p4Version

RUN apt-get update && \
    apt-get install -y mercurial apt-transport-https software-properties-common && \
    add-apt-repository ppa:git-core/ppa -y && \
    apt-get install -y git=${gitLinuxComponentVersion} git-lfs=${gitLFSLinuxComponentVersion} && \
    git lfs install --system && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
# Perforce (p4 CLI)
    apt-key adv --fetch-keys https://package.perforce.com/perforce.pubkey && \
    (. /etc/os-release && \
      echo "deb http://package.perforce.com/apt/$ID $VERSION_CODENAME release" > \
      /etc/apt/sources.list.d/perforce.list ) && \
    apt-get update && \
    (. /etc/os-release && apt-get install -y helix-cli-base="${p4Version}~$VERSION_CODENAME" helix-cli="${p4Version}~$VERSION_CODENAME" ) && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    # Docker & ContainerD
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add - && \
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" && \
    apt-cache policy docker-ce && \
    apt-get update && \
    # docker-ce, docker-ce-cli package name format: "25.0.5-1~ubuntu.20.04~focal"
    apt-get install -y docker-ce=${dockerLinuxComponentVersion}.$(lsb_release -rs)~$(lsb_release -cs) \
        docker-ce-cli=${dockerLinuxComponentVersion}.$(lsb_release -rs)~$(lsb_release -cs) \
        containerd.io:amd64=${containerdIoLinuxComponentVersion} \
        systemd && \
    systemctl disable docker && \
    sed -i -e 's/\r$//' /services/run-docker.sh && \
# Dotnet
    apt-get install -y --no-install-recommends ${dotnetLibs} && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    mkdir -p /usr/share/dotnet && \
# .NET 6.0
    curl -SL ${dotnetLinuxComponent} --output /tmp/dotnet.tar.gz && \
    echo "Downloaded .NET 6.0 (Linux AMD64) checksum: $(sha512sum tmp/dotnet.tar.gz)" && \
    echo "${dotnetLinuxComponentSHA512} */tmp/dotnet.tar.gz" | sha512sum -c -; \
    tar -zxf /tmp/dotnet.tar.gz -C /usr/share/dotnet && \
    rm /tmp/dotnet.tar.gz && \
    find /usr/share/dotnet -name "*.lzma" -type f -delete && \
    ln -s /usr/share/dotnet/dotnet /usr/bin/dotnet && \
# Trigger .NET CLI first run experience by running arbitrary cmd to populate local package cache
    dotnet help && \
    dotnet --info && \
# Other
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    chown -R buildagent:buildagent /services && \
    usermod -aG docker buildagent

# A better fix for TW-52939 Dockerfile build fails because of aufs
VOLUME /var/lib/docker

USER buildagent

