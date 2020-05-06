rmdir "generated" /s /q
rmdir "context/generated" /s /q

dotnet run -p tool/TeamCity.Docker/TeamCity.Docker.csproj -f %1 -- generate -s configs -f "configs/common.config;configs/windows.config;configs/windows-internal.config;configs/linux.config;configs/linux-internal.config" -c context -t context/generated -d .teamcity -b "TC2019_2_BuildDist;TC_Trunk_BuildDist:eap" -r PROJECT_EXT_2307
dotnet run -p tool/TeamCity.Docker/TeamCity.Docker.csproj -f %1 -- generate -s configs -f "configs/common.config;configs/windows.config;configs/linux.config" -c context -t generated