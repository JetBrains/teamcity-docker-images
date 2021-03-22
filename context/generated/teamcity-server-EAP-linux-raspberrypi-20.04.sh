#!/bin/bash
cd ../..
docker pull ubuntu:20.04
echo TeamCity/buildAgent > context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
docker build -f "context/generated/linux/Server/RaspberryPi/20.04/Dockerfile" -t teamcity-server:EAP-linux-raspberrypi-20.04 "context"
