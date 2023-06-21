import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import generated.HubProject
import generated.LocalProject
import hosted.BuildAndPushHosted
import hosted.scheduled.build.TeamCityDockerImagesScheduledBuild
import hosted.scheduled.build.TeamCityScheduledImageBuildWindows
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.project
import jetbrains.buildServer.configs.kotlin.v2019_2.version

version = "2019.2"

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
        param("dockerImage.teamcity.buildNumber", "%dep.TC2023_05_BuildDistDocker.build.number%")
        param("teamcity.ui.settings.readOnly", "false")

        // Used by build for teamcity.jetbrains.com
        param("hostedLinuxVersion", "20.04")
    }
})

project(RootProject)
