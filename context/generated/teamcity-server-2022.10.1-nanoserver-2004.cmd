cd ../..
docker pull mcr.microsoft.com/powershell:nanoserver-2004
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/2004/Dockerfile" -t teamcity-server:2022.10.1-nanoserver-2004 "context"
