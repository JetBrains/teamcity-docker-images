cd ../..
docker pull mcr.microsoft.com/windows/nanoserver:1903
docker pull mcr.microsoft.com/powershell:nanoserver-1903
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile" -t teamcity-minimal-agent:2022.10.5-nanoserver-1903 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1903/Dockerfile" -t teamcity-agent:2022.10.5-windowsservercore-1903 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1903/Dockerfile" -t teamcity-agent:2022.10.5-nanoserver-1903 "context"
