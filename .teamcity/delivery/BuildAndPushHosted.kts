package delivery

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import utils.ImageInfoRepository
import utils.dsl.general.teamCityBuildDistDocker
import utils.dsl.general.teamCityImageBuildFeatures
import utils.dsl.steps.buildAndPushToStaging
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand

/**
 * Builds Ubuntu-based Server image and publishes it to staging.
 */
object BuildAndPushHosted : BuildType({
    name = "Build and push for teamcity.jetbrains.com"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    vcs { root(TeamCityDockerImagesRepo) }

    params {
        param("dockerImage.platform", "linux")
        param("docker.buildImagePostfix", "staging")
    }

    steps {
        dockerCommand {
            name = "Preflight check of base image - ubuntu:20.04"
            commandType = other {
                subCommand = "pull"
                commandArgs = "ubuntu:20.04"
            }
        }

        val serverImages = ImageInfoRepository.getAmdLinuxImages2004().filter { it.name.contains("server") }
        serverImages.forEach { serverImg ->
            buildAndPushToStaging(serverImg)
        }
    }

    features {
        teamCityImageBuildFeatures(requiredSpaceGb = 4)
    }

    dependencies {
        teamCityBuildDistDocker()
    }
})
