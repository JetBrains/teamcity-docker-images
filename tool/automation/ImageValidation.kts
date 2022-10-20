import java.lang.Exception
import java.lang.NumberFormatException
import java.lang.System
import java.lang.Void
import java.util.concurrent.TimeUnit

/**
 * Target values used for validation purposes.
 */
object ValidationConstants {
    const val ALLOWED_IMAGE_SIZE_INCREASE_THRESHOLD_PERCENT = 5.0
}


/**
 * Mark-up exception class for failed validation of Docker images.
 */
class DockerImageSizeIncreaseException(message: String) : Exception(message)

/**
 * Executes command.
 * @param command - command to be execution
 * @param redirectStderr - indicates if error output must be captured along with ...
 * ... stdout
 * @return result of command's execution ; null in case of exception
 */
fun executeCommand(command: String, redirectStderr: Boolean, timeoutSec: Long = 60): String? {
    println(command)
    return runCatching {
        // -- converting command to list containing the arguments
        val args = command.split(Regex("(?<!(\"|').{0,255}) | (?!.*\\1.*)"))
        var builder = ProcessBuilder(args)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .apply {
                // -- attach stderr-redirecting if required
                if (redirectStderr) {
                    this.redirectError(ProcessBuilder.Redirect.PIPE)
                }
            }

        // -- execute command with timeout
        builder.start().apply {waitFor(timeoutSec, TimeUnit.SECONDS)}
            .inputStream.bufferedReader().readText()
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()
}

/**
 * Calculates percentage increase from initial to final value.
 * @param initial - initial value
 * @param final - final value
 * @return percentage increase
 */
fun getPercentageIncrease(initial: Int, final: Int): Float {
    return ((100f*(final - initial)) / initial)
}


/**
 * Retrieves docker image size.
 * @param name image fully-qualified domain name
 * @return image size in bytes, null in case image does not exist
 */
fun getDockerImageSize(name: String): Int? {
    var cmdResult = this.executeCommand("docker inspect -f \"{{ .Size }}\" $name", true) ?: null
    try {
        // remove quotes from reult string
        val imageSizeStr = cmdResult.toString().trim().replace("^\"|\"$".toRegex(), "")
        return Integer.parseInt(imageSizeStr)

    } catch (ex: Exception) {
        System.err.println("Unable to convert size of image into an integer number: $cmdResult $ex")
        return null
    }
}


/**
 * Generates ID of previous TeamCity Docker image assuming the pattern didn't change.
 * WARNING: the function depends on the assumption that tag pattern ...
 * ... is "<year>.<buld number>-<OS>".
 * TODO: Handle potential minor releases, e.g. 2022.04.1, 2022.04.4, etc.
 */
fun getPrevDockerImageId(imageId: String): String {
    // TODO: surround with try-catch
    var curImageTag = imageId.split(":")[1]
    var curImageTagElems = curImageTag.split(".")

    // handling 2 types: 2022.04-OS and 2022.04.2-OS
    val isMinorRelease = curImageTagElems.size > 2


    var imageBuildNum = if (isMinorRelease) curImageTagElems[2].split("-")[0] else curImageTagElems[1].split("-")[0]

    var oldBuildNumber = Integer.parseInt(imageBuildNum) - 1

    // -- construct old image tag based on retrieved information from the current one
    // -- -- adding "0" since build number has at least 2 digits
    val oldBuildNumString = if (oldBuildNumber < 10 && !isMinorRelease) ("0" + oldBuildNumber) else oldBuildNumber

    // TODO: CHANGE IF MINOR RELEASE
    val originalImageValue = if (isMinorRelease) (curImageTagElems[0] + "." + curImageTagElems[1] + "." + imageBuildNum + "-") else (curImageTagElems[0] + "." + imageBuildNum + "-")
    val oldImageValue = if (isMinorRelease)  (curImageTagElems[0] + "." + curImageTagElems[1] + "." + oldBuildNumString + "-") else (curImageTagElems[0] + "." + oldBuildNumString + "-")

    println(originalImageValue)
    val oldImageId = imageId.replace(originalImageValue, oldImageValue)
    return oldImageId
}


/**
 * Tries to pull Docker image from registry.
 * @param name - docker image fully-qualified domain name
 * @return true if image had been successfully pulled, false otherwise
 */
fun pullDockerImage(name: String): Boolean {
    val res = this.executeCommand("docker pull $name", true)
    if (res == null) {
        return false
    }
    return !(res.contains("Error response from daemon", ignoreCase = true))
}


/**
 * Validates Docker image size.
 * Criteria: it shouldn't increase by more than threshold (@see ALLOWED_IMAGE_SIZE_INCREASE_THRESHOLD_PERCENT).
 */
fun verifyImageSizeRegression(currentName: String, previousName: String) {
    // -- get size of current image
    val curSize = this.getDockerImageSize(currentName)
    if (curSize == null) {
        println("Image does not exist on the agent: $currentName \n Perhaps image tag was not specified?")
        return
    }

    // -- get size of previous image
    val prevImagePullSucceeded = this.pullDockerImage(previousName)
    val prevSize = this.getDockerImageSize(previousName)
    if (!prevImagePullSucceeded || prevSize == null) {
        println("Unable to get size of previous image: $previousName")
        return
    }

    // -- calculates image increase & notify if exceeds threshold
    val percentageIncrease = this.getPercentageIncrease(curSize, prevSize)
    if (percentageIncrease > ValidationConstants.ALLOWED_IMAGE_SIZE_INCREASE_THRESHOLD_PERCENT) {
        System.err.println("Unexpected percentage increase in image size: $percentageIncrease")
        throw DockerImageSizeIncreaseException("image size is higher")
    }

}


fun main(args: Array<String>) {
    if (args.size < 2) {
        throw IllegalArgumentException("Not enough CLI arguments.")
    }
    val imageName = args[0]
    val prevImageName = args[1]
    verifyImageSizeRegression(imageName, prevImageName)
}

//  kotlinc -script tool/automation/ImageValidation.kts mcr.microsoft.com/dotnet/core/samples:dotnetapp-buster-slim
main(args)
