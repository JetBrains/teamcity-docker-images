package utils.models

/**
 * Holds data about the building docker image.
 */
data class ImageInfo(
    val name: String,
    val dockerfilePath: String,
    // 'baseFqdn' - basic image domain name, could be used as a reference within Dockerfile (e.g. for base image)
    // 'stagingFqdn' - domain name of the image, including the registry, which will be used for deployment
    val baseFqdn: String,
    val stagingFqdn: String,
    val productionFqdn: String
)