package delivery.arm

import common.TeamCityDockerImagesRepo
import utils.ImageInfoRepository
import utils.dsl.steps.buildAndPushToStaging
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.FailureAction
import jetbrains.buildServer.configs.kotlin.v2019_2.ReuseBuilds
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import utils.config.DeliveryConfig
import utils.dsl.general.teamCityImageBuildFeatures

/**
 * Building and deploying aarch64 (ARM) Linux-based Docker images into staging registry, which is defined ...
 * ... within upstream's parameters.
 */
object push_staging_linux_2004_aarch64 : BuildType({
    name = "[aarch64] [Linux 2004] Build and deploy staging images"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    description = "Build and deploy Linux-based Docker images for aarch64 platform."
    vcs {
        root(TeamCityDockerImagesRepo.TeamCityDockerImagesRepo)
    }

    steps {
        dockerCommand {
            name = "pull ubuntu:20.04"
            commandType = other {
                subCommand = "pull"
                commandArgs = "ubuntu:20.04"
            }
        }

        ImageInfoRepository.getArmLinuxImages2004().forEach { imageInfo ->
            buildAndPushToStaging(imageInfo)
        }
    }

    features {
        teamCityImageBuildFeatures(requiredSpaceGb = 8)
    }

    dependencies {
        dependency(AbsoluteId(DeliveryConfig.buildDistDockerDepId)) {
            snapshot {
                onDependencyFailure = FailureAction.IGNORE
                reuseBuilds = ReuseBuilds.ANY
            }
            artifacts {
                artifactRules = "TeamCity.zip!/**=>context/TeamCity"
            }
        }
    }
    params {
        param("system.teamcity.agent.ensure.free.space", "8gb")
    }

    requirements {
        // must be built on aarch64-based agents
        contains("teamcity.agent.name", "arm")
    }
})
