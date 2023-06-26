package generated

import generated.production.PushHubLinux
import generated.production.PushHubWindows
import generated.production.manifest.PublishHubVersion
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object HubProject : Project({
    name = "TeamCity Docker Images Deployment into Production Registry"
    description = "Configurations aimed for the promotion of eamCity Docker Images from staging to production registry."
    buildType(PushHubLinux.push_hub_linux)
    buildType(PushHubWindows.push_hub_windows)
    buildType(PublishHubVersion.publish_hub_version)
})
