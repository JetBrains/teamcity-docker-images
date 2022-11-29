# Default arguments
ARG teamcityAgentImage='teamcity-agent:2022.10.1-linux'

# The list of required arguments
# ARG teamcityAgentImage



FROM ${teamcityAgentImage}

USER root

RUN apt-get update && \
    apt-get install -y --no-install-recommends sudo && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    echo 'buildagent ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers && \  
    rm -rf /var/lib/apt/lists/*

USER buildagent
