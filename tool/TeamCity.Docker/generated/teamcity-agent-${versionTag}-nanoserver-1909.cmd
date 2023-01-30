cd ..
docker pull mcr.microsoft.com/windows/nanoserver:1909
docker pull mcr.microsoft.com/powershell:nanoserver-1909
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1909
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/windows/MinimalAgent/nanoserver/1909/Dockerfile" -t teamcity-minimal-agent:${versionTag}-nanoserver-1909 "context"
docker build -f "generated/windows/Agent/windowsservercore/1909/Dockerfile" -t teamcity-agent:${versionTag}-windowsservercore-1909 "context"
docker build -f "generated/windows/Agent/nanoserver/1909/Dockerfile" -t teamcity-agent:${versionTag}-nanoserver-1909 "context"
