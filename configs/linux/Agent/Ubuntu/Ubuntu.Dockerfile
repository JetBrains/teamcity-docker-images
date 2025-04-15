# The list of required arguments
# ARG dotnetLinuxComponent
# ARG dotnetLinuxComponentSHA512
# ARG teamcityMinimalAgentImage
# ARG dotnetLibs
# ARG gitLinuxComponentVersion
# ARG gitLFSLinuxComponentVersion
# ARG dockerLinuxComponentVersion
# ARG ubuntuImage
# ARG p4Version

# Id teamcity-agent
# Platform ${linuxPlatform}
# Tag ${versionTag}-linux${linuxVersion}
# Tag ${latestTag}
# Tag ${versionTag}
# Repo ${repo}
# Weight 1

## ${agentCommentHeader}

# @AddToolToDoc [${jdkLinuxComponentName}](${jdkLinuxComponent})
# @AddToolToDoc [Python venv](https://docs.python.org/3/library/venv.html#module-venv)

# @AddToolToDoc [${jdkLinuxComponentName}](${jdkLinuxComponent})
# @AddToolToDoc [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
# @AddToolToDoc ${gitLFSLinuxComponentName}
# @AddToolToDoc ${gitLinuxComponentName}
# @AddToolToDoc Mercurial
# @AddToolToDoc ${dockerLinuxComponentName}
# @AddToolToDoc ${containerdIoLinuxComponentName}
# @AddToolToDoc [${dotnetLinuxComponentName}](${dotnetLinuxComponent})
# @AddToolToDoc ${p4Name}


# Based on ${teamcityMinimalAgentImage}
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
    # Perforce (p4 CLI)
    curl -Lo /usr/local/bin/p4 "https://www.perforce.com/downloads/perforce/${p4Version}/bin.linux26x86_64/p4" && \
    chmod +x /usr/local/bin/p4 && \
    p4 -V && \
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
    usermod -aG docker buildagent && \
    [ -f /etc/gitconfig ] || (echo "'/etc/gitconfig' does not exist, while LFS filter is required" && exit 1)

# A better fix for TW-52939 Dockerfile build fails because of aufs
VOLUME /var/lib/docker

USER buildagent

