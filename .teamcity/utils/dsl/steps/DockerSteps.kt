package utils.dsl.steps

import utils.Utils
import utils.models.ImageInfo
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildSteps
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import utils.ImageInfoRepository
import utils.config.DeliveryConfig

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
 * @param manifestTag tag of the manifest, with which given tags will be associated, e.g. 'latest'
 */
fun BuildSteps.publishManifest(imageName: String, tags: List<String>, manifestTag: String = DeliveryConfig.tcVersion) {
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

/**
 * Publishes manifest for Linux-based images.
 * @param name manifest name (usually, either 'latest' or release tag)
 * @param repo target Docker registry
 * @param postfix (optional) postfix for image name (e.g. "-staging")
 * @param version (optional) image tag, by default equals to manifest name
 */
fun BuildSteps.publishLinuxManifests(name: String, repo: String, postfix: String = "", version: String = "") {
    val ver = version.ifEmpty { name }

    // 1. Publish Server Manifests
    val serverTags = ImageInfoRepository.getAllServerTags(ver)
    publishManifest("${repo}teamcity-server${postfix}", serverTags, name)

    // 2. Publish Agent Manifests
    val agentTags = ImageInfoRepository.getAllAgentTags(ver)
    publishManifest("${repo}teamcity-agent${postfix}", agentTags, name)

    // 3. Publish Minimal Agent Manifests
    val minAgentTags = ImageInfoRepository.getAllMinimalAgentTags(ver)
    publishManifest(
        "${repo}teamcity-minimal-agent${postfix}",
        minAgentTags,
        name
    )

    // 4. Publish Agent-sudo Manifests
    val sudoAgentTags = ImageInfoRepository.getAllSudoAgentTags(ver)
    publishManifest(
        imageName = "${repo}teamcity-agent${postfix}",
        tags = sudoAgentTags,
        manifestTag = "${name}-linux-sudo"
    )
}

/**
 * Publishes manifest for Windows-based images.
 * Currently, only Windows Server Core is published.
 * @param name manifest name (usually, either 'latest' or release tag)
 * @param repo target Docker registry
 * @param postfix (optional) postfix for image name (e.g. "-staging")
 * @param version (optional) image tag, by default equals to manifest name
 */
fun BuildSteps.publishWindowsManifests(name: String, repo: String, postfix: String = "", version: String = "") {
    val agentTagsWinServerCore = ImageInfoRepository.getWindowsCoreAgentTags(version)
    publishManifest(
        "${repo}teamcity-agent${postfix}",
        agentTagsWinServerCore,
        "${name}-windowsservercore"
    )
}
