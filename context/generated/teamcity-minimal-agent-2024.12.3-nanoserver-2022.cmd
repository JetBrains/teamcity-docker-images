cd ../..
docker pull mcr.microsoft.com/windows/nanoserver:ltsc2022
docker pull mcr.microsoft.com/powershell:nanoserver-ltsc2022
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/windows/MinimalAgent/nanoserver/2022/Dockerfile" -t teamcity-minimal-agent:2024.12.3-nanoserver-2022 "context"
