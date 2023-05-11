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

object publish_hub_version: BuildType({
	 name = "Publish as version"
	buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
	 enablePersonalBuilds = false
	 type = BuildTypeSettings.Type.DEPLOYMENT
	 maxRunningBuilds = 1
	 steps {
		 script {
		 	 name = "remove manifests"
		 	 scriptContent = """if exist "%%USERPROFILE%%\.docker\manifests\" rmdir "%%USERPROFILE%%\.docker\manifests\" /s /q"""
		 }
	dockerCommand {
		 name = "manifest create teamcity-server:2023.05"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "create %docker.deployRepository%teamcity-server:2023.05 %docker.deployRepository%teamcity-server:2023.05-linux %docker.deployRepository%teamcity-server:2023.05-nanoserver-1809 %docker.deployRepository%teamcity-server:2023.05-nanoserver-2004"
		 }
	}
	dockerCommand {
		 name = "manifest push teamcity-server:2023.05"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "push %docker.deployRepository%teamcity-server:2023.05"
		 }
	}
	dockerCommand {
		 name = "manifest inspect teamcity-server:2023.05"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "inspect %docker.deployRepository%teamcity-server:2023.05 --verbose"
		 }
	}
	dockerCommand {
		 name = "manifest create teamcity-agent:2023.05"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "create %docker.deployRepository%teamcity-agent:2023.05 %docker.deployRepository%teamcity-agent:2023.05-linux %docker.deployRepository%teamcity-agent:2023.05-nanoserver-1809 %docker.deployRepository%teamcity-agent:2023.05-nanoserver-2004"
		 }
	}
	dockerCommand {
		 name = "manifest push teamcity-agent:2023.05"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "push %docker.deployRepository%teamcity-agent:2023.05"
		 }
	}
	dockerCommand {
		 name = "manifest inspect teamcity-agent:2023.05"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "inspect %docker.deployRepository%teamcity-agent:2023.05 --verbose"
		 }
	}
	dockerCommand {
		 name = "manifest create teamcity-minimal-agent:2023.05"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "create %docker.deployRepository%teamcity-minimal-agent:2023.05 %docker.deployRepository%teamcity-minimal-agent:2023.05-linux %docker.deployRepository%teamcity-minimal-agent:2023.05-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:2023.05-nanoserver-2004"
		 }
	}
	dockerCommand {
		 name = "manifest push teamcity-minimal-agent:2023.05"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "push %docker.deployRepository%teamcity-minimal-agent:2023.05"
		 }
	}
	dockerCommand {
		 name = "manifest inspect teamcity-minimal-agent:2023.05"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "inspect %docker.deployRepository%teamcity-minimal-agent:2023.05 --verbose"
		 }
	}
	dockerCommand {
		 name = "manifest create teamcity-agent:2023.05-windowsservercore"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "create %docker.deployRepository%teamcity-agent:2023.05-windowsservercore %docker.deployRepository%teamcity-agent:2023.05-windowsservercore-1809 %docker.deployRepository%teamcity-agent:2023.05-windowsservercore-2004"
		 }
	}
	dockerCommand {
		 name = "manifest push teamcity-agent:2023.05-windowsservercore"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "push %docker.deployRepository%teamcity-agent:2023.05-windowsservercore"
		 }
	}
	dockerCommand {
		 name = "manifest inspect teamcity-agent:2023.05-windowsservercore"
		 commandType = other {
			 subCommand = "manifest"
			 commandArgs = "inspect %docker.deployRepository%teamcity-agent:2023.05-windowsservercore --verbose"
		 }
	}
	 }
		 dependencies {
			 snapshot(AbsoluteId("TC2023_05_BuildDistDocker")) {

				 reuseBuilds = ReuseBuilds.ANY 
 			 onDependencyFailure = FailureAction.IGNORE 
	 }
			 snapshot(PushHubLinux.push_hub_linux) {

				 onDependencyFailure =  FailureAction.FAIL_TO_START 
 		 }
			 snapshot(PushHubWindows.push_hub_windows) {

				 onDependencyFailure =  FailureAction.FAIL_TO_START 
 		 }
		 }
	requirements {
		 noLessThanVer("docker.version", "18.05.0")
		 contains("docker.server.osType", "windows")
		 contains("teamcity.agent.jvm.os.name", "Windows 10")
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

