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

object LocalProject : Project({
name = "Staging registry"
buildType(PushLocalLinux1804.push_local_linux_18_04)
buildType(PushLocalLinux2004.push_local_linux_20_04)
buildType(PushLocalWindows1803.push_local_windows_1803)
buildType(PushLocalWindows1809.push_local_windows_1809)
buildType(PushLocalWindows1903.push_local_windows_1903)
buildType(PushLocalWindows1909.push_local_windows_1909)
buildType(PushLocalWindows2004.push_local_windows_2004)
buildType(PublishLocal.publish_local)
buildType(ImageValidation.image_validation)
})
