call tool\build-tool.cmd
rmdir "context/generated" /s /q
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/version.config;configs/internal.config;configs/windows.config;configs/linux.config;configs/linuxARM.config" -c context -t context/generated -d .teamcity/generated -r "PROJECT_EXT_774" -b TC_Trunk_BuildDistDocker
