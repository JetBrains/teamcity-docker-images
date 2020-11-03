# The list of required arguments
# ARG teamcityAgentImage
# ARG dockerComposeLinuxComponentVersion
# ARG dockerLinuxComponentVersion

# Id teamcity-agent
# Tag ${versionTag}-linux${linuxVersion}-sudo
# Platform ${linuxPlatform}
# Repo ${repo}
# Weight 1

## ${agentCommentHeader}
## This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

# Based on ${teamcityAgentImage}
FROM ${teamcityAgentImage}

USER root

# A better fix for TW-52939 Dockerfile build fails because of aufs
#VOLUME /var/lib/docker

COPY run-docker.sh /services/run-docker.sh

ARG dockerComposeLinuxComponentVersion
ARG dockerLinuxComponentVersion

RUN sed -i -e 's/\r$//' /services/run-docker.sh && \
    apt-get update && \
# Install ${dockerLinuxComponentName}
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add - && \
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" && \
    apt-cache policy docker-ce && \
    apt-get update && \
    apt-get install -y  docker-ce=${dockerLinuxComponentVersion}-$(lsb_release -cs) \
                        docker-ce-cli=${dockerLinuxComponentVersion}-$(lsb_release -cs) \
                        containerd.io=1.2.13-2 \
                        systemd && \
    systemctl disable docker && \
# Install [Docker Compose v.${dockerComposeLinuxComponentVersion}](https://github.com/docker/compose/releases/tag/${dockerComposeLinuxComponentVersion})
    curl -SL "https://github.com/docker/compose/releases/download/${dockerComposeLinuxComponentVersion}/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose && \
    apt-get install -y --no-install-recommends sudo && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    echo 'buildagent ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers && \
    chown -R buildagent:buildagent /services && \
    rm -rf /var/lib/apt/lists/* && \
    usermod -aG docker buildagent

USER buildagent
