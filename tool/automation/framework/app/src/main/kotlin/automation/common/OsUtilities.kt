package automation.common

import java.util.concurrent.TimeUnit

/**
 * Utilities dedicated to interaction with OS utilities.
 */
class OsUtilities {

    companion object {
        /**
         * Executes command.
         * @param command - command to be execution
         * @param redirectStderr - indicates if error output must be captured along with ...
         * ... stdout
         * @return result of command's execution ; null in case of exception
         */
        @JvmStatic
        fun executeCommand(command: String, redirectStderr: Boolean = true, timeoutSec: Long = 60): String? {
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
                builder.start().apply { waitFor(timeoutSec, TimeUnit.SECONDS) }
                    .inputStream.bufferedReader().readText()
            }.onFailure {
                it.printStackTrace()
            }.getOrNull()
        }
    }

}
