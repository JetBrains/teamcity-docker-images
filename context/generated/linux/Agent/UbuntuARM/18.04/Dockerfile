# Default arguments
ARG containerdIoLinuxComponentVersion='1.6.28-2'
ARG dockerLinuxComponentVersion='5:27.5.1-1~ubuntu'
ARG dotnetLibs='libc6 libgcc1 libgssapi-krb5-2 libicu60 libssl1.1 libstdc++6 zlib1g'
ARG dotnetLinuxARM64Component='https://builds.dotnet.microsoft.com/dotnet/Sdk/8.0.415/dotnet-sdk-8.0.415-linux-arm64.tar.gz'
ARG dotnetLinuxARM64ComponentSHA512='c2efcccfd83690482d3314b23a9d9b53d41591795eb50e02857cb495dd1fde132f2c332dc243095463338d2dc6cd362cd7ea7ae3a9ce75b32ab54a517b91def8'
ARG gitLFSLinuxComponentVersion='2.3.4-1'
ARG gitLinuxComponentVersion='1:2.41.0-0ppa1~ubuntu18.04.1'
ARG p4Version='r24.2'
ARG repo=''
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:EAP-linux-arm64-18.04'

# The list of required arguments
# ARG dotnetLinuxARM64Component
# ARG dotnetLinuxARM64ComponentSHA512
# ARG teamcityMinimalAgentImage
# ARG dotnetLibs
# ARG gitLinuxComponentVersion
# ARG gitLFSLinuxComponentVersion
# ARG dockerLinuxComponentVersion
# ARG ubuntuImage
# ARG p4Version





# Build runtime for installing Git LFS
FROM ${ubuntuImage} AS builder

ENV GIT_LFS_VERSION=v3.6.1

# Install required dependencies & build Git LFS
RUN apt-get update && \
    apt-get install -y \
    make \
    gcc \
    git \
    zlib1g-dev \
    gnupg \
    curl && \
    # Install Git LFS
    curl -sLO https://github.com/git-lfs/git-lfs/releases/download/${GIT_LFS_VERSION}/git-lfs-linux-arm64-${GIT_LFS_VERSION}.tar.gz && \
    mkdir git-lfs-${GIT_LFS_VERSION} && tar -xzf git-lfs-linux-arm64-${GIT_LFS_VERSION}.tar.gz -C git-lfs-${GIT_LFS_VERSION} --strip-components 1 && \
    PREFIX="/usr" ./git-lfs-${GIT_LFS_VERSION}/install.sh && \
    # Copy configuration with Git LFS filter
    cp ~/.gitconfig /etc/gitconfig && \
    # Clean up
    rm -rf git-lfs-linux-arm64-${GIT_LFS_VERSION}.tar.gz git-lfs-${GIT_LFS_VERSION} && \
    rm -rf /var/lib/apt/lists/*


FROM ${teamcityMinimalAgentImage}

# Copy Git LFS with the config
COPY --from=builder /etc/gitconfig /etc/gitconfig
COPY --from=builder /usr/bin/git-lfs /usr/bin/git-lfs

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

# SCM Operations
ARG p4Version
ARG gitLinuxComponentVersion

RUN apt-get update && \
    apt-get install -y mercurial apt-transport-https software-properties-common && \
    # Git
    add-apt-repository ppa:git-core/ppa -y && \
    apt-get install -y git=${gitLinuxComponentVersion} && \
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
# Perforce (p4 CLI)
    curl -Lo /usr/local/bin/p4 "https://www.perforce.com/downloads/perforce/${p4Version}/bin.linux26aarch64/p4" && \
    chmod +x /usr/local/bin/p4 && \
    p4 -V && \
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
    usermod -aG docker buildagent && \
    [ -f /etc/gitconfig ] || (echo "'/etc/gitconfig' does not exist, while LFS filter is required" && exit 1)

# A better fix for TW-52939 Dockerfile build fails because of aufs
VOLUME /var/lib/docker

USER buildagent

