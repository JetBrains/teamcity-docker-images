package com.jetbrains.teamcity.docker.hub.data

import com.jetbrains.teamcity.docker.DockerImage
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Representation of response for the request through ...
 * ... https://hub.docker.com/v2/repositories/<repository>/<image>/tags
 */
@Serializable
class DockerRegistryImagesInfo {
    @Contextual
    lateinit var count: String
    lateinit var results: List<DockerRepositoryInfo>
}
