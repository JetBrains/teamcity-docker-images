package com.jetbrains.teamcity.docker.validation

import com.jetbrains.teamcity.common.MathUtils
import com.jetbrains.teamcity.docker.DockerImage
import com.jetbrains.teamcity.docker.hub.DockerRegistryAccessor
import com.jetbrains.teamcity.docker.hub.data.DockerRepositoryInfo
import com.jetbrains.teamcity.docker.hub.data.DockerhubImage
import com.jetbrains.teamcity.teamcity.TeamCityUtils


/**
 * Utilities aimed at simplification of Docker Image(-s) validation.
 */
class DockerImageValidationUtilities {
    companion object {

        /**
         * Validates the size of Docker Image.
         * 1. Retrieves data about original image from Dockerhub. The provided repository-tag pair might correspond to ...
         * ... multiple corresponding images (OS, OS Version, Architecture) - they will be retrieved as well.
         * 2. Reports the sizes of all corresponding images into standart output as TeamCity Service Messages.
         * 3. Attempts to retrieve previous image from Dockerhub using its REST API and timestamps.
         * 4. Compare the size of each corresponding image.
         * @param originalImageFqdn fully-qualified domain name of the original image
         * @param registryUri URI of Docker Registry where image is placed
         * @returns list of assosiated images that didn't pass the validation.
         */
        fun validateImageSize(originalImageFqdn: String, registryUri: String, threshold: Float): ArrayList<DockerhubImage> {
            val registryAccessor = DockerRegistryAccessor(registryUri)
            val currentImage = DockerImage(originalImageFqdn)
            val imagesFailedValidation = ArrayList<DockerhubImage>()

            // -- all images associated with registry-tag pair
            val originalImageRegistryInfo = registryAccessor.getRegistryInfo(currentImage)
            originalImageRegistryInfo.images.forEach { assosiatedImage ->
                // -- report size for each image
                // TODO: update documentation with "OS" reference
                TeamCityUtils.reportTeamCityStatistics("SIZE-${DockerImageValidationUtilities.getImageStatisticsId(currentImage.toString())}", assosiatedImage.size)

                // -- compare image
                val dockerHubInfoOfPreviousRelease: DockerRepositoryInfo? = registryAccessor.getPreviousImages(currentImage,
                                                                                                        assosiatedImage.os,
                                                                                                        assosiatedImage.osVersion) ?: null
                if (dockerHubInfoOfPreviousRelease == null || dockerHubInfoOfPreviousRelease.images.size != 1) {
//                    throw IllegalStateException("Unable to determine previous image for $originalImageFqdn-${assosiatedImage.os}")
                    println("Unable to determine previous image for $originalImageFqdn-${assosiatedImage.os}")
                    return@forEach
                }

                // -- we will always have only 1 corresponding image, due to extensive criteria
                val previousImage = dockerHubInfoOfPreviousRelease.images.first()
                val percentageChange = MathUtils.getPercentageIncrease(assosiatedImage.size.toLong(), previousImage.size.toLong())
                println("$originalImageFqdn-${assosiatedImage.os}-${assosiatedImage.osVersion}-${assosiatedImage.architecture}: "
                        + "\n\t - Original size: ${assosiatedImage.size} ($originalImageFqdn)"
                        + "\n\t - Previous size: ${previousImage.size} (${dockerHubInfoOfPreviousRelease.name})"
                        + "\n\t - Percentage change: ${percentageChange}\n")
                if (percentageChange > threshold) {
                    imagesFailedValidation.add(assosiatedImage)
                } else {
                    print("Validation succeeded for $originalImageFqdn-${assosiatedImage.os}")
                }
            }
            return imagesFailedValidation
        }

        /**
         * Generates ID of previous TeamCity Docker image assuming the pattern didn't change.
         * WARNING: the function depends on the assumption that tag pattern ...
         * ... is "<year>.<month number>-<OS>".
         */
        fun getPrevDockerImageId(curImage: DockerImage): DockerImage? {
            val curImageTagElems = curImage.tag.split(".")
            if (curImageTagElems.size < 2) {
                // image is highly likely doesn't correspond to pattern
                System.err.println("Unable to auto-determine previous image tag - it doesn't correspond to pattern: $curImage")
                return null
            }

            // handling 2 types: 2022.04-OS and 2022.04.2-OS
            val isMinorRelease = curImageTagElems.size > 2

            if (!isMinorRelease) {
                System.err.println("Automatic determination of previous release is supported only for minor version of TeamCity.")
                return null
            }

            val imageBuildNum = curImageTagElems[2].split("-")[0]

            // -- construct old image tag based on retrieved information from the current one
            // -- -- adding "0" since build number has at least 2 digits
            val oldBuildNumber = Integer.parseInt(imageBuildNum) - 1

            val originalImageTagPart = (curImageTagElems[0] + "." + curImageTagElems[1] + "." + imageBuildNum + "-")
            val determinedOldImageTagPart = (curImageTagElems[0] + "." + curImageTagElems[1] + "." + oldBuildNumber + "-")

            // Replace current image's numeric part of tag with determined "old" value, e.g. "2022.04.2-" -> "2022.04.1-"
            return DockerImage(curImage.repo, curImage.tag.replace(originalImageTagPart, determinedOldImageTagPart))
        }

        /**
         * Returns image ID for statistics within TeamCity. ID consists of image name with removed repository and release.
         * Example: "some-registry.example.io/teamcity-agent:2022.10-windowsservercore-1809" -> "teamcity-agent-windowsservercore-1809"
         * Purpose: let it be possible to compare different images regardless of the release.
         * @param image - Docker image FQDN;
         * @return ID of an image for TeamCity statistics
         */
        fun getImageStatisticsId(image: String): String {
            // 1. Remove registry
            val imageRegistryElements = image.split('/')
            val imageNameNoRegistry = imageRegistryElements[imageRegistryElements.size - 1]

            // 2. Remove release from tag
            val imageTagElements = imageNameNoRegistry.split(':')[1].split('-')
            // remove tag
            return imageNameNoRegistry.replace("${imageTagElements[0]}-", "")

        }
    }
}
