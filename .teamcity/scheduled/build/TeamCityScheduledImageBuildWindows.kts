package scheduled.build

import common.TeamCityDockerImagesRepo_AllBranches
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import utils.ImageInfoRepository
import utils.dsl.general.teamCityBuildDistDocker
import utils.dsl.general.teamCityImageBuildFeatures
import utils.dsl.steps.buildImage
import utils.dsl.steps.publishToStaging

/**
 * Scheduled build of TeamCity Docker Images for Windows.
 */
object TeamCityScheduledImageBuildWindows : BuildType({
    name = "[amd] TeamCity Docker Images - Automated Scheduled Build - Windows"

    vcs {
        root(TeamCityDockerImagesRepo_AllBranches)
    }

    params {
        // the images will be published into registry that holds nightly builds
        param("docker.buildRepository", "%docker.nightlyRepository%")
        // no postfix needed
        param("docker.buildImagePostfix", "")
        param("tc.image.version", "%dockerImage.teamcity.buildNumber%")
    }

    steps {
        // 1. Build Windows 1809-based images
        val win1809images = ImageInfoRepository.getWindowsImages1809()
        win1809images.forEach { winImage1809 ->
            buildImage(winImage1809)
        }

        // 2. Publish Windows 1809 images into staging (nightly) repository
        win1809images.forEach { imageInfo -> publishToStaging(imageInfo) }

        // 3. Build Windows 2004-based images
        val win2004images = ImageInfoRepository.getWindowsImages2004()
        win2004images.forEach { winImage2004 ->
            buildImage(winImage2004)
        }

        // 4. Publish Windows 2004 images into staging (nightly) repository
        win2004images.forEach { imageInfo -> publishToStaging(imageInfo) }
    }

    dependencies {
        teamCityBuildDistDocker()
    }

    features {
        teamCityImageBuildFeatures(requiredSpaceGb = 50)
    }

    // An implicit Windows 10 requirement has been added in order to prevent DotNet's WebClient internal exception.
    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows 10")
    }
})
