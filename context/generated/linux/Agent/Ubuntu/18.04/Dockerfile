# Default arguments
ARG dockerComposeLinuxComponentVersion='1.24.1'
ARG dockerLinuxComponentVersion='5:19.03.13~3-0~ubuntu'
ARG dotnetLibs='libc6 libgcc1 libgssapi-krb5-2 libicu60 libssl1.1 libstdc++6 zlib1g'
ARG dotnetLinuxComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/3.1.405/dotnet-sdk-3.1.405-linux-x64.tar.gz'
ARG dotnetLinuxComponentSHA512='924ec0ab3f126d340ef37fe90263a91f31218995716d1ad5a817bdc6ef71e4d8e87a91edeeb785f5dff3912cc08fe87615718986bb5540ff23e9edf2302e38dd'
ARG gitLinuxComponentVersion='1:2.17.1-1ubuntu0.7'
ARG repo=''
ARG teamcityMinimalAgentImage='teamcity-minimal-agent:2020.2.2-linux-18.04'

# The list of required arguments
# ARG dotnetLinuxComponent
# ARG dotnetLinuxComponentSHA512
# ARG teamcityMinimalAgentImage
# ARG dotnetLibs
# ARG gitLinuxComponentVersion
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

ARG dotnetLinuxComponent
ARG dotnetLinuxComponentSHA512
ARG dotnetLibs
ARG gitLinuxComponentVersion
ARG dockerComposeLinuxComponentVersion
ARG dockerLinuxComponentVersion

RUN apt-get update && \
    apt-get install -y git=${gitLinuxComponentVersion} mercurial apt-transport-https software-properties-common && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add - && \
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" && \
    apt-cache policy docker-ce && \
    apt-get update && \
    apt-get install -y  docker-ce=${dockerLinuxComponentVersion}-$(lsb_release -cs) \
                        docker-ce-cli=${dockerLinuxComponentVersion}-$(lsb_release -cs) \
                        containerd.io=1.2.13-2 \
                        systemd && \
    systemctl disable docker && \
    sed -i -e 's/\r$//' /services/run-docker.sh && \
    curl -SL "https://github.com/docker/compose/releases/download/${dockerComposeLinuxComponentVersion}/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose && \
    apt-get install -y --no-install-recommends ${dotnetLibs} && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    curl -SL ${dotnetLinuxComponent} --output /tmp/dotnet.tar.gz && \
    echo "${dotnetLinuxComponentSHA512} */tmp/dotnet.tar.gz" | sha512sum -c -; \
    mkdir -p /usr/share/dotnet && \
    tar -zxf /tmp/dotnet.tar.gz -C /usr/share/dotnet && \
    rm /tmp/dotnet.tar.gz && \
    find /usr/share/dotnet -name "*.lzma" -type f -delete && \
    ln -s /usr/share/dotnet/dotnet /usr/bin/dotnet && \
# Trigger .NET CLI first run experience by running arbitrary cmd to populate local package cache
    dotnet help && \
# Other
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    chown -R buildagent:buildagent /services && \
    usermod -aG docker buildagent

# A better fix for TW-52939 Dockerfile build fails because of aufs
VOLUME /var/lib/docker

USER buildagent

