// NOTE: THIS IS AN AUTO-GENERATED FILE. IT HAD BEEN CREATED USING TEAMCITY.DOCKER PROJECT. ...
// ... IF NEEDED, PLEASE, EDIT DSL GENERATOR RATHER THAN THE FILES DIRECTLY. ... 
// ... FOR MORE DETAILS, PLEASE, REFER TO DOCUMENTATION WITHIN THE REPOSITORY.
package generated.production

import generated.staging.PublishLocal
import hosted.utils.ImageInfoRepository
import hosted.utils.dsl.general.teamCityImageBuildFeatures
import hosted.utils.dsl.steps.moveToProduction
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.FailureAction

object push_hub_linux : BuildType({
    name = "Push linux"
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
        snapshot(PublishLocal.publish_local) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})
