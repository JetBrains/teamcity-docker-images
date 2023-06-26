// NOTE: THIS IS AN AUTO-GENERATED FILE. IT HAD BEEN CREATED USING TEAMCITY.DOCKER PROJECT. ...
// ... IF NEEDED, PLEASE, EDIT DSL GENERATOR RATHER THAN THE FILES DIRECTLY. ... 
// ... FOR MORE DETAILS, PLEASE, REFER TO DOCUMENTATION WITHIN THE REPOSITORY.
package generated

import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object HubProject : Project({
	 name = "Docker hub"
	 buildType(PushHubLinux.push_hub_linux)
	 buildType(PushHubWindows.push_hub_windows)
	 buildType(PublishHubVersion.publish_hub_version)
})
