call tool\build-tool.cmd
rmdir "generated" /s /q
rmdir "context/generated" /s /q
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/local.config;configs/windows.config;configs/linux.config;configs/linuxARM.config" -t generated -c context
:: PROJECT_EXT_774 - global docker registry with writable access
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/version.config;configs/internal.config;configs/windows.config;configs/linux.config;configs/linuxARM.config" -c context -t context/generated -d .teamcity/generated -r "PROJECT_EXT_774,PROJECT_EXT_315" -b TC_Trunk_BuildDistDocker
