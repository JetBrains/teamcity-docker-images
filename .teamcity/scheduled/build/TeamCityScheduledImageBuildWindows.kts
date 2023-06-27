package scheduled.build

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import scheduled.build.model.DockerImageInfo
import utils.dsl.general.teamCityBuildDistDocker
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.DockerCommandStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import java.util.*

/**
 * Scheduled build of TeamCity Docker Images for Windows.
 */
object TeamCityScheduledImageBuildWindows : BuildType({

    name = "TeamCity Docker Images - Automated Scheduled Build - Windows"

    vcs {
        root(TeamCityDockerImagesRepo)
    }


    val images = LinkedList(
        listOf(
            // Windows 18.09
            // -- Windows 18.09 minimal agents
            DockerImageInfo(
                "teamcity-minimal-agent",
                "EAP-nanoserver-1809",
                "context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile"
            ),
            // -- Windows 18.09 core, build based on Minimal Agents
            DockerImageInfo(
                "teamcity-agent",
                "EAP-windowsservercore-1809",
                "context/generated/windows/Agent/windowsservercore/1809/Dockerfile"
            ),
            // -- 18.09 NanoServer, build based on 18.09 Windows Server Core Agents
            DockerImageInfo(
                "teamcity-agent",
                "EAP-nanoserver-1809",
                "context/generated/windows/Agent/nanoserver/1809/Dockerfile"
            ),
            DockerImageInfo(
                "teamcity-server",
                "EAP-nanoserver-1809",
                "context/generated/windows/Server/nanoserver/1809/Dockerfile"
            ),


            // Windows 20.04
            // -- Windows 20.04 minimal agent
            DockerImageInfo(
                "teamcity-minimal-agent",
                "EAP-nanoserver-2004",
                "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile"
            ),

            // -- Windows 20.04 agents
            DockerImageInfo(
                "teamcity-agent",
                "EAP-windowsservercore-2004",
                "context/generated/windows/Agent/windowsservercore/2004/Dockerfile"
            ),

            // -- Windows 20.04 nanoservers
            DockerImageInfo(
                "teamcity-server",
                "EAP-nanoserver-2004",
                "context/generated/windows/Server/nanoserver/2004/Dockerfile"
            ),
            DockerImageInfo(
                "teamcity-agent",
                "EAP-nanoserver-2004",
                "context/generated/windows/Agent/nanoserver/2004/Dockerfile"
            )
        )
    )

    steps {

        images.forEach { imageInfo ->
            dockerCommand {
                name = "build ${imageInfo.repository}-${imageInfo.tag}"
                commandType = build {
                    platform = DockerCommandStep.ImagePlatform.Windows
                    source = file {
                        path = imageInfo.dockerfilePath
                    }
                    contextDir = "context"
                    namesAndTags = "${imageInfo.repository}:${imageInfo.tag}"
                }
            }
        }
    }

    dependencies {
        teamCityBuildDistDocker()
        dependency(AbsoluteId("TC_Trunk_BuildDistDocker")) {
            artifacts {
                artifactRules = "TeamCity.zip!/**=>context/TeamCity"
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

    // An implicit Windoiws 10 requirement has been added in order to prevent DotNet's WebClient internal exception.
    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows 10")
    }
})
