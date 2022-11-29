// NOTE: THIS IS AN AUTO-GENERATED FILE. IT HAD BEEN CREATED USING TEAMCITY.DOCKER PROJECT. ...
// ... IF NEEDED, PLEASE, EDIT DSL GENERATOR RATHER THAN THE FILES DIRECTLY. ...
// ... FOR MORE DETAILS, PLEASE, REFER TO DOCUMENTATION WITHIN THE REPOSITORY.
package generated

import common.TeamCityDockerImagesRepo
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.BuildFailureOnText
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.failOnText
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.BuildFailureOnMetric
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.failOnMetricChange
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger


object image_validation: BuildType({
		name = "Validation (post-push) of Docker images (Windows / Linux)"
		buildNumberPattern="test-%build.counter%"

		vcs {
			root(TeamCityDockerImagesRepo.TeamCityDockerImagesRepo)
		}

		triggers {
			// Execute the build once the images are available within %deployRepository%
			finishBuildTrigger {
				buildType = "${PublishHubVersion.publish_hub_version.id}"
			}
		}

		params {
			// -- inherited parameter, removed in debug purposes
			param("dockerImage.teamcity.buildNumber", "-")
		}

		val images = listOf("%docker.deployRepository%teamcity-agent:2022.10-windowsservercore-1809",
			"%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2022.10-nanoserver-1809",
			"%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2022.10-nanoserver-1809",
			"%docker.deployRepository%teamcity-server%docker.buildImagePostfix%:2022.10-nanoserver-2004",
			"%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2022.10-windowsservercore-2004",
			"%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2022.10-nanoserver-2004",
			"%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2022.10-nanoserver-2004",
			// -- linux images
			"%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2022.10-linux",
			"%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2022.10-linux-sudo",
			"%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2022.10-linux"
		)

		steps {
			images.forEach { imageFqdn ->
				// Generate validation for each image fully-qualified domain name (FQDN)
				gradle {
					name = "Image Verification Gradle - $imageFqdn"
					tasks = "clean build run --args=\"validate  $imageFqdn %docker.buildRepository.login% %docker.buildRepository.token%\""
					workingDir = "tool/automation/framework"
					buildFile = "build.gradle"
					jdkHome = "%env.JDK_11_x64%"
					executionMode = BuildStep.ExecutionMode.ALWAYS
				}
			}
		}

		failureConditions {
			//	Build is considered to be failed if the size of any image had changed by more than 5%
//            images.forEach {
//                failOnMetricChange {
//                    // -- target metric
//                    param("metricKey", "SIZE-$it".replace("%docker.deployRepository%", "").replace("2022.10-", ""))
//                    units = BuildFailureOnMetric.MetricUnit.PERCENTS
//                    threshold = 5
//                    comparison = BuildFailureOnMetric.MetricComparison.MORE
//                    compareTo = build {
//                        buildRule = lastSuccessful()
//                    }
//                }
//            }

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
			noLessThanVer("docker.version", "18.05.0")
			exists("env.JDK_11")
			// Images are validated mostly via DockerHub REST API. In case ...
			// ... Docker agent will be used, platform-compatibility must be addressed, ...
			// ... especially in case of Windows images.
			contains("teamcity.agent.jvm.os.name", "Linux")
		}

		features {
			dockerSupport {
				cleanupPushedImages = true
				loginToRegistry = on {
					dockerRegistryId = "PROJECT_EXT_774,PROJECT_EXT_315"
				}
			}
		}

//	dependencies {
//		 dependency(AbsoluteId("TC_Trunk_DockerImages_push_hub_windows")) {
//			 snapshot { onDependencyFailure = FailureAction.ADD_PROBLEM }
//		 }
//		 dependency(AbsoluteId("TC_Trunk_DockerImages_push_hub_linux")) {
//			 snapshot { onDependencyFailure = FailureAction.ADD_PROBLEM }
//		 }
//	}
})
