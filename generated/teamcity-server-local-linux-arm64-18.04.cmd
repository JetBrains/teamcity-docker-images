cd ..
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/linux/Server/UbuntuARM/18.04/Dockerfile" -t teamcity-server:local-linux-arm64-18.04 "context"
