cd ../..
docker pull ubuntu:24.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/24.04/Dockerfile" -t teamcity-minimal-agent:2025.11-linux-arm64 "context"
