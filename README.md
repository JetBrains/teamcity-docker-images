## [<img src="https://cdn.worldvectorlogo.com/logos/teamcity.svg" height="20" align="center"/>](https://www.jetbrains.com/teamcity/) docker images

[<img src="http://jb.gg/badges/official.svg" height="20"/>](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)

### Minimal agent (jetbrains/teamcity-minimal-agent)

This minimal image adds just a TeamCity agent without any tools like VCS clients, etc. It is suitable for simple builds and can serve as a base for your custom images.

- [Dockerfile](context/generated/teamcity-minimal-agent.md)
- [Repo](https://hub.docker.com/r/jetbrains/teamcity-minimal-agent)

### Agent (jetbrains/teamcity-agent)

This image adds a TeamCity agent suitable for Java and .NET development.

- [Dockerfile](context/generated/teamcity-agent.md)
- [Repo](https://hub.docker.com/r/jetbrains/teamcity-agent)

### Server (jetbrains/teamcity-server)

- [Dockerfile](context/generated/teamcity-server.md)
- [Repo](https://hub.docker.com/r/jetbrains/teamcity-server)

### Build images locally

- Ensure [Docker](https://www.docker.com/get-started) installed.
- Clone this repository.
- Download the required TeamCity [_.tar.gz_ file](https://www.jetbrains.com/teamcity/download/#section=section-get).
- Unpack this file into the directory _context/TeamCity_ within the cloned repository.
- Run docker build commands like [on this page](context/generated/teamcity-minimal-agent.md) keeping the proposed order from the root directory of the cloned repository. The proposed order is important because some TeamCity images may be based on other TeamCity images.