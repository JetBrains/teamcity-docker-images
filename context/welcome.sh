#!/bin/sh
cat <<EOF
   Welcome to TeamCity Server Docker container

 * Installation directory: ${TEAMCITY_DIST}
 * Logs directory:         ${TEAMCITY_LOGS}
 * Data directory:         ${TEAMCITY_DATA_PATH}

   TeamCity will be running under '`whoami`' user (`id -u`/`id -g`)

EOF

