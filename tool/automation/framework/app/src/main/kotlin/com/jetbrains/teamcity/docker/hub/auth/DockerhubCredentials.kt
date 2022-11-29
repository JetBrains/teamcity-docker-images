package com.jetbrains.teamcity.docker.hub.auth

/**
 * Represents credentials required for Dockerhub REST API access.
 */
class DockerhubCredentials {
    val username: String?
    val token: String?

    /**
     * Creates Dockerhub credentials instance.
     * @param username - Dockerhub Username
     * @param token - Dockerhub persistent token
     */
    constructor(username: String?, token: String?) {
        this.username = username?.trim() ?: ""
        this.token = token?.trim() ?: ""
    }
}
