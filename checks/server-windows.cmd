if not exist "%CD%\data" mkdir "%CD%\data"
if not exist "%CD%\logs" mkdir "%CD%\logs"

docker run -it --rm --name teamcity-server-instance -v "%CD%\data:C:/ProgramData/JetBrains/TeamCity" -v "%CD%\logs:C:/TeamCity/logs" -p 8111:8111 teamcity-server:eap-latest-nanoserver-1809