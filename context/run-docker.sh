#!/bin/bash

if [ "$DOCKER_IN_DOCKER" = "start" ] ; then

# By default, Docker in containers uses nftables. However, the host may be using legacy iptables.
# This section optionally switches the container to use legacy iptables for Docker-in-Docker compatibility. See: TW-94273
  if [ "$DOCKER_IPTABLES_LEGACY" == "1" ]; then
       # Switch IPTables-related tools to legacy tables
       sudo update-alternatives --set iptables /usr/sbin/iptables-legacy
       sudo update-alternatives --install /usr/bin/iptables iptables /usr/sbin/iptables-nft 30
       sudo update-alternatives --set iptables /usr/sbin/iptables-legacy

       # Disable conflicting services
       sudo systemctl disable --now ufw
       sudo systemctl disable --now nftables
       sudo apt update && apt install -t iptables-persistent

       # Enable legacy tables by defaults
       sudo systemctl enable --now iptables
  fi

 # Do cover the case when the container is restarted:
 sudo rm /var/run/docker.pid 2>/dev/null
 sudo rm -rf /var/run/docker/containerd 2>/dev/null

 sudo service docker start
 echo "Docker daemon started"
 service docker status
fi
