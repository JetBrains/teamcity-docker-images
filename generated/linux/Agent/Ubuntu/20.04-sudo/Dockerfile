# Default arguments
ARG dockerComposeLinuxComponentVersion='1.24.1'
ARG dockerLinuxComponentVersion='5:19.03.13~3-0~ubuntu'
ARG repo='https://hub.docker.com/r/jetbrains/'
ARG teamcityAgentImage='teamcity-agent:local-linux'

# The list of required arguments
# ARG teamcityAgentImage
# ARG dockerComposeLinuxComponentVersion
# ARG dockerLinuxComponentVersion



FROM ${teamcityAgentImage}

USER root

COPY run-docker.sh /services/run-docker.sh

ARG dockerComposeLinuxComponentVersion
ARG dockerLinuxComponentVersion

RUN sed -i -e 's/\r$//' /services/run-docker.sh && \
    apt-get update && \
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add - && \
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" && \
    apt-cache policy docker-ce && \
    apt-get update && \
    apt-get install -y  docker-ce=${dockerLinuxComponentVersion}-$(lsb_release -cs) \
                        docker-ce-cli=${dockerLinuxComponentVersion}-$(lsb_release -cs) \
                        containerd.io=1.2.13-2 \
                        systemd && \
    systemctl disable docker && \
    curl -SL "https://github.com/docker/compose/releases/download/${dockerComposeLinuxComponentVersion}/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose && \
    apt-get install -y --no-install-recommends sudo && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    echo 'buildagent ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers && \
    chown -R buildagent:buildagent /services && \
    rm -rf /var/lib/apt/lists/* && \
    usermod -aG docker buildagent

USER buildagent
