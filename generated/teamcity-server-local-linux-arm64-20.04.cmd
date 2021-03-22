cd ..
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "generated/linux/Server/UbuntuARM/20.04/Dockerfile" -t teamcity-server:local-linux-arm64-20.04 "context"
