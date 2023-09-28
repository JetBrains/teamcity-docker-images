#
# Dockerfile containing manifest of TeamCity Agent with .NET 3.1 for TeamCity Agent (ARM-based)
#


# TODO: Add SUDO image
FROM ${teamcityArmImage}
USER root

# Remove .NET versions and install .NET 3.1
# TODO: Add OPTIONAL removal of versions
#rm -rf /usr/share/dotnet && \
#    mkdir -p /usr/share/dotnet && \
# See: # https://dotnet.microsoft.com/download/dotnet/3.1
RUN curl -SL https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-linux-arm64.tar.gz \
        --output /tmp/dotnet.tar.gz && \
        echo "Downloaded .NET 3.1 (Linux ARM64) checksum: $(sha512sum tmp/dotnet.tar.gz)" && \
        echo "ff311df0db488f3b5cc03c7f6724f8442de7e60fa0a503ec8f536361ce7a357ad26d09d2499d68c50ebdfa751a5520bba4aaa77a38b191c892d5a018561ce422 */tmp/dotnet.tar.gz" | sha512sum -c -; \
        tar -zxf /tmp/dotnet.tar.gz -C /usr/share/dotnet && \
        rm /tmp/dotnet.tar.gz && \
        find /usr/share/dotnet -name "*.lzma" -type f -delete && \
        ln -sf /usr/share/dotnet/dotnet /usr/bin/dotnet && \
    dotnet help && \
    dotnet --info && \
    chown -R buildagent:buildagent /services

VOLUME /var/lib/docker
USER buildagent

