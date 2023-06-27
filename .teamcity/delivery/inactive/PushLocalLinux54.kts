package delivery.inactive

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object push_local_linux_5_4 : BuildType({
    name = "ON PAUSE Build and push linux 5.4"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    description = "teamcity-minimal-agent:EAP-linux-raspbian5.4,EAP"
})
