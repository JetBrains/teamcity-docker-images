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

object push_local_windows_1909 : BuildType({
name = "ON PAUSE Build and push windows 1909"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
description  = "teamcity-server:2020.2.3-nanoserver-1909,latest,2020.2.3 teamcity-minimal-agent:2020.2.3-nanoserver-1909,latest,2020.2.3 teamcity-agent:2020.2.3-windowsservercore-1909,2020.2.3-windowsservercore,latest-windowsservercore:2020.2.3-nanoserver-1909,latest,2020.2.3"
})

