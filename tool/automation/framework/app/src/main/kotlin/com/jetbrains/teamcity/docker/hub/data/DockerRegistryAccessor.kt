package com.jetbrains.teamcity.docker.hub.data

import com.jetbrains.teamcity.common.network.HttpRequestUtilities
import com.jetbrains.teamcity.docker.DockerImage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Provides access to Docker registry.
 */
class DockerRegistryAccessor {

    private val uri: String
    private val jsonSerializer: Json

    /**
     * Creates DockerRegistryAccessor instance.
     * @param uri - Docker Registry URI
     */
    constructor(uri: String) {
        this.uri = uri
        this.jsonSerializer = Json {
            // -- remove the necessity to include parsing of unused fields
            ignoreUnknownKeys = true;
            // -- parse JSON fields that don't have an assigned serializer into a String, e.g.: Number
            isLenient = true
        }
    }

    /**
     * Retrieves the size of Docker image
     */
    public fun getSize(image: DockerImage): String {
        return this.getRegistryInfo(image).fullSize
    }

    /**
     * Returns general information about Docker Registry.
     */
    public fun getRegistryInfo(image: DockerImage): DockerRepositoryInfo {
        val registryResponse: String = HttpRequestUtilities.performGetRequest("${this.uri}/repositories/${image.repo}/tags/${image.tag}") ?: ""
        return jsonSerializer.decodeFromString(registryResponse)
    }
}