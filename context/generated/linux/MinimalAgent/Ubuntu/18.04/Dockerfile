# Default arguments
ARG jdkLinuxComponent='https://corretto.aws/downloads/resources/21.0.13.0.11/amazon-corretto-21.0.13.0.11-linux-x64.tar.gz'
ARG jdkLinuxComponentMD5SUM='952bee5b1b79bfb8331ce0faa9f85960'
ARG ubuntuImage='ubuntu:18.04'

# The list of required arguments
# ARG jdkLinuxComponent
# ARG jdkLinuxComponentMD5SUM
# ARG ubuntuImage




FROM ${ubuntuImage}

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8' DEBIAN_FRONTEND=noninteractive TZ="Europe/London" 

RUN apt-get update && \
    apt-get install -y --no-install-recommends curl ca-certificates fontconfig locales unzip \
    # See: TW-91621
    netcat-openbsd && \
# Python
    apt-get install -y python3-venv && \
    # CVE-2026-111943: fail the build if the CA bundle still contains the emSign ECC Root CA - G3 or AAA Certificate Services roots
    mkdir /tmp/ca && csplit -sz -f /tmp/ca/c /etc/ssl/certs/ca-certificates.crt '/-----BEGIN CERTIFICATE-----/' '{*}' && \
    fps="$(for f in /tmp/ca/c*; do openssl x509 -in "$f" -noout -fingerprint -sha256; done)" && \
    [ -n "$fps" ] && \
    ! echo "$fps" | grep -F \
      -e '86:A1:EC:BA:08:9C:4A:8D:3B:BE:27:34:C6:12:BA:34:1D:81:3E:04:3C:F9:E8:A8:62:CD:5C:57:A3:6B:BE:6B' \
      -e 'D7:A7:A0:FB:5D:7E:27:31:D7:71:E9:48:4E:BC:DE:F7:1D:5F:0C:3E:0A:29:48:78:2B:C8:3E:E0:EA:69:9E:F4' && \
    rm -rf /tmp/ca && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    # Locale adjustment. See TW-91776
    echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen && \
    locale-gen en_US.UTF-8 && \
    rm -rf /var/lib/apt/lists/* && \
# Delete the default Ubuntu user if it exists with a conflicting UID. See: https://bugs.launchpad.net/cloud-images/+bug/2005129
    if id -u ubuntu >/dev/null 2>&1 && [ "$(id -u ubuntu)" -eq 1000 ]; then userdel -r -f ubuntu; fi && \
    groupadd -g 1000 buildagent && \
    useradd -m -u 1000 -g 1000 buildagent && \
    # TW-98512: Ensures alignment of the 'docker' user group across releases
    groupadd -g 999 docker

# JDK
ARG jdkLinuxComponent
ARG jdkLinuxComponentMD5SUM

RUN set -eux; \
    curl -LfsSo /tmp/openjdk.tar.gz ${jdkLinuxComponent}; \
    echo "${jdkLinuxComponentMD5SUM} */tmp/openjdk.tar.gz" | md5sum -c -; \
    mkdir -p /opt/java/openjdk; \
    cd /opt/java/openjdk; \
    tar -xf /tmp/openjdk.tar.gz --strip-components=1; \
    chown -R root:root /opt/java; \
    rm -rf /tmp/openjdk.tar.gz;

ENV JAVA_HOME=/opt/java/openjdk \
    JDK_HOME=/opt/java/openjdk \
    PATH="/opt/java/openjdk/bin:$PATH"

RUN update-alternatives --install /usr/bin/java java ${JDK_HOME}/bin/java 1 && \
    update-alternatives --set java ${JDK_HOME}/bin/java && \
    update-alternatives --install /usr/bin/javac javac ${JDK_HOME}/bin/javac 1 && \
    update-alternatives --set javac ${JDK_HOME}/bin/javac

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
