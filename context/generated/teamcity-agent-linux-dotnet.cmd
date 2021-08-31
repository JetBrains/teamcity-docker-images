cd ../..
docker pull jetbrains/teamcity-agent:EAP-linux-sudo
echo TeamCity > context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/20.04-dotnet/Dockerfile" -t teamcity-agent:linux-dotnet "context"
