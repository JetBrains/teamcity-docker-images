import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import delivery.BuildAndPushHosted
import delivery.HubProject
import delivery.LocalProject
import scheduled.build.TeamCityDockerImagesScheduledBuild
import scheduled.build.TeamCityScheduledImageBuildWindows
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.project
import jetbrains.buildServer.configs.kotlin.v2019_2.version
import scheduled.build.TeamCityDockerImageScheduledBuildLinuxManifest
import utils.config.DeliveryConfig

version =  "2019.2"

object RootProject : Project({
    vcsRoot(TeamCityDockerImagesRepo)
    subProject(LocalProject.LocalProject)
    subProject(HubProject.HubProject)
    buildType(BuildAndPushHosted.BuildAndPushHosted)

    buildType(TeamCityDockerImagesScheduledBuild.TeamCityDockerImagesScheduledBuild)
    buildType(TeamCityScheduledImageBuildWindows.TeamCityScheduledImageBuildWindows)
    buildType(TeamCityScheduledImageBuildLinux_Base("amd64", "linux"))
    buildType(TeamCityScheduledImageBuildLinux_Base("aarch64", "arm"))
    buildType(TeamCityDockerImageScheduledBuildLinuxManifest)

    params {
        // The build using the following parameters inherited from the hosting TeamCity Server:
        // * %docker.deployRepository% - production repository
        // *  %docker.buildRepository% - staging repository
        // Please, overwrite them here, if needed.

        param("dockerImage.teamcity.buildNumber", "%dep.${DeliveryConfig.buildDistDockerDepId}.build.number%")
        // TeamCity version: EAP, 2023.05, etc.
        param("tc.image.version", DeliveryConfig.tcVersion)

        param("teamcity.ui.settings.readOnly", "false")

        // Used by build for teamcity.jetbrains.com
        param("hostedLinuxVersion", "20.04")
    }
})

project(RootProject)
