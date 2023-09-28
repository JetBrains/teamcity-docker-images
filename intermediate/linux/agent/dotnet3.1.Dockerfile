#
# Dockerfile containing manifest of TeamCity Agent with .NET 3.1.
#


# TODO: Add SUDO image
FROM ${teamcityAmdImage}
USER root

# Remove .NET versions and install .NET 3.1
# TODO: Add OPTIONAL removal of versions
#rm -rf /usr/share/dotnet && \
#    mkdir -p /usr/share/dotnet && \
# See: # https://dotnet.microsoft.com/download/dotnet/3.1
RUN curl -SL https://dotnetcli.azureedge.net/dotnet/Runtime/3.1.32/dotnet-runtime-3.1.32-linux-x64.tar.gz \
        --output /tmp/dotnet.tar.gz && \
        echo "Downloaded .NET 3.1 (Linux AMD64) checksum: $(sha512sum tmp/dotnet.tar.gz)" && \
        echo "56c5e045844f5474a9a12b42e4a22c851985fac5690e227ce62b529d644c4faeaafdfe255de2f1e86a90c0c114e7de66ce4de1692fbf66357ac4d35341f933c3 */tmp/dotnet.tar.gz" | sha512sum -c -; \
        tar -zxf /tmp/dotnet.tar.gz -C /usr/share/dotnet && \
        rm /tmp/dotnet.tar.gz && \
        find /usr/share/dotnet -name "*.lzma" -type f -delete && \
        ln -sf /usr/share/dotnet/dotnet /usr/bin/dotnet && \
    dotnet help && \
    dotnet --info && \
    chown -R buildagent:buildagent /services

VOLUME /var/lib/docker
USER buildagent

