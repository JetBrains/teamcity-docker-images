#!/bin/bash

set -euxE
set -o pipefail


if [ "$DOCKER_IN_DOCKER" = "start" ] ; then

 # Do cover the case when the container is restarted:
 sudo rm /var/run/docker.pid 2>/dev/null
 sudo rm -rf /var/run/docker/containerd 2>/dev/null

 sudo service docker start
 echo "Docker daemon started"
 service docker status
fi
