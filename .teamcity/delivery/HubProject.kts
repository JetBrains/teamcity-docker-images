package delivery

import delivery.production.PushHubLinux
import delivery.production.PushHubWindows
import delivery.production.manifest.PublishHubVersion
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object HubProject : Project({
    name = "TeamCity Docker Images Deployment into Production Registry"
    description = "Configurations designed the promotion of eamCity Docker Images from staging to production registry."
    buildType(PushHubLinux.push_hub_linux)
    buildType(PushHubWindows.push_hub_windows)
    buildType(PublishHubVersion.publish_hub_version)
})
