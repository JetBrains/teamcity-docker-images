## TeamCity Server - Powerful Continuous Integration and Continuous Delivery out of the box

[<img src="http://jb.gg/badges/official.svg" height="20"/>](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)

This is an official [JetBrains TeamCity](https://www.jetbrains.com/teamcity/) server image. The image is suitable for production use and evaluation purposes.

<img src="https://raw.githubusercontent.com/JetBrains/teamcity-docker-images/master/logo/GitHub.png" height="20" align="center"/> More details about tags and components are [here](https://github.com/JetBrains/teamcity-docker-images/blob/master/context/generated/teamcity-server.md).

## How to Use This Image

First, pull the image from the Docker Hub Repository

```docker pull jetbrains/teamcity-server```

 
### Linux container

Use the following command to start a container with TeamCity server



```
docker run --name teamcity-server-instance  \
    -v <path-to-data-directory>:/data/teamcity_server/datadir \
    -v <path-to-logs-directory>:/opt/teamcity/logs  \
    -p <port-on-host>:8111 \
    jetbrains/teamcity-server
```  
where

 - **\<path-to-data-directory>** is the host machine directory to serve as the [TeamCity Data Directory](https://www.jetbrains.com/help/teamcity/teamcity-data-directory.html) where TeamCity stores project settings and build results. Pass an empty directory for the brand new start. If the mapping is not set, you will lose all the TeamCity settings on the container shutdown.
 - **\<path-to-logs-directory>** is the host machine directory to store the TeamCity server logs. The mapping can be omitted, but then the logs will be lost on container shutdown which will make issues investigation impossible.

Due to security reasons, by default, the container is launched under `user 1000`.
If you need root permissions (`user 0`), a corresponding configuration key could be passed to Docker - `docker run ... --user 0 ... jetbrains/teamcity-server`.

Please, note that the running of Docker Containers under `root` user impose potential security vulnerabilities, including privilege escalation, thus a strong security assessment of the environment 
is recommended prior to the start-up.


#### TeamCity behind HTTPS reverse proxy

If TeamCity acts as an endpoint for a reverse proxy server like Nginx or Apache, 
it should be configured to provide secure cookies to end users.

To achieve that, you can pass an additional 
`-e TEAMCITY_HTTPS_PROXY_ENABLED=true` parameter to the `docker run` command. With this parameter, TeamCity will be 
started with an alternative `server-https-proxy.xml` configuration file which enables HTTPS options.

Alternatively, you can use a custom Tomcat configuration (see below).


#### Configuring HTTPS Access to TeamCity Server

TeamCity Server could be configured to use HTTPS connection, thus port `443` would be used for encrypted traffic. 

For security reasons, some operating systems impose restrictions
on binding "privileged" ports (typically, ports below 1024) for non-root users, such as user 1000.

Given that TeamCity Containers, by default, are launched under _user 1000_, in order to configure HTTPS connection, there are 2 options:
1. **(recommended) Map non-privileged port on host machine to default HTTPS port inside the container**. As a result, TeamCity will be accessible via HTTPS without running the server under the root user (which is otherwise required for accessing the privileged port `443`).
```
docker run --name teamcity-server-instance  \
    ...
    -p 443:8443
    ...
    jetbrains/teamcity-server
```
2. **Launch TeamCity Container under a _root_ user**. Please, note that it's not considered a good practice, thus significant security assessment must be done within target environment.
```
docker run --name teamcity-server-instance  \
    ...
    --user 0
    ...
    jetbrains/teamcity-server
```


#### Alternative Tomcat configuration

TeamCity has Tomcat J2EE server under the hood, and if you need to provide an alternative configuration for the TomCat, you can use extra parameter
```
-v /alternative/path/to/conf:/opt/teamcity/conf 
```

To get a sample of the current contents of the Tomcat's `conf` directory, use the [`docker cp`](https://docs.docker.com/engine/reference/commandline/cp/) command.

### Windows container

```
docker run --name teamcity-server-instance
    -v <path-to-data-directory>:C:/ProgramData/JetBrains/TeamCity
    -v <path-to-logs-directory>:C:/TeamCity/logs
    -v <path-to-temp-directory>:C:/TeamCity/temp
    -p <port-on-host>:8111
    jetbrains/teamcity-server
```  

See the **\<path-to-data-directory>** and **\<path-to-logs-directory>** descriptions above; **\<path-to-temp-directory>** is the directory for temporary files.

We also suggest allocating a sufficient amount of resources to the Docker process, like in this example:

```
docker run --memory="6g" --cpus=4 -e TEAMCITY_SERVER_MEM_OPTS="-Xmx3g -XX:MaxPermSize=270m -XX:ReservedCodeCacheSize=640m" --name teamcity-server-instance
    -v <path-to-data-directory>:C:/ProgramData/JetBrains/TeamCity
    -v <path-to-logs-directory>:C:/TeamCity/logs
    -v <path-to-temp-directory>:C:/TeamCity/temp
    -p <port-on-host>:8111
    jetbrains/teamcity-server
```

The details on the known problems in Windows containers are available in the [TeamCity documentation](https://www.jetbrains.com/help/teamcity/known-issues.html#KnownIssues-WindowsDockerContainers).

### Database

TeamCity stores set of users and build results in an SQL database in addition to the Data Directory.
By default, the TeamCity server uses an internal database stored on the file system under the data directory. However, production use requires an [external database](https://www.jetbrains.com/help/teamcity/set-up-external-database.html).

To use the server for production, make sure to review and apply the [recommendations](https://www.jetbrains.com/help/teamcity/installing-and-configuring-the-teamcity-server.html#InstallingandConfiguringtheTeamCityServer-ConfiguringServerforProductionUse).

### Build agents

You will need at least one TeamCity agent to run builds. Check the [`jetbrains/teamcity-agent`](https://hub.docker.com/r/jetbrains/teamcity-agent/) and [`jetbrains/teamcity-minimal-agent`](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent/) images.

To learn how you can start the TeamCity server together with agents in one go, see these [Docker Compose samples](https://github.com/JetBrains/teamcity-docker-samples).

## Additional Commands

When you need to pass additional environment variables to the server process, use the regular `-e` option. For example, to pass TEAMCITY_SERVER_MEM_OPTS environment variable, use:

```
docker run --name teamcity-server-instance   \
       -e TEAMCITY_SERVER_MEM_OPTS="-Xmx2g -XX:MaxPermSize=270m -XX:ReservedCodeCacheSize=640m" \
       -v <path-to-data-directory>:/data/teamcity_server/datadir  \
       -v <path-to-log-directory>:/opt/teamcity/logs   \
       -p <port-on-host>:8111 \
       jetbrains/teamcity-server
```  
&nbsp;
To run the `maintainDB` script (e.g. for the server backup), stop your running container and execute the following command from your host:  
```
docker run -it --name teamcity-server-instance  \
    -v <path-to-data-directory>:/data/teamcity_server/datadir  \
    -v <path-to-log-directory>:/opt/teamcity/logs  \
    -p <port-on-host>:8111 \
    jetbrains/teamcity-server \
    "/opt/teamcity/bin/maintainDB.sh" "backup"
```  
&nbsp;

Be sure to keep all the local system paths the same with the main server start command.

To change the context of the TeamCity app inside a Tomcat container, pass `-e TEAMCITY_CONTEXT=/context` to the `docker run` command. The default one is `ROOT`, meaning that the server would be available at `http://host/`.

## Upgrading TeamCity

Make sure to check the generic TeamCity [upgrade instructions](https://www.jetbrains.com/help/teamcity/upgrade.html).
If you made no changes to the container, you can just stop the running container, pull a newer version of the image and the server in it via the usual command.
If you changed the image, you will need to replicate the changes to the new TeamCity server image. In general, use Docker common sense to perform the upgrade.

## License

The image is available under the [TeamCity license](https://www.jetbrains.com/teamcity/buy/license.html).
TeamCity is free for perpetual use with the limitation of 100 build configurations (jobs) and 3 agents. [Licensing details](https://www.jetbrains.com/help/teamcity/licensing-policy.html).

## Feedback

Report issues of suggestions to the official TeamCity [issue tracker](https://youtrack.jetbrains.com/issues/TW).

## Other TeamCity Images
* [Minimal Build Agent](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent/)
* [Build Agent](https://hub.docker.com/r/jetbrains/teamcity-agent/)
