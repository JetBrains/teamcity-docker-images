// NOTE: THIS IS AN AUTO-GENERATED FILE. IT HAD BEEN CREATED USING TEAMCITY.DOCKER PROJECT. ...
// ... IF NEEDED, PLEASE, EDIT DSL GENERATOR RATHER THAN THE FILES DIRECTLY. ... 
// ... FOR MORE DETAILS, PLEASE, REFER TO DOCUMENTATION WITHIN THE REPOSITORY.
package generated

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.BuildFailureOnText
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.failOnText
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.BuildFailureOnMetric
import jetbrains.buildServer.configs.kotlin.v2019_2.failureConditions.failOnMetricChange
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.kotlinFile
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.Trigger
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import hosted.BuildAndPushHosted

object push_hub_linux: BuildType({
	 name = "Push linux"
	buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
	 steps {
		dockerCommand {
			 
			 name = "pull teamcity-server%docker.buildImagePostfix%:2023.05.6-linux"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2023.05.6-linux"
			 }
		}
		
		dockerCommand {
			
			 name = "tag teamcity-server%docker.buildImagePostfix%:2023.05.6-linux"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2023.05.6-linux %docker.deployRepository%teamcity-server:2023.05.6-linux"
			}
		}
		
		dockerCommand {
			 
			 name = "push teamcity-server%docker.buildImagePostfix%:2023.05.6-linux"
			 commandType = push {
				 namesAndTags = """
		%docker.deployRepository%teamcity-server:2023.05.6-linux
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 enabled = false
			 name = "pull teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-arm64-sudo"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-arm64-sudo"
			 }
		}
		
		dockerCommand {
			enabled = false
			name = "tag teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-arm64-sudo"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-arm64-sudo %docker.deployRepository%teamcity-agent:2023.05.6-linux-arm64-sudo"
			}
		}
		
		dockerCommand {
			enabled = false
			name = "push teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-arm64-sudo"
			 commandType = push {
				 namesAndTags = """
		%docker.deployRepository%teamcity-agent:2023.05.6-linux-arm64-sudo
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			enabled = false
			name = "pull teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-arm64"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-arm64"
			 }
		}
		
		dockerCommand {
			enabled = false
			name = "tag teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-arm64"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-arm64 %docker.deployRepository%teamcity-agent:2023.05.6-linux-arm64"
			}
		}
		
		dockerCommand {
			enabled = false
			name = "push teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-arm64"
			 commandType = push {
				 namesAndTags = """
		%docker.deployRepository%teamcity-agent:2023.05.6-linux-arm64
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "pull teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux"
			 }
		}
		
		dockerCommand {
			
			 name = "tag teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux %docker.deployRepository%teamcity-agent:2023.05.6-linux"
			}
		}
		
		dockerCommand {
			 
			 name = "push teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux"
			 commandType = push {
				 namesAndTags = """
		%docker.deployRepository%teamcity-agent:2023.05.6-linux
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "pull teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-sudo"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-sudo"
			 }
		}
		
		dockerCommand {
			
			 name = "tag teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-sudo"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-sudo %docker.deployRepository%teamcity-agent:2023.05.6-linux-sudo"
			}
		}
		
		dockerCommand {
			 
			 name = "push teamcity-agent%docker.buildImagePostfix%:2023.05.6-linux-sudo"
			 commandType = push {
				 namesAndTags = """
		%docker.deployRepository%teamcity-agent:2023.05.6-linux-sudo
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "pull teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.6-linux"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.6-linux"
			 }
		}
		
		dockerCommand {
			
			 name = "tag teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.6-linux"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.6-linux %docker.deployRepository%teamcity-minimal-agent:2023.05.6-linux"
			}
		}
		
		dockerCommand {
			 
			 name = "push teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.6-linux"
			 commandType = push {
				 namesAndTags = """
		%docker.deployRepository%teamcity-minimal-agent:2023.05.6-linux
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
	 }
	 features {
		 freeDiskSpace {
		 	 requiredSpace = "6gb"
		 	 failBuild = true
		 }
		 dockerSupport {
		 	 cleanupPushedImages = false
		 	 loginToRegistry = on {
		 		 dockerRegistryId = "PROJECT_EXT_774,PROJECT_EXT_315"
		 	 }
		 }
		 swabra {
		 	 forceCleanCheckout = true
		 }
	 }
	params {
		 param("system.teamcity.agent.ensure.free.space", "6gb")
	}
	 requirements {
	 	 contains("docker.server.osType", "linux")
	 }
		 dependencies {
			 snapshot(PublishLocal.publish_local) {

				 onDependencyFailure =  FailureAction.FAIL_TO_START 
 		 }
		 }
})

