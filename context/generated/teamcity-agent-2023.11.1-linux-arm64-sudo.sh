#!/bin/bash
cd ../..
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile" -t teamcity-minimal-agent:2023.11.1-linux-arm64 "context"
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile" -t teamcity-agent:2023.11.1-linux-arm64 "context"
docker build -f "context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile" -t teamcity-agent:2023.11.1-linux-arm64-sudo "context"
