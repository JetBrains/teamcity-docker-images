package delivery.production

import utils.ImageInfoRepository
import utils.dsl.general.publishStagingManifests
import utils.dsl.general.teamCityImageBuildFeatures
import utils.dsl.steps.moveToProduction
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object push_hub_windows : BuildType({
    name = "[Windows] [Production] Release TeamCity Docker Images into Production Registry"
    description = "Moves TeamCity Windows-based staging images into production registry."
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"

    params {
        param("dockerImage.platform", "windows")
    }

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
        publishStagingManifests()
    }
})
