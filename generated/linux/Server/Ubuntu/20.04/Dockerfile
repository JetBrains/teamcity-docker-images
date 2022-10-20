# Default arguments
ARG gitLFSLinuxComponentVersion='2.9.2-1'
ARG gitLinuxComponentVersion='1:2.38.1-0ppa1~ubuntu20.04.1'
ARG jdkServerLinuxComponent='https://corretto.aws/downloads/resources/11.0.15.9.1/amazon-corretto-11.0.15.9.1-linux-x64.tar.gz'
ARG jdkServerLinuxComponentMD5SUM='30f9268b8f4c2c2f8c1676611b88aa0d'
ARG p4Version='2021.2-2338640'
ARG repo='https://hub.docker.com/r/jetbrains/'
ARG ubuntuImage='ubuntu:20.04'

# The list of required arguments
# ARG jdkServerLinuxComponent
# ARG jdkServerLinuxComponentMD5SUM
# ARG ubuntuImage
# ARG gitLinuxComponentVersion
# ARG gitLFSLinuxComponentVersion



FROM ${ubuntuImage}

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'

RUN apt-get update && \
    apt-get install -y --no-install-recommends curl ca-certificates fontconfig locales unzip  && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen && \
    locale-gen en_US.UTF-8 && \
    rm -rf /var/lib/apt/lists/*

# JDK preparation start

ARG jdkServerLinuxComponent
ARG jdkServerLinuxComponentMD5SUM

RUN set -eux; \
    curl -LfsSo /tmp/openjdk.tar.gz ${jdkServerLinuxComponent}; \
    echo "${jdkServerLinuxComponentMD5SUM} */tmp/openjdk.tar.gz" | md5sum -c -; \
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
    CATALINA_TMPDIR=/opt/teamcity/temp \
    TEAMCITY_SERVER_MEM_OPTS="-Xmx2g -XX:ReservedCodeCacheSize=350m" \
    LANG=C.UTF-8

EXPOSE 8111

ARG gitLinuxComponentVersion

ARG gitLFSLinuxComponentVersion

ARG p4Version

RUN apt-get update && \
    apt-get install -y mercurial gnupg software-properties-common && \
    add-apt-repository ppa:git-core/ppa -y && \
    apt-get install -y git=${gitLinuxComponentVersion} git-lfs=${gitLFSLinuxComponentVersion} && \
    git lfs install --system && \
    apt-key adv --fetch-keys https://package.perforce.com/perforce.pubkey && \
    (. /etc/os-release && \
      echo "deb http://package.perforce.com/apt/$ID $VERSION_CODENAME release" > \
      /etc/apt/sources.list.d/perforce.list ) && \
    apt-get update && \
    (. /etc/os-release && apt-get install -y helix-cli-base="${p4Version}~$VERSION_CODENAME" helix-cli="${p4Version}~$VERSION_CODENAME" ) && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/*

COPY welcome.sh /welcome.sh
COPY run-server.sh /run-server.sh
COPY check-server-volumes.sh /services/check-server-volumes.sh
COPY run-server-services.sh /run-services.sh

RUN chmod +x /welcome.sh /run-server.sh /run-services.sh && sync && \
    groupadd -g 1000 tcuser && \
    useradd -r -u 1000 -g tcuser -d $TEAMCITY_DIST tcuser && \
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
