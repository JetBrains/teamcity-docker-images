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

object push_hub_linux: BuildType({
	 name = "Push linux"
	buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
	 steps {
		dockerCommand {
			 
			 name = "pull teamcity-server%docker.buildImagePostfix%:EAP-linux"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-linux"
			 }
		}
		
		dockerCommand {
			
			 name = "tag teamcity-server%docker.buildImagePostfix%:EAP-linux"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-linux %docker.deployRepository%teamcity-server:EAP-linux"
			}
		}
		
		dockerCommand {
			 
			 name = "push teamcity-server%docker.buildImagePostfix%:EAP-linux"
			 commandType = push {
				 namesAndTags = """
		%docker.deployRepository%teamcity-server:EAP-linux
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "pull teamcity-agent%docker.buildImagePostfix%:EAP-linux"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux"
			 }
		}
		
		dockerCommand {
			
			 name = "tag teamcity-agent%docker.buildImagePostfix%:EAP-linux"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux %docker.deployRepository%teamcity-agent:EAP-linux"
			}
		}
		
		dockerCommand {
			 
			 name = "push teamcity-agent%docker.buildImagePostfix%:EAP-linux"
			 commandType = push {
				 namesAndTags = """
		%docker.deployRepository%teamcity-agent:EAP-linux
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "pull teamcity-agent%docker.buildImagePostfix%:EAP-linux-sudo"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-sudo"
			 }
		}
		
		dockerCommand {
			
			 name = "tag teamcity-agent%docker.buildImagePostfix%:EAP-linux-sudo"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-sudo %docker.deployRepository%teamcity-agent:EAP-linux-sudo"
			}
		}
		
		dockerCommand {
			 
			 name = "push teamcity-agent%docker.buildImagePostfix%:EAP-linux-sudo"
			 commandType = push {
				 namesAndTags = """
		%docker.deployRepository%teamcity-agent:EAP-linux-sudo
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "pull teamcity-minimal-agent%docker.buildImagePostfix%:EAP-linux"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-linux"
			 }
		}
		
		dockerCommand {
			
			 name = "tag teamcity-minimal-agent%docker.buildImagePostfix%:EAP-linux"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-linux %docker.deployRepository%teamcity-minimal-agent:EAP-linux"
			}
		}
		
		dockerCommand {
			 
			 name = "push teamcity-minimal-agent%docker.buildImagePostfix%:EAP-linux"
			 commandType = push {
				 namesAndTags = """
		%docker.deployRepository%teamcity-minimal-agent:EAP-linux
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
	 }
	 features {
		 freeDiskSpace {
		 	 requiredSpace = "4gb"
		 	 failBuild = true
		 }
		 dockerSupport {
		 	 cleanupPushedImages = true
		 	 loginToRegistry = on {
		 		 dockerRegistryId = "PROJECT_EXT_774"
		 	 }
		 }
		 swabra {
		 	 forceCleanCheckout = true
		 }
	 }
	params {
		 param("system.teamcity.agent.ensure.free.space", "4gb")
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

