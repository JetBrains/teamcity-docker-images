package automation.docker.hub.models

import automation.common.network.HttpRequestUtilities

/**
 * Provides access to Docker registry.
 * @param uri - Docker registry URI
 */
class DockerRegistryAccessor(uri: String) {
    private val address: String = "https://hub.docker.com/v2"

    /**
     * Retrieves the size of Docker image
     */
    fun getSize(repo: String, tag: String): String {
        return HttpRequestUtilities.performRequest("$address/repositories/${repo}/tags/${tag}") ?: ""
        // https://hub.docker.com/v2/repositories/jetbrains/teamcity-agent/tags/2022.10-windowsservercore-1809
    }
}