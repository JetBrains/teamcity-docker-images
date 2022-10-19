# Configuration options

The program accepts verbs (main options), as well as their configuration (sub-options).

`generate` Generate docker and readme files.

`build` - Build docker images for session.

## Generate Dockerfiles

`generate` verb specified the necessity to generate Dockerfile. It has the following options:

| Option | short flag | Required | Description |
| --- | --- | --- | ------ |
| `target` | `-t` | true | Path to directory for generating docker files. |
| `source` | `-s` | false | Path to configuration directory. |
| `context` | `-c` | false | Path to the context directory. |
| `files` | `-f` | false | Semicolon separated configuration file. Seoarator - `;` |
| `dsl` | `-d` | false | Path to directory for teamcity DSL script settings.kts. |
| `build` | `-b` | false | TeamCity build configuration id. |
| `registry` | `-r` | false | TeamCity docker registry id. |
| `verbose` | `-v` | false | Add it for detailed output." |
 
Example:
```
generate -s configs -f "configs/common.config;configs/local.config;configs/windows.config;configs/linux.config;configs/linuxARM.config" -c context -t generated
``` 

## Build Docker images
`build` verb specifies the necessity to build Docker images for session. It has the following options:
| Option | short flag | Required | Description |
| `source` | `-s` | false | Path to configuration directory. |
| `files` | `-f` | false | Semicolon separated configuration file. Seoarator - `;` |
| `context` | `-c` | false | Path to the context directory. |
| `docker` | `-d` | false | The Docker Engine endpoint like tcp://localhost:2375 (defaults: npipe://./pipe/docker_engine fo windows and unix:///var/run/docker.sock for others). |
| `verbose` | `-v` | false | Specified neccessity for verbose (detailed) output. |
| `regex` | `-r` | false | Regular expression for filtering an internal build graph. Used for development purposes mostly. |
