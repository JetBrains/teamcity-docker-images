cd ..
docker pull mcr.microsoft.com/windows/nanoserver:2004
docker pull mcr.microsoft.com/powershell:nanoserver-2004
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-2004
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/windows/MinimalAgent/nanoserver/2004/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-2004 "context"
docker build -f "generated/windows/Agent/windowsservercore/2004/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-2004 "context"
docker build -f "generated/windows/Agent/nanoserver/2004/Dockerfile" -t teamcity-agent:${versionTag}-nanoserver-2004 "context"
