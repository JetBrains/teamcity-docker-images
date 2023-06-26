package hosted.arm

import common.TeamCityDockerImagesRepo
import hosted.utils.ImageInfoRepository
import hosted.utils.dsl.steps.buildAndPublishImage
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.FailureAction
import jetbrains.buildServer.configs.kotlin.v2019_2.ReuseBuilds
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand

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

    params {
        // will be included into the tag, e.g. 2023.05-linux-amd64
        param("tc.image.version", "EAP")
    }

    steps {
        dockerCommand {
            name = "pull ubuntu:20.04"
            commandType = other {
                subCommand = "pull"
                commandArgs = "ubuntu:20.04"
            }
        }

        ImageInfoRepository.getArmImages().forEach { imageInfo ->
            buildAndPublishImage(imageInfo)
        }
    }

    features {
        freeDiskSpace {
            requiredSpace = "8gb"
            failBuild = true
        }

        dockerSupport {
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_774,PROJECT_EXT_315"
            }
        }

        swabra {
            forceCleanCheckout = true
        }
    }

    dependencies {
        dependency(AbsoluteId("TC_Trunk_BuildDistDocker")) {
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
