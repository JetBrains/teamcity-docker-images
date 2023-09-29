#
# Dockerfile containing manifest of TeamCity Linux Agent (AMD) with custom .NET SDKs
# See: https://dotnet.microsoft.com/download/dotnet/
#

ARG teamCityAgentImage
ARG dotnetSdkVersion
ARG dotnetSdkChecksum

FROM ${teamCityAgentImage}
USER root

RUN rm -rf /usr/share/dotnet && \
    mkdir -p /usr/share/dotnet && \
    curl -SL https://dotnetcli.azureedge.net/dotnet/Sdk/${dotnetSdkVersion}/dotnet-sdk-${dotnetSdkVersion}-linux-x64.tar.gz \
        --output /tmp/dotnet.tar.gz && \
        echo "Downloaded .NET SDK $$dotnetSdkVersion (Linux AMD64) checksum: $(sha512sum tmp/dotnet.tar.gz)" && \
        echo "$dotnetSdkChecksum */tmp/dotnet.tar.gz" | sha512sum -c - && \
        tar -zxf /tmp/dotnet.tar.gz -C /usr/share/dotnet && \
        rm /tmp/dotnet.tar.gz && \
        find /usr/share/dotnet -name "*.lzma" -type f -delete && \
        ln -sf /usr/share/dotnet/dotnet /usr/bin/dotnet && \
    dotnet help && \
    dotnet --info && \
    chown -R buildagent:buildagent /services

VOLUME /var/lib/docker
USER buildagent

