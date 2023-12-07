package utils.config

/**
 * Holds Delivery-related configuration.
 */
class DeliveryConfig {
    companion object {
        // Configuration within remote TeamCity Instance, thus the ID is stored as string
        const val buildDistDockerDepId = "TC_202311x_BuildDistDocker"

        // Version must correspond to generated Dockerfiles, as minimal agent is a ...
        // ... base image for regular agent.
        const val tcVersion = "2023.11.1"
    }
}
