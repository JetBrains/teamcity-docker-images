package com.jetbrains.teamcity.docker.validation

import com.jetbrains.teamcity.docker.DockerImage


/**
 * Utilities aimed at simplification of Docker Image(-s) validation.
 */
class ImageValidationUtils {
    companion object {

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
