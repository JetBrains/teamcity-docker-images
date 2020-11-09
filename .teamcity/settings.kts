import jetbrains.buildServer.configs.kotlin.v2019_2.*
import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import hosted.*
import generated.*

version = "2019.2"

object RootProject : Project({
    vcsRoot(TeamCityDockerImagesRepo)
    subProject(LocalProject.LocalProject)
    subProject(HubProject.HubProject)
    buildType(BuildAndPushHosted.BuildAndPushHosted)
    params {
        param("dockerImage.teamcity.buildNumber", "%dep.TC_Trunk_BuildDistDocker.build.number%")
        param("teamcity.ui.settings.readOnly", "false")
    }
})

project(RootProject)