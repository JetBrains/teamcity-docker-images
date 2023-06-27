package generated.staging.manifest

import hosted.utils.ImageInfoRepository
import hosted.utils.dsl.general.teamCityImageBuildFeatures
import hosted.utils.dsl.general.teamCityStagingImagesSnapshot
import hosted.utils.dsl.steps.publishManifest
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildTypeSettings
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

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

        // typically, either 'latest' or release version
        val manifestName = "%tc.image.version%"

        // 1. Publish Server Manifests
        val serverTags = ImageInfoRepository.getAllServerTags(manifestName)
        publishManifest("%docker.buildRepository%teamcity-server%docker.buildImagePostfix%", serverTags, manifestName)

        // 2. Publish Agent Manifests
        val agentTags = ImageInfoRepository.getAllAgentTags(manifestName)
        publishManifest("%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%", agentTags, manifestName)

        // 3. Publish Minimal Agent Manifests
        val minAgentTags = ImageInfoRepository.getAllMinimalAgentTags(manifestName)
        publishManifest(
            "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%",
            minAgentTags,
            manifestName
        )

        // 4. Publish Windows Server Core Agents Manifests
        val agentTagsWinServerCore = ImageInfoRepository.getWindowsCoreAgentTags(manifestName)
        publishManifest(
            "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%",
            agentTagsWinServerCore,
            "${manifestName}-windowsservercore"
        )
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
        teamCityImageBuildFeatures(requiredSpaceGb = 1)
    }
})
