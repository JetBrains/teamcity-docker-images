@for /f %%i in ('docker system info --format "{{.OSType}}"') do set OSType=%%i
@SET DRIVE=/
@IF [%OSType%]==[windows] SET DRIVE=C:/
docker run -it --rm "-w=%DRIVE%teamcity" "--volume=%~dp0..:%DRIVE%teamcity" mcr.microsoft.com/dotnet/sdk:6.0 %*