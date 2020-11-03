#!/bin/bash

if [ "$DOCKER_IN_DOCKER" = "start" ] ; then
 sudo rm /var/run/docker.pid 2>/dev/null
 sudo service docker start
 echo "Docker daemon started"
 service docker status
fi
