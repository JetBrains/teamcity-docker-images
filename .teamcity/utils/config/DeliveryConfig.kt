package utils.config

/**
 * Holds Delivery-related configuration.
 */
class DeliveryConfig {
    companion object {
        // Configuration within remote TeamCity Instance, thus the ID is stored as string
        const val buildDistDockerDepId = "TC_Trunk_BuildDistDocker"
        const val tcVersion = "EAP"
    }
}
