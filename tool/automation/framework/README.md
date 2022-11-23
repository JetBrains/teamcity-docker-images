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

### 3.1 Available Options
| Action     | Parameter                                                                                  | Description                                       |
|------------|--------------------------------------------------------------------------------------------|---------------------------------------------------|
| `validate` | - [`String`] `original Docker image` <br/> - [`String`] `(optional) previous Docker image` | Performs the actions of validating Docker images. |


### 3.2 A note on validation - TeamCity statistics
The process of validating the size of new TeamCity Docker images is automated via custom failure condition -
[BuildFailureOnMetric : FailureCondition](https://buildserver.labs.intellij.net/app/dsl-documentation/-team-city%20-kotlin%20-d-s-l/jetbrains.buildServer.configs.kotlin.failureConditions/-build-failure-on-metric/index.html).
The compressed size of an image (non OS-/drive-dependant) is being reported to TeamCity via custom service message (see: [Reporting Build Statistics](https://www.jetbrains.com/help/teamcity/service-messages.html#Reporting+Build+Statistics)):
```
##teamcity[buildStatisticValue key='SIZE-teamcity-agent:windowsservercore-1809' value='6092317594']
```
Therefore, in case the pattern of such message is changed, make sure to include the appropriate changes into the DSL's failure condition as well.

### 3.3 A note on automation's dependencies / assumptions
* **Version pattern**. TeamCity Releases following the name pattern: `<year>.<month number>-<platform / OS>`. If previous image
wasn't explicitly specified, the application would try to determine it automatically. Please, note that automatic determination works
with minor releases only, e.g. `2022.04.2` -> `2022.04.1`.

