package automation.docker.validation

import DockerImageValidationException
import automation.common.MathUtils
import automation.common.constants.ValidationConstants
import automation.docker.DockerUtilities
import automation.teamcity.TeamCityUtils

class ImageValidationUtils {
    companion object {


        /**
         * Generates ID of previous TeamCity Docker image assuming the pattern didn't change.
         * WARNING: the function depends on the assumption that tag pattern ...
         * ... is "<year>.<buld number>-<OS>".
         */
        fun getPrevDockerImageId(imageId: String): String {
            var curImageTag = imageId.split(":")[1]
            var curImageTagElems = curImageTag.split(".")

            if (curImageTagElems.size < 2) {
                // image is highly likely doesn't correspond to pattern
                throw IllegalArgumentException("Unable to auto-determine previous image tag - it doesn't correspond to pattern: $imageId")
            }

            // handling 2 types: 2022.04-OS and 2022.04.2-OS
            val isMinorRelease = curImageTagElems.size > 2

            var imageBuildNum = if (isMinorRelease) curImageTagElems[2].split("-")[0]
            else curImageTagElems[1].split("-")[0]

            var oldBuildNumber = Integer.parseInt(imageBuildNum) - 1

            // -- construct old image tag based on retrieved information from the current one
            // -- -- adding "0" since build number has at least 2 digits
            val oldBuildNumString = if (oldBuildNumber < 10 && !isMinorRelease) ("0" + oldBuildNumber)
            else oldBuildNumber

            // Replace current image's numeric part of tag with determined "old" value, e.g. "2022.04.2-" -> "2022.04.1-"
            val originalImageTagPart = if (isMinorRelease) (curImageTagElems[0] + "." + curImageTagElems[1] + "." + imageBuildNum + "-")
            else (curImageTagElems[0] + "." + imageBuildNum + "-")
            val determinedOldImageTagPart = if (isMinorRelease)  (curImageTagElems[0] + "." + curImageTagElems[1] + "." + oldBuildNumString + "-")
            else (curImageTagElems[0] + "." + oldBuildNumString + "-")

            val oldImageId = imageId.replace(originalImageTagPart, determinedOldImageTagPart)
            return oldImageId
        }




        /**
         * Validates Docker image size.
         * Criteria: it shouldn't increase by more than threshold (@see ALLOWED_IMAGE_SIZE_INCREASE_THRESHOLD_PERCENT).
         * @param currentName - name of original Docker image
         * @param previousName - name of previous Docker image
         * @return true if image size increase suppressed given threshold; false otherwise (including situation when ...
         * ... it wasn't possible to determine any of image sizes)
         */
        fun imageSizeChangeSuppressesThreshold(currentName: String, previousName: String, threshold: Float): Boolean {
            // -- get size of current image
            val curSize = DockerUtilities.getDockerImageSize(currentName)
            if (curSize == null) {
                System.err.println("Image does not exist on the agent: $currentName")
                return false
            }

            // -- report image size to TeamCity
            TeamCityUtils.reportTeamCityStatistics("SIZE-$currentName", curSize)

            // -- get size of previous image
            val prevImagePullSucceeded = DockerUtilities.pullDockerImage(previousName)
            val prevSize = DockerUtilities.getDockerImageSize(previousName)
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
        fun validate(imageName: String, prevImageName: String = ""): Boolean {

            var previousImage = prevImageName
            if (previousImage.isEmpty()) {
                // -- previous image name was not explicitly specified => try to determine automatically )by pattern)
                try {
                    previousImage = getPrevDockerImageId(imageName)
                } catch (ex: IndexOutOfBoundsException) {
                    throw java.lang.IllegalArgumentException(
                        "Unable to determine previous image tag from given ID: $imageName \n" +
                                "Expected image name pattern: \"<year>.<build number>-<OS>\""
                    )
                }
            }

            return imageSizeChangeSuppressesThreshold(imageName, previousImage, ValidationConstants.ALLOWED_IMAGE_SIZE_INCREASE_THRESHOLD_PERCENT)
//            if (imageSizeChangeSuppressesThreshold) {
//                throw DockerImageValidationException("Image $imageName size compared to previous ($prevImageName) " +
//                        "suppresses ${ValidationConstants.ALLOWED_IMAGE_SIZE_INCREASE_THRESHOLD_PERCENT}% threshold.")
//            }
        }
    }
}