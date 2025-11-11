# Default arguments
ARG gitLFSLinuxComponentVersion='2.3.4-1'
ARG gitLinuxComponentVersion='1:2.41.0-0ppa1~ubuntu18.04.1'
ARG jdkServerLinuxARM64Component='https://corretto.aws/downloads/resources/21.0.9.10.1/amazon-corretto-21.0.9.10.1-linux-aarch64.tar.gz'
ARG jdkServerLinuxARM64ComponentMD5SUM='f8568c459023d0327937e7a6ca9ea5ce'
ARG p4Version='r25.1'
ARG repo=''
ARG ubuntuImage='ubuntu:18.04'

# The list of required arguments
# ARG jdkServerLinuxARM64Component
# ARG jdkServerLinuxARM64ComponentMD5SUM
# ARG ubuntuImage
# ARG gitLinuxComponentVersion
# ARG gitLFSLinuxComponentVersion
# ARG p4Version





# Build runtime for installing Git LFS
FROM ${ubuntuImage} AS builder

ARG gitLFSLinuxComponentVersion

# Install required dependencies & build Git LFS
RUN apt-get update && \
    apt-get install -y \
    make \
    gcc \
    git \
    zlib1g-dev \
    gnupg \
    curl && \
    # Install Git LFS
    curl -sLO https://github.com/git-lfs/git-lfs/releases/download/${gitLFSLinuxComponentVersion}/git-lfs-linux-arm64-${gitLFSLinuxComponentVersion}.tar.gz && \
    mkdir git-lfs-${gitLFSLinuxComponentVersion} && tar -xzf git-lfs-linux-arm64-${gitLFSLinuxComponentVersion}.tar.gz -C git-lfs-${gitLFSLinuxComponentVersion} --strip-components 1 && \
    PREFIX="/usr" ./git-lfs-${gitLFSLinuxComponentVersion}/install.sh && \
    # Copy configuration with Git LFS filter
    cp ~/.gitconfig /etc/gitconfig && \
    # Clean up
    rm -rf git-lfs-linux-arm64-${gitLFSLinuxComponentVersion}.tar.gz git-lfs-${gitLFSLinuxComponentVersion} && \
    rm -rf /var/lib/apt/lists/*


FROM ${ubuntuImage}

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'

RUN apt-get update && \
    apt-get install -y --no-install-recommends curl ca-certificates fontconfig locales unzip \
    # See: TW-91621
    netcat-openbsd \
    # Git & Git LFS Runtime dependencies
    libcurl4-openssl-dev libexpat1-dev zlib1g-dev && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    # Locale adjustment. See TW-91776
    echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen && \
    locale-gen en_US.UTF-8

# Copy Git LFS with the config
COPY --from=builder /etc/gitconfig /etc/gitconfig
COPY --from=builder /usr/bin/git-lfs /usr/bin/git-lfs

# JDK preparation start
ARG jdkServerLinuxARM64Component
ARG jdkServerLinuxARM64ComponentMD5SUM

RUN set -eux; \
    curl -LfsSo /tmp/openjdk.tar.gz ${jdkServerLinuxARM64Component}; \
    echo "${jdkServerLinuxARM64ComponentMD5SUM} */tmp/openjdk.tar.gz" | md5sum -c -; \
    mkdir -p /opt/java/openjdk; \
    cd /opt/java/openjdk; \
    tar -xf /tmp/openjdk.tar.gz --strip-components=1; \
    chown -R root:root /opt/java; \
    rm -rf /tmp/openjdk.tar.gz;

ENV JAVA_HOME=/opt/java/openjdk \
    PATH="/opt/java/openjdk/bin:$PATH"

RUN update-alternatives --install /usr/bin/java java ${JAVA_HOME}/bin/java 1 && \
    update-alternatives --set java ${JAVA_HOME}/bin/java && \
    update-alternatives --install /usr/bin/javac javac ${JAVA_HOME}/bin/javac 1 && \
    update-alternatives --set javac ${JAVA_HOME}/bin/javac

ENV TEAMCITY_DATA_PATH=/data/teamcity_server/datadir \
    TEAMCITY_DIST=/opt/teamcity \
    TEAMCITY_LOGS=/opt/teamcity/logs \
    TEAMCITY_ENV=container \
    CATALINA_TMPDIR=/opt/teamcity/temp \
    TEAMCITY_SERVER_MEM_OPTS="-Xmx2g -XX:ReservedCodeCacheSize=640m" \
    LANG=C.UTF-8

EXPOSE 8111

# SCM Operations: Mercirual, Perforce CLI & Mandatory utilities
ARG p4Version
ARG gitLinuxComponentVersion

RUN apt-get update && \
    # Mercurial
    apt-get install -y mercurial gnupg software-properties-common && \
    # Git
    add-apt-repository ppa:git-core/ppa -y && \
    apt-get install -y git=${gitLinuxComponentVersion} && \
    # Perforce (p4 CLI)
    curl -Lo /usr/local/bin/p4 "https://www.perforce.com/downloads/perforce/${p4Version}/bin.linux26aarch64/p4" && \
    chmod +x /usr/local/bin/p4 && \
    p4 -V && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    [ -f /etc/gitconfig ] || (echo "'/etc/gitconfig' does not exist, while LFS filter is required" && exit 1)

COPY welcome.sh /welcome.sh
COPY run-server.sh /run-server.sh
COPY check-server-volumes.sh /services/check-server-volumes.sh
COPY run-server-services.sh /run-services.sh

RUN chmod +x /welcome.sh /run-server.sh /run-services.sh && sync && \
    # Delete the default Ubuntu user if it exists with a conflicting UID. See: https://bugs.launchpad.net/cloud-images/+bug/2005129
    if id -u ubuntu >/dev/null 2>&1 && [ "$(id -u ubuntu)" -eq 1000 ]; then userdel -r -f ubuntu; fi && \
    groupadd -g 1000 tcuser && \
    useradd -r -m -u 1000 -g tcuser tcuser && \
    echo '[ ! -z "$TERM" -a -x /welcome.sh -a -x /welcome.sh ] && /welcome.sh' >> /etc/bash.bashrc && \
    sed -i -e 's/\r$//' /welcome.sh && \
    sed -i -e 's/\r$//' /run-server.sh && \
    sed -i -e 's/\r$//' /run-services.sh && \
    sed -i -e 's/\r$//' /services/check-server-volumes.sh && \
    mkdir -p $TEAMCITY_DATA_PATH $TEAMCITY_LOGS $CATALINA_TMPDIR && \
    chown -R tcuser:tcuser /services $TEAMCITY_DIST $TEAMCITY_DATA_PATH $TEAMCITY_LOGS $CATALINA_TMPDIR

COPY --chown=tcuser:tcuser TeamCity $TEAMCITY_DIST
RUN echo "docker-ubuntu" > $TEAMCITY_DIST/webapps/ROOT/WEB-INF/DistributionType.txt

USER tcuser:tcuser

VOLUME $TEAMCITY_DATA_PATH \
       $TEAMCITY_LOGS \
       $CATALINA_TMPDIR

CMD ["/run-services.sh"]
