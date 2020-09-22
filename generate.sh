#!/bin/bash
bash tool/build-tool.sh
rm -rf "generated"
rm -rf "context/generated"
bin/TeamCity.Docker generate -s configs -f "configs/common.config;configs/windows.config;configs/linux.config" -c context -t generated
bin/TeamCity.Docker generate -s configs -f "configs/common.config;configs/internal.config;configs/windows.config;configs/linux.config" -c context -t context/generated -d .teamcity -r "PROJECT_EXT_315,PROJECT_EXT_4003,PROJECT_EXT_4022" -b TC_Trunk_BuildDistDocker