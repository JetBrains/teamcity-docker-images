package com.jetbrains.teamcity.docker

import com.jetbrains.teamcity.common.OsUtils
import com.jetbrains.teamcity.docker.exceptions.DockerImageValidationException

/**
 * Utilities for working with Docker images using its CLI.
 */
class DockerCliUtilities {

    companion object {

        /**
         * Tries to pull Docker image from registry.
         * @param image - Docker image to be pulled
         * @return true if image had been successfully pulled, false otherwise
         */
        fun pullDockerImage(image: DockerImage): Boolean {
            val cmdResult = OsUtils.executeCommand("docker pull $image", true) ?: ""
            println("Pulling $image ... \n $cmdResult")
            // using success messages since some errors from docker daemon (e.g. invalid platform type) are not ...
            // ... captured by Kotlin's ProcessBuilder.
            val successMessages = arrayOf("Pull complete", "Image is up to date", "Downloaded newer", "Download complete")
            return successMessages.any { cmdResult.contains(it, ignoreCase = true) }
        }

        /**
         * Pulls Docker image with certain amount of retries. Purpose: handle potential communication issues with ...
         * ... Docker agent.
         * @param image - Docker Image to be pulled
         * @param retryCount - amount of total attempts to pull the image
         * @param delayMillis - delay between attempts
         */
        fun pullDockerImageWithRetry(image: DockerImage, retryCount: Int, delayMillis: Long = 1000): Boolean {
            var pullSucceeded = false
            var attempts = retryCount
            while (attempts > 0) {
                pullSucceeded = pullDockerImage(image)
                if (pullSucceeded) {
                    break
                }
                attempts--
                Thread.sleep(delayMillis)
            }
            return pullSucceeded
        }

        /**
         * Retrieves Docker Image size using Docker CLI.
         * @param image - docker image whose size shall be inspected
         * @return image size in bytes, null in case image does not exist
         */
        fun getDockerImageSize(image: DockerImage): Int? {
            // ensure image exists
            if (!dockerImageExists(image)) {
                val imgPullSucceeded: Boolean = pullDockerImageWithRetry(image, 2)
                if (!imgPullSucceeded) {
                    throw DockerImageValidationException("Image does not exist neither on agent, nor within registry: $image")
                }
            }

            val cmdResult = OsUtils.executeCommand("docker inspect -f \"{{ .Size }}\" $image", true)
            return try {
                // remove quotes from result string
                val imageSizeStr = cmdResult.toString().trim().replace("^\"|\"$".toRegex(), "")
                Integer.parseInt(imageSizeStr)
            } catch (ex: Exception) {
                System.err.println("Unable to convert size of image into an integer number: $cmdResult $ex")
                null
            }
        }

        /**
         * Checks if Docker image exists on agent.
         * @param image Docker image whose existence will be checked
         * @return true if image exists, false otherwise
         */
        private fun dockerImageExists(image: DockerImage): Boolean {
            val cmdResult = OsUtils.executeCommand("docker images -q $image", true) ?: return false
            return cmdResult.isNotEmpty()
        }
    }
}
