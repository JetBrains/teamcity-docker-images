# The list of required arguments
# ARG dotnetLatestLinuxComponent
# ARG dotnetLatestLinuxComponentSHA512

# Id teamcity-agent
# Tag linux${linuxVersion}-dotnet
# Platform ${linuxPlatform}
# Repo ${repo}
# Weight 1

## ${agentCommentHeader}
## This image can be built manually. It contains a set of .NET SDK. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

# Based on ${teamcityAgentImage}
FROM ${teamcityAgentImage}

USER root

ARG dotnetLatestLinuxComponent
ARG dotnetLatestLinuxComponentSHA512

RUN \
# Install [${dotnetLatestLinuxComponentName}](${dotnetLatestLinuxComponent})
    apt-get install -y --no-install-recommends ${dotnetLibs} && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    curl -SL ${dotnetLatestLinuxComponent} --output /tmp/dotnet.tar.gz && \
    echo "${dotnetLatestLinuxComponentSHA512} */tmp/dotnet.tar.gz" | sha512sum -c -; \
    tar -zxf /tmp/dotnet.tar.gz -C /usr/share/dotnet && \
    rm /tmp/dotnet.tar.gz && \
    find /usr/share/dotnet -name "*.lzma" -type f -delete

# Trigger .NET CLI first run experience by running arbitrary cmd to populate local package cache
RUN dotnet help

USER buildagent
