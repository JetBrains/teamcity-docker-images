# The list of required arguments
# ARG jdkServerLinuxARM64Component
# ARG jdkServerLinuxARM64ComponentMD5SUM
# ARG ubuntuImage
# ARG gitLinuxComponentVersion
# ARG gitLFSLinuxComponentVersion

# Id teamcity-server
# Tag ${versionTag}-linux${linuxVersion}
# Tag ${latestTag}
# Tag ${versionTag}
# Platform ${linuxPlatform}
# Repo ${repo}
# Weight 1

## ${serverCommentHeader}

# @AddToolToDoc [${jdkServerLinuxARM64ComponentName}](${jdkServerLinuxARM64Component})
# @AddToolToDoc ${gitLinuxComponentName}
# @AddToolToDoc ${gitLFSLinuxComponentName}


# Build runtime for Git & Git LFS binaries
FROM ${ubuntuImage} AS builder

ENV GIT_VERSION=2.47.1
ENV GIT_LFS_VERSION=v3.6.1

# Install required dependencies for building Git and Git LFS
RUN apt-get update && \
    apt-get install -y \
    libssl-dev build-essential autoconf \
    make \
    gcc \
    libcurl4-openssl-dev \
    libexpat1-dev \
    gettext \
    unzip \
    zlib1g-dev \
    gnupg \
    curl && \
    # Install Git
    curl -O https://www.kernel.org/pub/software/scm/git/git-${GIT_VERSION}.tar.gz && \
    curl -O https://www.kernel.org/pub/software/scm/git/git-${GIT_VERSION}.tar.gz.sig && \
    tar -xzf git-${GIT_VERSION}.tar.gz && \
    cd git-${GIT_VERSION} && \
    make configure && ./configure --prefix=/usr && \
    make all && \
    make install && \
    cd .. && \
    rm -rf git-${GIT_VERSION}* && \
    # Install Git LFS
    curl -sLO https://github.com/git-lfs/git-lfs/releases/download/${GIT_LFS_VERSION}/git-lfs-linux-arm64-${GIT_LFS_VERSION}.tar.gz && \
    mkdir git-lfs-${GIT_LFS_VERSION} && tar -xzf git-lfs-linux-arm64-${GIT_LFS_VERSION}.tar.gz -C git-lfs-${GIT_LFS_VERSION} --strip-components 1 && \
    PREFIX="/usr" ./git-lfs-${GIT_LFS_VERSION}/install.sh && \
    # Copy configuration with Git LFS filter
    cp ~/.gitconfig /etc/gitconfig && \
    # Clean up
    rm -rf git-lfs-linux-arm64-${GIT_LFS_VERSION}.tar.gz git-lfs-${GIT_LFS_VERSION} && \
    rm -rf /var/lib/apt/lists/*


# Based on ${ubuntuImage} 0
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

# Copy compiled Git, Git LFS and its configuration from the builder stage
COPY --from=builder /etc/gitconfig /etc/gitconfig
COPY --from=builder /usr/bin/git /usr/bin/git
COPY --from=builder /usr/libexec/git-core /usr/libexec/git-core
COPY --from=builder /usr/share/git-core /usr/share/git-core
COPY --from=builder /usr/bin/git-lfs /usr/bin/git-lfs

# JDK preparation start
ARG jdkServerLinuxARM64Component
ARG jdkServerLinuxARM64ComponentMD5SUM
ARG p4Version

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
RUN apt-get update && \
    apt-get install -y mercurial gnupg software-properties-common && \
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