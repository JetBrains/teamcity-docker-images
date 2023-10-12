package delivery

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import delivery.production.manifest.PublishHubVersion
import utils.dsl.general.teamCityImageBuildFeatures
import utils.dsl.general.teamCityStagingImagesSnapshot
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.BuildFailureOnText
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.failOnText
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object image_validation : BuildType({
    name = "Validation of Size Regression - Staging Docker Images (Windows / Linux)"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    vcs {
        root(TeamCityDockerImagesRepo)
    }

    triggers {
        // Execute the build once the images are available within %deployRepository%
        finishBuildTrigger {
            buildType = "${PublishHubVersion.publish_hub_version.id}"
            // if filter won't be specified, only <default> branch would be included
            branchFilter = """
                 +:development/*
                 +:release/*
             """.trimIndent()
        }
    }

    params {
        // -- inherited parameter, removed in debug purposes
        param("dockerImage.teamcity.buildNumber", "-")
    }

    val targetImages: HashMap<String, String> = hashMapOf(
        "teamcity-server-%tc.image.version%-linux" to "%docker.deployRepository%teamcity-server%docker.buildImagePostfix%:%tc.image.version%-linux",
        "teamcity-agent-%tc.image.version%-linux" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-linux",
        "teamcity-agent-%tc.image.version%-linux-arm64-sudo" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-linux-arm64-sudo",
        "teamcity-agent-%tc.image.version%-linux-amd64-sudo" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-linux-amd64-sudo",
        "teamcity-minimal-agent-%tc.image.version%-linux" to "%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:%tc.image.version%-linux",
        "teamcity-server-%tc.image.version%-nanoserver-1809" to "%docker.deployRepository%teamcity-server%docker.buildImagePostfix%:%tc.image.version%-nanoserver-1809",
        "teamcity-agent-%tc.image.version%-windowsservercore-1809" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-windowsservercore-1809",
        "teamcity-agent-%tc.image.version%-nanoserver-1809" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-nanoserver-1809",
        "teamcity-minimal-agent-%tc.image.version%-nanoserver-1809" to "%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:%tc.image.version%-nanoserver-1809",
        "teamcity-server-%tc.image.version%-nanoserver-2004" to "%docker.deployRepository%teamcity-server%docker.buildImagePostfix%:%tc.image.version%-nanoserver-2004",
        "teamcity-agent-%tc.image.version%-windowsservercore-2004" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-windowsservercore-2004",
        "teamcity-agent-%tc.image.version%-nanoserver-2004" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-nanoserver-2004",
        "teamcity-minimal-agent-%tc.image.version%-nanoserver-2004" to "%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:%tc.image.version%-nanoserver-2004"
    )

    steps {
        targetImages.forEach { (imageVerificationStepId, imageDomainName) ->
            // Generate validation for each image fully-qualified domain name (FQDN)
            gradle {
                name = "Image Verification - $imageVerificationStepId"
                tasks =
                    "clean build run --args=\"validate  $imageDomainName %docker.stagingRepository.login% %docker.stagingRepository.token%\""
                workingDir = "tool/automation/framework"
                buildFile = "build.gradle"
                jdkHome = "%env.JDK_11_x64%"
                executionMode = BuildStep.ExecutionMode.ALWAYS
            }
        }
    }

    failureConditions {
        // Failed in case the validation via framework didn't succeed
        failOnText {
            conditionType = BuildFailureOnText.ConditionType.CONTAINS
            pattern = "DockerImageValidationException"
            failureMessage = "Docker Image validation have failed"
            // allows the steps to continue running even in case of one problem
            reportOnlyFirstMatch = false
        }
    }

    requirements {
        exists("env.JDK_11")
        // Images are validated mostly via DockerHub REST API. In case ...
        // ... Docker agent will be used, platform-compatibility must be addressed, ...
        // ... especially in case of Windows images.
        contains("teamcity.agent.jvm.os.name", "Linux")
    }

    features {
        teamCityImageBuildFeatures()
    }

    dependencies {
        // Dependency on the build of the Docker image
        teamCityStagingImagesSnapshot()
    }
})
