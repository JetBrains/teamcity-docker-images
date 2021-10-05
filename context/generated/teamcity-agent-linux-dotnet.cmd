cd ../..
docker pull jetbrains/teamcity-agent:2021.1.4-linux-sudo
echo TeamCity > context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/20.04-dotnet/Dockerfile" -t teamcity-agent:linux-dotnet "context"
