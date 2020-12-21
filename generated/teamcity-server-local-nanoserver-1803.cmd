cd ..
docker pull mcr.microsoft.com/powershell:nanoserver-1803
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/windows/Server/nanoserver/1803/Dockerfile" -t teamcity-server:local-nanoserver-1803 "context"
