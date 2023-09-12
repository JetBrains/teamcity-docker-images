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

object push_local_windows_1809 : BuildType({
	 name = "Build and push windows 1809"
	 buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
	 description  = "teamcity-server:2023.05.4-nanoserver-1809,latest,2023.05.4 teamcity-minimal-agent:2023.05.4-nanoserver-1809,latest,2023.05.4 teamcity-agent:2023.05.4-windowsservercore-1809,2023.05.4-windowsservercore,latest-windowsservercore:2023.05.4-nanoserver-1809,latest,2023.05.4"
	 vcs {
		 root(TeamCityDockerImagesRepo)
	 }

 	 steps {
		dockerCommand {
			 
			 name = "pull mcr.microsoft.com/powershell:nanoserver-1809"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "mcr.microsoft.com/powershell:nanoserver-1809"
			 }
		}
		
		dockerCommand {
			 
			 name = "pull mcr.microsoft.com/windows/nanoserver:1809"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "mcr.microsoft.com/windows/nanoserver:1809"
			 }
		}
		
		dockerCommand {
			 
			 name = "pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019"
			 commandType = other {
				 subCommand = "pull"
				 commandArgs = "mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019"
			 }
		}
		
		script {
			
			 name = "context teamcity-server:2023.05.4-nanoserver-1809"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity/buildAgent >> context/.dockerignore
		echo TeamCity/temp >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-server:2023.05.4-nanoserver-1809"
			 commandType = build {
				 source = file {
					 path = """context/generated/windows/Server/nanoserver/1809/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-server:2023.05.4-nanoserver-1809
		""".trimIndent()
		}
		param("dockerImage.platform", "windows")
		}
		
		script {
			
			 name = "context teamcity-minimal-agent:2023.05.4-nanoserver-1809"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity/webapps >> context/.dockerignore
		echo TeamCity/devPackage >> context/.dockerignore
		echo TeamCity/lib >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-minimal-agent:2023.05.4-nanoserver-1809"
			 commandType = build {
				 source = file {
					 path = """context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-minimal-agent:2023.05.4-nanoserver-1809
		""".trimIndent()
		}
		param("dockerImage.platform", "windows")
		}
		
		script {
			
			 name = "context teamcity-agent:2023.05.4-windowsservercore-1809"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity/webapps >> context/.dockerignore
		echo TeamCity/devPackage >> context/.dockerignore
		echo TeamCity/lib >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-agent:2023.05.4-windowsservercore-1809"
			 commandType = build {
				 source = file {
					 path = """context/generated/windows/Agent/windowsservercore/1809/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-agent:2023.05.4-windowsservercore-1809
		""".trimIndent()
		}
		param("dockerImage.platform", "windows")
		}
		
		script {
			
			 name = "context teamcity-agent:2023.05.4-nanoserver-1809"
			 scriptContent = """
		echo 2> context/.dockerignore
		echo TeamCity/webapps >> context/.dockerignore
		echo TeamCity/devPackage >> context/.dockerignore
		echo TeamCity/lib >> context/.dockerignore
		""".trimIndent()
		}
		
		dockerCommand {
		
			 name = "build teamcity-agent:2023.05.4-nanoserver-1809"
			 commandType = build {
				 source = file {
					 path = """context/generated/windows/Agent/nanoserver/1809/Dockerfile"""
				 }
			 contextDir = "context"
			 commandArgs = "--no-cache"
			 namesAndTags = """
		teamcity-agent:2023.05.4-nanoserver-1809
		""".trimIndent()
		}
		param("dockerImage.platform", "windows")
		}
		
		dockerCommand {
			
			 name = "tag teamcity-server:2023.05.4-nanoserver-1809"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-server:2023.05.4-nanoserver-1809 %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2023.05.4-nanoserver-1809"
			}
		}
		
		dockerCommand {
			
			 name = "tag teamcity-minimal-agent:2023.05.4-nanoserver-1809"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-minimal-agent:2023.05.4-nanoserver-1809 %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.4-nanoserver-1809"
			}
		}
		
		dockerCommand {
			
			 name = "tag teamcity-agent:2023.05.4-windowsservercore-1809"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-agent:2023.05.4-windowsservercore-1809 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.4-windowsservercore-1809"
			}
		}
		
		dockerCommand {
			
			 name = "tag teamcity-agent:2023.05.4-nanoserver-1809"
			 commandType = other {
				 subCommand = "tag"
				 commandArgs = "teamcity-agent:2023.05.4-nanoserver-1809 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.4-nanoserver-1809"
			}
		}
		
		dockerCommand {
			 
			 name = "push teamcity-server:2023.05.4-nanoserver-1809"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2023.05.4-nanoserver-1809
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "push teamcity-minimal-agent:2023.05.4-nanoserver-1809"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.4-nanoserver-1809
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "push teamcity-agent:2023.05.4-windowsservercore-1809"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.4-windowsservercore-1809
		""".trimIndent()
				 removeImageAfterPush = false
			 }
		}
		
		dockerCommand {
			 
			 name = "push teamcity-agent:2023.05.4-nanoserver-1809"
			 commandType = push {
				 namesAndTags = """
		%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.4-nanoserver-1809
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
				 dockerRegistryId = "PROJECT_EXT_774,PROJECT_EXT_315"
			 }
		}
		swabra {
			 forceCleanCheckout = true
		}
	}
	dependencies {
		 dependency(AbsoluteId("TC2023_05_BuildDistDocker")) {
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
		 contains("teamcity.agent.jvm.os.name", "Windows 10")
	}
})

