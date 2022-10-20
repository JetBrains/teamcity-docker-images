import java.util.concurrent.TimeUnit

/**
 * Executes command.
 * @param command - command to be execution
 * @param redirectStderr - indicates if error output must be captured along with ...
 * ... stdout
 * @return result of command's execution ; null in case of exception
 */
fun executeCommand(command: String, redirectStderr: Boolean, timeoutSec: Long = 60): String? {
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
fun getDockerImageSize(name: String): String? {
    var cmd = "docker inspect -f \"{{ .Size }}\" $name"
    return this.executeCommand(cmd, true) ?: null
}


fun main() {
    var res = this.getDockerImageSize("mcr.microsoft.com/dotnet/core/samples:dotnetapp-buster-slim")
    print(res)
}

main()
