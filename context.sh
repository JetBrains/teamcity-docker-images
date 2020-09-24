#!/bin/bash
sed -i -e 's/\r$//' ./tool/agent-upgrade-dist/gradlew && \
bash ./tool/agent-upgrade-dist/gradlew -p ./tool/agent-upgrade-dist/ run --args=./../../context/TeamCity