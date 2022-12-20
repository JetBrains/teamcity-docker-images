package hosted.nightly

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/**
 * Nightly Composite Build of TeamCity Docker Images for all platforms.
 * Purpose: ensure that all dependencies within Docker Images are up-to-date.
 */
object TeamCityDockerImagesNightly : BuildType({
    name = "TeamCity Docker Images - Nightly Build"
    description = "Nightly Build of TeamCity Images"

    type = Type.COMPOSITE

    vcs {
        showDependenciesChanges = true
    }

    triggers {
        schedule {
            id = "TRIGGER_TC_DOCKER_IMAGES_NIGHTLY"
            schedulingPolicy = daily {
                hour = 0
                minute = 15
            }
            branchFilter = "+:<default>"
            withPendingChangesOnly = false
        }
    }

    dependencies {
        arrayOf(
            TeamCityNightlyImageBuildWindows.TeamCityNightlyImageBuildWindows,
            TeamCityNightlyImageBuildLinux.TeamCityNightlyImageBuildLinux
        ).forEach {
            snapshot(it) {
                onDependencyFailure = FailureAction.ADD_PROBLEM
                onDependencyCancel = FailureAction.CANCEL
            }
        }
    }
})
