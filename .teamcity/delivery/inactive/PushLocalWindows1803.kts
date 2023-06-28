package delivery.inactive

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object push_local_windows_1803 : BuildType({
    name = "ON PAUSE Build and push windows 1803"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    description =
        "teamcity-server:EAP-nanoserver-1803,EAP teamcity-minimal-agent:EAP-nanoserver-1803,EAP teamcity-agent:EAP-windowsservercore-1803,EAP-windowsservercore,-windowsservercore:EAP-nanoserver-1803,EAP"
})
