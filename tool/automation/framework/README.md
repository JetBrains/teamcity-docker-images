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


### 2.1 TeamCity statistics
Image size is reported into TeamCity via Service Message using the following pattern: `"SIZE-$currentName"`.

### 2.2 A note on automation's dependencies / assumptions

* **Version pattern**. TeamCity Releases following the name pattern: `<year>.<month number>-<platform / OS>`. If previous image
wasn't explicitly specified, the application would try to determine it automatically. Please, note that automatic determination works
with minor releases only, e.g. `2022.04.2` -> `2022.04.1`.

