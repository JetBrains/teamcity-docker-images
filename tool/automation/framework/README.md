# Automation Utilities

## 1. Purpose
Automation Framework that simplifies release process of Docker Images.

## 2. Build
The application could be built using Gradle Wrapper.
```
./gradlew clean build
```


## 3. Execution

```
./gradlew <option> --args="<parameters>"
```
Example:
```
./gradlew clean build run --args="validate jetbrains/teamcity-agent:2022.10.2-windowsservercore-2004"
...
##teamcity[buildStatisticValue key='SIZE-teamcity-agent:windowsservercore-2004' value='5066342155']
jetbrains/teamcity-agent:2022.10.2-windowsservercore-2004-windows-10.0.19041.1415-amd64: 
	 - Original size: 5066342155 (jetbrains/teamcity-agent:2022.10.2-windowsservercore-2004)
	 - Previous size: 5067986140 (2022.10.1-windowsservercore-2004)
	 - Percentage change: 0.03% (max allowable - 5.0%)
```

### 3.1 Available Options

| Action           | Parameter                                                                                                                                  | Description                                                                           |
|------------------|--------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| `validate`       | - [`String`] `original Docker image` <br/> - [`String`] `(optional) Dockerhub username` <br/> - [`String`] `(optional) Dockerhub password` | Performs the actions of validating Docker images.                                     |
| `get-size-trend` | - [`String`] `Docker image`     <br/> - [`String`] `(optional) Dockerhub username` <br/> - [`String`] `(optional) Dockerhub password`      | Print out the trend (CSV) for the size of specified Docker Image using Dockerhub API. |

### 3.2 A note on Dockerhub's authentication
Dockerhub REST API allows to access private repositories using [JSON Web Token (JWT)](https://docs.docker.com/registry/spec/auth/jwt/).
Inside of code, the provided credentials - username and persistent access-token - are converted into JWT, which will be used throughout
the session.


### 3.3 TeamCity statistics
Image size is reported into TeamCity via Service Message using the following pattern: `"SIZE-$currentName"`.
Please, note that each repository-tag pair might have multiple associated images, whose differences may include:
* Operating System;
* Version of Operating System;
* Architecture;
Each associated image is being validated within the framework.

### 3.4 A note on automation's dependencies / assumptions

* **Version pattern**. TeamCity Releases following the name pattern: `<year>.<month number>-<platform / OS>`. If previous image
wasn't explicitly specified, the application would try to determine it automatically. Please, note that automatic determination works
with minor releases only, e.g. `2022.04.2` -> `2022.04.1`.

