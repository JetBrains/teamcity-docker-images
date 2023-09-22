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

    this.dockerCommand {
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

/**
 * Ensures build number within Linux-based Docker Image matches expected the expected one (including no 'EAP' postfix).
 */
fun BuildSteps.verifyBuildNumInLinuxImage(image: ImageInfo, tcBuildNum: String = "%dockerImage.teamcity.buildNumber%") {
    val buildId = "BUILD_${tcBuildNum}"
    val tcInstalLDir = "/opt/teamcity"
    val filePath = "${tcInstalLDir}/${buildId}"
    this.script {
        name = "Sanity Check of Build Number inside Linux-based Docker Images"
        scriptContent = """
            #!/bin/bash
            if [ ! -f "$filePath" ]; then
                echo "The file with build ID '${buildId}' does not exist in '$filePath'."
                exit 1
            fi
            
            # Regular expression: look for version ending with "EAP", e.g. "2023.05.4 EAP", "2023.05 EAP"
            if grep -qrE "${getEapLookupRegex()}" ${tcInstalLDir} ; then
                echo "'EAP' notation had been found. Please, remove it to proceed the publication to DockerHub."
                exit 1
            else
        """.trimIndent()
        formatStderrAsError = true
        dockerImage = image.stagingFqdn
        dockerPull = true
    }
}

/**
 * Ensures build number within Windows-based Docker Image matches expected the expected one (including no 'EAP' postfix).
 */
fun BuildSteps.verifyBuildNumInWindowsImage(image: ImageInfo, tcBuildNum: String = "%dockerImage.teamcity.buildNumber%") {
    val buildId = "BUILD_${tcBuildNum}"
    val tcInstallDir = "C:\\TeamCity"
    val bldFilePath = "${tcInstallDir}\\${buildId}"

    this.script {
        name = "Sanity Check of Build Number inside Windows-based Docker Images"
        scriptContent = """
            if (!(Test-Path -Path "${bldFilePath}" -PathType Leaf)) {
                Write-Output "The file '${buildId}' does not exist in '${bldFilePath}'."
                exit 1
            }
            
            ${'$'}regex = "${getEapLookupRegex()}"
            ${'$'}items = Get-ChildItem -Path "${tcInstallDir}" -Recurse | Select-String -Pattern ${'$'}regex
            if (${'$'}items) {
                Write-Host "'EAP' notation had been found in ${tcInstallDir}. Please, remove it to proceed the publication to DockerHub."
                exit 1
            }
        """.trimIndent()
        formatStderrAsError = true
        dockerImage = image.stagingFqdn
        dockerPull = true
    }
}

/**
 * Returns regualr expression for lookup of 'EAP' notation - look for version ending with "EAP", e.g. ...
 * ... "2023.05.4 EAP", "2023.05 EAP"
 */
private fun getEapLookupRegex(): String {
    return "[0-9]+[.][0-9]{2}(.1)? EAP"
}
