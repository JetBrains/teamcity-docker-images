package com.jetbrains.teamcity.docker.hub.auth

/**
 * Represents credentials required for Dockerhub REST API access.
 */
class DockerhubCredentials(val username: String?, val token: String?) { }
