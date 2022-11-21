package com.jetbrains.teamcity.docker.exceptions

/**
 * Mark-up exception class for failed validation of Docker images.
 */
class DockerImageValidationException(message: String) : Exception(message)
