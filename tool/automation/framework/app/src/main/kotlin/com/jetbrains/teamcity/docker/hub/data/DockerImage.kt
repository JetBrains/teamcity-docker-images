package com.jetbrains.teamcity.docker.hub.data

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class DockerImage {
    lateinit var architecture: String
    lateinit var os: String
    lateinit var digest: String

    // "Contextual" as the large number values (Long, Number, etc.) aren't serializable by Kotlinx
    @Contextual
    lateinit var size: String
}
