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
 * Updates staging tag for given image.
 */
fun BuildSteps.changeStagingTag(image: ImageInfo, tag: String, newTag: String) {
    this.dockerCommand {
        name = "Re-tag image [${image.name}]"
        commandType = other {
            subCommand = "tag"
            commandArgs = "${image.stagingFqdn} ${image.stagingFqdn.replace(tag, newTag)}"
        }
    }
}

/**
 * Moves given image from staging into production repository.
 */
fun BuildSteps.moveToProduction(image: ImageInfo) {
    this.dockerCommand {
        name = "Pull image [${image.name}] for further re-tagging"
        commandType = other {
            subCommand = "pull"
            commandArgs = image.stagingFqdn
        }
    }

    this.dockerCommand {
        name = "Re-tag image [${image.name}] for publishing into [${image.productionFqdn}]"
        commandType = other {
            subCommand = "tag"
            commandArgs = "${image.stagingFqdn} ${image.productionFqdn}"
        }
    }

    this.dockerCommand {
        name =  "Publish [${image.productionFqdn}] after re-tag"
        commandType = push {
            namesAndTags = image.productionFqdn
            // must always be disabled - no need to clear-up images from production registry
            removeImageAfterPush = false
        }
    }
}
