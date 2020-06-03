# Default arguments
ARG teamcityAgentImage='teamcity-agent:18.04'

# The list of required arguments
# ARG teamcityAgentImage




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
