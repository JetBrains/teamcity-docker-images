package generated.staging

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import hosted.utils.ImageInfoRepository
import hosted.utils.dsl.general.teamCityBuildDistDocker
import hosted.utils.dsl.general.teamCityImageBuildFeatures
import hosted.utils.dsl.steps.buildAndPublishImage
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand

object push_local_linux_20_04 : BuildType({
	 name = "[Ubuntu 20.04] [Staging] Build And Push TeamCity Docker Images "
	 buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
	 description  = "teamcity-server:EAP-linux,EAP teamcity-minimal-agent:EAP-linux,EAP teamcity-agent:EAP-linux,EAP:EAP-linux-sudo:EAP-linux-arm64,EAP:EAP-linux-arm64-sudo"
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
		
		ImageInfoRepository.getAmdImages().forEach { imageInfo ->
			buildAndPublishImage(imageInfo)
		}
	}

	features {
		teamCityImageBuildFeatures(requiredSpaceGb = 8)
	}

	dependencies {
		teamCityBuildDistDocker()
	}

	requirements {
		// In order to correctly build AMD-based images, we wouldn't want it to be scheduled on ARM-based agent
		doesNotContain("teamcity.agent.name", "arm")
	}
})

