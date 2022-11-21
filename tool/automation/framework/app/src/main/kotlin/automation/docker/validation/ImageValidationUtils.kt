package automation.docker.validation

import automation.common.MathUtils
import automation.common.constants.ValidationConstants
import automation.docker.DockerUtils
import automation.teamcity.TeamCityUtils
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.IllegalFormatException

/**
 * Utilities aimed at simplification of Docker Image(-s) validation.
 */
class ImageValidationUtils {
    companion object {

        /**
         * Generates ID of previous TeamCity Docker image assuming the pattern didn't change.
         * WARNING: the function depends on the assumption that tag pattern ...
         * ... is "<year>.<buld number>-<OS>".
         */
        fun getPrevDockerImageId(imageId: String): String? {
            val curImageTag = imageId.split(":")[1]
            val curImageTagElems = curImageTag.split(".")

            if (curImageTagElems.size < 2) {
                // image is highly likely doesn't correspond to pattern
                System.err.println("Unable to auto-determine previous image tag - it doesn't correspond to pattern: $imageId")
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

            // Replace current image's numeric part of tag with determined "old" value, e.g. "2022.04.2-" -> "2022.04.1-"
            val originalImageTagPart = (curImageTagElems[0] + "." + curImageTagElems[1] + "." + imageBuildNum + "-")
            val determinedOldImageTagPart = (curImageTagElems[0] + "." + curImageTagElems[1] + "." + oldBuildNumber + "-")
            val oldImageId = imageId.replace(originalImageTagPart, determinedOldImageTagPart)
            return oldImageId
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

        /**
         * Validates Docker image size.
         * Criteria: it shouldn't increase by more than threshold (@see ALLOWED_IMAGE_SIZE_INCREASE_THRESHOLD_PERCENT).
         * @param currentName - name of original Docker image
         * @param previousName - name of previous Docker image
         * @return true if image size increase suppressed given threshold; false otherwise (including situation when ...
         * ... it wasn't possible to determine any of image sizes)
         */
        fun imageSizeChangeSuppressesThreshold(currentName: String, previousName: String?, threshold: Float): Boolean {
            // -- get size of current image
            val curSize = DockerUtils.getDockerImageSize(currentName)
            TeamCityUtils.reportTeamCityStatistics("SIZE-${ImageValidationUtils.getImageStatisticsId(currentName)}", curSize!!)
            if (curSize == null) {
                System.err.println("Image does not exist on the agent: $currentName")
                return false
            }

            if (previousName.isNullOrBlank()) {
                return false
            }

            // -- get size of previous image
            val prevImagePullSucceeded = DockerUtils.pullDockerImageWithRetry(previousName, 2)
            val prevSize = DockerUtils.getDockerImageSize(previousName)
            if (!prevImagePullSucceeded || prevSize == null) {
                System.err.println("Unable to get size of previous image: $previousName")
                return false
            }

            // -- calculates image increase & notify if exceeds threshold
            val percentageIncrease = MathUtils.getPercentageIncrease(curSize, prevSize)
            return (percentageIncrease > threshold)
        }

        /**
         * Validates Docker Image.
         * @param imageName - image to be validated
         * @param prevImageName - (optional) previous Docker image
         * @return true if image matches each criteria
         */
        fun validateSize(imageName: String, prevImageName: String = ""): Boolean {
            // -- previous image name was not explicitly specified => try to determine automatically (by pattern)
            val previousImage = if (!prevImageName.isEmpty()) prevImageName else getPrevDockerImageId(imageName)
            return !imageSizeChangeSuppressesThreshold(imageName, previousImage, ValidationConstants.ALLOWED_IMAGE_SIZE_INCREASE_THRESHOLD_PERCENT)
        }
    }
}
