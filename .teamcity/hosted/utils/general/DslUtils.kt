package hosted.utils.general


import generated.*
import hosted.arm.PushStagingLinux2004_Aarch64
import hosted.arm.push_production_linux_2004_aarch64
import hosted.utils.config.DeliveryConfig
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra



/**
 *
 *  The file encapsulated general utilities for Kotlin DSL: shared VCS roots, dependencies, steps, etc.
 *  It couldn't be included into a companion object as a static method, thus they're provided as a ...
 *  ... set of methods.
 *
 */


/**
 * Provides snapshot dependency on TeamCity Build compatible with Docker.
 */
fun Dependencies.teamCityBuildDistDocker() {
    this.dependency(AbsoluteId(DeliveryConfig.buildDistDockerDepId)) {
        snapshot {
            onDependencyFailure = FailureAction.IGNORE
            reuseBuilds = ReuseBuilds.ANY
        }
        artifacts {
            artifactRules = "TeamCity.zip!/**=>context/TeamCity"
        }
    }
}

/**
 * Dependency on successful publishing of staging manifests.
 */
fun Dependencies.publishStagingManifests() {
    snapshot(PublishLocal.publish_local) {
        onDependencyFailure = FailureAction.FAIL_TO_START
    }
}

/**
 * Returns snapshot dependencies on build configurations required for publishing Staging Images of TeamCity.
 */
fun Dependencies.teamCityStagingImagesSnapshot() {
    snapshot(AbsoluteId(DeliveryConfig.buildDistDockerDepId)) {
        onDependencyFailure = FailureAction.FAIL_TO_START
        reuseBuilds = ReuseBuilds.ANY
        synchronizeRevisions = false
    }
    snapshot(PushLocalLinux2004.push_local_linux_20_04) {
        onDependencyFailure = FailureAction.FAIL_TO_START
    }

    snapshot(PushLocalWindows1809.push_local_windows_1809) {
        onDependencyFailure = FailureAction.FAIL_TO_START
    }

    snapshot(PushLocalWindows2004.push_local_windows_2004) {
        onDependencyFailure = FailureAction.FAIL_TO_START
    }

    snapshot(PushStagingLinux2004_Aarch64.push_staging_linux_2004_aarch64) {
        onDependencyFailure = FailureAction.FAIL_TO_START
    }
}

/**
 * Returns snapshot dependencies on build configurations required for publishing Production Images of TeamCity.
 */
fun Dependencies.teamCityProdImagesSnapshot() {
    snapshot(AbsoluteId(DeliveryConfig.buildDistDockerDepId)) {
        onDependencyFailure = FailureAction.IGNORE
    }

    snapshot(PushHubLinux.push_hub_linux) {
        onDependencyFailure = FailureAction.FAIL_TO_START
    }

    snapshot(push_production_linux_2004_aarch64) {
        onDependencyFailure = FailureAction.FAIL_TO_START
    }

    snapshot(PushHubWindows.push_hub_windows) {
        onDependencyFailure = FailureAction.FAIL_TO_START
    }
}

/**
 * Shared features required for the build of TeamCity Docker Images.
 *
 * @param requiredSpaceGb space required for the build (more for image build-up, less for metadata (tag))
 * @param registries list of supported Docker Registries
 */
fun BuildFeatures.teamCityImageBuildFeatures(requiredSpaceGb: Int = 1,
                                             registries: List<String> = listOf(Registries.SPACE, Registries.HUB)) {
    this.freeDiskSpace {
        requiredSpace = "${requiredSpaceGb}gb"
        failBuild = true
    }

    this.dockerSupport {
        cleanupPushedImages = true
        loginToRegistry = on {
            dockerRegistryId = registries.joinToString(",")
        }
    }

    this.swabra {
        forceCleanCheckout = true
    }
}