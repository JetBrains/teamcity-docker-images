cd ../..
docker pull mcr.microsoft.com/windows/nanoserver:ltsc2022
docker pull mcr.microsoft.com/powershell:nanoserver-ltsc2022
docker pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2022
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/ltsc2022/Dockerfile" -t teamcity-minimal-agent:EAP-nanoserver-ltsc2022 "context"
docker build -f "context/generated/windows/Agent/windowsservercore/ltsc2022/Dockerfile" -t teamcity-agent:EAP-windowsservercore-ltsc2022 "context"
docker build -f "context/generated/windows/Agent/nanoserver/ltsc2022/Dockerfile" -t teamcity-agent:EAP-nanoserver-ltsc2022 "context"
