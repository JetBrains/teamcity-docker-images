#!/bin/bash

echo '/run-services.sh'

for entry in /services/*.sh
do
  if [[ -f "$entry" ]]; then
    echo "$entry"
    [[ ! -x "$entry" ]] && (chmod +x "$entry"; sync)
    "$entry"
  fi
done

echo '/run-agent.sh'
exec '/run-agent.sh'
