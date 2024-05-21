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

object image_validation: BuildType({
	 name = "Validation of Size Regression - Staging Docker Images (Windows / Linux)"
	 buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
	 vcs {
		 root(TeamCityDockerImagesRepo)
	 }

	 triggers {
		 // Execute the build once the images are available within %deployRepository%
		 finishBuildTrigger {
			 buildType = "${PublishHubVersion.publish_hub_version.id}"
			 // if filter won't be specified, only <default> branch would be included
			  branchFilter = """
				 +:development/*
				 +:release/*
			 """.trimIndent()
		 }
	 }

	 params {
		 // -- inherited parameter, removed in debug purposes
		 param("dockerImage.teamcity.buildNumber", "-")
	 }

	 val targetImages: HashMap<String, String> = hashMapOf(
"teamcity-server-2023.05.5-linux" to "%docker.deployRepository%teamcity-server%docker.buildImagePostfix%:2023.05.5-linux", 
		"teamcity-agent-2023.05.5-linux-arm64-sudo" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.5-linux-arm64-sudo", 
		"teamcity-agent-2023.05.5-linux-arm64" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.5-linux-arm64", 
		"teamcity-agent-2023.05.5-linux" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.5-linux", 
		"teamcity-agent-2023.05.5-linux-sudo" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.5-linux-sudo", 
		"teamcity-minimal-agent-2023.05.5-linux" to "%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.5-linux", 
		"teamcity-server-2023.05.5-nanoserver-1809" to "%docker.deployRepository%teamcity-server%docker.buildImagePostfix%:2023.05.5-nanoserver-1809", 
		"teamcity-agent-2023.05.5-windowsservercore-1809" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.5-windowsservercore-1809", 
		"teamcity-agent-2023.05.5-nanoserver-1809" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.5-nanoserver-1809", 
		"teamcity-minimal-agent-2023.05.5-nanoserver-1809" to "%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.5-nanoserver-1809", 
		"teamcity-server-2023.05.5-nanoserver-2004" to "%docker.deployRepository%teamcity-server%docker.buildImagePostfix%:2023.05.5-nanoserver-2004", 
		"teamcity-agent-2023.05.5-windowsservercore-2004" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.5-windowsservercore-2004", 
		"teamcity-agent-2023.05.5-nanoserver-2004" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:2023.05.5-nanoserver-2004", 
		"teamcity-minimal-agent-2023.05.5-nanoserver-2004" to "%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2023.05.5-nanoserver-2004"
	  )

	 steps {
		   targetImages.forEach { (imageVerificationStepId, imageDomainName) ->
		     // Generate validation for each image fully-qualified domain name (FQDN)
		     gradle {
			       name = "Image Verification - $imageVerificationStepId"
			       tasks = "clean build run --args=\"validate  $imageDomainName %docker.stagingRepository.login% %docker.stagingRepository.token%\""
			       workingDir = "tool/automation/framework"
			       buildFile = "build.gradle"
			       jdkHome = "%env.JDK_11_x64%"
			       executionMode = BuildStep.ExecutionMode.ALWAYS
			     }
		   }
	 }

	 failureConditions {
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
		  exists("env.JDK_11")
		  // Images are validated mostly via DockerHub REST API. In case ...
		  // ... Docker agent will be used, platform-compatibility must be addressed, ...
		  // ... especially in case of Windows images.
		  contains("teamcity.agent.jvm.os.name", "Linux")
	 }

	 features {
		   dockerSupport {
			     cleanupPushedImages = false
			     loginToRegistry = on {
			       dockerRegistryId = "PROJECT_EXT_774,PROJECT_EXT_315"
			     }
		   }
	 }
	 dependencies {
		    // Last build of Docker Image
		    dependency(BuildAndPushHosted.BuildAndPushHosted) {
			      artifacts {
				        artifactRules = "TeamCity.zip!/**=>context/TeamCity"
				        cleanDestination = true
				        lastSuccessful()
			      }
		    }
	 }
})

