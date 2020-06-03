call tool\build-tool.cmd
rmdir "context/generated" /s /q
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/windows.config;configs/linux.config" -c context -t context/generated -d .teamcity -b "TC2019_2_BuildDist:2019_2;TC_Trunk_BuildDist" -r PROJECT_EXT_2307