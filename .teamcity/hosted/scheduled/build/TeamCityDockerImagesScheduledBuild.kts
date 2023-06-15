package hosted.scheduled.build

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule

/**
 * Scheduled Composite Build of TeamCity Docker Images for each of supported platforms.
 * Purpose: ensure that all dependencies within Docker Images are up-to-date.
 */
object TeamCityDockerImagesScheduledBuild : BuildType({
    name = "TeamCity Docker Images - Automated Scheduled Build"
    description = "Regular automated build of TeamCity Docker Images"

    type = Type.COMPOSITE

    vcs {
        showDependenciesChanges = true
    }

    params {
        // the images will be published into registry that holds nightly builds
        param("docker.buildRepository", "%docker.nightlyRepository%")
        // no postfix needed
        param("docker.buildImagePostfix", "")
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
            TeamCityScheduledImageBuildWindows.TeamCityScheduledImageBuildWindows,
            TeamCityScheduledImageBuildLinux.TeamCityScheduledImageBuildLinux
        ).forEach {
            snapshot(it) {
                onDependencyFailure = FailureAction.ADD_PROBLEM
                onDependencyCancel = FailureAction.CANCEL
            }
        }
    }
})
