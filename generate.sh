#!/bin/bash
bash tool/build-tool.sh
rm -rf "generated"
rm -rf "context/generated"
bin/TeamCity.Docker generate -s configs -f "configs/common.config;configs/windows.config;configs/linux.config" -c context -t generated
bin/TeamCity.Docker generate -s configs -f "configs/common.config;configs/internal.config;configs/windows.config;configs/linux.config" -c context -t context/generated -d .teamcity -b TC2020_1_BuildDistTarGzWar
