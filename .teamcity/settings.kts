import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import generated.HubProject
import generated.LocalProject
import hosted.BuildAndPushHosted
import hosted.scheduled.build.TeamCityDockerImagesScheduledBuild
import hosted.scheduled.build.TeamCityScheduledImageBuildWindows
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.project
import jetbrains.buildServer.configs.kotlin.v2019_2.version

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

    params {
        // The build using the following parameters inherited from the hosting TeamCity Server:
        // * %docker.deployRepository% - production repository
        // *  %docker.buildRepository% - staging repository
        // Please, overwrite them here, if needed.

        param("dockerImage.teamcity.buildNumber", "%dep.TC_Trunk_BuildDistDocker.build.number%")
        param("teamcity.ui.settings.readOnly", "false")

        // TeamCity version: EAP, 2023.05, etc.
        param("tc.image.version", "EAP")

        // Used by build for teamcity.jetbrains.com
        param("hostedLinuxVersion", "20.04")
    }
})

project(RootProject)
