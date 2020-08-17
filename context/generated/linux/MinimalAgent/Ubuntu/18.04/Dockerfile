# Default arguments
ARG jdkLinuxComponent='https://repo.labs.intellij.net/cache/https/corretto.aws/downloads/resources/8.252.09.1/amazon-corretto-8.252.09.1-linux-x64.tar.gz'
ARG jdkLinuxMD5SUM='7e9925dbe18506ce35e226c3b4d05613'
ARG ubuntuImage='ubuntu:18.04'

# The list of required arguments
# ARG jdkLinuxComponent
# ARG jdkLinuxMD5SUM
# ARG ubuntuImage



FROM ${ubuntuImage}

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl ca-certificates fontconfig locales unzip \
    && echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen \
    && locale-gen en_US.UTF-8 \
    && rm -rf /var/lib/apt/lists/*

ARG jdkLinuxComponent
ARG jdkLinuxMD5SUM

RUN set -eux; \
    curl -LfsSo /tmp/openjdk.tar.gz ${jdkLinuxComponent}; \
    echo "${jdkLinuxMD5SUM} */tmp/openjdk.tar.gz" | md5sum -c -; \
    mkdir -p /opt/java/openjdk; \
    cd /opt/java/openjdk; \
    tar -xf /tmp/openjdk.tar.gz --strip-components=1; \
    rm -rf /tmp/openjdk.tar.gz;

ENV JAVA_HOME=/opt/java/openjdk \
    JRE_HOME=/opt/java/openjdk/jre \
    PATH="/opt/java/openjdk/bin:$PATH"

RUN update-alternatives --install /usr/bin/java java ${JRE_HOME}/bin/java 1 && \
    update-alternatives --set java ${JRE_HOME}/bin/java && \
    update-alternatives --install /usr/bin/javac javac ${JRE_HOME}/../bin/javac 1 && \
    update-alternatives --set javac ${JRE_HOME}/../bin/javac

# JDK preparation end
ENV CONFIG_FILE=/data/teamcity_agent/conf/buildAgent.properties \
    LANG=C.UTF-8

LABEL dockerImage.teamcity.version="latest" \
      dockerImage.teamcity.buildNumber="latest"

COPY run-agent.sh /run-agent.sh
COPY run-agent-services.sh /run-services.sh
COPY TeamCity/buildAgent /opt/buildagent

RUN apt-get update && \
    useradd -m buildagent && \
    chmod +x /opt/buildagent/bin/*.sh && \
    chmod +x /run-agent.sh /run-services.sh && sync && \
    sed -i -e 's/\r$//' /run-agent.sh && \
    sed -i -e 's/\r$//' /run-services.sh

RUN mkdir -p /data/teamcity_agent/conf \
    && chown -R buildagent:buildagent /data/teamcity_agent \
    && mkdir -p /opt/buildagent/work \
    && mkdir -p /opt/buildagent/system \
    && mkdir -p /opt/buildagent/temp \
    && mkdir -p /opt/buildagent/plugins \
    && mkdir -p /opt/buildagent/logs \
    && mkdir -p /opt/buildagent/tools \
    && rm -Rf /opt/buildagent/plugins/* \
    && chown -R buildagent:buildagent /opt/buildagent \
    && chown buildagent:buildagent /run-agent.sh \
    && chown buildagent:buildagent /run-services.sh \
    && chmod +x /opt/buildagent/bin/*.sh

VOLUME /data/teamcity_agent/conf
VOLUME /opt/buildagent/work
VOLUME /opt/buildagent/system
VOLUME /opt/buildagent/temp
VOLUME /opt/buildagent/logs
VOLUME /opt/buildagent/tools
VOLUME /opt/buildagent/plugins

USER buildagent

CMD ["/run-services.sh"]
