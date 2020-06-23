# The list of required arguments
# ARG teamcityAgentImage

# Id teamcity-agent
# Tag ${versionTag}-linux-sudo
# Platform ${linuxPlatform}
# Repo ${repo}
# Weight 1

## ${agentCommentHeader}
## This image allows to do *__sudo__* without a password for the *__builduser__* user. 

# Based on ${teamcityAgentImage}
FROM ${teamcityAgentImage}

USER root

COPY run-docker-sudo.sh /services/run-docker-sudo.sh

RUN apt-get update && \
    apt-get install -y --no-install-recommends sudo && \
    echo 'buildagent ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers && \
    rm -f /services/run-docker.sh && \
    chown -R buildagent:buildagent /services && \
    rm -rf /var/lib/apt/lists/*

USER buildagent