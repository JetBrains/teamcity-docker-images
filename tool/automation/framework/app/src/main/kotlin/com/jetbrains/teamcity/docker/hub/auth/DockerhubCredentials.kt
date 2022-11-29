package com.jetbrains.teamcity.docker.hub.auth

/**
 * Represents credentials required for Dockerhub REST API access.
 * @param username - Dockerhub Username
 * @param token - Dockerhub persistent token
 */
class DockerhubCredentials(val username: String?, val token: String?) { }
