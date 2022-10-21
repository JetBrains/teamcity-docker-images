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
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.BuildFailureOnText
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.failOnText

object DockerImageValidation : BuildType({
    name = "Validate Docker images pushed into teamcity.jetbrains.com"
    description = "Validation of Docker images already uploaded into registry."

    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(TeamCityDockerImagesRepo)
    }
    steps {

        kotlinFile {
            path = "automation/ImageValidation.kts"
            arguments = "%docker.buildRepository%teamcity-server-staging:%dockerImage.teamcity.buildNumber%" 
            //arguments = "jetbrains/teamcity-server:2022.04.4"
        }
    }


    failureConditions {
        failOnText {
            id = "BUILD_EXT_1"
            conditionType = BuildFailureOnText.ConditionType.REGEXP
            pattern = ".*DockerImageValidationException.*"
            reverse = false
        }
    }

    dependencies {
        // Launch after images are built and pushed into registry
        snapshot(AbsoluteId("TC_Trunk_BuildDistDocker")) {
            onDependencyFailure = FailureAction.FAIL_TO_START
            synchronizeRevisions = false
        }
    }

    requirements {}
    features {}
})