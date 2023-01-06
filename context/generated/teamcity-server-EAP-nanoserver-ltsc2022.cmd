cd ../..
docker pull mcr.microsoft.com/powershell:nanoserver-ltsc2022
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/windows/Server/nanoserver/ltsc2022/Dockerfile" -t teamcity-server:EAP-nanoserver-ltsc2022 "context"
