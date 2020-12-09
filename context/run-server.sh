#!/bin/bash

# Setting default values if variables not present
: ${TEAMCITY_DIST:=/opt/teamcity}
: ${TEAMCITY_LOGS:=${TEAMCITY_DIST}/logs}
: ${TEAMCITY_CONTEXT:=ROOT}
: ${TEAMCITY_SERVER_XML:="-config conf/server.xml"}
: ${TEAMCITY_STOP_WAIT_TIME:=60}
export TEAMCITY_LOGS

find "${TEAMCITY_LOGS}" -maxdepth 1 -type f -name '*.pid' -exec rm -f '{}' \;

if [[ "$TEAMCITY_CONTEXT" != "ROOT" ]]; then
    current="$(ls ${TEAMCITY_DIST}/webapps | head -1)"
    [[ "$current" != "$TEAMCITY_CONTEXT" ]] && mv "${TEAMCITY_DIST}/webapps/$current" "${TEAMCITY_DIST}/webapps/$TEAMCITY_CONTEXT"
fi

if [[ "$TEAMCITY_HTTPS_PROXY_ENABLED" == "true" ]]; then
  TEAMCITY_SERVER_XML="-config conf/server-https-proxy.xml"
fi

# Set traps to gently shutdown server on `docker stop`, `docker restart` or `docker kill -s 15`
trap "'${TEAMCITY_DIST}/bin/teamcity-server.sh' stop ${TEAMCITY_STOP_WAIT_TIME} -force; exit \$?" TERM INT HUP

# & and wait required for traps to work
cd "${TEAMCITY_DIST}"
echo "TeamCity server.xml parameter: $TEAMCITY_SERVER_XML"
bin/teamcity-server.sh run $TEAMCITY_SERVER_XML &
wait $!
exit $?