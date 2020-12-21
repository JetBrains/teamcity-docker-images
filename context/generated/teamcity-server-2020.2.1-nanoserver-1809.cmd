cd ../..
docker pull mcr.microsoft.com/powershell:nanoserver-1809
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/1809/Dockerfile" -t teamcity-server:2020.2.1-nanoserver-1809 "context"
