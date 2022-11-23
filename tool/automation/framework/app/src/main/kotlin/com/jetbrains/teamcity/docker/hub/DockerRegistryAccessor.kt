package com.jetbrains.teamcity.docker.hub

import com.jetbrains.teamcity.common.network.HttpRequestUtilities
import com.jetbrains.teamcity.docker.DockerImage
import com.jetbrains.teamcity.docker.hub.data.DockerRegistryImagesInfo
import com.jetbrains.teamcity.docker.hub.data.DockerRepositoryInfo
import com.jetbrains.teamcity.docker.hub.data.DockerhubImage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.IllegalStateException
import java.time.Instant

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
    public fun getSize(image: DockerImage): Long {
        return this.getRegistryInfo(image).fullSize.toLong()
    }

    /**
     * Returns general information about Docker Registry.
     */
    public fun getRegistryInfo(image: DockerImage): DockerRepositoryInfo {
        val registryResponse: String = HttpRequestUtilities.performGetRequest("${this.uri}/repositories/${image.repo}/tags/${image.tag}") ?: ""
        return jsonSerializer.decodeFromString(registryResponse)
    }

    /**
     * Determines previous image pushed into the registry.
     * @param currentImage original Docker image
     * @param targetOs Operating System for which previous image should be found. Multiple images might have an equal ...
     * ... repository and tags, but different target OS. The size will be different as well.
     * @param osVersion - version of operating system. Used mostly for Windows images.
     */
    public fun getPreviousImages(currentImage: DockerImage, targetOs: String = "linux", osVersion: String? = ""): List<DockerhubImage>? {
        val registryResponse: String = HttpRequestUtilities.performGetRequest("${this.uri}/repositories/${currentImage.repo}/tags/") ?: ""
        if (registryResponse.isEmpty()) {
            return null
        }
        val registryInfo: DockerRegistryImagesInfo = jsonSerializer.decodeFromString(registryResponse)

        // get the TAG of previous image. It might have multiple corresponding images (same tag, but different target OS)
        val previousImageRepository = registryInfo.results
                                                                .filter { it -> (it.name != currentImage.tag) }
                                                                .minByOrNull { result -> Instant.parse(result.tagLastPushed) }
        if (previousImageRepository == null) {
            return null
        }

        // filter by target OS
        var foundImageWithEqualOs = previousImageRepository.images.filter { it.os.equals(targetOs) }
        if (osVersion != null && !osVersion.isEmpty()) {
            foundImageWithEqualOs = previousImageRepository.images.filter { it.osVersion.equals(osVersion) }
        }

        return foundImageWithEqualOs
    }

    public fun getSizeOfPreviousImage(currentImage: DockerImage, targetOs: String = "linux", osVersion: String = ""): Long? {
        val dockerhubImage: List<DockerhubImage>? = this.getPreviousImages(currentImage, targetOs, osVersion)
        if (dockerhubImage == null || dockerhubImage.isEmpty()) {
            return null
        }
        return dockerhubImage.size.toLong()

    }
}
