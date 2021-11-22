# Default arguments
ARG dotnetLatestLinuxComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.401/dotnet-sdk-5.0.401-linux-x64.tar.gz'
ARG dotnetLatestLinuxComponentSHA512='a444d44007709ceb68d8f72dec0531e17f85f800efc0007ace4fa66ba27f095066930e6c6defcd2f85cdedea2fec25e163f5da461c1c2b8563e5cd7cb47091e0'
ARG teamcityAgentImage='jetbrains/teamcity-agent:2020.2.1-linux-sudo'

# The list of required arguments
# ARG dotnetLatestLinuxComponent
# ARG dotnetLatestLinuxComponentSHA512



FROM ${teamcityAgentImage}

USER root

ARG dotnetLatestLinuxComponent
ARG dotnetLatestLinuxComponentSHA512

RUN \
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
