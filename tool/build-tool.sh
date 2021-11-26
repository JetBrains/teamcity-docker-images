#!/bin/bash

case $(uname | tr '[:upper:]' '[:lower:]') in
  linux*)
    export RID=linux-x64
    export TOOL=TeamCity.Docker
    ;;
  darwin*)
    export RID=osx-x64
    export TOOL=TeamCity.Docker
    ;;
  msys*)
    export RID=win-x64
    export TOOL=TeamCity.Docker.exe
    ;;
  *)
    export RID=win-x64
    export TOOL=TeamCity.Docker.exe
    ;;
esac

bash $(dirname "$0")/sdk.sh dotnet publish "tool/TeamCity.Docker/TeamCity.Docker.csproj" -f net6.0 -c release -r $RID --nologo --self-contained -o bin /p:PublishSingleFile=true