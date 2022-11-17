import java.lang.Exception
import java.lang.System
import java.util.concurrent.TimeUnit

/**
 * Target values used for validation purposes.
 */
object ValidationConstants {
    const val ALLOWED_IMAGE_SIZE_INCREASE_THRESHOLD_PERCENT = 5.0f
}

/**
 * Mark-up exception class for failed validation of Docker images.
 */
class DockerImageValidationException(message: String) : Exception(message)


/**
 * Executes command.
 * @param command - command to be execution
 * @param redirectStderr - indicates if error output must be captured along with ...
 * ... stdout
 * @return result of command's execution ; null in case of exception
 */
fun executeCommand(command: String, redirectStderr: Boolean = true, timeoutSec: Long = 60): String? {
    return runCatching {
        // -- converting command to list containing the arguments
        val args = command.split(Regex("(?<!(\"|').{0,255}) | (?!.*\\1.*)"))
        val builder = ProcessBuilder(args)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .apply {
                // -- attach stderr-redirecting if required
                if (redirectStderr) {
                    this.redirectError(ProcessBuilder.Redirect.PIPE)
                }
            }

        // -- execute command with timeout
        builder.start().apply { waitFor(timeoutSec, TimeUnit.SECONDS) }
            .inputStream.bufferedReader().readText()
    }.onFailure {
        println(it.message)
        it.printStackTrace()
    }.getOrNull()
}


/**
 * Calculates modulo percentage increase from initial to final value.
 * @param initial - initial value
 * @param final - final value
 * @return percentage increase
 */
fun getPercentageIncrease(initial: Long, final: Long): Float {
    return Math.abs(((100f*(final - initial)) / initial))
}


/**
 * Retrieves docker image size.
 * @param name image fully-qualified domain name
 * @return image size in bytes, null in case image does not exist
 */
fun getDockerImageSize(name: String): Long? {
    // ensure image exists
    if (!this.dockerImageExists(name)) {
        val imgPullSucceeded: Boolean = this.pullDockerImageWithRetry(name, 2)

        // TODO: Remove - added in debug purposes
        println("Checking the size of image: $name ... \n ${this.executeCommand("docker inspect -f \"{{ .Size }}\" $name", true)}")

        if (!imgPullSucceeded) {
            throw DockerImageValidationException("Image does not exist neither on agent, nor within registry: $name")
        }
    }

    val cmdResult = this.executeCommand("docker inspect -f \"{{ .Size }}\" $name", true)
    println("Checking the size of image: $name ... \n $cmdResult")
    return try {
        // remove quotes from result string
        val imageSizeStr = cmdResult.toString().trim().replace("^\"|\"$".toRegex(), "")
        println("Image size is $imageSizeStr")
        imageSizeStr.toLong()
    } catch (ex: Exception) {
        System.err.println("Unable to convert size of image into an integer number: $cmdResult $ex")
        null
    }
}

fun debugCheckImages(image: String) {
    this.executeCommand("docker manifest inspect $image")
}

/**
 * Checks if Docker image exists on agent.
 * @param name Docker image name
 * @return true if image exists, false otherwise
 */
fun dockerImageExists(name: String): Boolean {
    val cmdResult = this.executeCommand("docker images -q $name", true) ?: return false
    return cmdResult.isNotEmpty()
}


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
        throw IllegalArgumentException("Unable to auto-determine previous image tag - it doesn't correspond to pattern: $imageId")
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
 * Tries to pull Docker image from registry.
 * @param name - docker image fully-qualified domain name
 * @return true if image had been successfully pulled, false otherwise
 */
fun pullDockerImage(name: String): Boolean {
    val cmdResult = this.executeCommand("docker pull $name", true) ?: ""
    println("Pulling $name ... \n $cmdResult")
    // using success messages since some errors from docker daemon (e.g. invalid platform type) are not ...
    // ... captured by Kotlin's ProcessBuilder.
    val successMessages = arrayOf("Pull complete", "Image is up to date", "Downloaded newer", "Download complete")
    return successMessages.any { cmdResult.contains(it, ignoreCase = true) }
}

/**
 * Pulls Docker image with certain amount of retries. Purpose: handle potential communication issues with ...
 * ... Docker agent.
 * @param name - Docker Image fully-qualified domain name
 * @param retryCount - amount of total attempts to pull the image
 * @param delayMillis - delay between attempts
 */
fun pullDockerImageWithRetry(name: String, retryCount: Int, delayMillis: Long = 1000): Boolean {
    var pullSucceeded = false
    var attempts = retryCount
    while (attempts > 0) {
        pullSucceeded = this.pullDockerImage(name)
        if (pullSucceeded) {
            break
        }
        attempts--
        Thread.sleep(delayMillis)
    }
    return pullSucceeded
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
    println("Checking image size via manifest: ${debugCheckImages(currentName)}")
    println(executeCommand("docker images ls --all"))
    val curSize = this.getDockerImageSize(currentName)
    if (curSize == null) {
        System.err.println("Image does not exist on the agent: $currentName")
        return false
    }

    // -- report image size to TeamCity
    this.reportTeamCityStatistics("SIZE-${this.getImageStatisticsId(currentName)}", curSize)

    if (previousName.isNullOrBlank()) {
        return false
    }

    // -- get size of previous image
    val prevImagePullSucceeded = this.pullDockerImageWithRetry(previousName, 2)
    val prevSize = this.getDockerImageSize(previousName)
    if (!prevImagePullSucceeded || prevSize == null) {
        System.err.println("Unable to get size of previous image: $previousName")
        return false
    }

    // -- calculates image increase & notify if exceeds threshold
    val percentageIncrease = this.getPercentageIncrease(curSize, prevSize)
    return (percentageIncrease > threshold)
}

/**
 * Reports statistics to TeamCity via Service Messages.
 * @param key metric ID
 * @param value metricValue
 *
 * TODO: Think about generic 'value' type
 */
fun reportTeamCityStatistics(key: String, value: Long) {
    println("##teamcity[buildStatisticValue key='$key' value='$value']")
}

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        throw IllegalArgumentException("Not enough CLI arguments.")
    }
    val imageName = args[0]

    val prevImageName = if (args.size >= 2) {
        // -- take image name
        args[1]
    } else {
        // -- previous image name was not explicitly specified => try to determine automatically )by pattern)
        try {
            this.getPrevDockerImageId(imageName)
        } catch (ex: IndexOutOfBoundsException) {
            throw IllegalArgumentException("Unable to determine previous image tag from given ID: $imageName \n" +
                    "Expected image name pattern: \"<year>.<build number>-<OS>\"")
        }
    }

    val imageSizeChangeSuppressesThreshold = this.imageSizeChangeSuppressesThreshold(imageName,
                                                                                                prevImageName,
                                                                                                ValidationConstants.ALLOWED_IMAGE_SIZE_INCREASE_THRESHOLD_PERCENT)
    if (imageSizeChangeSuppressesThreshold) {
        throw DockerImageValidationException("Image $imageName size compared to previous ($prevImageName) " +
                "suppresses ${ValidationConstants.ALLOWED_IMAGE_SIZE_INCREASE_THRESHOLD_PERCENT}% threshold.")
    }
}

main(args)
