package com.jetbrains.teamcity.docker.hub.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Define response for a particular image
 */
@Serializable
class DockerRepositoryInfo {
    lateinit var images: List<DockerImage>
    lateinit var name: String

    @SerialName("full_size")
    lateinit var fullSize: String
}
