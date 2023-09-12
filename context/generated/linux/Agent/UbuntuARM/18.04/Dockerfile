# Default arguments
ARG containerdIoLinuxComponentVersion='1.4.12-1'
ARG dockerComposeLinuxComponentVersion='1.28.5'
ARG dockerLinuxComponentVersion='5:20.10.12~3-0~ubuntu'
ARG dotnetLibs='libc6 libgcc1 libgssapi-krb5-2 libicu60 libssl1.1 libstdc++6 zlib1g'
ARG dotnetLinuxARM64Component='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.413/dotnet-sdk-6.0.413-linux-arm64.tar.gz'
ARG dotnetLinuxARM64Component_31='https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-linux-arm64.tar.gz'
ARG dotnetLinuxARM64Component_50='https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.17/dotnet-runtime-5.0.17-linux-arm64.tar.gz'
ARG dotnetLinuxARM64ComponentSHA512='7f05a9774d79e694da5a6115d9916abf87a65e40bd6bdaa5dca1f705795436bc8e764242f7045207386a86732ef5519f60bdb516a3860e4860bca7ee91a21759'
ARG dotnetLinuxARM64ComponentSHA512_31='ff311df0db488f3b5cc03c7f6724f8442de7e60fa0a503ec8f536361ce7a357ad26d09d2499d68c50ebdfa751a5520bba4aaa77a38b191c892d5a018561ce422'
ARG dotnetLinuxARM64ComponentSHA512_50='99cb11871924d3abedcc9c8079c54bc0c550203c7cbe4e349ed70d4355f40e4620b68d90b797e6461d898c06bed6953580e2cd4ad01483e5de107ca5a3409610'
ARG gitLFSLinuxComponentVersion='2.3.4-1'
ARG gitLinuxComponentVersion='1:2.40.0-0ppa1~ubuntu18.04.1'
ARG repo=''
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:2022.10.5-linux-arm64-18.04'

# The list of required arguments
# ARG dotnetLinuxARM64Component
# ARG dotnetLinuxARM64ComponentSHA512
# ARG dotnetLinuxARM64Component_31
# ARG dotnetLinuxARM64ComponentSHA512_31
# ARG dotnetLinuxARM64Component_50
# ARG dotnetLinuxARM64ComponentSHA512_50
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
ARG dotnetLinuxARM64Component_31
ARG dotnetLinuxARM64ComponentSHA512_31
ARG dotnetLinuxARM64Component_50
ARG dotnetLinuxARM64ComponentSHA512_50
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
    curl -SL "https://github.com/docker/compose/releases/download/${dockerComposeLinuxComponentVersion}/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose && \
# Dotnet
    apt-get install -y --no-install-recommends ${dotnetLibs} && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    mkdir -p /usr/share/dotnet && \
    curl -SL ${dotnetLinuxARM64Component_31} --output /tmp/dotnet.tar.gz && \
    echo "${dotnetLinuxARM64ComponentSHA512_31} */tmp/dotnet.tar.gz" | sha512sum -c -; \
    tar -zxf /tmp/dotnet.tar.gz -C /usr/share/dotnet && \
    rm /tmp/dotnet.tar.gz && \
    find /usr/share/dotnet -name "*.lzma" -type f -delete && \
    curl -SL ${dotnetLinuxARM64Component_50} --output /tmp/dotnet.tar.gz && \
    echo "${dotnetLinuxARM64ComponentSHA512_50} */tmp/dotnet.tar.gz" | sha512sum -c -; \
    tar -zxf /tmp/dotnet.tar.gz -C /usr/share/dotnet && \
    rm /tmp/dotnet.tar.gz && \
    find /usr/share/dotnet -name "*.lzma" -type f -delete && \
    curl -SL ${dotnetLinuxARM64Component} --output /tmp/dotnet.tar.gz && \
    echo "${dotnetLinuxARM64ComponentSHA512} */tmp/dotnet.tar.gz" | sha512sum -c -; \
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

