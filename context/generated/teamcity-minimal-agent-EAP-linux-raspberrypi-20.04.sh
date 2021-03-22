#!/bin/bash
cd ../..
docker pull ubuntu:20.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/RaspberryPi/20.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-raspberrypi-20.04 "context"
