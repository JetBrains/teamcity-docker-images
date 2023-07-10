package delivery

import delivery.arm.PushProductionLinux2004_Aarch64
import delivery.production.PushHubLinux
import delivery.production.PushHubWindows
import delivery.production.manifest.PublishHubVersion
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object HubProject : Project({
    name = "TeamCity Docker Images Deployment into Production Registry"
    description = "Configurations designed the promotion of TeamCity Docker Images from staging to production registry."
    buildType(PushHubLinux.push_hub_linux)
    buildType(PushHubWindows.push_hub_windows)
    buildType(PublishHubVersion.publish_hub_version)
    buildType(PushProductionLinux2004_Aarch64.push_production_linux_2004_aarch64)
})
