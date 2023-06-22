package hosted.utils.steps

import hosted.utils.Utils
import hosted.utils.models.ImageInfo
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildSteps
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

/**
 *
 * The file contains wrappers across build steps that'd be useful in context of Docker Image creation.
 *
 */


/**
 * Build given image: sets context, builds it, adds tag.
 */
fun BuildSteps.buildImage(imageInfo: ImageInfo) {
    this.script {
        name = "Set build context for [${imageInfo.name}]"
        scriptContent = Utils.getDockerignoreCtx(imageInfo)
    }

    this.dockerCommand {
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

    this.dockerCommand {
        name = "Tag image for staging [${imageInfo.baseFqdn}]"
        commandType = other {
            subCommand = "tag"
            commandArgs = "${imageInfo.baseFqdn} ${imageInfo.stagingFqdn}"
        }
    }
}

/**
 * Publishes provided image into its registry.
 */
fun BuildSteps.publishToStaging(imageInfo: ImageInfo) {
    this.dockerCommand {
        name = "Push image to registry - [${imageInfo.stagingFqdn}]"
        commandType = push {
            namesAndTags = imageInfo.stagingFqdn.trimIndent()
            removeImageAfterPush = false
        }
    }
}

/**
 * Build and publishes given Docker image.
 * @param imageInfo information about Docker image
 */
fun BuildSteps.buildAndPublishImage(imageInfo: ImageInfo) {
    buildImage(imageInfo)
    publishToStaging(imageInfo)
}

/**
 * Moves image from one registry to another.
 * @param image information about the image
 * @param oldRegistry
 */
fun BuildSteps.modifyTagAndPush(image: ImageInfo, oldRegistry: String, newRegistry: String) {
    val productionFqdn: String = image.stagingFqdn.replace(oldRegistry, newRegistry)

    this.dockerCommand {
        name = "Pull image [${image.name}] for further re-tagging"
        commandType = other {
            subCommand = "pull"
            commandArgs = image.stagingFqdn
        }
    }

    this.dockerCommand {
        name = "Re-tag image [${image.name}] for publishing into [${newRegistry}]"
        commandType = other {
            subCommand = "tag"
            commandArgs = "${image.stagingFqdn} $productionFqdn"
        }
    }

    this.dockerCommand {
        name =  "Publish [${productionFqdn}] after re-tag"
        commandType = push {
            namesAndTags = productionFqdn
        }
    }
}
