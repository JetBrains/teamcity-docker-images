package hosted.utils.config

/**
 * Holds Delivery-related configuration.
 */
class DeliveryConfig {
    companion object {
        // Configuration within remote TeamCity Instance, thus the ID is stored as string
        const val buildDistDockerDepId = "TC2023_05_BuildDistDocker"

        // Version must correspond to generated Dockerfiles, as minimal agent is a ...
        // ... base image for regular agent.
        const val tcVersion = "2023.05.2"
    }
}
