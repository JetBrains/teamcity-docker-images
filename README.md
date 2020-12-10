## [<img src="https://cdn.worldvectorlogo.com/logos/teamcity.svg" height="20" align="center"/>](https://www.jetbrains.com/teamcity/) docker images

[<img src="http://jb.gg/badges/official.svg" height="20"/>](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)

### Minimal agent (jetbrains/teamcity-minimal-agent)

[![jetbrains/teamcity-minimal-agent](https://img.shields.io/docker/pulls/jetbrains/teamcity-minimal-agent.svg)](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent/)

This minimal image adds just a TeamCity agent without any tools like VCS clients, etc. It is suitable for simple builds and can serve as a base for your custom images.

- [Repo](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)
- [Details](context/generated/teamcity-minimal-agent.md)
- [How to use](dockerhub/teamcity-minimal-agent/README.md)

### Agent (jetbrains/teamcity-agent)

[![jetbrains/teamcity-agent](https://img.shields.io/docker/pulls/jetbrains/teamcity-agent.svg)](https://hub.docker.com/r/jetbrains/teamcity-agent/) 

This image adds a TeamCity agent suitable for Java and .NET development.

- [Repo](https://hub.docker.com/r/jetbrains/teamcity-agent)
- [Details](context/generated/teamcity-agent.md)
- [How to use](dockerhub/teamcity-agent/README.md)

### Server (jetbrains/teamcity-server)

[![jetbrains/teamcity-server](https://img.shields.io/docker/pulls/jetbrains/teamcity-server.svg)](https://hub.docker.com/r/jetbrains/teamcity-server/)

- [Repo](https://hub.docker.com/r/jetbrains/teamcity-server)
- [Details](context/generated/teamcity-server.md)
- [How to use](dockerhub/teamcity-server/README.md)

### Build images locally

- Ensure [Docker](https://www.docker.com/get-started) installed.
- Clone this repository.
- Download the required TeamCity [_.tar.gz_ file](https://www.jetbrains.com/teamcity/download/#section=section-get). For instance ```wget -c https://download.jetbrains.com/teamcity/TeamCity-2020.1.tar.gz -O - | tar -xz -C context```
- Unpack this file into the directory [context/TeamCity](context/TeamCity) within the cloned repository.
- Apply required changes in the directory [configs](configs).
- Generate docker and readme files by running the _generate.sh_ or _generate.cmd_ script. All generated artifacts will be placed into the directory [generated](generated).
- To add plugins to TeamCity agents run the _context.sh_ or _context.cmd_ script. This is optional as otherwise the TeamCity agents will load plugins by themselves on first launch.
- Run docker build commands like [on this page](generated/teamcity-agent.md) keeping the proposed order from the root directory of the cloned repository. The proposed order is important because some TeamCity images may be based on other TeamCity images.
- Check the docker images by running ```docker-compose up``` in the directories like [checks/windows-local](checks/windows-local) or [checks/linux-local](checks/linux-local).