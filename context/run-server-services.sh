#!/bin/bash

# Fail if one of the service scripts fail
set -euxE
set -o pipefail

echo '/run-services.sh'

for entry in /services/*.sh
do
  if [[ -f "$entry" ]]; then
    echo "$entry"
    [[ ! -x "$entry" ]] && (chmod +x "$entry"; sync)
    "$entry"
  fi
done

echo '/run-server.sh'
exec '/run-server.sh'
