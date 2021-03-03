call tool\build-tool.cmd
rmdir "generated" /s /q
rmdir "context/generated" /s /q
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/local.config;configs/windows.config;configs/linux.config" -t generated -c context
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/version.config;configs/internal.config;configs/windows.config;configs/linux.config" -c context -t context/generated -d .teamcity/generated -r "PROJECT_EXT_774" -b TC2020_2_BuildDistDocker
