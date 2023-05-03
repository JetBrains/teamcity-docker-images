# Default arguments
ARG containerdIoLinuxComponentVersion='1.4.12-1'
ARG dockerComposeLinuxComponentVersion='1.28.5'
ARG dockerLinuxComponentVersion='5:20.10.12~3-0~ubuntu'
ARG dotnetLibs='libc6 libgcc1 libgssapi-krb5-2 libicu60 libssl1.1 libstdc++6 zlib1g'
ARG dotnetLinuxARM64Component='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/6.0.100/dotnet-sdk-6.0.100-linux-arm64.tar.gz'
ARG dotnetLinuxARM64Component_31='https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.21/dotnet-runtime-3.1.21-linux-arm64.tar.gz'
ARG dotnetLinuxARM64Component_50='https://dotnetcli.azureedge.net/dotnet/Runtime/5.0.12/dotnet-runtime-5.0.12-linux-arm64.tar.gz'
ARG dotnetLinuxARM64ComponentSHA512='e5983c1c599d6dc7c3c7496b9698e47c68247f04a5d0d1e3162969d071471297bce1c2fd3a1f9fb88645006c327ae79f880dcbdd8eefc9166fd717331f2716e7'
ARG dotnetLinuxARM64ComponentSHA512_31='80971125650a2fa0163e39a2de98bc2e871c295b723559e6081a3ab59d99195aa5b794450f8182c5eb4e7e472ca1c13340ef1cc8a5588114c494bbb5981f19c4'
ARG dotnetLinuxARM64ComponentSHA512_50='a8089fad8d21a4b582aa6c3d7162d56a21fee697fd400f050a772f67c2ace5e4196d1c4261d3e861d6dc2e5439666f112c406104d6271e5ab60cda80ef2ffc64'
ARG gitLFSLinuxComponentVersion='2.3.4-1'
ARG gitLinuxComponentVersion='1:2.40.1-0ppa1~ubuntu18.04.1'
ARG repo=''
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:EAP-linux-arm64-18.04'

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

