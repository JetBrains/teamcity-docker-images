#!/bin/bash
bash tool/build-tool.sh
rm -rf "context/generated"
bin/TeamCity.Docker generate -s configs -f "configs/common.config;configs/version.config;configs/internal.config;configs/windows.config;configs/linux.config;configs/linuxARM.config" -c context -t context/generated -d .teamcity/generated -r "PROJECT_EXT_774" -b TC_Trunk_BuildDistDocker
