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

object publish_local: BuildType(
{
name = "Publish"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
enablePersonalBuilds = false
type = BuildTypeSettings.Type.DEPLOYMENT
maxRunningBuilds = 1
steps {
script {
name = "remove manifests"
scriptContent = """if exist "%%USERPROFILE%%\.docker\manifests\" rmdir "%%USERPROFILE%%\.docker\manifests\" /s /q"""
}
dockerCommand {
name = "manifest create teamcity-agent:2021.1.4"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-linux %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-nanoserver-1809 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-agent:2021.1.4"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:2021.1.4"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent:2021.1.4"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2021.1.4 %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2021.1.4-linux %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2021.1.4-nanoserver-1809 %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2021.1.4-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent:2021.1.4"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2021.1.4"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent:2021.1.4"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2021.1.4 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-server:2021.1.4"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2021.1.4 %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2021.1.4-linux %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2021.1.4-nanoserver-1809 %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2021.1.4-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-server:2021.1.4"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2021.1.4"
}
}
dockerCommand {
name = "manifest inspect teamcity-server:2021.1.4"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2021.1.4 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:2021.1.4-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-windowsservercore %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-windowsservercore-1809 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-windowsservercore-2004"
}
}
dockerCommand {
name = "manifest push teamcity-agent:2021.1.4-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:2021.1.4-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-windowsservercore --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:latest %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-linux %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-nanoserver-1809 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:latest --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:latest %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2021.1.4-linux %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2021.1.4-nanoserver-1809 %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2021.1.4-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:latest --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-server:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:latest %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2021.1.4-linux %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2021.1.4-nanoserver-1809 %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2021.1.4-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-server:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-server:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:latest --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:latest-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:latest-windowsservercore %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-windowsservercore-1809 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2021.1.4-windowsservercore-2004"
}
}
dockerCommand {
name = "manifest push teamcity-agent:latest-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:latest-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:latest-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:latest-windowsservercore --verbose"
}
}
}
dependencies {
snapshot(AbsoluteId("TC2021_1_BuildDistDocker"))
{
onDependencyFailure = FailureAction.FAIL_TO_START
reuseBuilds = ReuseBuilds.ANY
synchronizeRevisions = false
}
snapshot(PushLocalLinux2004.push_local_linux_20_04)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
snapshot(PushLocalWindows1809.push_local_windows_1809)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
snapshot(PushLocalWindows2004.push_local_windows_2004)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
}
requirements {
noLessThanVer("docker.version", "18.05.0")
contains("docker.server.osType", "windows")
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

