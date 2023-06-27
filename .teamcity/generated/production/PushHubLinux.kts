package generated.production

import hosted.utils.ImageInfoRepository
import hosted.utils.dsl.general.publishStagingManifests
import hosted.utils.dsl.general.teamCityImageBuildFeatures
import hosted.utils.dsl.steps.moveToProduction
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType

object push_hub_linux : BuildType({
    name = "[Linux] [Production] Release TeamCity Docker Images into Production Registry"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"

    steps {
        ImageInfoRepository.getAmdImages().forEach { imageInfo ->
            moveToProduction(imageInfo)
        }
    }

    features {
        teamCityImageBuildFeatures(requiredSpaceGb = 4)
    }

    requirements {
        contains("docker.server.osType", "linux")
        // In order to correctly build AMD-based images, we wouldn't want it to be scheduled on ARM-based agent
        doesNotContain("teamcity.agent.name", "arm")
    }

    dependencies {
        publishStagingManifests()
    }
})
