package com.jetbrains.teamcity.docker.hub.data

import kotlinx.serialization.Serializable

/**
 * Represents model for Dockerhub's personal access token (PAT) response for ...
 * ... "https://hub.docker.com/v2/users/login" endpoint.
 * Details: https://docs.docker.com/docker-hub/api/latest/#tag/authentication
 */
@Serializable
class DockerhubPersonalAccessToken {
    lateinit var token: String
}
