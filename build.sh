#!/bin/bash
bash tool/build-tool.sh
bin/TeamCity.Docker build -s "configs/linux" -f "configs/common.config;configs/linux.config" -c context -r "$1.*"