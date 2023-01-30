package com.jetbrains.teamcity.docker.hub.data

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Model containing the representation of Docker Image based on DockerHub REST API response.
 */
@Serializable
class DockerhubImage {
    lateinit var architecture: String

    lateinit var os: String

    @Contextual
    @SerialName("os_version")
    var osVersion: String? = null

    lateinit var digest: String

    @Contextual
    lateinit var size: String
}
