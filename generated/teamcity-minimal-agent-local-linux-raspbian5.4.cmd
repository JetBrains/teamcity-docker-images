cd ..
docker pull raspbian:5.4
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "generated/linux/MinimalAgent/Raspbian/5.4/Dockerfile" -t teamcity-minimal-agent:local-linux-raspbian5.4 "context"
