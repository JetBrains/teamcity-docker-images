#!/bin/bash

case $(uname | tr '[:upper:]' '[:lower:]') in
  linux*)
    export RID=linux-x64
    ;;
  darwin*)
    export RID=osx-x64
    ;;
  msys*)
    export RID=win-x64
    ;;
  *)
    export RID=win-x64
    ;;
esac

echo $RID

bash sdk.sh dotnet publish "tool/TeamCity.Docker/TeamCity.Docker.csproj" -f netcoreapp3.1 -c release -r $RID --nologo -o tool/bin -p:PublishSingleFile=true -p:PublishTrimmed=true