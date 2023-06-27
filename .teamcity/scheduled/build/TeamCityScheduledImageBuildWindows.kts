package scheduled.build

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import scheduled.build.model.DockerImageInfo
import utils.dsl.general.teamCityBuildDistDocker
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.DockerCommandStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import utils.ImageInfoRepository
import utils.dsl.general.teamCityImageBuildFeatures
import utils.dsl.steps.buildImage
import java.util.*

/**
 * Scheduled build of TeamCity Docker Images for Windows.
 */
object TeamCityScheduledImageBuildWindows : BuildType({

    name = "TeamCity Docker Images - Automated Scheduled Build - Windows"

    vcs {
        root(TeamCityDockerImagesRepo)
    }

    steps {

        // Build images, while don't push anywhere - a sanity check for the correctness of all ...
        // ... image's dependencies (CLI tools, etc.)
        ImageInfoRepository.getWindowsImages1809().forEach { winImage1809 ->
            buildImage(winImage1809)
        }

        ImageInfoRepository.getWindowsImages2004().forEach { winImage2004 ->
            buildImage(winImage2004)
        }
    }

    dependencies {
        teamCityBuildDistDocker()
    }

    features {
        teamCityImageBuildFeatures(requiredSpaceGb = 50)
    }

    // An implicit Windoiws 10 requirement has been added in order to prevent DotNet's WebClient internal exception.
    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows 10")
    }
})
