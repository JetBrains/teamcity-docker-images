#!/bin/bash
bash tool/build-tool.sh
rm -rf "context/generated"
bin/TeamCity.Docker generate -s configs -f "configs/common.config;configs/windows.config;configs/linux.config" -c context/generated -t generated -d .teamcity -b "TC2019_2_BuildDist:2019_2;TC_Trunk_BuildDist" -r PROJECT_EXT_2307