call tool\build-tool.cmd
rmdir "context/generated" /s /q
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/windows.config;configs/linux.config" -c context -t context/generated -d .teamcity -b TC_Trunk_BuildDistTarGzWar -p "latest;2020.1.1;eap"