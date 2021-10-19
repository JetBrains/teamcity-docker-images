cd ../..
docker pull jetbrains/teamcity-agent:2021.2-nanoserver-1809
echo TeamCity > context/.dockerignore
docker build -f "context/generated/windows/Agent/nanoserver/1809-dotnet/Dockerfile" -t teamcity-agent:nanoserver-dotnet "context"
