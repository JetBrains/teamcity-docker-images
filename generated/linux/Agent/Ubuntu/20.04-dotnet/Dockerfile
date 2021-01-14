# Default arguments
ARG dotnetLatestLinuxComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.102/dotnet-sdk-5.0.102-linux-x64.tar.gz'
ARG teamcityAgentImage='jetbrains/teamcity-agent:2020.2-linux-sudo'

# The list of required arguments
# ARG dotnetLatestLinuxComponent



FROM ${teamcityAgentImage}

USER root

ARG dotnetLatestLinuxComponent

RUN \
    apt-get install -y --no-install-recommends ${dotnetLibs} && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    curl -SL ${dotnetLatestLinuxComponent} --output dotnet.tar.gz && \
    tar -zxf dotnet.tar.gz -C /usr/share/dotnet && \
    rm dotnet.tar.gz && \
    find /usr/share/dotnet -name "*.lzma" -type f -delete

# Trigger .NET CLI first run experience by running arbitrary cmd to populate local package cache
RUN dotnet help

USER buildagent
