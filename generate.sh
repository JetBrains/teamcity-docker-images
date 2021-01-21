#!/bin/bash
bash tool/build-tool.sh
rm -rf "generated"
rm -rf "context/generated"
bin/TeamCity.Docker generate -s configs -f "configs/common.config;configs/local.config;configs/windows.config;configs/linux.config" -c context -t generated
bin/TeamCity.Docker generate -s configs -f "configs/common.config;configs/version.config;configs/internal.config;configs/windows.config;configs/linux.config" -c context -t context/generated -d .teamcity/generated -r "PROJECT_EXT_4022" -b TC2020_2_BuildDistDocker