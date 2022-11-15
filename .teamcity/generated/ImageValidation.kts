// NOTE: THIS IS AN AUTO-GENERATED FILE. IT HAD BEEN CREATED USING TEAMCITY.DOCKER PROJECT. ...
// ... IF NEEDED, PLEASE, EDIT DSL GENERATOR RATHER THAN THE FILES DIRECTLY. ...
// ... FOR MORE DETAILS, PLEASE, REFER TO DOCUMENTATION WITHIN THE REPOSITORY.
package generated

import common.TeamCityDockerImagesRepo
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.BuildFailureOnText
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.failOnText
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.kotlinFile
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger


object image_validation: BuildType(
    {


        name = "Validation (post-push) of Docker images"
        buildNumberPattern="test-%build.counter%"

        vcs {root(TeamCityDockerImagesRepo.TeamCityDockerImagesRepo)}

        params {
            // -- inherited parameter, removed in debug purposes
            param("dockerImage.teamcity.buildNumber", "-")
        }

        val images = listOf("%docker.deployRepository%teamcity-server:2022.10-linux",
                                        "%docker.deployRepository%teamcity-agent:2022.10-linux",
                                        "%docker.deployRepository%teamcity-agent:2022.10-linux-sudo",
                                        "%docker.deployRepository%teamcity-minimal-agent:2022.10-linux",
                                        "%docker.deployRepository%teamcity-server:2022.10-nanoserver-1809",
                                        "%docker.deployRepository%teamcity-agent:2022.10-windowsservercore-1809",
                                        "%docker.deployRepository%teamcity-agent:2022.10-nanoserver-1809",
                                        "%docker.deployRepository%teamcity-minimal-agent:2022.10-nanoserver-1809",
                                        "%docker.deployRepository%teamcity-server:2022.10-nanoserver-2004",
                                        "%docker.deployRepository%teamcity-agent:2022.10-windowsservercore-2004",
                                        "%docker.deployRepository%teamcity-agent:2022.10-nanoserver-2004",
                                        "%docker.deployRepository%teamcity-minimal-agent:2022.10-nanoserver-2004")

        steps {
            images.forEach {
                kotlinFile {
                    name = "Image Verification - $it"

                    path = "tool/automation/ImageValidation.main.kts"
                    arguments = "$it"
                    executionMode = BuildStep.ExecutionMode.ALWAYS

                }
            }
        }


        failureConditions {
            failOnText {
                conditionType = BuildFailureOnText.ConditionType.CONTAINS
                pattern = "DockerImageValidationException"
                failureMessage = "Docker Image validation have failed"
                // allows the steps to continue running even in case of one problem
                reportOnlyFirstMatch = false
            }
        }
        triggers {
            finishBuildTrigger {
                buildType = "${PublishHubVersion.publish_hub_version.id}"
            }
        }
        requirements {
            // -- compatibility with Windows images
            contains("teamcity.agent.jvm.os.name", "Windows")
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

        // -- build number dependency
//        dependency(AbsoluteId("TC_Trunk_BuildDistDocker")) {
//            snapshot {
//                reuseBuilds = ReuseBuilds.ANY
//                onDependencyFailure = FailureAction.IGNORE
//            }
//        }
//	}
    })
