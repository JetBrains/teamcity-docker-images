#!/bin/bash
cd ../..
docker pull ubuntu:18.04
echo TeamCity/webapps > context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
docker build -f "context/generated/linux/MinimalAgent/RaspberryPi/18.04/Dockerfile" -t teamcity-minimal-agent:EAP-linux-raspberrypi-18.04 "context"
