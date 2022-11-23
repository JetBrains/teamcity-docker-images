package com.jetbrains.teamcity.docker

/**
 * Describes general information about Docker image
 */
class DockerImage {
    var repo: String
    var tag: String

    /**
     * Instantiates object using image fully-qualified domain name.
     */
    constructor(imageDomainName: String) {
        val imageNameElements = imageDomainName.split(":")
        if (imageNameElements.size > 2) {
            throw IllegalArgumentException("Image seem to have more tags that expected. Expected format: '<repo>:<tag>'")
        }

        this.repo = imageNameElements[0]
        this.tag = if (imageNameElements.size == 2) imageNameElements[1] else "latest"
    }

    /**
     * Instantiates object using repository and tag explicitly.
     */
    constructor(repo: String, tag: String) {
        this.repo = repo
        this.tag = tag
    }

    override fun toString(): String {
        return "${this.repo}:${this.tag}"
    }
}
