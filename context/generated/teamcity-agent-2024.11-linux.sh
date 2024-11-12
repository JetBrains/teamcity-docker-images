#!/bin/bash
cd ../..
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/Ubuntu/22.04/Dockerfile" -t teamcity-minimal-agent:2024.11-linux "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/Ubuntu/22.04/Dockerfile" -t teamcity-agent:2024.11-linux "context"
