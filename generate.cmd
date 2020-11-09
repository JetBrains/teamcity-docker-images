call tool\build-tool.cmd
rmdir "generated" /s /q
rmdir "context/generated" /s /q
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/local.config;configs/windows.config;configs/linux.config" -t generated -c context
bin\TeamCity.Docker.exe generate -s configs -f "configs/common.config;configs/version.config;configs/internal.config;configs/windows.config;configs/linux.config" -c context -t context/generated -d .teamcity/generated -r "PROJECT_EXT_315,PROJECT_EXT_4003,PROJECT_EXT_4022" -b TC_Trunk_BuildDistDocker
