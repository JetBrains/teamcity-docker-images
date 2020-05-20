IF "%~1" == "" GOTO error

call tool\build-tool.cmd
tool\bin\TeamCity.Docker.exe build -s "configs" -f "configs/common.config;configs/windows.config;configs/linux.config" -c context -r "%1.*"
exit

:error
@echo Please use a tag filter, for instance: 
@echo   build linux
@echo   build 1903