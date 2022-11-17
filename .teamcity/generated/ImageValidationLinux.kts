// NOTE: THIS IS AN AUTO-GENERATED FILE. IT HAD BEEN CREATED USING TEAMCITY.DOCKER PROJECT. ...
// ... IF NEEDED, PLEASE, EDIT DSL GENERATOR RATHER THAN THE FILES DIRECTLY. ...
// ... FOR MORE DETAILS, PLEASE, REFER TO DOCUMENTATION WITHIN THE REPOSITORY.
package generated

import common.TeamCityDockerImagesRepo
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.BuildFailureOnText
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.failOnText
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.kotlinFile
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger


object image_validation_linux: BuildType(
    {


        name = "Validation (post-push) of Docker images (Linux)"
        buildNumberPattern="test-%build.counter%"

        vcs {root(TeamCityDockerImagesRepo.TeamCityDockerImagesRepo)}

        params {
            // -- inherited parameter, removed in debug purposes
            param("dockerImage.teamcity.buildNumber", "-")
        }

        val images = listOf("%docker.deployRepository%teamcity-agent:2022.10-linux",
                            "%docker.deployRepository%teamcity-agent:2022.10-linux-sudo",
                            "%docker.deployRepository%teamcity-minimal-agent:2022.10-linux")

        steps {
            images.forEach {
                // 1. pull image
                dockerCommand {
                    name = "pull $it"
                    commandType = other {
                        subCommand = "pull"
                        commandArgs = "$it"
                    }
                }

                // 2. verify image
                kotlinFile {
                    name = "Image Verification - $it"

                    path = "tool/automation/ImageValidation.main.kts"
                    arguments = "$it"
                    executionMode = BuildStep.ExecutionMode.ALWAYS

                }
            }
        }


        failureConditions {

            // fail in case statistics for any image changes for more than N percent
//            images.forEach {
//                failOnMetricChange {
//                    // -- target metric
//                    param("metricKey", it.replace("%docker.deployRepository%", "").replace("2022.10-", ""))
//                    units = BuildFailureOnMetric.MetricUnit.PERCENTS
//                    // -- 5% increase
//                    threshold = 5
//                    comparison = BuildFailureOnMetric.MetricComparison.MORE
//                    compareTo = build {
//                        buildRule = lastSuccessful()
//                    }
//                }
//            }


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
            // -- compatibility with Linux images
            contains("teamcity.agent.jvm.os.name", "Linux")
            noLessThanVer("docker.version", "18.05.0")
        }

        features {
            dockerSupport {
                cleanupPushedImages = true
                loginToRegistry = on {
                    dockerRegistryId = "PROJECT_EXT_774,PROJECT_EXT_315"
                }
            }
        }
    })
