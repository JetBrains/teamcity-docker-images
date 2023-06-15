import common.TeamCityDockerImagesRepo
import hosted.utils.ImageInfoRepository
import hosted.utils.Utils
import hosted.utils.models.ImageInfo
import hosted.utils.steps.buildAndPublishImage
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import java.lang.IllegalArgumentException

/**
 * Base class for the build of Linux-based Docker images.
 * @param platform target platform, e.g. amd64, aarch64 (ARM)
 * @param agentReq (optional) a substring that agent name ('teamcity.agent.name') must contain
 */
class TeamCityScheduledImageBuildLinux_Base(private val platform: String, private val agentReq: String = "") :
    BuildType({
        name = "[${platform}] TeamCity Docker Images - Automated Scheduled Build - Linux"
        id("TeamCityScheduledImageBuildLinux_Base${platform}")

        val images: Set<ImageInfo> = when {
            platform.lowercase().contains("arm") || platform.lowercase()
                .contains("aarch") -> ImageInfoRepository.getArmImages(
                "%docker.nightlyRepository%",
                "%dockerImage.teamcity.buildNumber%"
            )

            platform.lowercase().contains("amd") -> ImageInfoRepository.getAmdImages(
                "%docker.nightlyRepository%",
                "%dockerImage.teamcity.buildNumber%"
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

            images.forEach { imageInfo -> buildAndPublishImage(imageInfo) }

            script {
                name = "Generate Sample docker-compose manifest for the created images"
                scriptContent = """
                cat <<EOF > teamcity-linux-%tc.image.version%.docker-compose${platform.trim()}.yml
                ${Utils.getSampleComposeFile("%docker.nightlyRepository%", "%tc.image.version%")}
                EOF
                """.trimIndent()
            }
        }

        dependencies {
            dependency(AbsoluteId("TC_Trunk_BuildDistDocker")) {
                artifacts {
                    artifactRules = "TeamCity.zip!/**=>context/TeamCity"
                    cleanDestination = true
                    lastSuccessful()
                }
            }
        }

        features {
            dockerSupport {
                cleanupPushedImages = true
                loginToRegistry = on {
                    dockerRegistryId = "PROJECT_EXT_315"
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