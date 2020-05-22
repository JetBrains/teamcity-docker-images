## TeamCity Build Agent

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) build agent image.

The [TeamCity build agent](https://confluence.jetbrains.com/display/TCDL/Build+Agent) connects to the TeamCity server and spawns the actual build processes.
You can use the ```jetbrains/teamcity-server``` image to run a TeamCity server.

This image adds a TeamCity agent suitable for Java development. It is based on ```jetbrains/teamcity-minimal-agent``` but gives you more benefits, e.g. 

* client-side checkout if you use 'git' or 'mercurial'
* more bundled build tools
* 'docker-in-docker' on Linux

## Image Tags

The Linux image tags have the following suffixes:

* `linux`, `latest` ([ubuntu](https://github.com/JetBrains/teamcity-docker-agent/blob/master/ubuntu/Dockerfile))

The Windows image tags have the following suffixes:

* `nanoserver-1803`, `latest` ([nanoserver 1803](https://github.com/JetBrains/teamcity-docker-agent/blob/master/nanoserver/1803/Dockerfile))
* `nanoserver-1809`, `latest` ([nanoserver 1809](https://github.com/JetBrains/teamcity-docker-agent/blob/master/nanoserver/1809/Dockerfile))
* `windowsservercore-1803`, `windowsservercore` ([windowsservercore 1803](https://github.com/JetBrains/teamcity-docker-agent/blob/master/windowsservercore/1803/Dockerfile))
* `windowsservercore-1809`, `windowsservercore` ([windowsservercore 1809](https://github.com/JetBrains/teamcity-docker-agent/blob/master/windowsservercore/1809/Dockerfile))

## How to Use This Image

Pull the TeamCity image from the Docker Hub Repository: 
```
jetbrains/teamcity-agent
```  
&nbsp;
and use the following command to start a container with TeamCity agent running inside 

a Linux container:

```
docker run -it -e SERVER_URL="<url to TeamCity server>"  \ 
    -v <path to agent config folder>:/data/teamcity_agent/conf  \      
    jetbrains/teamcity-agent
```
&nbsp;
or a Windows container:
```
docker run -it -e SERVER_URL="<url to TeamCity server>"
    -v <path to agent config folder>:C:/BuildAgent/conf
    jetbrains/teamcity-agent
```
&nbsp;
where 
**<url to TeamCity server>** is the full URL for TeamCity server, accessible by the agent. Note that "localhost" will not generally not work as it will refer to the "localhost" inside the container.
**<path to agent config folder>** is the host machine directory to serve as the TeamCity agent config directory. We recommend providing this binding in order to persist the agent configuration, e.g. authorization on the server. Note that you should map a different folder for every new agent you create.

You can also provide your agent's name using **-e AGENT_NAME="<agent name>"**. If this variable is omitted, the name for the agent will be generated automatically by the server.

When you run the agent for the first time, you should authorize it via the TeamCity server UI: go to the **Unauthorized Agents** page in your browser. See [more details](https://confluence.jetbrains.com/display/TCDL/Build+Agent).
All information about agent authorization is stored in the agent's configuration folder. If you stop the container with the agent and then start a new one with the same config folder, the agent's name and  authorization state will be preserved.

A TeamCity agent does not need manual upgrade: it will upgrade itself automatically on connecting to an upgraded server.

### Preserving Checkout Directories Between Builds

When build agent container is restarted, it re-checkouts sources for the builds. 

To avoid this, you should pass a couple of additional options to preserve build agent state between restarts: 

  1. Preserve checked out sources (`-v /opt/buildagent/work:/opt/buildagent/work`)
  1. Keep internal build agent caches (`-v /opt/buildagent/system:/opt/buildagent/system`)
  
You can use other than `/opt/buildagent/` source path prefix on the host machine unless you're going to use Docker Wrapper via `docker.sock` (see below). 

### Running Builds Which Require Docker

In a Linux container, if you need a Docker daemon available inside your builds, you have two options:

1) Docker from the host (in this case you will benefit from the caches shared between the host and all your containers but there is a security concern: your build may actually harm your host Docker, so use it at your own risk) 

```
docker run -it -e SERVER_URL="<url to TeamCity server>"  \
    -v <path to agent config folder>:/data/teamcity_agent/conf \
    -v /var/run/docker.sock:/var/run/docker.sock  \
    -v /opt/buildagent/work:/opt/buildagent/work \
    -v /opt/buildagent/temp:/opt/buildagent/temp \
    -v /opt/buildagent/tools:/opt/buildagent/tools \
    -v /opt/buildagent/plugins:/opt/buildagent/plugins \
    -v /opt/buildagent/system:/opt/buildagent/system \
    jetbrains/teamcity-agent 
```

Volume options starting with `-v /opt/buildagent/` are required if you want to use [Docker Wrapper](https://confluence.jetbrains.com/display/TCDL/Docker+Wrapper) on this build agent. 
Without them, the corresponding builds with the enabled docker wrapper (for Command Line, Maven, Ant, Gradle, and since TeamCity 2018.1, .NET CLI (dotnet) and PowerShell runners) will not work. Unfortunately, using several docker-based build agents from the same host is not possible.

If you omit these options, you can run several build agents (but you need to specify different `<path to agent config folder>` for them), but [Docker Wrapper](https://confluence.jetbrains.com/display/TCDL/Docker+Wrapper) won't work on such agents. 

The problem is, that multiple agent containers would use the same (/opt/buildagent/\*) directories as they are mounted from the host machine to the agent container and that the docker wrapper mounts the directories from the host to the nested docker wrapper container. And, you cannot use multiple agent containers with *different paths* on the host as the docker wrapper would still try to map the paths as they are in the agent container, but from the host machine to the nested docker wrapper container. To make several agents work with docker wrapper and docker.sock option, one have to build different teamcity-agent docker images with different paths of teamcity-agent installation inside those images (like `/opt/buildagentN`), and start those images with corresponding parameters like `-v /opt/buildagent1/work:/opt/buildagent1/work` etc.


2) New Docker daemon running within your container  (note that in this case the container should be run with **—privileged** flag)
```
docker run -it -e SERVER_URL="<url to TeamCity server>"  \
    -v <path to agent config folder>:/data/teamcity_agent/conf \
    -v docker_volumes:/var/lib/docker \
    --privileged -e DOCKER_IN_DOCKER=start \    
    jetbrains/teamcity-agent 
```

The option `-v docker_volumes:/var/lib/docker` is related to the case when the `aufs` filesystem is used and when a build agent is started from a Windows machine ([related issue](https://youtrack.jetbrains.com/issue/TW-52939)).
If you want to start several build agents, you need to specify different volumes for them, like `-v agent1_volumes:/var/lib/docker`, `-v agent2_volumes:/var/lib/docker`.

### Windows Containers Limitations

The details on the known problems in Windows containers are available on the [in the TeamCity documentation](https://confluence.jetbrains.com/display/TCDL/Known+Issues#KnownIssues-TeamCityWindowsDockerImages).
 
## Customization

You can customize the image via the usual Docker procedure:

1. Run the image
```
docker run -it -e SERVER_URL="<url to TeamCity server>"  \ 
    -v <path to agent config folder>:/data/teamcity_agent/conf  \
    --name="my-customized-agent"  \
    jetbrains/teamcity-agent-minimal  \
```
2. Enter the container
```
docker exec -it my-customized-agent bash
```
3. Change whatever you need

4. Exit and [create a new image](https://docs.docker.com/engine/reference/commandline/commit/) from the container:
```
docker commit my-customized-agent <the registry where you what to store the image>
```

## License

The image is available under the [TeamCity license](https://www.jetbrains.com/teamcity/buy/license.html).
TeamCity is free for perpetual use with the limitation of 100 build configurations (jobs) and 3 agents. [Licensing details](https://confluence.jetbrains.com/display/TCDL/Licensing+Policy).

## Feedback

Report issues of suggestions to the official TeamCity [issue tracker](https://youtrack.jetbrains.com/issues/TW).

## Under The Hood

This image includes:

* ubuntu:18.04 (Linux)
* microsoft/windowsservercore or microsoft/nanoserver (Windows)
* Amazon Corretto 8, JDK 64 bit
* git 
* mercurial ([except nanoserver images](https://bitbucket.org/tortoisehg/thg/issues/5136/provide-tortoisehg-builds-for-windows-as))
* .NET Core SDK
* MSBuild Tools (windowsservercore-based images)
* docker-engine (Linux)

## Other TeamCity Images
* [TeamCity Server](https://hub.docker.com/r/jetbrains/teamcity-server/)
* [Minimal Build Agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent/)

## Dockerfile source
https://github.com/JetBrains/teamcity-docker-agent