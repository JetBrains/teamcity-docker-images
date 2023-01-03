package hosted.scheduled.build

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.DockerCommandStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand


/**
 * Scheduled build of TeamCity Docker Images for Linux.
 */
object TeamCityScheduledImageBuildLinux : BuildType({
    name = "TeamCity Docker Images - Automated Scheduled Build - Linux"

    vcs {
        root(TeamCityDockerImagesRepo)
    }

    // <image name> - <dockerfile path>
    val targetImages: HashMap<String, String> = hashMapOf(
        // Ubuntu 20.04
        "teamcity-server-linux-20-04" to "context/generated/linux/Server/Ubuntu/20.04/Dockerfile",
        "teamcity-minimal-agent-linux-20-04" to "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile",
        "teamcity-agent-linux-20-04" to "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile",
        "teamcity-agent-linux-sudo-20-04" to "context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile",
        // -- ARM images are commented out since TeamCity, currently, does not support it
        // "teamcity-agent-linux-arm64-20-04" to "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile",
        // "teamcity-agent-linux-arm64-sudo-20-04" to "context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile",
    )

    steps {
        targetImages.forEach { (imageName, dockerfilePath) ->
            dockerCommand {
                name = "build $imageName"
                commandType = build {
                    source = file {
                        path = dockerfilePath
                    }
                    platform = DockerCommandStep.ImagePlatform.Linux
                    contextDir = "context"
                    namesAndTags = "${imageName}:%dockerImage.teamcity.buildNumber%"
                }
            }
        }
    }

    requirements {
        contains("docker.server.osType", "linux")
        contains("system.agent.name", "docker")
    }
})
