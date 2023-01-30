call tool\build-tool.cmd
rmdir "generated" /s /q
rmdir "context/generated" /s /q
rmdir "generated" /s /q
bin\TeamCity.Docker generate -s configs -f "configs/common.config;configs/local.config;configs/windows.config;configs/linux.config;configs/linuxARM.config" -c context -t generated
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/version.config;configs/internal.config;configs/windows.config;configs/linux.config;configs/linuxARM.config" -c context -t context/generated -d .teamcity/generated -r "PROJECT_EXT_774" -b TC2022_10_BuildDistDocker
