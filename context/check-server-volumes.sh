#!/bin/bash

PERMISSION_PROBLEM=0

_check_dir() {
  DIR=$1
  DIR_NAME=$2

  touch "$DIR/.file.txt" 2>/dev/null
  if [ ! -f "$DIR/.file.txt" ]; then
      echo ">>> Permission problem: $DIR_NAME '$DIR' is not a writeable directory"
      export PERMISSION_PROBLEM=1
  fi
  rm "$DIR/.file.txt" 2>/dev/null
}

echo ""

_check_dir "$TEAMCITY_DATA_PATH" 'TEAMCITY_DATA_PATH'
_check_dir "$TEAMCITY_LOGS" 'TEAMCITY_LOGS'
_check_dir "$CATALINA_TMPDIR" 'CATALINA_TMPDIR'

if [ "$PERMISSION_PROBLEM" = "1" ]; then
    cat <<END

    Looks like some mandatory directories are not writable (see above).
    TeamCity container is running under '`whoami`' (`id -u`/`id -g`) user.

    A quick workaround: pass '-u 0' parameter to 'docker run' command to start it under 'root' user.
    The proper fix: run 'chown -R 1000:1000' on the corresponding volume(s), this can take noticeable time.

END
    exit 1
fi
