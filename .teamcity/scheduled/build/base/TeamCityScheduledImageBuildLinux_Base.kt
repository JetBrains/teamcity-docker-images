import common.TeamCityDockerImagesRepo
import utils.ImageInfoRepository
import utils.Utils
import utils.models.ImageInfo
import utils.dsl.steps.buildImage
import utils.dsl.steps.publishToStaging
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import utils.config.DeliveryConfig
import utils.config.Registries
import utils.dsl.general.teamCityBuildDistDocker
import utils.dsl.general.teamCityImageBuildFeatures
import java.lang.IllegalArgumentException

/**
 * Base class for the build of Linux-based Docker images.
 * @param platform target platform, e.g. amd64, aarch64 (ARM)
 * @param agentReq a substring that agent name ('teamcity.agent.name') must contain
 */
class TeamCityScheduledImageBuildLinux_Base(private val platform: String, private val agentReq: String) : BuildType({
    name = "[${platform}] TeamCity Docker Images - Automated Scheduled Build - Linux"
    id("TeamCityScheduledImageBuildLinux_Base${platform}")

    vcs {
        root(TeamCityDockerImagesRepo.TeamCityDockerImagesRepo)
    }

    // all .yml files (e.g. compose samples)
    artifactRules = "+:*.yml"

    params {
        // the images will be published into registry that holds nightly builds
        param("docker.buildRepository", "%docker.nightlyRepository%")
        // no postfix needed
        param("docker.buildImagePostfix", "")
        param("tc.image.version", "%dockerImage.teamcity.buildNumber%")
    }

    steps {
        val images: Set<ImageInfo> = when {
            platform.lowercase().contains("arm") || platform.lowercase()
                .contains("aarch") -> ImageInfoRepository.getArmLinuxImages2004(
                stagingRepo = "%docker.nightlyRepository%",
                version = "%dockerImage.teamcity.buildNumber%",
                prodRepo = "%docker.nightlyRepository%",
                dockerfileTag = DeliveryConfig.tcVersion
            )

            platform.lowercase().contains("amd") -> ImageInfoRepository.getAmdLinuxImages2004(
                stagingRepo = "%docker.nightlyRepository%",
                version = "%dockerImage.teamcity.buildNumber%",
                prodRepo = "%docker.nightlyRepository%",
                dockerfileTag = DeliveryConfig.tcVersion
            )

            else -> throw IllegalArgumentException("Unable to find images for specified platform [${platform}]")
        }

        // build each image
        images.forEach { imageInfo -> buildImage(imageInfo) }

        // publish images if build of each one of them succeeded
        images.forEach { imageInfo -> publishToStaging(imageInfo) }

        script {
            name = "Generate Sample docker-compose manifest for the created images"
            scriptContent = """
                cat <<EOF > teamcity-linux-%tc.image.version%.docker-compose.${platform.trim()}.yml
                ${Utils.getSampleComposeFile("%docker.nightlyRepository%", "%tc.image.version%", "", platform)}
                EOF
                """.trimIndent()
        }
    }

    dependencies {
        teamCityBuildDistDocker()
    }

    features {
        teamCityImageBuildFeatures(requiredSpaceGb = 8, registries = listOf(Registries.SPACE))
    }

    requirements {
        // in case of aarch64, images must be built on aarch64-based agents
        contains("teamcity.agent.name", agentReq)
    }

    // We retain nightly builds for the previous 2 weeks
    cleanup {
        keepRule {
            id = "TC_DOCKER_IMAGES_SCHEDULED_CLEANUP"
            keepAtLeast = days(14)
            dataToKeep = everything()
            applyPerEachBranch = true
            preserveArtifactsDependencies = true
        }
    }
})
