package automation.docker

import DockerImageValidationException
import automation.common.OsUtilities

class DockerUtilities {

    companion object {

        /**
         * Tries to pull Docker image from registry.
         * @param name - docker image fully-qualified domain name
         * @return true if image had been successfully pulled, false otherwise
         */
        fun pullDockerImage(name: String): Boolean {
            val cmdResult = OsUtilities.executeCommand("docker pull $name", true) ?: ""

            // using success messages since some errors from docker daemon (e.g. invalid platform type) are not ...
            // ... captured by Kotlin's ProcessBuilder.
            val successMessages = arrayOf("Pull complete", "Image is up to date")
            return successMessages.any { cmdResult.contains(it, ignoreCase = true) }
        }

        /**
         * Retrieves docker image size.
         * @param name image fully-qualified domain name
         * @return image size in bytes, null in case image does not exist
         */
        fun getDockerImageSize(name: String): Int? {
            // ensure image exists
            if (!this.dockerImageExists(name)) {
                val imgPullSucceeded: Boolean = this.pullDockerImage(name)
                if (!imgPullSucceeded) {
                    throw DockerImageValidationException("Image does not exist neither on agent, nor within registry: $name")
                }
            }

            var cmdResult = OsUtilities.executeCommand("docker inspect -f \"{{ .Size }}\" $name", true)
            try {
                // remove quotes from result string
                val imageSizeStr = cmdResult.toString().trim().replace("^\"|\"$".toRegex(), "")
                return Integer.parseInt(imageSizeStr)
            } catch (ex: Exception) {
                System.err.println("Unable to convert size of image into an integer number: $cmdResult $ex")
                return null
            }
        }


        /**
         * Checks if Docker image exists on agent.
         * @param name Docker image name
         * @return true if image exists, false otherwise
         */
        fun dockerImageExists(name: String): Boolean {
            val cmdResult = OsUtilities.executeCommand("docker images -q $name", true)
            if (cmdResult == null) { return false }

            return !cmdResult.isEmpty()
        }
    }
}