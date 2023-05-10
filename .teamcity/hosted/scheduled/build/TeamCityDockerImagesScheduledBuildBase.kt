package hosted.scheduled.build

import common.TeamCityDockerImagesRepo
import hosted.scheduled.build.model.DockerImageInfo
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.DockerCommandStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule

/**
 * Base class for build configurations responsible for scheduled build of TeamCity Docker images.
 *
 * @param os identifier of the OS that corresponds to the base of target TeamCity images
 * @param images list of TeamCity Docker images to be built
 */
class TeamCityDockerImagesScheduledBuildBase(private val os: String,
                                             private val images: List<DockerImageInfo>): BuildType({
    // ID is specified explicitly to prevent ordering failure
    id("TeamCityDockerImagesScheduledBuildBase-${os}".replace("-", "_"))

    name = "TeamCity Docker Images - Automated Scheduled Build - ${os}"

    vcs {
        root(TeamCityDockerImagesRepo.TeamCityDockerImagesRepo)
    }

    triggers {
        schedule {
            id = "TRIGGER_TC_DOCKER_IMAGES_NIGHTLY"
            schedulingPolicy = daily {
                hour = 0
                minute = 15
            }
            branchFilter = "+:<default>"
            withPendingChangesOnly = false
        }
    }

    steps {
        images.forEach { imageInfo ->
            dockerCommand {
                name = "build ${imageInfo.repository}-${imageInfo.tag}"
                commandType = build {
                    source = file {
                        path = imageInfo.dockerfilePath
                    }
                    platform = DockerCommandStep.ImagePlatform.Linux
                    contextDir = "context"
                    namesAndTags = "${imageInfo.repository}:${imageInfo.tag}"
                }
            }
        }
    }

    dependencies {
        dependency(AbsoluteId("TC2023_05_BuildDistDocker")) {
            artifacts {
                artifactRules = "TeamCity.zip!/**=>context/TeamCity"
                cleanDestination = true
                lastSuccessful()
            }
        }
    }

    features {
        dockerSupport {
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_774"
            }
        }
    }
})
