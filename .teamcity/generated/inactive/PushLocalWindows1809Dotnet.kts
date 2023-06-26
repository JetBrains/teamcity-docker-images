package generated.inactive

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object push_local_windows_1809_dotnet : BuildType({
name = "ON PAUSE Build and push windows 1809-dotnet"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
description  = "teamcity-agent:nanoserver-dotnet"
})
