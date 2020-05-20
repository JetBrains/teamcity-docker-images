#!/bin/bash
rm -rf "context/generated"
bash tool/sdk.sh dotnet run -p tool/TeamCity.Docker/TeamCity.Docker.csproj -- generate -s configs -f "configs/common.config;configs/windows.config;configs/linux.config" -c context/generated -t generated -d .teamcity -b TC2019_2_BuildDist -r PROJECT_EXT_2307