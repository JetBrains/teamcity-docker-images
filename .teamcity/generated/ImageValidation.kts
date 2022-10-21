package generated

import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.kotlinFile
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo

object image_validation: BuildType(
{
name = "Validation (post-push) of Docker images"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
steps {
kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-linux"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-linux" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-arm64-sudo"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-arm64-sudo" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-arm64"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-arm64" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-sudo"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-sudo" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-linux"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-linux" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-2004"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-2004" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-2004"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-2004" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-2004" }

kotlinFile {
name = "Image Verification - %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-2004"
path = "automation/ImageValidation.kts"
arguments = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-2004" }

}
dependencies {
dependency(AbsoluteId("PROJECT_EXT_774")) {
snapshot { onDependencyFailure = FailureAction.FAIL_TO_START }
}
}
})

