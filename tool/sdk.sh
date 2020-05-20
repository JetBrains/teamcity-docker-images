#!/bin/bash
#-H localhost:2375
echo docker run -it --rm -w="/teamcity" -v "$(pwd)/..:/teamcity" mcr.microsoft.com/dotnet/core/sdk:3.1 $@