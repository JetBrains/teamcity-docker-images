# The list of required arguments
# ARG teamcityAgentImage

# Id teamcity-agent
# Tag ${tag}
# Tag linux-sudo
# Platform ${linuxPlatform}
# Repo ${repo}
# Weight 1
# HasManifest false

## ${agentCommentHeader}

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