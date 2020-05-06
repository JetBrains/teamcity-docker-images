if not exist "%CD%\data" mkdir "%CD%\data"
if not exist "%CD%\logs" mkdir "%CD%\logs"

docker run -it --rm --name teamcity-server-instance -v "%CD%\data:/data/teamcity_server/datadir" -v "%CD%\logs:/opt/teamcity/logs" -p 8111:8111 nikolayp/teamcity-server:eap-linux bash