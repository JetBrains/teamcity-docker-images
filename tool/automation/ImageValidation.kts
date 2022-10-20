import java.lang.Exception
import java.lang.NumberFormatException
import java.lang.System
import java.lang.Void
import java.util.concurrent.TimeUnit

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

    // TODO: change condition
    if (curSize < prevSize) {
        // TODO: change exception type
        throw java.lang.Exception("image size is higher")
    }
    println("CurSize: $curSize, prevSize: $prevSize")
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
