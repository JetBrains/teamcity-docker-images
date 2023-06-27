package delivery.inactive

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object push_local_windows_1903 : BuildType({
    name = "ON PAUSE Build and push windows 1903"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    description =
        "teamcity-server:EAP-nanoserver-1903,EAP teamcity-minimal-agent:EAP-nanoserver-1903,EAP teamcity-agent:EAP-windowsservercore-1903,EAP-windowsservercore,-windowsservercore:EAP-nanoserver-1903,EAP"
})
