# Default arguments
ARG dotnetLatestLinuxComponent='https://dotnetcli.blob.core.windows.net/dotnet/Sdk/5.0.203/dotnet-sdk-5.0.203-linux-x64.tar.gz'
ARG dotnetLatestLinuxComponentSHA512='49d8f0414806a9c938192ed13e7707ac2609ca6c2dc408d616e56e98fc0a954b1aa3f569858f7ba38fb79b2ee36dc1920c7f08d1ba4f93da501542b1c8a1320f'
ARG teamcityAgentImage='jetbrains/teamcity-agent:2021.1-linux-sudo'

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
