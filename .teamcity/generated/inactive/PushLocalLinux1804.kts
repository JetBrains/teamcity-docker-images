// NOTE: THIS IS AN AUTO-GENERATED FILE. IT HAD BEEN CREATED USING TEAMCITY.DOCKER PROJECT. ...
// ... IF NEEDED, PLEASE, EDIT DSL GENERATOR RATHER THAN THE FILES DIRECTLY. ... 
// ... FOR MORE DETAILS, PLEASE, REFER TO DOCUMENTATION WITHIN THE REPOSITORY.
package generated.inactive

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object push_local_linux_18_04 : BuildType({
	 name = "ON PAUSE Build and push linux 18.04"
	 buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
	 description  = "teamcity-server:EAP-linux-arm64-18.04,EAP:EAP-linux-18.04,EAP teamcity-minimal-agent:EAP-linux-arm64-18.04,EAP:EAP-linux-18.04,EAP teamcity-agent:EAP-linux-arm64-18.04,EAP:EAP-linux-arm64-18.04-sudo:EAP-linux-18.04,EAP:EAP-linux-18.04-sudo"
})

