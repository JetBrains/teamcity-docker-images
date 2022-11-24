package com.jetbrains.teamcity.docker.hub.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Define response for a particular image
 */
@Serializable
class DockerRepositoryInfo {
    lateinit var images: List<DockerhubImage>
    lateinit var name: String

    @SerialName("full_size")
    lateinit var fullSize: String

    // TODO: Try to determine if "tag_created" could be fetched

    // Format: "2022-10-27T18:54:14.359296Z"
    @SerialName("tag_last_pushed")
    lateinit var tagLastPushed: String
}
