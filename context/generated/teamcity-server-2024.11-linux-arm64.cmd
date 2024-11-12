cd ../..
docker pull ubuntu:22.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/UbuntuARM/22.04/Dockerfile" -t teamcity-server:2024.11-linux-arm64 "context"
