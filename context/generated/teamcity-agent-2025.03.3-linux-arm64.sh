#!/bin/bash
cd ../..
docker pull ubuntu:22.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/22.04/Dockerfile" -t teamcity-minimal-agent:2025.03.3-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/22.04/Dockerfile" -t teamcity-agent:2025.03.3-linux-arm64 "context"
