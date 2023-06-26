package generated.inactive

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object push_local_linux_18_04 : BuildType({
	 name = "ON PAUSE Build and push linux 18.04"
	 buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
	 description  = "teamcity-server:EAP-linux-arm64-18.04,EAP:EAP-linux-18.04,EAP teamcity-minimal-agent:EAP-linux-arm64-18.04,EAP:EAP-linux-18.04,EAP teamcity-agent:EAP-linux-arm64-18.04,EAP:EAP-linux-arm64-18.04-sudo:EAP-linux-18.04,EAP:EAP-linux-18.04-sudo"
})
