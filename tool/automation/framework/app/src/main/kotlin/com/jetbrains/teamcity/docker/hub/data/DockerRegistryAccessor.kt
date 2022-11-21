package com.jetbrains.teamcity.docker.hub.data

import com.jetbrains.teamcity.common.network.HttpRequestUtilities
import com.jetbrains.teamcity.docker.DockerImage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Provides access to Docker registry.
 * @param uri - Docker registry URI
 */
class DockerRegistryAccessor(uri: String) {

    private val address: String = "https://hub.docker.com/v2"

    // -- use isLateient to sufficiently parse Number arguments (int would be overflown)
    private val jsonSerializer = Json { ignoreUnknownKeys = true; isLenient = true }

    /**
     * Retrieves the size of Docker image
     */
    public fun getSize(image: DockerImage): String {
        return this.getRegistryInfo(image.repo, image.tag).fullSize
    }

    /**
     * Returns general information about Docker Registry.
     */
    public fun getRegistryInfo(repo: String, tag: String = "latest"): DockerRepositoryInfo {
        val registryResponse: String = HttpRequestUtilities.performRequest("${this.address}/repositories/${repo}/tags/${tag}") ?: ""
        return jsonSerializer.decodeFromString<DockerRepositoryInfo>(registryResponse)
    }
}