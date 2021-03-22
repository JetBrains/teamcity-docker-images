cd ../..
docker pull ubuntu:18.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/RaspberryPi/18.04/Dockerfile" -t teamcity-server:EAP-linux-raspberrypi-18.04 "context"
