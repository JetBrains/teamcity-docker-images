package delivery.staging

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import utils.ImageInfoRepository
import utils.dsl.general.teamCityBuildDistDocker
import utils.dsl.general.teamCityImageBuildFeatures
import utils.dsl.steps.buildAndPushToStaging
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand

object push_local_windows_2004 : BuildType({
    name = "[Windows 2004] [Staging] Build And Push TeamCity Docker Images"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    description = "Build Windows 2004-based TeamCity Docker images and pushes them into staging registry.\n" +
            "Target images: teamcity-server (NanoServer 2004), minimal agent (NanoServer 2004), " +
            "regular agent (NanoServer 2004, WindowsServer Core 2004)."

    vcs {
        root(TeamCityDockerImagesRepo)
    }

    params {
        param("dockerImage.platform", "windows")
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

        ImageInfoRepository.getWindowsImages2004().forEach { imageInfo ->
            buildAndPushToStaging(imageInfo)
        }
    }

    features {
        // Windows-based images require more available disk space
        teamCityImageBuildFeatures(requiredSpaceGb = 43)
    }

    dependencies {
        teamCityBuildDistDocker()
    }

    requirements {
        // In order to correctly build AMD-based images, we wouldn't want it to be scheduled on ARM-based agent
        doesNotContain("teamcity.agent.name", "arm")
        contains("teamcity.agent.jvm.os.name", "Windows 10")
    }
})
