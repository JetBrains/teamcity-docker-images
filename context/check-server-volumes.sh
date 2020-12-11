#!/bin/bash

PERMISSION_PROBLEM=0
if [ ! -w "$TEAMCITY_DATA_PATH" ]; then
    echo ">>> TEAMCITY_DATA_PATH '$TEAMCITY_DATA_PATH' is not a writeable directory"
    PERMISSION_PROBLEM=1
fi

if [ ! -w "$TEAMCITY_LOGS" ]; then
    echo ">>> TEAMCITY_LOGS '$TEAMCITY_LOGS' is not a writeable directory"
    PERMISSION_PROBLEM=1
fi

if [ ! -w "$CATALINA_TMPDIR" ]; then
    echo ">>> CATALINA_TMPDIR '$CATALINA_TMPDIR' is not a writeable directory"
    PERMISSION_PROBLEM=1
fi

if [ "$PERMISSION_PROBLEM" = "1" ]; then
    echo "    Current user is '`whoami`' (`id -u`/`id -g`)"
    echo "    Check permissions and for the corresponding volume(s)."
    echo "    If you're upgrading, chown -R 1000:1000 on the corresponding volume(s) directory may help."
    exit 1
fi
