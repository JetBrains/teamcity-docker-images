import Settings.RootProject.buildTypes
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import hosted.*
import generated.*
import hosted.scheduled.build.TeamCityDockerImagesScheduledBuild
import hosted.scheduled.build.TeamCityScheduledImageBuildLinux
import hosted.scheduled.build.TeamCityScheduledImageBuildWindows

version = "2019.2"

object RootProject : Project({
    vcsRoot(TeamCityDockerImagesRepo)
    subProject(LocalProject.LocalProject)
    subProject(HubProject.HubProject)
    buildType(BuildAndPushHosted.BuildAndPushHosted)

    buildType(TeamCityDockerImagesScheduledBuild.TeamCityDockerImagesScheduledBuild)
    buildType(TeamCityScheduledImageBuildWindows.TeamCityScheduledImageBuildWindows)
    buildType(TeamCityScheduledImageBuildLinux.TeamCityScheduledImageBuildLinux)

    params {
        param("dockerImage.teamcity.buildNumber", "%dep.TC2023_05_BuildDistDocker.build.number%")
        param("teamcity.ui.settings.readOnly", "false")

        // Used by build for teamcity.jetbrains.com
        param("hostedLinuxVersion", "20.04")
    }
})

project(RootProject)