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

object push_local_windows_2004 : BuildType({
	 name = "Build and push windows 2004"
	 buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
	 description  = "teamcity-server:EAP-nanoserver-2004,EAP teamcity-minimal-agent:EAP-nanoserver-2004,EAP teamcity-agent:EAP-windowsservercore-2004,EAP-windowsservercore,-windowsservercore:EAP-nanoserver-2004,EAP"
	 vcs {
		 root(TeamCityDockerImagesRepo)
	 }

 	 steps {
		dockerCommand {
			 
			 name = "pull mcr.microsoft.com/powershell:nanoserver-2004"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "mcr.microsoft.com/powershell:nanoserver-2004"
			 }
		}
		
		dockerCommand {
			 
			 name = "pull mcr.microsoft.com/windows/nanoserver:2004"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "mcr.microsoft.com/windows/nanoserver:2004"
			 }
		}
		
		dockerCommand {
			 
			 name = "pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-2004"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-2004"
			 }
		}
		
		script {
			
			 name = "context teamcity-server:EAP-nanoserver-2004"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity/buildAgent >> context/.dockerignore
		echo TeamCity/temp >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-server:EAP-nanoserver-2004"
			 commandType = build {
				 source = file {
					 path = """context/generated/windows/Server/nanoserver/2004/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-server:EAP-nanoserver-2004
		""".trimIndent()
		}
		param("dockerImage.platform", "windows")
		}
		
		script {
			
			 name = "context teamcity-minimal-agent:EAP-nanoserver-2004"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity/webapps >> context/.dockerignore
		echo TeamCity/devPackage >> context/.dockerignore
		echo TeamCity/lib >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-minimal-agent:EAP-nanoserver-2004"
			 commandType = build {
				 source = file {
					 path = """context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-minimal-agent:EAP-nanoserver-2004
		""".trimIndent()
		}
		param("dockerImage.platform", "windows")
		}
		
		script {
			
			 name = "context teamcity-agent:EAP-windowsservercore-2004"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity/webapps >> context/.dockerignore
		echo TeamCity/devPackage >> context/.dockerignore
		echo TeamCity/lib >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-agent:EAP-windowsservercore-2004"
			 commandType = build {
				 source = file {
					 path = """context/generated/windows/Agent/windowsservercore/2004/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-agent:EAP-windowsservercore-2004
		""".trimIndent()
		}
		param("dockerImage.platform", "windows")
		}
		
		script {
			
			 name = "context teamcity-agent:EAP-nanoserver-2004"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity/webapps >> context/.dockerignore
		echo TeamCity/devPackage >> context/.dockerignore
		echo TeamCity/lib >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-agent:EAP-nanoserver-2004"
			 commandType = build {
				 source = file {
					 path = """context/generated/windows/Agent/nanoserver/2004/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-agent:EAP-nanoserver-2004
		""".trimIndent()
		}
		param("dockerImage.platform", "windows")
		}
		
		dockerCommand {
			
			 name = "tag teamcity-server:EAP-nanoserver-2004"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-server:EAP-nanoserver-2004 %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-2004"
			}
		}
		
		dockerCommand {
			
			 name = "tag teamcity-minimal-agent:EAP-nanoserver-2004"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-minimal-agent:EAP-nanoserver-2004 %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
			}
		}
		
		dockerCommand {
			
			 name = "tag teamcity-agent:EAP-windowsservercore-2004"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-agent:EAP-windowsservercore-2004 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-2004"
			}
		}
		
		dockerCommand {
			
			 name = "tag teamcity-agent:EAP-nanoserver-2004"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-agent:EAP-nanoserver-2004 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
			}
		}
		
		dockerCommand {
			 
			 name = "push teamcity-server:EAP-nanoserver-2004"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-2004
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "push teamcity-minimal-agent:EAP-nanoserver-2004"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-2004
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "push teamcity-agent:EAP-windowsservercore-2004"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-2004
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "push teamcity-agent:EAP-nanoserver-2004"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-2004
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
	}
	features {
		freeDiskSpace {
			 requiredSpace = "43gb"
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
		 param("system.teamcity.agent.ensure.free.space", "43gb")
	}
	requirements {
		// In order to correctly build AMD-based images, we wouldn't want it to be scheduled on ARM-based agent
		doesNotContain("teamcity.agent.name", "arm")
		 contains("teamcity.agent.jvm.os.name", "Windows 10")
	}
})

