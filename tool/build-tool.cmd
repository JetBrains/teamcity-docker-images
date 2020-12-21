if not exist "%~dp0..\bin\TeamCity.Docker.exe" %~dp0sdk dotnet publish "tool/TeamCity.Docker/TeamCity.Docker.csproj" -f net5.0 -c release -r win-x64 --nologo -o bin /p:PublishSingleFile=true
