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

object push_local_linux_20_04 : BuildType({
	 name = "Build and push linux 20.04"
	 buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
	 description  = "teamcity-server:2023.05.1-linux,2023.05.1 teamcity-minimal-agent:2023.05.1-linux,2023.05.1 teamcity-agent:2023.05.1-linux,2023.05.1:2023.05.1-linux-sudo:2023.05.1-linux-arm64,2023.05.1:2023.05.1-linux-arm64-sudo"
	 vcs {
		 root(TeamCityDockerImagesRepo)
	 }

 	 steps {
		dockerCommand {
			 
			 name = "pull ubuntu:20.04"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "ubuntu:20.04"
			 }
		}
		
		script {
			
			 name = "context teamcity-server:2023.05.1-linux"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity/buildAgent >> context/.dockerignore
		echo TeamCity/temp >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-server:2023.05.1-linux"
			 commandType = build {
				 source = file {
					 path = """context/generated/linux/Server/Ubuntu/20.04/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-server:2023.05.1-linux
		""".trimIndent()
		}
		param("dockerImage.platform", "linux")
		}
		
		script {
			
			 name = "context teamcity-minimal-agent:2023.05.1-linux"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity/webapps >> context/.dockerignore
		echo TeamCity/devPackage >> context/.dockerignore
		echo TeamCity/lib >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-minimal-agent:2023.05.1-linux"
			 commandType = build {
				 source = file {
					 path = """context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-minimal-agent:2023.05.1-linux
		""".trimIndent()
		}
		param("dockerImage.platform", "linux")
		}
		
		script {
			
			 name = "context teamcity-agent:2023.05.1-linux"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-agent:2023.05.1-linux"
			 commandType = build {
				 source = file {
					 path = """context/generated/linux/Agent/Ubuntu/20.04/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-agent:2023.05.1-linux
		""".trimIndent()
		}
		param("dockerImage.platform", "linux")
		}
		
		script {
			
			 name = "context teamcity-agent:2023.05.1-linux-sudo"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-agent:2023.05.1-linux-sudo"
			 commandType = build {
				 source = file {
					 path = """context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-agent:2023.05.1-linux-sudo
		""".trimIndent()
		}
		param("dockerImage.platform", "linux")
		}
		
		script {
			// ARM-based images are currently not supported by TeamCity 
			enabled = false
			 name = "context teamcity-agent:2023.05.1-linux-arm64"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		// ARM-based images are currently not supported by TeamCity 
			enabled = false
			 name = "build teamcity-agent:2023.05.1-linux-arm64"
			 commandType = build {
				 source = file {
					 path = """context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-agent:2023.05.1-linux-arm64
		""".trimIndent()
		}
		param("dockerImage.platform", "linux")
		}
		
		script {
			// ARM-based images are currently not supported by TeamCity 
			enabled = false
			 name = "context teamcity-agent:2023.05.1-linux-arm64-sudo"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		// ARM-based images are currently not supported by TeamCity 
			enabled = false
			 name = "build teamcity-agent:2023.05.1-linux-arm64-sudo"
			 commandType = build {
				 source = file {
					 path = """context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-agent:2023.05.1-linux-arm64-sudo
		""".trimIndent()
		}
		param("dockerImage.platform", "linux")
		}
		
		dockerCommand {
			
			 name = "tag teamcity-server:2023.05.1-linux"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-server:2023.05.1-linux %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2023.05.1-linux"
			}
		}
		
		dockerCommand {
			
			 name = "tag teamcity-minimal-agent:2023.05.1-linux"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-minimal-agent:2023.05.1-linux %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.1-linux"
			}
		}
		
		dockerCommand {
			
			 name = "tag teamcity-agent:2023.05.1-linux"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-agent:2023.05.1-linux %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.1-linux"
			}
		}
		
		dockerCommand {
			
			 name = "tag teamcity-agent:2023.05.1-linux-sudo"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-agent:2023.05.1-linux-sudo %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.1-linux-sudo"
			}
		}
		
		dockerCommand {
			// ARM-based images are currently not supported by TeamCity 
			enabled = false
			 name = "tag teamcity-agent:2023.05.1-linux-arm64"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-agent:2023.05.1-linux-arm64 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.1-linux-arm64"
			}
		}
		
		dockerCommand {
			// ARM-based images are currently not supported by TeamCity 
			enabled = false
			 name = "tag teamcity-agent:2023.05.1-linux-arm64-sudo"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-agent:2023.05.1-linux-arm64-sudo %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.1-linux-arm64-sudo"
			}
		}
		
		dockerCommand {
			 
			 name = "push teamcity-server:2023.05.1-linux"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2023.05.1-linux
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "push teamcity-minimal-agent:2023.05.1-linux"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.1-linux
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "push teamcity-agent:2023.05.1-linux"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.1-linux
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "push teamcity-agent:2023.05.1-linux-sudo"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.1-linux-sudo
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 // ARM-based images are currently not supported by TeamCity 
			enabled = false
			 name = "push teamcity-agent:2023.05.1-linux-arm64"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.1-linux-arm64
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 // ARM-based images are currently not supported by TeamCity 
			enabled = false
			 name = "push teamcity-agent:2023.05.1-linux-arm64-sudo"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.1-linux-arm64-sudo
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
	}
	features {
		freeDiskSpace {
			 requiredSpace = "8gb"
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
	dependencies {
		 dependency(AbsoluteId("TC_Trunk_BuildDistDocker")) {
			 snapshot {
				 onDependencyFailure = FailureAction.IGNORE
				 reuseBuilds = ReuseBuilds.ANY
			 }
			 artifacts {
				 artifactRules = "TeamCity.zip!/**=>context/TeamCity"
			 }
		 }
	}
	params {
		 param("system.teamcity.agent.ensure.free.space", "8gb")
	}
	requirements {
	}
})

