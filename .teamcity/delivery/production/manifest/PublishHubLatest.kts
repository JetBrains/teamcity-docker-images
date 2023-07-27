package delivery.production.manifest

import utils.ImageInfoRepository
import utils.dsl.general.teamCityImageBuildFeatures
import utils.dsl.general.teamCityProdImagesSnapshot
import utils.dsl.steps.publishManifest
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildTypeSettings
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.version
import utils.dsl.steps.publishLinuxManifests
import utils.dsl.steps.publishWindowsManifests

/**
 * TODO: Merge with 'publish_hub_version'?
 */
object publish_hub_latest : BuildType({
    name = "[All] [Production] Release Manifests as 'latest' into Production Registry"
    description = "Publish Docker Manifests into production registry as 'latest' tag."
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1
    steps {
        script {
            name = "remove manifests"
            scriptContent =
                """if exist "%%USERPROFILE%%\.docker\manifests\" rmdir "%%USERPROFILE%%\.docker\manifests\" /s /q"""
        }

        // 'version' - TeamCity version, e.g. 2023.05.1
        // 'manifestName' - ID of manifest (usually, 'latest')
        publishLinuxManifests(name = "latest", repo = "%docker.deployRepository%", postfix = "", version = "%tc.image.version%")
        publishWindowsManifests(name = "latest", repo = "%docker.deployRepository%", postfix = "", version = "%tc.image.version%")
    }

    dependencies {
        teamCityProdImagesSnapshot()
    }

    requirements {
        noLessThanVer("docker.version", "18.05.0")
        contains("docker.server.osType", "windows")
    }

    features {
        teamCityImageBuildFeatures(useCleanup = false)
    }
})
