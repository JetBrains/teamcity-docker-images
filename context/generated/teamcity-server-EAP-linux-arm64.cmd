cd ../..
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/20.04/Dockerfile" -t teamcity-server:EAP-linux-arm64 "context"
