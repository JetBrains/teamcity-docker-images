import jetbrains.buildServer.configs.kotlin.v2019_2.*
import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import generated.*

version = "2019.2"

object RootProject : Project({
    vcsRoot(TeamCityDockerImagesRepo)
    subProject(LocalProject.LocalProject)
    subProject(HubProject.HubProject)
    buildType(TestBuildType.TestBuildType)
    params {
        param("dockerImage.teamcity.buildNumber", "4")
        param("docker.buildRepository", "jetbrains/teamcity")

        param("dep.TC_Trunk_BuildDistDocker.build.number", "%dep.TC2022_10_BuildDistDocker.build.number%")
        param("teamcity.ui.settings.readOnly", "false")
        param("docker.buildImagePostfix", "-staging")

        // Used by build for teamcity.jetbrains.com
        param("hostedLinuxVersion", "20.04")
    }
})

project(RootProject)