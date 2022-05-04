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
name = "manifest create teamcity-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
}
}
dockerCommand {
name = "manifest push teamcity-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-linux %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-server:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-linux %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809"
}
}
dockerCommand {
name = "manifest push teamcity-server:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP"
}
}
dockerCommand {
name = "manifest inspect teamcity-server:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:EAP-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809"
}
}
dockerCommand {
name = "manifest push teamcity-agent:EAP-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:EAP-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore --verbose"
}
}
}
dependencies {
snapshot(AbsoluteId("TC_Trunk_BuildDistDocker"))
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
}
requirements {
noLessThanVer("docker.version", "18.05.0")
contains("docker.server.osType", "windows")
contains("system.agent.name", "Docker")
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

