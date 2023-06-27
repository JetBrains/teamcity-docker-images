# TeamCity Docker Images Delivery Pipeline

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
