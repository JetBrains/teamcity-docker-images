package hosted.utils.dsl.general

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
    this.dependency(AbsoluteId("TC_Trunk_BuildDistDocker")) {
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
 * Shared features required for the build of TeamCity Docker Images.
 */
fun BuildFeatures.teamCityImageBuildFeatures() {
    this.freeDiskSpace {
        requiredSpace = "8gb"
        failBuild = true
    }

    this.dockerSupport {
        cleanupPushedImages = true
        loginToRegistry = on {
            // Dockerhub, Space
            dockerRegistryId = "PROJECT_EXT_774,PROJECT_EXT_315"
        }
    }

    this.swabra {
        forceCleanCheckout = true
    }
}
