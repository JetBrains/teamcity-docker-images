package delivery.production.manifest

import utils.ImageInfoRepository
import utils.dsl.general.teamCityImageBuildFeatures
import utils.dsl.general.teamCityProdImagesSnapshot
import utils.dsl.steps.publishManifest
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildTypeSettings
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import utils.dsl.steps.publishLinuxManifests
import utils.dsl.steps.publishWindowsManifests

object publish_hub_version : BuildType({
    name = "[All] [Production] Release Manifests as 'version' into Production Registry"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    description = "Publish Docker Manifests into production repository with 'version' tag, e.g. 2023.05.1."

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
        publishLinuxManifests(name = "%tc.image.version%", repo = "%docker.deployRepository%")
        publishWindowsManifests(name = "%tc.image.version%", repo = "%docker.deployRepository%")
    }

    dependencies {
        teamCityProdImagesSnapshot()
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
