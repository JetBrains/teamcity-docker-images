# TeamCity Docker Images Delivery Pipeline

[.teamcity](../.teamcity) holds Kotlin DSL-based build configurations that define delivery process
for TeamCity Docker Images.

## Structure

```
.teamcity
├── common          # Common entities (VCS roots, etc.) for delivery build configurations.
├── generated
    └── inactive    # Inactive configurations
    └── production  # Delivery into production registry
    └── staging     # Build of image & delivery into staging registry
├── hosted           
    └── arm         # Build of ARM-based images
    └── scheduled   # Scheduled build of images (e.g. nightly)
    └── utils       # Common DSL utils (shared requirements, dependencies, etc.)
```

## Changes for the creation of new release

To modify the parameters for a specific TeamCity release, kindly follow these steps:
1. Generate Dockerfiles for the images by referring to the ["build your custom docker images"](../README.md) documentation.
2. Configure the build configurations by making modifications to the [DeliveryConfig.kt](../.teamcity/utils/config/DeliveryConfig.kt) file:
* Update the `buildDistDockerDepId` field: This field requires the ID of the build configuration that provides an archive with the ProductProduct created for Docker Images.
* Update the `tcVersion` field: This field represents the current version of TeamCity, such as `EAP`, `2023.05.1`, and so on.
