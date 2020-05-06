#!/bin/bash

dotnet run -p tool/TeamCity.Docker/TeamCity.Docker.csproj -- build -s "configs/linux" -f "configs/common.config;configs/linux.config" -c context -r "$1"