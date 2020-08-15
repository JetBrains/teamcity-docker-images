## [<img src="https://cdn.worldvectorlogo.com/logos/teamcity.svg" height="20" align="center"/>](https://www.jetbrains.com/teamcity/) docker images

[<img src="http://jb.gg/badges/official.svg" height="20"/>](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)

### Minimal agent (jetbrains/teamcity-minimal-agent)

[![jetbrains/teamcity-minimal-agent](https://img.shields.io/docker/pulls/jetbrains/teamcity-minimal-agent.svg)](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent/)

This minimal image adds just a TeamCity agent without any tools like VCS clients, etc. It is suitable for simple builds and can serve as a base for your custom images.

- [Repo](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)
- [Details](generated/teamcity-minimal-agent.md)
- [How to use](dockerhub/teamcity-minimal-agent/README.md)

### Agent (jetbrains/teamcity-agent)

[![jetbrains/teamcity-agent](https://img.shields.io/docker/pulls/jetbrains/teamcity-agent.svg)](https://hub.docker.com/r/jetbrains/teamcity-agent/) 

This image adds a TeamCity agent suitable for Java and .NET development.

- [Repo](https://hub.docker.com/r/jetbrains/teamcity-agent)
- [Details](generated/teamcity-agent.md)
- [How to use](dockerhub/teamcity-agent/README.md)

### Server (jetbrains/teamcity-server)

[![jetbrains/teamcity-server](https://img.shields.io/docker/pulls/jetbrains/teamcity-server.svg)](https://hub.docker.com/r/jetbrains/teamcity-server/)

- [Repo](https://hub.docker.com/r/jetbrains/teamcity-server)
- [Details](generated/teamcity-server.md)
- [How to use](dockerhub/teamcity-server/README.md)

### Build images locally

- Ensure [Docker](https://www.docker.com/get-started) installed.
- Clone this repository.
- Download the required TeamCity [_.tar.gz_ file](https://www.jetbrains.com/teamcity/download/#section=section-get). For instance ```wget -c https://download.jetbrains.com/teamcity/TeamCity-2020.1.tar.gz -O - | tar -xz -C context```
- Unpack this file into the directory _context/TeamCity_ within the cloned repository.
- Run docker build commands like [on this page](generated/teamcity-minimal-agent.md) keeping the proposed order from the root directory of the cloned repository. The proposed order is important because some TeamCity images may be based on other TeamCity images.

### Contribution

- Ensure [Docker](https://www.docker.com/get-started) installed.
- Fork this repository.
- Download the required TeamCity [_.tar.gz_ file](https://www.jetbrains.com/teamcity/download/#section=section-get). For instance ```wget -c https://download.jetbrains.com/teamcity/TeamCity-2020.1.tar.gz -O - | tar -xz -C context```
- Unpack this file into the directory _context/TeamCity_ within the cloned repository.
- Apply required changes in the directory _configs_.
- Check the docker build by running the _build.sh_ or _build.cmd_ script.
- Generate docker and readme files by running the _generate.sh_ or _generate.cmd_ script. All generated artifacts will be placed into the directory _context/generated_.
- Create a pull request.
