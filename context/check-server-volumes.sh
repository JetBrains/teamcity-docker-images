#!/bin/bash

PERMISSION_PROBLEM=0

_check_dir() {
  DIR=$1
  DIR_NAME=$2

  touch "$DIR/.file.txt" 2>/dev/null
  if [ ! -f "$DIR/.file.txt" ]; then
      echo ">>> $DIR_NAME '$DIR' is not a writeable directory"
      export PERMISSION_PROBLEM=1
  fi
  rm "$DIR/.file.txt" 2>/dev/null
}

_check_dir "$TEAMCITY_DATA_PATH" 'TEAMCITY_DATA_PATH'
_check_dir "$TEAMCITY_LOGS" 'TEAMCITY_LOGS'
_check_dir "$CATALINA_TMPDIR" 'CATALINA_TMPDIR'

if [ "$PERMISSION_PROBLEM" = "1" ]; then
    echo "    Current user is '`whoami`' (`id -u`/`id -g`)"
    echo "    Check permissions and for the corresponding volume(s)."
    echo "    If you're upgrading, chown -R 1000:1000 on the corresponding volume(s) directory may help."
    exit 1
fi
