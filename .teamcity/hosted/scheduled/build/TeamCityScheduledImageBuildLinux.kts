package hosted.scheduled.build

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import hosted.scheduled.build.model.DockerImageInfo
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.FailureAction
import jetbrains.buildServer.configs.kotlin.v2019_2.ReuseBuilds
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.DockerCommandStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import java.util.*
import kotlin.collections.HashMap


/**
 * Scheduled build of TeamCity Docker Images for Linux.
 */
object TeamCityScheduledImageBuildLinux : BuildType({
    name = "TeamCity Docker Images - Automated Scheduled Build - Linux"

    vcs {
        root(TeamCityDockerImagesRepo)
    }


    // -- order matters as teamcity-agent used teamcity-minimal-agent as base image
    val images = LinkedList(listOf(
        // Ubuntu 20.04
        DockerImageInfo("teamcity-server", "2023.05.1-linux", "context/generated/linux/Server/Ubuntu/20.04/Dockerfile"),
        DockerImageInfo("teamcity-minimal-agent", "2023.05.1-linux", "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile"),
        DockerImageInfo("teamcity-agent", "2023.05.1-linux", "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile"),
        DockerImageInfo("teamcity-agent", "2023.05.1-linux-sudo", "context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile"),
        // -- ARM images are commented out since TeamCity, currently, does not support it
        // DockerImageInfo("teamcity-agent", "2023.05.1-linux-arm64", "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile"),
        // DockerImageInfo("teamcity-agent", "2023.05.1-linux-arm64-sudo", "context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile")
    ))

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
        dependency(AbsoluteId("TC_Trunk_BuildDistDocker")) {
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
