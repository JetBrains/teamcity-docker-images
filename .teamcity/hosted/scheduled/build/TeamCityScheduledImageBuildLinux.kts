package hosted.scheduled.build

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import hosted.utils.ImageInfoRepository
import hosted.utils.Utils
import hosted.utils.steps.buildAndPublishImage
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.version


/**
 * Scheduled build of TeamCity Docker Images for Linux.
 */
object TeamCityScheduledImageBuildLinux : BuildType({
    name = "TeamCity Docker Images - Automated Scheduled Build - Linux"

    vcs {
        root(TeamCityDockerImagesRepo)
    }

    // all .yml files (e.g. compose samples)
    artifactRules = "+:*.yml"

    params {
        // the images will be published into registry that holds nightly builds
        param("docker.buildRepository", "%docker.nightlyRepository%")
        // no postfix needed
        param("docker.buildImagePostfix", "")

        // 'trunk' could be replaced with a particular release number, such as 2023.05.
        param("tc.image.version", "2023.05-%dockerImage.teamcity.buildNumber%")
    }

    steps {
        ImageInfoRepository
            // args: repository, version
            .getAmdLinuxImages2004(stagingRepo = "%docker.nightlyRepository%",
                version = "%dockerImage.teamcity.buildNumber%",
                prodRepo =  "%docker.nightlyRepository%")
            .forEach { imageInfo -> buildAndPublishImage(imageInfo) }

        script {
            name = "Generate Sample docker-compose manifest for the created images"
            scriptContent = """
                cat <<EOF > teamcity-linux-%tc.image.version%.docker-compose.yml
                ${Utils.getSampleComposeFile("%docker.nightlyRepository%", "%tc.image.version%")}
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
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_315"
            }
        }
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
