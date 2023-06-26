package generated.production

import hosted.utils.ImageInfoRepository
import hosted.utils.dsl.general.teamCityImageBuildFeatures
import hosted.utils.dsl.steps.moveToProduction
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

object push_hub_windows : BuildType({
    name = "Push windows"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    steps {
        // Move Windows 1809-based Docker Images into production registry
        ImageInfoRepository.getWindowsImages1809().forEach { imageInfo ->
            moveToProduction(imageInfo)
        }

        // Move Windows 2004-based Docker Images into production registry
        ImageInfoRepository.getWindowsImages2004().forEach { imageInfo ->
            moveToProduction(imageInfo)
        }
    }

    features {
        // Windows Images Require more disk space
        teamCityImageBuildFeatures(requiredSpaceGb = 52)
    }

    requirements {
        contains("docker.server.osType", "windows")
        // In order to correctly build AMD-based images, we wouldn't want it to be scheduled on ARM-based agent
        doesNotContain("teamcity.agent.name", "arm")
        contains("teamcity.agent.jvm.os.name", "Windows 10")
    }

    dependencies {
        snapshot(PublishLocal.publish_local) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})
