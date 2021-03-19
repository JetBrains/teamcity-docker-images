# The list of required arguments
# ARG jdkLinuxComponent
# ARG jdkLinuxComponentMD5SUM
# ARG linuxImage

# Id teamcity-minimal-agent
# Tag ${versionTag}-linux${linuxVersion}
# Tag ${latestTag}
# Tag ${versionTag}
# Platform ${linuxPlatform}
# Repo ${repo}
# Weight 1

## ${agentCommentHeader}

# Based on ${linuxImage} 0
FROM ${linuxImage}

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8' DEBIAN_FRONTEND=noninteractive TZ="Europe/London" 

RUN apt-get update && \
    apt-get install -y --no-install-recommends curl ca-certificates fontconfig locales unzip && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen && \
    locale-gen en_US.UTF-8 && \
    rm -rf /var/lib/apt/lists/* && \
    useradd -m buildagent

# Install [${jdkLinuxComponentName}](${jdkLinuxComponent})
ARG jdkLinuxComponent
ARG jdkLinuxComponentMD5SUM

RUN set -eux; \
    curl -LfsSo /tmp/openjdk.tar.gz ${jdkLinuxComponent}; \
    echo "${jdkLinuxComponentMD5SUM} */tmp/openjdk.tar.gz" | md5sum -c -; \
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

COPY --chown=buildagent:buildagent run-agent.sh /run-agent.sh
COPY --chown=buildagent:buildagent run-agent-services.sh /run-services.sh
COPY --chown=buildagent:buildagent TeamCity/buildAgent /opt/buildagent

RUN chmod +x /opt/buildagent/bin/*.sh && \
    chmod +x /run-agent.sh /run-services.sh && sync && \
    mkdir -p /data/teamcity_agent/conf && \
    chown -R buildagent:buildagent /data/teamcity_agent && \
    sed -i -e 's/\r$//' /run-agent.sh && \
    sed -i -e 's/\r$//' /run-services.sh    

USER buildagent

RUN mkdir -p /opt/buildagent/work && \
    mkdir -p /opt/buildagent/system/.teamcity-agent && \
    mkdir -p /opt/buildagent/temp && \
    mkdir -p /opt/buildagent/plugins && \
    mkdir -p /opt/buildagent/logs && \
    mkdir -p /opt/buildagent/tools && \
    echo >> /opt/buildagent/system/.teamcity-agent/teamcity-agent.xml && \
    sed -i -e 's/\r$//' /opt/buildagent/system/.teamcity-agent/teamcity-agent.xml && \
    echo >> /opt/buildagent/system/.teamcity-agent/unpacked-plugins.xml && \
    sed -i -e 's/\r$//' /opt/buildagent/system/.teamcity-agent/unpacked-plugins.xml

VOLUME /data/teamcity_agent/conf
VOLUME /opt/buildagent/work
VOLUME /opt/buildagent/system
VOLUME /opt/buildagent/temp
VOLUME /opt/buildagent/logs
VOLUME /opt/buildagent/tools
VOLUME /opt/buildagent/plugins

CMD ["/run-services.sh"]