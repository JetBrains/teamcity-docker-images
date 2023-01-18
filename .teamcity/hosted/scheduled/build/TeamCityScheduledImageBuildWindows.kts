package hosted.scheduled.build

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.DockerCommandStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand

/**
 * Scheduled build of TeamCity Docker Images for Windows.
 */
object TeamCityScheduledImageBuildWindows : BuildType({
    name = "TeamCity Docker Images - Automated Scheduled Build - Windows"

    vcs {
        root(TeamCityDockerImagesRepo)
    }

    // <image name> - <dockerfile path>
    val targetImages: HashMap<String, String> = hashMapOf(
        // Windows 1809
        "teamcity-server-nanoserver-1809" to "context/generated/windows/Server/nanoserver/1809/Dockerfile",
        "teamcity-minimal-agent-nanoserver-1809" to "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile",
        "teamcity-agent-windowsservercore-1809" to "context/generated/windows/Agent/windowsservercore/1809/Dockerfile",
        "teamcity-agent-nanoserver-1809" to "context/generated/windows/Agent/nanoserver/1809/Dockerfile",

        // Windows 20.04
        "teamcity-server-nanoserver-2004" to "context/generated/windows/Server/nanoserver/2004/Dockerfile",
        "teamcity-minimal-agent-nanoserver-2004" to "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile",
        "teamcity-agent-windowsservercore-2004" to "context/generated/windows/Agent/windowsservercore/2004/Dockerfile",
        "teamcity-agent-nanoserver-2004" to "context/generated/windows/Agent/nanoserver/2004/Dockerfile",
    )

    steps {
        targetImages.forEach { (imageName, dockerfilePath) ->
            dockerCommand {
                name = "build $imageName"
                commandType = build {
                    source = file {
                        path = dockerfilePath
                    }
                    platform = DockerCommandStep.ImagePlatform.Windows
                    contextDir = "context"
                    namesAndTags = "${imageName}:%dockerImage.teamcity.buildNumber%"
                }
            }
        }
    }
})
