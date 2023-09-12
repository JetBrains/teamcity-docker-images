import common.TeamCityDockerImagesRepo
import hosted.utils.ImageInfoRepository
import hosted.utils.Utils
import hosted.utils.config.DeliveryConfig
import hosted.utils.general.Registries
import hosted.utils.models.ImageInfo
import hosted.utils.steps.buildImage
import hosted.utils.steps.changeStagingTag
import hosted.utils.steps.publishToStaging
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.version

/**
 * Base class for the build of Linux-based Docker images.
 * The image uses parameter 'docker.nightlyRepository', which, for security reasons, should be defined at ...
 * ... TeamCity-server level rather than DSL.
 *
 * @param platform target platform, e.g. amd64, aarch64 (ARM)
 * @param agentReq a substring that agent name ('teamcity.agent.name') must contain
 */
class TeamCityScheduledImageBuildLinux_Base(private val platform: String, private val agentReq: String) : BuildType({
    name = "[${platform}] TeamCity Docker Images - Automated Scheduled Build - Linux"
    id("TeamCityScheduledImageBuildLinux_Base${platform}")

    val images: Set<ImageInfo> = when {
        platform.lowercase().contains("arm") || platform.lowercase()
            .contains("aarch") -> ImageInfoRepository.getArmLinuxImages2004(
            stagingRepo = "%docker.nightlyRepository%",
            dockerfileTag = DeliveryConfig.tcVersion,
            version = "%dockerImage.teamcity.buildNumber%"
        )

        platform.lowercase().contains("amd") -> ImageInfoRepository.getAmdLinuxImages2004(
            stagingRepo = "%docker.nightlyRepository%",
            dockerfileTag = DeliveryConfig.tcVersion,
            version = "%dockerImage.teamcity.buildNumber%"
        )

        else -> throw IllegalArgumentException("Unable to find images for specified platform [${platform}]")
    }

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
        dependency(AbsoluteId("TC2023_05_BuildDistDocker")) {
            artifacts {
                artifactRules = "TeamCity.zip!/**=>context/TeamCity"
                cleanDestination = true
                lastSuccessful()
            }
        }
    }

    features {
        dockerSupport {
            cleanupPushedImages = false
            loginToRegistry = on {
                dockerRegistryId = Registries.SPACE
            }
        }
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
