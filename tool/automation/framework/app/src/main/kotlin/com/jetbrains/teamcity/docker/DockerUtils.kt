package com.jetbrains.teamcity.docker

import com.jetbrains.teamcity.common.OsUtils

class DockerUtils {

    companion object {

        /**
         * Tries to pull Docker image from registry.
         * @param name - docker image fully-qualified domain name
         * @return true if image had been successfully pulled, false otherwise
         */
        fun pullDockerImage(name: String): Boolean {
            val cmdResult = OsUtils.executeCommand("docker pull $name", true) ?: ""
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
                pullSucceeded = pullDockerImage(name)
                if (pullSucceeded) {
                    break
                }
                attempts--
                Thread.sleep(delayMillis)
            }
            return pullSucceeded
        }

        /**
         * Retrieves docker image size.
         * @param name image fully-qualified domain name
         * @return image size in bytes, null in case image does not exist
         */
        fun getDockerImageSize(name: String): Int? {
            // ensure image exists
            if (!dockerImageExists(name)) {
                val imgPullSucceeded: Boolean = pullDockerImageWithRetry(name, 2)
                if (!imgPullSucceeded) {
                    throw DockerImageValidationException("Image does not exist neither on agent, nor within registry: $name")
                }
            }

            var cmdResult = OsUtils.executeCommand("docker inspect -f \"{{ .Size }}\" $name", true)
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
            val cmdResult = OsUtils.executeCommand("docker images -q $name", true) ?: return false
            return cmdResult.isNotEmpty()
        }
    }
}
