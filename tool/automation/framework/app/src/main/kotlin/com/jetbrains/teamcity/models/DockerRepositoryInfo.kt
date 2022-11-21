package com.jetbrains.teamcity.models

import com.jetbrains.teamcity.models.DockerImage
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
