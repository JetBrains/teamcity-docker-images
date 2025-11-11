cd ../..
docker pull ubuntu:24.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/24.04/Dockerfile" -t teamcity-server:2025.11-linux "context"
