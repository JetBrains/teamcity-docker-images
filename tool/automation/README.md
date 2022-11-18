# Automation Utilities

## 1. Purpose
Utilities that simplify automation of releasing TeamCity docker images.


## 2. Image Validation
[ImageValidation.kts](ImageValidation.kts) - responsible for validation of Docker images.
**Usage**:
```
kotlinc -script ImageValidation.kts \
    <docker image fully-qualified domain name> \
    <(optional) previous docker image fully-qualified domain name>
```

### 2.1 TeamCity statistics
Image size is reported into TeamCity via Service Message using the following pattern: `"SIZE-$currentName"`.


### 2.2 A note on automation's dependencies / assumptions
Automation tools has the following assumptions that might be changed over the time / releases:
* Automation path. [TeamCityKotlinSettingsGenerator.cs](../TeamCity.Docker/TeamCityKotlinSettingsGenerator.cs)
```
path = \"tool/automation/ImageValidation.kts\
```

* Version pattern. If not explicitly specified, [ImageValidation.kts](ImageValidation.kts) is trying to determine previous
* Docker image by the following parent: `<year>.<buld number>-<OS>`. If that would be no longer applicable, please, consider making a related change. 


# Updated usage (TODO: Refactor)
```
validate jetbrains/teamcity-server:2022.04.3 <(optionally> etbrains/teamcity-server:2022.04.2)
```