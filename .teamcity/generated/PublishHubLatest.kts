package generated

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import hosted.utils.ImageInfoRepository
import hosted.utils.dsl.general.teamCityProdImagesSnapshot
import hosted.utils.dsl.steps.publishManifest

object publish_hub_latest : BuildType({
    name = "Publish as latest"
    description = "Publish Docker Manifests into production repository."
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
        val manifestName = "latest"
        val version = "%tc.image.version%"

        // 1. Publish Server Manifests
        val serverTags = ImageInfoRepository.getAllServerTags(version)
        publishManifest("%docker.deployRepository%teamcity-server", serverTags, manifestName)

        // 2. Publish Agent Manifests
        val agentTags = ImageInfoRepository.getAllAgentTags(version)
        publishManifest("%docker.deployRepository%teamcity-agent", agentTags, manifestName)

        // 3. Publish Minimal Agent Manifests
        val minAgentTags = ImageInfoRepository.getAllMinimalAgentTags(version)
        publishManifest(
            "%docker.deployRepository%teamcity-minimal-agent",
            minAgentTags,
            manifestName
        )

        // 4. Publish Windows Server Core Agents Manifests
//            val agentTagsWinServerCore = ImageInfoRepository.getWindowsCoreAgentTags(version)
//            publishManifest(
//                "%docker.deployRepository%teamcity-agent",
//                agentTagsWinServerCore,
//                "${manifestName}-windowsservercore"
//            )

    }
    dependencies {
        teamCityProdImagesSnapshot()
    }

    requirements {
        noLessThanVer("docker.version", "18.05.0")
        contains("docker.server.osType", "windows")
    }
    features {
        dockerSupport {
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_774"
            }
        }
    }
})

