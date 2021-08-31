cd ..
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile" -t teamcity-minimal-agent:local-linux-arm64-20.04 "context"
