package delivery.arm

import common.TeamCityDockerImagesRepo
import utils.ImageInfoRepository
import utils.dsl.general.teamCityBuildDistDocker
import utils.dsl.general.teamCityImageBuildFeatures
import utils.dsl.steps.moveToProduction
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

/**
 * Deploy aarch64 (ARM) TeamCity Docker images into production registry.
 */
object push_production_linux_2004_aarch64 : BuildType({
    name = "[aarch64] [Linux 2004] Build and deploy production images"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    description = "Publish Linux-based Docker images for aarch64 platform."
    vcs {
        root(TeamCityDockerImagesRepo.TeamCityDockerImagesRepo)
    }

    steps {
        ImageInfoRepository.getArmLinuxImages2004().forEach { imageInfo ->
            moveToProduction(imageInfo)
        }
    }

    features {
        teamCityImageBuildFeatures()
    }

    dependencies {
        teamCityBuildDistDocker()
    }

    requirements {
        // must be built on aarch64-based agents
        contains("teamcity.agent.name", "arm")
    }
})
