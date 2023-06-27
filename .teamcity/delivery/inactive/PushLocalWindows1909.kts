package delivery.inactive

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object push_local_windows_1909 : BuildType({
    name = "ON PAUSE Build and push windows 1909"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    description =
        "teamcity-server:EAP-nanoserver-1909,EAP teamcity-minimal-agent:EAP-nanoserver-1909,EAP teamcity-agent:EAP-windowsservercore-1909,EAP-windowsservercore,-windowsservercore:EAP-nanoserver-1909,EAP"
})
