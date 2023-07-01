package scheduled.build

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import utils.dsl.general.teamCityImageBuildFeatures
import utils.dsl.steps.publishLinuxManifests


object TeamCityDockerImageScheduledBuildLinuxManifest : BuildType({
    name = "[Linux] TeamCity Docker Images - Automated Scheduled Build - Linux - publish manifest"
    description = "Creates and publishes manifest for image built for different architectures: AMD, ARM."

    params {
        // the images will be published into registry that holds nightly builds
        param("docker.buildRepository", "%docker.nightlyRepository%")
        // no postfix needed
        param("docker.buildImagePostfix", "")
        param("tc.image.version", "%dockerImage.teamcity.buildNumber%")
    }

    steps {
        script {
            name = "remove manifests"
            scriptContent =
                """if exist "%%USERPROFILE%%\.docker\manifests\" rmdir "%%USERPROFILE%%\.docker\manifests\" /s /q"""
        }

        publishLinuxManifests(name = "%dockerImage.teamcity.buildNumber%",
            repo = "%docker.nightlyRepository%",
            postfix = "")
    }

    features {
        teamCityImageBuildFeatures()
    }
})