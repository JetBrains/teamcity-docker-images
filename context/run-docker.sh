#!/bin/bash

if [ "$DOCKER_IN_DOCKER" = "start" ] ; then

  # Cover the case when the host of the image uses legacy IPTables, see: TW-94273
  if [ "$DOCKER_IPTABLES_LEGACY" == "1" ]; then

    # Prior to the switch, check the presence of existing IPTables (e.g., run-docker.sh is executed in runtime)
    if iptables --version 2>/dev/null | grep -q "nf_tables"; then
        rule_count=$(iptables -S 2>/dev/null | grep -v '^-P' | wc -l)
        if [ "$rule_count" -gt 0 ]; then
            echo "WARNING: Found $rule_count existing iptables rules using nftables backend."
            echo "These rules will become invisible after switching to legacy but will still exist in kernel memory."
            echo ""
        fi
    fi

    # Switch IPTables / IP6Tables and verify the switch
    sudo update-alternatives --set iptables /usr/sbin/iptables-legacy
    sudo update-alternatives --set ip6tables /usr/sbin/ip6tables-legacy

    # Verify legacy mode has been enabled
    for tool in iptables ip6tables; do
        $tool --version | grep -q legacy || {
            echo "ERROR: [$tool] is not running in legacy mode, even though legacy mode was requested"
            exit 1
        }
    done

    # Switch related tools and their alternatives to use legacy tables
    for tool in iptables-save iptables-restore ip6tables-save ip6tables-restore; do
        if update-alternatives --display $tool >/dev/null 2>&1; then
          # NB! Format: ip6tables-save => ip6tables-legacy-save
          legacy_tool="/usr/sbin/${tool%-*}-legacy-${tool#*-}"

          if [ -f "$legacy_tool" ]; then
            sudo update-alternatives --set $tool $legacy_tool
            echo "[$tool] was set to legacy"
          fi
        fi
    done
  fi

 # Do cover the case when the container is restarted:
 sudo rm /var/run/docker.pid 2>/dev/null
 sudo rm -rf /var/run/docker/containerd 2>/dev/null

 sudo service docker start
 echo "Docker daemon started"
 service docker status
fi
