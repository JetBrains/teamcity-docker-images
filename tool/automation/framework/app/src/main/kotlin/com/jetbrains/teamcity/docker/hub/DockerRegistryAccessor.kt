package com.jetbrains.teamcity.docker.hub

import com.jetbrains.teamcity.common.constants.ValidationConstants
import com.jetbrains.teamcity.common.network.HttpRequestsUtilities
import com.jetbrains.teamcity.docker.DockerImage
import com.jetbrains.teamcity.docker.hub.auth.DockerhubCredentials
import com.jetbrains.teamcity.docker.hub.data.DockerRegistryInfoAboutImages
import com.jetbrains.teamcity.docker.hub.data.DockerRepositoryInfo
import com.jetbrains.teamcity.docker.hub.data.DockerhubPersonalAccessToken
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import java.net.http.HttpResponse

/**
 * Provides access to Docker registry.
 * @param uri - Docker Registry URI
 * @param credentials - (optional) - credentials for the access of Dockerhub REST API
 */
class DockerRegistryAccessor(private val uri: String,
                             private val ignoreStaging: Boolean,
                             credentials: DockerhubCredentials?) {
    private val httpRequestsUtilities: HttpRequestsUtilities = HttpRequestsUtilities()
    private val token: String?
    private val jsonSerializer: Json = Json {
        // -- remove the necessity to include parsing of unused fields
        ignoreUnknownKeys = true
        // -- parse JSON fields that don't have an assigned serializer into a String, e.g.: Number
        isLenient = true
    }


    init {
        this.token = if (credentials != null && credentials.isUsable()) this.getPersonalAccessToken(credentials) else ""
    }

    constructor(uri: String, credentials: DockerhubCredentials?) : this(uri, false, credentials)


    /**
     * Returns general information about Docker Repository.
     * @param image image for which the "repository" (all associated images: OS, OS Version, arch.) would be ...
     * ... retrieved.
     * @return information about the repository; null in case inaccessible
     */
    fun getRepositoryInfo(image: DockerImage): DockerRepositoryInfo {
        val registryResponse: HttpResponse<String?> = this.httpRequestsUtilities.getJsonWithAuth(
            "${this.uri}/repositories/${image.repo}/tags/${image.tag}",
            this.token
        )
        val result = registryResponse.body() ?: ""

        if (!this.httpRequestsUtilities.isResponseSuccessful(registryResponse) || result.isEmpty()) {
            throw IllegalStateException("Unable to get information about the repository from Docker registry: $registryResponse")
        }
        return jsonSerializer.decodeFromString(result)
    }

    /**
     * Returns information about images within given registry.
     * @param image image from given registry
     * @param pageSize maximal amount of images to be included into Dockerhub's response
     */
    fun getInfoAboutImagesInRegistry(image: DockerImage, pageSize: Int): DockerRegistryInfoAboutImages? {
        val repo = if (ignoreStaging) image.repo.replace(ValidationConstants.STAGING_POSTFIX, "") else image.repo
        val registryResponse: HttpResponse<String?> = httpRequestsUtilities.getJsonWithAuth(
            "${this.uri}/repositories"
                    + "/${repo}/tags?page_size=$pageSize", this.token
        )
        val result = registryResponse.body() ?: ""

        if (!this.httpRequestsUtilities.isResponseSuccessful(registryResponse) || result.isEmpty()) {
            throw IllegalStateException("Unable to get information about the repository from Docker registry: $registryResponse")
        }
        return jsonSerializer.decodeFromString(result)
    }

    /**
     * Determines previous image pushed into the registry.
     * @param currentImage original Docker image
     * @param targetOs Operating System for which previous image should be found. Multiple images might have an equal ...
     * ... repository and tags, but different target OS. The size will be different as well.
     * @param osVersion - version of operating system. Used mostly for Windows images.
     */
    fun getPreviousImages(
        currentImage: DockerImage,
        targetOs: String = "linux",
        osVersion: String? = ""
    ): DockerRepositoryInfo? {

        val registryInfo = this.getInfoAboutImagesInRegistry(currentImage, 50)
        if (registryInfo == null) {
            print("Registry information for given image was not found: $currentImage")
            return null
        }
        // get the TAG of previous image. It might have multiple corresponding images (same tag, but different target OS)
        val previousImageRepository = registryInfo.results
            // Remove current & EAP (non-production) tags
            .asSequence()
            .filter {
                return@filter ((it.name != currentImage.tag)
                        // EAP & latest
                        && (!it.name.contains(ValidationConstants.PRE_PRODUCTION_IMAGE_PREFIX))
                        && (!it.name.contains(ValidationConstants.LATEST)))
            }
            // Remove releases that were after the image under test
            .filter { return@filter isPrevRelease(it.name, currentImage.tag) }
            // lookup previous image for specific distribution, e.g. 2023.05-linux, 2023.05-windowsservercore, etc.
            .filter { return@filter isSameDistribution(currentImage.tag, it.name) }
            // Sort based on tag
            .sortedWith { lhs, rhs -> imageTagComparator(lhs.name, rhs.name) }
            .lastOrNull() ?: throw RuntimeException("Previous images weren't found for $currentImage")

        // -- 1. Filter by OS type
        previousImageRepository.images = previousImageRepository.images.filter { it.os == targetOs }
        if (previousImageRepository.images.isNotEmpty() && !osVersion.isNullOrEmpty()) {
            // --- 2. Filter by OS version (e.g. specific version of Windows, Linux)
            val imagesFilteredByTarget = previousImageRepository.images.filter { it.osVersion.equals(osVersion) }
            if (imagesFilteredByTarget.isEmpty()) {
                // Found images that matches OS type, but doesn't match OS version, e.g. ...
                // ... - Previous: teamcity-agent:2022.10.1--windowsservercore-2004 (Windows 10.0.17763.3650)
                // ... - Current : teamcity-agent:2022.10.2-windowsservercore-2004 (Windows 10.0.17763.3887)
                println(
                    "$currentImage - found previous image - ${previousImageRepository.name}, but OS version is "
                            + "different - $osVersion and ${previousImageRepository.images.first().osVersion} \n"
                            + "Images with mismatching OS versions, but matching tags will be compared."
                )
                return previousImageRepository
            }

            previousImageRepository.images = imagesFilteredByTarget
        }

        return previousImageRepository
    }

    /**
     * Compares image tags, e.g. (2023.05.1 > 2023.05)
     */
    private fun imageTagComparator(lhsImageTag: String, rhsImageTag: String): Int {
        val lhsTagComponents = lhsImageTag.split("-")[0].split(".").map { it.toIntOrNull() }
        val rhsTagComponents = rhsImageTag.split("-")[0].split(".").map { it.toIntOrNull() }

        for (i in 0 until maxOf(lhsTagComponents.size, rhsTagComponents.size)) {
            // e.g. 2023.05 transforms into 2023.05.0 for comparison purposes
            val lhsTagComponent: Int = lhsTagComponents.getOrNull(i) ?: 0
            val rhsTagComponent = rhsTagComponents.getOrNull(i) ?: 0
            if (lhsTagComponent != rhsTagComponent) {
                return lhsTagComponent.compareTo(rhsTagComponent)
            }
        }
        return lhsTagComponents.size.compareTo(rhsTagComponents.size)
    }

    /**
     * returns true if lhs was earlier release than rhs
     */
    private fun isPrevRelease(lhsTag: String, rhsTag: String): Boolean {
        return imageTagComparator(lhsTag, rhsTag) < 0
    }

    /**
     * Returns true if both images below to the same distribution, e.g. lhs="2023.05.1-windowsservercore", rhs= ...
     * ...="2023.05-windowsservercore" will return true.
     */
    private fun isSameDistribution(lhsTag: String, rhsTag: String): Boolean {
        val nameComponents = lhsTag.split("-")
        if (nameComponents.size == 1) {
            // e.g. "2023.05"
            return true
        }
        return try {
            // 2023.05-linux-amd64 => linux-amd64
            val something = lhsTag.split("-", limit = 2)[1]
            rhsTag.contains(something)
        } catch (e: Exception) {
            println("Image name does not match the expected pattern, thus would be filtered out: $rhsTag")
            false
        }
    }

    /**
     * Creates a session-based Personal Access Token (PAT) for DockerHub REST API access to private repositories.
     * See: https://docs.docker.com/docker-hub/api/latest/#tag/authentication/operation/PostUsersLogin
     * @param credentials - objects containing username and access token
     * @return session-based personal-access token (PAT)
     */
    private fun getPersonalAccessToken(credentials: DockerhubCredentials): String {
        val webTokenRequestBody = JsonObject(
            mapOf(
                "username" to JsonPrimitive(credentials.username),
                // instead of "password", we could supply persistent personal-access token ...
                // ... to generate JSON Web Token (JWT)
                "password" to JsonPrimitive(credentials.token)
            )
        )
        val response = httpRequestsUtilities.putJsonWithAuth(
            "${this.uri}/users/login",
            webTokenRequestBody.toString()
        )
        if (response.body().isNullOrEmpty()) {
            throw RuntimeException("Unable to obtain Dockerhub JSON Web Token, status: ${response.statusCode()}")
        }
        if (response.statusCode() == 401) {
            throw IllegalArgumentException("Unable to generate Dockerhub JSON Web Token - provided credentials are incorrect \n ${response.body()}")
        } else if (response.statusCode() == 429) {
            throw IllegalStateException("Unable to generate Dockerhub JSON Web Token - credentials are incorrect with too many failed attempts.")
        }

        // Retrieve web token in JSON format as string
        val webTokenJsonString = response.body() ?: ""
        if (webTokenJsonString.isEmpty()) {
            throw RuntimeException("Failed to obtain JSON Web Token - response body is empty. \n $response \n ${this.uri}")
        }

        val authResponseJson = jsonSerializer.decodeFromString<DockerhubPersonalAccessToken>(webTokenJsonString)
        return authResponseJson.token
    }
}
