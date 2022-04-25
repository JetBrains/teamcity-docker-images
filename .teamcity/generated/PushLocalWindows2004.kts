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

object push_local_windows_2004 : BuildType({
name = "ON PAUSE Build and push windows 2004"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
description  = "teamcity-server:EAP-nanoserver-2004,EAP teamcity-minimal-agent:EAP-nanoserver-2004,EAP teamcity-agent:EAP-windowsservercore-2004,EAP-windowsservercore,-windowsservercore:EAP-nanoserver-2004,EAP"
})

