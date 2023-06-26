package generated.inactive

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object push_local_linux_20_04_dotnet : BuildType({
    name = "ON PAUSE Build and push linux 20.04-dotnet"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    description = "teamcity-agent:linux-dotnet"
})

