# The list of required arguments
# ARG dotnetLinuxComponent
# ARG dotnetLinuxComponentSHA512
# ARG dotnetLinuxComponent_31
# ARG dotnetLinuxComponentSHA512_31
# ARG dotnetLinuxComponent_50
# ARG dotnetLinuxComponentSHA512_50
# ARG teamcityMinimalAgentImage
# ARG dotnetLibs
# ARG gitLinuxComponentVersion
# ARG gitLFSLinuxComponentVersion
# ARG dockerComposeLinuxComponentVersion
# ARG dockerLinuxComponentVersion

# Id teamcity-agent
# Platform ${linuxPlatform}
# Tag ${versionTag}-linux${linuxVersion}
# Tag ${latestTag}
# Tag ${versionTag}
# Repo ${repo}
# Weight 1

## ${agentCommentHeader}

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
ARG dotnetLinuxComponent_31
ARG dotnetLinuxComponentSHA512_31
ARG dotnetLinuxComponent_50
ARG dotnetLinuxComponentSHA512_50
ARG dotnetLibs
ARG gitLinuxComponentVersion
ARG gitLFSLinuxComponentVersion
ARG dockerComposeLinuxComponentVersion
ARG dockerLinuxComponentVersion
ARG containerdIoLinuxComponentVersion
ARG p4Version

RUN apt-get update && \
# Install ${gitLinuxComponentName}
# Install ${gitLFSLinuxComponentName}
# Install Mercurial
    apt-get install -y git=${gitLinuxComponentVersion} git-lfs=${gitLFSLinuxComponentVersion} mercurial apt-transport-https software-properties-common && \
    git lfs install --system && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
# Install ${p4Name}
    apt-key adv --fetch-keys https://package.perforce.com/perforce.pubkey && \
    (. /etc/os-release && \
      echo "deb http://package.perforce.com/apt/$ID $VERSION_CODENAME release" > \
      /etc/apt/sources.list.d/perforce.list ) && \
    apt-get update && \
    (. /etc/os-release && apt-get install -y helix-cli="${p4Version}~$VERSION_CODENAME" ) && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
# Install ${dockerLinuxComponentName}, ${containerdIoLinuxComponentName}
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add - && \
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" && \
    apt-cache policy docker-ce && \
    apt-get update && \
    apt-get install -y  docker-ce=${dockerLinuxComponentVersion}-$(lsb_release -cs) \
                        docker-ce-cli=${dockerLinuxComponentVersion}-$(lsb_release -cs) \
                        containerd.io:amd64=${containerdIoLinuxComponentVersion} \
                        systemd && \
    systemctl disable docker && \
    sed -i -e 's/\r$//' /services/run-docker.sh && \
# Install [Docker Compose v.${dockerComposeLinuxComponentVersion}](https://github.com/docker/compose/releases/tag/${dockerComposeLinuxComponentVersion})
    curl -SL "https://github.com/docker/compose/releases/download/${dockerComposeLinuxComponentVersion}/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose && \
# Dotnet
    apt-get install -y --no-install-recommends ${dotnetLibs} && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    mkdir -p /usr/share/dotnet && \
# Install [${dotnetLinuxComponentName_31}](${dotnetLinuxComponent_31})
    curl -SL ${dotnetLinuxComponent_31} --output /tmp/dotnet.tar.gz && \
    echo "${dotnetLinuxComponentSHA512_31} */tmp/dotnet.tar.gz" | sha512sum -c -; \
    tar -zxf /tmp/dotnet.tar.gz -C /usr/share/dotnet && \
    rm /tmp/dotnet.tar.gz && \
    find /usr/share/dotnet -name "*.lzma" -type f -delete && \
# Install [${dotnetLinuxComponentName_50}](${dotnetLinuxComponent_50})
    curl -SL ${dotnetLinuxComponent_50} --output /tmp/dotnet.tar.gz && \
    echo "${dotnetLinuxComponentSHA512_50} */tmp/dotnet.tar.gz" | sha512sum -c -; \
    tar -zxf /tmp/dotnet.tar.gz -C /usr/share/dotnet && \
    rm /tmp/dotnet.tar.gz && \
    find /usr/share/dotnet -name "*.lzma" -type f -delete && \
# Install [${dotnetLinuxComponentName}](${dotnetLinuxComponent})
    curl -SL ${dotnetLinuxComponent} --output /tmp/dotnet.tar.gz && \
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

