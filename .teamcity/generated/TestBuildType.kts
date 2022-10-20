package generated

import jetbrains.buildServer.configs.kotlin.*

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.kotlinFile

object TestBuildType : BuildType({
    name = "Build and push for teamcity.jetbrains.com"

    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(TeamCityDockerImagesRepo)
    }
    steps {

        kotlinFile {
            path = "tool/automation/ImageValidation.kts"
            arguments = "%docker.buildRepository%teamcity-server-staging:%dockerImage.teamcity.buildNumber% %docker.buildRepository%teamcity-server-staging:%dockerImage.teamcity.buildNumber-1%"
        }
    }

    requirements {}
    features {}
})
