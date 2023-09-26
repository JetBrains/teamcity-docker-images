# The list of required arguments
# ARG teamcityAgentImage

# Id teamcity-agent
# Tag ${versionTag}-linux${linuxVersion}-sudo
# Platform ${linuxPlatform}
# Repo ${repo}
# Weight 1

## ${agentCommentHeader}
## This image allows to do *__sudo__* without a password for the *__buildagent__* user. ## To enable Docker, please add the following arguments: ```--privileged -e DOCKER_IN_DOCKER=start```.

# @AddToolToDoc [${jdkLinuxARM64ComponentName}](${jdkLinuxARM64Component})
# @AddToolToDoc [Python venv](https://docs.python.org/3/library/venv.html#module-venv)
# @AddToolToDoc ${gitLinuxComponentName}
# @AddToolToDoc ${gitLFSLinuxComponentName}
# @AddToolToDoc Mercurial
# @AddToolToDoc ${dockerLinuxComponentName}
# @AddToolToDoc ${containerdIoLinuxComponentName}
# @AddToolToDoc [Docker Compose v.${dockerComposeLinuxComponentVersion}](https://github.com/docker/compose/releases/tag/${dockerComposeLinuxComponentVersion})
# @AddToolToDoc [${dotnetLinuxARM64ComponentName}](${dotnetLinuxARM64Component})
# @AddToolToDoc [${dotnetLinuxARM64ComponentName_70}](${dotnetLinuxARM64Component_70})


# Based on ${teamcityAgentImage}
FROM ${teamcityAgentImage}

USER root

RUN apt-get update && \
    apt-get install -y --no-install-recommends sudo && \
    # https://github.com/goodwithtech/dockle/blob/master/CHECKPOINT.md#dkl-di-0005
    apt-get clean && rm -rf /var/lib/apt/lists/* && \
    echo 'buildagent ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers && \  
    rm -rf /var/lib/apt/lists/*

USER buildagent
