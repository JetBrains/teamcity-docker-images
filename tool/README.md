# TeamCity Docker Images - tools

# 1. Dockerfiles & Build Configurations generator
[TeamCity.Docker](TeamCity.Docker) is an utility application (C#) that generates Dockerfiles along with build configurations for
build up and publishing of TeamCity Docker images for different platforms.

## 1.1 Configuration options

The program accepts verbs (main options), as well as their configuration (sub-options).

`generate` Generate docker and readme files.

`build` - Build docker images for session.

### 1.1.1 Generate Dockerfiles

`generate` verb specified the necessity to generate Dockerfile. It has the following options:

| Option | short flag | Required | Description |
| --- | --- | --- | ------ |
| `target` | `-t` | true | Path to directory for generating docker files. |
| `source` | `-s` | false | Path to configuration directory. |
| `context` | `-c` | false | Path to the context directory. |
| `files` | `-f` | false | Semicolon separated configuration file. Separator - `;` |
| `dsl` | `-d` | false | Path to directory for teamcity DSL script settings.kts. |
| `build` | `-b` | false | TeamCity build configuration id. |
| `registry` | `-r` | false | TeamCity docker registry id. |
| `verbose` | `-v` | false | Add it for detailed output." |
 
Example:
```
generate -s configs -f "configs/common.config;configs/local.config;configs/windows.config;configs/linux.config;configs/linuxARM.config" -c context -t generated
``` 

### 1.1.2 Build Docker images
`build` verb specifies the necessity to build Docker images for session. It has the following options:

| Option | short flag | Required | Description |
| --- | --- | --- | ------ |
| `source` | `-s` | false | Path to configuration directory. |
| `files` | `-f` | false | Semicolon separated configuration file. Separator - `;` |
| `context` | `-c` | false | Path to the context directory. |
| `docker` | `-d` | false | The Docker Engine endpoint like tcp://localhost:2375 (defaults: npipe://./pipe/docker_engine fo windows and unix:///var/run/docker.sock for others). |
| `verbose` | `-v` | false | Specified neccessity for verbose (detailed) output. |
| `regex` | `-r` | false | Regular expression for filtering an internal build graph. Used for development purposes mostly. |

# 2. Automation utilities

[Automation utiltiies](automation) contains utilities that simplify automation of releasing TeamCity docker images.

## 2.1 Docker Image Validation

[ImageValidation.kts](automation/ImageValidation.kts) validates the state of Docker image.
```
kotlinc -script ImageValidation.kts <image name>
```

### 2.1.1 TeamCity statistics

Image size is reported via `"SIZE-$currentName"`

### 2.1.2 Dependencies
* TeamCity Kotlin DSL script with file name
* Pattern (Todo: add link to code where the pattern for image name is deterined)