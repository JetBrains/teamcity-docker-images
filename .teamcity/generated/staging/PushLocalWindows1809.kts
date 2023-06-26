package generated.staging

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import hosted.utils.ImageInfoRepository
import hosted.utils.dsl.general.teamCityBuildDistDocker
import hosted.utils.dsl.general.teamCityImageBuildFeatures
import hosted.utils.dsl.steps.buildAndPublishImage
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand

object push_local_windows_1809 : BuildType({
    name = "[Windows 1809] [Staging] Build And Push TeamCity Docker Images"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    description = "Build Windows 1809-based TeamCity Docker images and pushes them into staging registry.\n" +
                    "Build images are: teamcity-server (NanoServer 1809), minimal agent (NanoServer 1809), " +
                    "regular agent (NanoServer 1809, WindowsServer Core 1809)."
    vcs {
        root(TeamCityDockerImagesRepo)
    }

    params {
        param("dockerImage.platform", "windows")

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

        ImageInfoRepository.getWindowsImages1809().forEach { imageInfo ->
            buildAndPublishImage(imageInfo)
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
