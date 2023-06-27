package utils.dsl.steps

import utils.Utils
import utils.models.ImageInfo
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
 * Build and publishes given Docker image into staging repository.
 */
fun BuildSteps.buildAndPushToStaging(imageInfo: ImageInfo) {
    buildImage(imageInfo)
    publishToStaging(imageInfo)
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

/**
 * Create a manifest list for annotating images & publishes it into registry.
 * @param imageName domain name of image
 * @param tags list of tags that'd be associated with the manifest
 * @param manifestTag tag of the manifest, with which given tags will be associated, e.g. 'latest' TODO: change to "EAP" or teamcity version?
 */
fun BuildSteps.publishManifest(imageName: String, tags: List<String>, manifestTag: String = "latest") {
    // manifest name
    val manifestName = "${imageName}:${manifestTag}"

    val matchingManifests = StringBuilder()
    tags.forEach { tag -> matchingManifests.append(" ${imageName}:$tag")}

    this.dockerCommand {
        name = "Create manifest for [${imageName}]"
        commandType = other {
            subCommand = "manifest"
            // <latest> - <all other manifests>
            commandArgs = "create $manifestName $matchingManifests"
        }
    }

    this.dockerCommand {
        name = "Push manifest for [${imageName}]"
        commandType = other {
            subCommand = "manifest"
            commandArgs = "push $manifestName"
        }
    }

    dockerCommand {
        name = "Print-out manifest for [${imageName}]"
        commandType = other {
            subCommand = "manifest"
            commandArgs = "inspect $manifestName --verbose"
        }
    }
}
