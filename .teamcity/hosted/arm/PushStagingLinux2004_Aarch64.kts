package generated

import common.TeamCityDockerImagesRepo
import hosted.utils.Utils
import hosted.utils.models.ImageInfo
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.FailureAction
import jetbrains.buildServer.configs.kotlin.v2019_2.ReuseBuilds
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

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

    val imageInfoContainer = linkedSetOf<ImageInfo>(
        // Minimal Agents
        ImageInfo(
            "teamcity-minimal-agent:%tc.image.version%-linux-arm64",
            "context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile",
            "teamcity-minimal-agent:%tc.image.version%-linux-arm64",
            "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:%tc.image.version%-linux-arm64"
        ),

        // Regular Agents
        ImageInfo(
            "teamcity-agent:%tc.image.version%-linux-arm64",
            "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile",
            "teamcity-agent:%tc.image.version%-linux-arm64",
            "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-linux-arm64"
        ),
        ImageInfo(
            "teamcity-agent:%tc.image.version%-linux-arm64-sudo",
            "context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile",
            "teamcity-agent:%tc.image.version%-linux-arm64-sudo",
            "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-linux-arm64-sudo"
        ),

        // Servers
        ImageInfo(
            "teamcity-server:%tc.image.version%-linux-arm64",
            "context/generated/linux/Server/UbuntuARM/20.04/Dockerfile",
            "teamcity-server:%tc.image.version%-linux-arm64",
            "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:%tc.image.version%-linux-arm64"
        ),
        ImageInfo(
            "teamcity-server:%tc.image.version%-linux-arm64-sudo",
            "context/generated/linux/Server/UbuntuARM/20.04-sudo/Dockerfile",
            "teamcity-server:%tc.image.version%-linux-arm64-sudo",
            "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:%tc.image.version%-linux-arm64-sudo"
        ),
    )

    steps {
        dockerCommand {
            name = "pull ubuntu:20.04"
            commandType = other {
                subCommand = "pull"
                commandArgs = "ubuntu:20.04"
            }
        }

        imageInfoContainer.forEach { imageInfo ->

            script {
                name = "Set build context for [${imageInfo.name}]"
                scriptContent = Utils.getDockerignoreCtx(imageInfo)
            }

            dockerCommand {
                name = "Build [${imageInfo.name}]"
                commandType = build {
                    source = file {
                        path = imageInfo.dockerfilePath
                    }
                    contextDir = "context"
                    commandArgs = "--no-cache"
                    namesAndTags = imageInfo.baseFqdn.trimIndent()
                }
                param("dockerImage.platform", "linux")
            }

            dockerCommand {
                name = "Tag image for staging [${imageInfo.baseFqdn}]"
                commandType = other {
                    subCommand = "tag"
                    commandArgs = "${imageInfo.baseFqdn} ${imageInfo.stagingFqdn}"
                }
            }

            dockerCommand {
                name = "Push image to registry - [${imageInfo.stagingFqdn}]"
                commandType = push {
                    namesAndTags = imageInfo.stagingFqdn.trimIndent()
                    removeImageAfterPush = false
                }
            }
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
//        dependencies {
//            dependency(AbsoluteId("TC_Trunk_BuildDistDocker")) {
//                artifacts {
//                    artifactRules = "TeamCity.zip!/**=>context/TeamCity"
//                    cleanDestination = true
//                    lastSuccessful()
//                }
//            }
//        }
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
