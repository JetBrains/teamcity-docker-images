call tool\build-tool.cmd
rmdir "generated" /s /q
rmdir "context/generated" /s /q
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/windows.config;configs/linux.config" -t generated -c context
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/internal.config;configs/windows.config;configs/linux.config" -c context -t context/generated -d .teamcity -b TC2020_1_BuildDistTarGzWar