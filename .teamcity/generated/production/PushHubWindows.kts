// NOTE: THIS IS AN AUTO-GENERATED FILE. IT HAD BEEN CREATED USING TEAMCITY.DOCKER PROJECT. ...
// ... IF NEEDED, PLEASE, EDIT DSL GENERATOR RATHER THAN THE FILES DIRECTLY. ... 
// ... FOR MORE DETAILS, PLEASE, REFER TO DOCUMENTATION WITHIN THE REPOSITORY.
package generated.production

import hosted.utils.dsl.general.teamCityImageBuildFeatures
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

object push_hub_windows : BuildType({
    name = "Push windows"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    steps {
        dockerCommand {

            name = "pull teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809"
            commandType = other {
                subCommand = "pull"
                commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809"
            }
        }

        dockerCommand {

            name = "tag teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809"
            commandType = other {
                subCommand = "tag"
                commandArgs =
                    "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809 %docker.deployRepository%teamcity-server:EAP-nanoserver-1809"
            }
        }

        dockerCommand {

            name = "push teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809"
            commandType = push {
                namesAndTags = """
		%docker.deployRepository%teamcity-server:EAP-nanoserver-1809
		""".trimIndent()
                removeImageAfterPush = false
            }
        }

        dockerCommand {

            name = "pull teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809"
            commandType = other {
                subCommand = "pull"
                commandArgs =
                    "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809"
            }
        }

        dockerCommand {

            name = "tag teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809"
            commandType = other {
                subCommand = "tag"
                commandArgs =
                    "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809 %docker.deployRepository%teamcity-agent:EAP-windowsservercore-1809"
            }
        }

        dockerCommand {

            name = "push teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809"
            commandType = push {
                namesAndTags = """
		%docker.deployRepository%teamcity-agent:EAP-windowsservercore-1809
		""".trimIndent()
                removeImageAfterPush = false
            }
        }

        dockerCommand {

            name = "pull teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
            commandType = other {
                subCommand = "pull"
                commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
            }
        }

        dockerCommand {

            name = "tag teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
            commandType = other {
                subCommand = "tag"
                commandArgs =
                    "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809 %docker.deployRepository%teamcity-agent:EAP-nanoserver-1809"
            }
        }

        dockerCommand {

            name = "push teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
            commandType = push {
                namesAndTags = """
		%docker.deployRepository%teamcity-agent:EAP-nanoserver-1809
		""".trimIndent()
                removeImageAfterPush = false
            }
        }

        dockerCommand {

            name = "pull teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
            commandType = other {
                subCommand = "pull"
                commandArgs =
                    "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
            }
        }

        dockerCommand {

            name = "tag teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
            commandType = other {
                subCommand = "tag"
                commandArgs =
                    "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-1809"
            }
        }

        dockerCommand {

            name = "push teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
            commandType = push {
                namesAndTags = """
		%docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-1809
		""".trimIndent()
                removeImageAfterPush = false
            }
        }

        dockerCommand {

            name = "pull teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-2004"
            commandType = other {
                subCommand = "pull"
                commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-2004"
            }
        }

        dockerCommand {

            name = "tag teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-2004"
            commandType = other {
                subCommand = "tag"
                commandArgs =
                    "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-2004 %docker.deployRepository%teamcity-server:EAP-nanoserver-2004"
            }
        }

        dockerCommand {

            name = "push teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-2004"
            commandType = push {
                namesAndTags = """
		%docker.deployRepository%teamcity-server:EAP-nanoserver-2004
		""".trimIndent()
                removeImageAfterPush = false
            }
        }

        dockerCommand {

            name = "pull teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-2004"
            commandType = other {
                subCommand = "pull"
                commandArgs =
                    "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-2004"
            }
        }

        dockerCommand {

            name = "tag teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-2004"
            commandType = other {
                subCommand = "tag"
                commandArgs =
                    "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-2004 %docker.deployRepository%teamcity-agent:EAP-windowsservercore-2004"
            }
        }

        dockerCommand {

            name = "push teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-2004"
            commandType = push {
                namesAndTags = """
		%docker.deployRepository%teamcity-agent:EAP-windowsservercore-2004
		""".trimIndent()
                removeImageAfterPush = false
            }
        }

        dockerCommand {

            name = "pull teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
            commandType = other {
                subCommand = "pull"
                commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
            }
        }

        dockerCommand {

            name = "tag teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
            commandType = other {
                subCommand = "tag"
                commandArgs =
                    "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-2004 %docker.deployRepository%teamcity-agent:EAP-nanoserver-2004"
            }
        }

        dockerCommand {

            name = "push teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
            commandType = push {
                namesAndTags = """
		%docker.deployRepository%teamcity-agent:EAP-nanoserver-2004
		""".trimIndent()
                removeImageAfterPush = false
            }
        }

        dockerCommand {

            name = "pull teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
            commandType = other {
                subCommand = "pull"
                commandArgs =
                    "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
            }
        }

        dockerCommand {

            name = "tag teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
            commandType = other {
                subCommand = "tag"
                commandArgs =
                    "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-2004 %docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-2004"
            }
        }

        dockerCommand {

            name = "push teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
            commandType = push {
                namesAndTags = """
		%docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-2004
		""".trimIndent()
                removeImageAfterPush = false
            }
        }

    }

    features {
        // Windows Images Require more disk space
        teamCityImageBuildFeatures(requiredSpaceGb = 52)
    }

    requirements {
        contains("docker.server.osType", "windows")
        // In order to correctly build AMD-based images, we wouldn't want it to be scheduled on ARM-based agent
        doesNotContain("teamcity.agent.name", "arm")
        contains("teamcity.agent.jvm.os.name", "Windows 10")
    }

    dependencies {
        snapshot(PublishLocal.publish_local) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }
})

