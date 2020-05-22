## TeamCity Minimal Build Agent

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) minimal build agent image.

The [TeamCity build agent](https://confluence.jetbrains.com/display/TCDL/Build+Agent) connects to the TeamCity server and spawns the actual build processes.
You can use the ```jetbrains/teamcity-server``` image to run a TeamCity server.

This minimal image adds just a  TeamCity agent without any tools like VCS clients, etc. It is suitable for simple builds and can serve as a base for your custom images. For Java or .NET development we recommend using the default build agent image [jetbrains/teamcity-agent](https://hub.docker.com/r/jetbrains/teamcity-agent/).

## Image Tags

The Linux image tags have the following suffixes:

* `linux`, `latest` ([ubuntu](https://github.com/JetBrains/teamcity-docker-minimal-agent/blob/master/ubuntu/Dockerfile))

The Windows image tags have the following suffixes:

* `nanoserver-1803`, `latest` ([nanoserver 1803](https://github.com/JetBrains/teamcity-docker-minimal-agent/blob/master/nanoserver/1803/Dockerfile))
* `nanoserver-1809`, `latest` ([nanoserver 1809](https://github.com/JetBrains/teamcity-docker-minimal-agent/blob/master/nanoserver/1809/Dockerfile))

## How to Use This Image

Pull the TeamCity minimal image from the Docker Hub Repository:

```
jetbrains/teamcity-minimal-agent
``` 
&nbsp;
and use the following command to start a container with TeamCity agent running inside 
a Linux container:

```
docker run -it -e SERVER_URL="<url to TeamCity server>"  \ 
    -v <path to agent config folder>:/data/teamcity_agent/conf  \      
    jetbrains/teamcity-minimal-agent
```
&nbsp;
or a Windows container:
```
docker run -it -e SERVER_URL="<url to TeamCity server>" 
    -v <path to agent config folder>:C:/BuildAgent/conf      
    jetbrains/teamcity-minimal-agent
```
&nbsp;
where
**<url to TeamCity server>** is the full URL for TeamCity server, accessible by the agent. Note that "localhost" will not generally not work as it will refer to the "localhost" inside the container.
**<path to agent config folder>** is the host machine directory to serve as the TeamCity agent config directory. We recommend providing this binding in order to persist the agent configuration, e.g. authorization on the server. Note that you should map a different folder for every new agent you create.

You can also provide your agent's name using **-e AGENT_NAME="<agent name>"**. If this variable is omitted, the name for the agent will be generated automatically by the server.

When you run the agent for the first time, you should authorize it via the TeamCity server UI: go to the **Unauthorized Agents** page in your browser. See [more details](https://confluence.jetbrains.com/display/TCDL/Build+Agent).

All information about agent authorization is stored in agent's configuration folder. If you stop the container with the agent and then start a new one with the same config folder, the agent's name and authorization state will be preserved.

TeamCity agent does not need manual upgrade: it will upgrade itself automatically on connecting to an upgraded server.

### Windows Containers Limitations

The details on the known problems in Windows containers are available in the [TeamCity documentation](https://confluence.jetbrains.com/display/TCDL/Known+Issues#KnownIssues-TeamCityWindowsDockerImages).
 
## Customization

You can customize the image via the usual Docker procedure:

1. Run the image
```
docker run -it -e SERVER_URL="<url to TeamCity server>"  \ 
    -v <path to agent config folder>:/data/teamcity_agent/conf  \
    --name="my-customized-agent"  \
    jetbrains/teamcity-minimal-agent  \
```
2. Enter the container 
```
docker exec -it my-customized-agent bash
```

3. Change whatever you need
4. Exit and [create a new image](https://docs.docker.com/engine/reference/commandline/commit/) from the container
```
docker commit my-customized-agent <the registry where you what to store the image>
```

## License

The image is available under the [TeamCity license](https://www.jetbrains.com/teamcity/buy/license.html).
TeamCity is free for perpetual use with the limitation of 100 build configurations (jobs) and 3 agents. [Licensing details](https://confluence.jetbrains.com/display/TCDL/Licensing+Policy).

## Feedback

Report issues of suggestions to the official TeamCity [issue tracker](https://youtrack.jetbrains.com/issues/TW).

## Under The Hood

This image is built on the **TeamCity base image** which includes:

* ubuntu:18.04 (Linux)
* microsoft/nanoserver (Windows)
* Amazon Corretto 8, JRE 64 bit

## Other TeamCity Images
* [TeamCity Server](https://hub.docker.com/r/jetbrains/teamcity-server/)
* [Build Agent](https://hub.docker.com/r/jetbrains/teamcity-agent/)

## Dockerfile source
https://github.com/JetBrains/teamcity-docker-minimal-agent