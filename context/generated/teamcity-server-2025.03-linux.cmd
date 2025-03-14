cd ../..
docker pull ubuntu:22.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/Ubuntu/22.04/Dockerfile" -t teamcity-server:2025.03-linux "context"
