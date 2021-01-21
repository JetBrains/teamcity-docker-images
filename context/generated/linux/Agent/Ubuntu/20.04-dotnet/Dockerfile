# Default arguments
ARG dotnetLatestLinuxComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.102/dotnet-sdk-5.0.102-linux-x64.tar.gz'
ARG dotnetLatestLinuxComponentSHA512='0ce2d5365ca39808fb71baec4584d4ec786491c3735543dc93244604ea97e242377d0987cd8b1e529258dee68f203b5780559201e7ea6d84487d6d8d433329b3'
ARG teamcityAgentImage='jetbrains/teamcity-agent:EAP-linux-sudo'

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
