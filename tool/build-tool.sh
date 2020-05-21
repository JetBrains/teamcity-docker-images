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

echo $RID

bash $(dirname "$0")/sdk.sh dotnet publish "tool/TeamCity.Docker/TeamCity.Docker.csproj" -f netcoreapp3.1 -c release -r $RID --nologo -o bin