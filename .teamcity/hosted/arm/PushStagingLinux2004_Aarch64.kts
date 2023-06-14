package generated

import common.TeamCityDockerImagesRepo
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

    /**
     * Holds data about the building docker image.
     */
    data class ImageInfo(
        val name: String,
        val dockerfilePath: String,
        // 'baseFqdn' - basic image domain name, could be used as a reference within Dockerfile (e.g. for base image)
        // 'stagingFqdn' - domain name of the image, including the registry, which will be used for deployment
        val baseFqdn: String,
        val stagingFqdn: String
    )

    val imageInfoContainer = listOf<ImageInfo>(
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
                scriptContent = """
                echo 2> context/.dockerignore
                echo TeamCity >> context/.dockerignore
                """.trimIndent()
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
                dockerRegistryId = "PROJECT_EXT_774"
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
