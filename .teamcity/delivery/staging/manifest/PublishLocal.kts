package delivery.staging.manifest

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildTypeSettings
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import utils.dsl.general.teamCityImageBuildFeatures
import utils.dsl.general.teamCityStagingImagesSnapshot
import utils.dsl.steps.publishLinuxManifests
import utils.dsl.steps.publishWindowsManifests

object publish_local : BuildType({
    name = "[All] [Staging] Release Manifests as 'version' into Staging Registry"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    description = "Publish Docker Manifests into staging repository."
    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    steps {
        script {
            name = "remove manifests"
            scriptContent =
                """if exist "%%USERPROFILE%%\.docker\manifests\" rmdir "%%USERPROFILE%%\.docker\manifests\" /s /q"""
        }

        publishLinuxManifests(name = "%tc.image.version%",
            repo = "%docker.buildRepository%",
            postfix = "%docker.buildImagePostfix%")


        publishWindowsManifests(name = "%tc.image.version%",
            repo = "%docker.buildRepository%",
            postfix = "%docker.buildImagePostfix%")
    }

    dependencies {
        teamCityStagingImagesSnapshot()
    }

    requirements {
        noLessThanVer("docker.version", "18.05.0")
        contains("docker.server.osType", "windows")
        // In order to correctly build AMD-based images, we wouldn't want it to be scheduled on ARM-based agent
        doesNotContain("teamcity.agent.name", "arm")
        contains("teamcity.agent.jvm.os.name", "Windows 10")
    }

    features {
        teamCityImageBuildFeatures()
    }
})
