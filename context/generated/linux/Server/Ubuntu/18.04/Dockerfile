# Default arguments
ARG jdkServerLinuxComponent='https://repo.labs.intellij.net/cache/https/corretto.aws/downloads/resources/11.0.8.10.1/amazon-corretto-11.0.8.10.1-linux-x64.tar.gz'
ARG jdkServerLinuxMD5SUM='58b8ae057e363594fb2efaf70cf13b4d'
ARG ubuntuImage='ubuntu:18.04'

# The list of required arguments
# ARG jdkServerLinuxComponent
# ARG jdkServerLinuxMD5SUM
# ARG ubuntuImage



FROM ${ubuntuImage}

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl ca-certificates fontconfig locales unzip \
    && echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen \
    && locale-gen en_US.UTF-8 \
    && rm -rf /var/lib/apt/lists/*

# JDK preparation start

ARG jdkServerLinuxComponent
ARG jdkServerLinuxMD5SUM

RUN set -eux; \
    curl -LfsSo /tmp/openjdk.tar.gz ${jdkServerLinuxComponent}; \
    echo "${jdkServerLinuxMD5SUM} */tmp/openjdk.tar.gz" | md5sum -c -; \
    mkdir -p /opt/java/openjdk; \
    cd /opt/java/openjdk; \
    tar -xf /tmp/openjdk.tar.gz --strip-components=1; \
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
    TEAMCITY_TEMP=/opt/teamcity/temp \
    TEAMCITY_SERVER_MEM_OPTS="-Xmx2g -XX:ReservedCodeCacheSize=350m" \
    LANG=C.UTF-8

EXPOSE 8111
LABEL dockerImage.teamcity.version="latest" \
      dockerImage.teamcity.buildNumber="latest"

RUN apt-get update && \
    apt-get install -y git mercurial && \
    apt-get clean all

COPY welcome.sh /welcome.sh
COPY run-server.sh /run-server.sh
COPY run-server-services.sh /run-services.sh

RUN chmod +x /welcome.sh /run-server.sh /run-services.sh && sync && \
    groupadd -g 1000 tcuser && \
    useradd -r -u 1000 -g tcuser tcuser && \
    echo '[ ! -z "$TERM" -a -x /welcome.sh -a -x /welcome.sh ] && /welcome.sh' >> /etc/bash.bashrc && \
    sed -i -e 's/\r$//' /welcome.sh && \
    sed -i -e 's/\r$//' /run-server.sh && \
    sed -i -e 's/\r$//' /run-services.sh

COPY --chown=tcuser:tcuser TeamCity $TEAMCITY_DIST
RUN rm -rf $TEAMCITY_DIST/buildAgent && \
    rm -rf $TEAMCITY_DIST/temp && \
    echo "docker-ubuntu" > $TEAMCITY_DIST/webapps/ROOT/WEB-INF/DistributionType.txt

VOLUME $TEAMCITY_DATA_PATH \
       $TEAMCITY_LOGS \
       $TEAMCITY_TEMP

CMD ["/run-services.sh"]
