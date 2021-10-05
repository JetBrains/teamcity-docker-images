package generated

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo

object push_local_linux_18_04 : BuildType({
name = "ON PAUSE Build and push linux 18.04"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
description  = "teamcity-server:2021.1.4-linux-arm64-18.04,latest,2021.1.4:2021.1.4-linux-18.04,latest,2021.1.4 teamcity-minimal-agent:2021.1.4-linux-arm64-18.04,latest,2021.1.4:2021.1.4-linux-18.04,latest,2021.1.4 teamcity-agent:2021.1.4-linux-18.04,latest,2021.1.4:2021.1.4-linux-18.04-sudo"
})

