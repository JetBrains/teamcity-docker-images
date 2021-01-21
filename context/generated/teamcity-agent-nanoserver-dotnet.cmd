cd ../..
docker pull jetbrains/teamcity-agent:EAP-nanoserver-1809
echo TeamCity > context/.dockerignore
docker build -f "context/generated/windows/Agent/nanoserver/1809-dotnet/Dockerfile" -t teamcity-agent:nanoserver-dotnet "context"
