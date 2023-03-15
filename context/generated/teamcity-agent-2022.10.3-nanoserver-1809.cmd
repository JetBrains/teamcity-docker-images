cd ../..
docker pull mcr.microsoft.com/windows/nanoserver:1809
docker pull mcr.microsoft.com/powershell:nanoserver-1809
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile" -t teamcity-minimal-agent:2022.10.3-nanoserver-1809 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/1809/Dockerfile" -t teamcity-agent:2022.10.3-windowsservercore-1809 "context"
docker build -f "context/generated/windows/Agent/nanoserver/1809/Dockerfile" -t teamcity-agent:2022.10.3-nanoserver-1809 "context"
