# TeamCity Docker Images - tools
Tools dedicated to simplification and enhancements of [TeamCity](https://www.jetbrains.com/teamcity/) Docker Images release process.

# 1. Dockerfiles & Build Configurations generator
[TeamCity.Docker](TeamCity.Docker) is a utility application (C#) that generates Dockerfiles along with Kotlin DSL for build 
configurations aimed at creating and publishing TeamCity Docker Images for different platforms.

## 1.1 Configuration options
The program accepts verbs (main options), as well as their configuration (sub-options).

- `generate` - generate docker and readme files.
- `build` - Build docker images for session.

### 1.1.1 Generate Dockerfiles
`generate` verb specified the necessity to generate Dockerfile. It has the following options:

| Option     | short flag | Required | Description                                             |
|------------|------------|----------|---------------------------------------------------------|
| `target`   | `-t`       | true     | Path to directory for generating docker files.          |
| `source`   | `-s`       | false    | Path to configuration directory.                        |
| `context`  | `-c`       | false    | Path to the context directory.                          |
| `files`    | `-f`       | false    | Semicolon separated configuration file. Separator - `;` |
| `dsl`      | `-d`       | false    | Path to directory for teamcity DSL script settings.kts. |
| `build`    | `-b`       | false    | TeamCity build configuration id.                        |
| `registry` | `-r`       | false    | TeamCity docker registry id.                            |
| `verbose`  | `-v`       | false    | Add it for detailed output."                            |
 
Example:
```
generate -s configs -f "configs/common.config;configs/local.config;configs/windows.config;configs/linux.config;configs/linuxARM.config" -c context -t generated
``` 

### 1.1.2 Build Docker images
`build` verb specifies the necessity to build Docker images for session. It has the following options:

| Option    | short flag | Required | Description                                                                                                                                          |
|-----------|------------|----------|------------------------------------------------------------------------------------------------------------------------------------------------------|
| `source`  | `-s`       | false    | Path to configuration directory.                                                                                                                     |
| `files`   | `-f`       | false    | Semicolon separated configuration file. Separator - `;`                                                                                              |
| `context` | `-c`       | false    | Path to the context directory.                                                                                                                       |
| `docker`  | `-d`       | false    | The Docker Engine endpoint like tcp://localhost:2375 (defaults: npipe://./pipe/docker_engine fo windows and unix:///var/run/docker.sock for others). |
| `verbose` | `-v`       | false    | Specified necessity for verbose (detailed) output.                                                                                                   |
| `regex`   | `-r`       | false    | Regular expression for filtering an internal build graph. Used for development purposes mostly.                                                      |

# 2. Automation utilities
[Automation utilities](automation/framework) contains utilities that simplify release process. Please, follow the link for the detailed documentation.
