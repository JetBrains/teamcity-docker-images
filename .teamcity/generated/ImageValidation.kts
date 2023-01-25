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
		 }
	 }

	 params {
		 // -- inherited parameter, removed in debug purposes
		 param("dockerImage.teamcity.buildNumber", "-")
	 }

	 val targetImages: HashMap<String, String> = hashMapOf(
"teamcity-server-EAP-linux" to "%docker.deployRepository%teamcity-server%docker.buildImagePostfix%:EAP-linux", 
		"teamcity-agent-EAP-linux" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux", 
		"teamcity-agent-EAP-linux-sudo" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-sudo", 
		"teamcity-minimal-agent-EAP-linux" to "%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-linux", 
		"teamcity-server-EAP-nanoserver-1809" to "%docker.deployRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809", 
		"teamcity-agent-EAP-windowsservercore-1809" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809", 
		"teamcity-agent-EAP-nanoserver-1809" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809", 
		"teamcity-minimal-agent-EAP-nanoserver-1809" to "%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809", 
		"teamcity-server-EAP-nanoserver-2004" to "%docker.deployRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-2004", 
		"teamcity-agent-EAP-windowsservercore-2004" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-2004", 
		"teamcity-agent-EAP-nanoserver-2004" to "%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-2004", 
		"teamcity-minimal-agent-EAP-nanoserver-2004" to "%docker.deployRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
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
			     cleanupPushedImages = true
			     loginToRegistry = on {
			       dockerRegistryId = "PROJECT_EXT_774"
			     }
		   }
	 }
})

