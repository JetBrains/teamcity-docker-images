# TeamCity Docker Images Delivery Pipeline

[.teamcity](../.teamcity/) holds Kotlin DSL-based build configurations that define delivery process
for TeamCity Docker Images.

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

In order to change the release, please, do the following:

