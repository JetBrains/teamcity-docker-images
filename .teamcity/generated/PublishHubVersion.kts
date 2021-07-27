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

object publish_hub_version: BuildType(
{
name = "Publish as version"
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
name = "manifest create teamcity-server:2021.1.2"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-server:2021.1.2 %docker.deployRepository%teamcity-server:2021.1.2-linux %docker.deployRepository%teamcity-server:2021.1.2-nanoserver-1809 %docker.deployRepository%teamcity-server:2021.1.2-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-server:2021.1.2"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-server:2021.1.2"
}
}
dockerCommand {
name = "manifest inspect teamcity-server:2021.1.2"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-server:2021.1.2 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:2021.1.2"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:2021.1.2 %docker.deployRepository%teamcity-agent:2021.1.2-linux %docker.deployRepository%teamcity-agent:2021.1.2-nanoserver-1809 %docker.deployRepository%teamcity-agent:2021.1.2-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-agent:2021.1.2"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-agent:2021.1.2"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:2021.1.2"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-agent:2021.1.2 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent:2021.1.2"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-minimal-agent:2021.1.2 %docker.deployRepository%teamcity-minimal-agent:2021.1.2-linux %docker.deployRepository%teamcity-minimal-agent:2021.1.2-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:2021.1.2-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent:2021.1.2"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-minimal-agent:2021.1.2"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent:2021.1.2"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-minimal-agent:2021.1.2 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:2021.1.2-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:2021.1.2-windowsservercore %docker.deployRepository%teamcity-agent:2021.1.2-windowsservercore-1809 %docker.deployRepository%teamcity-agent:2021.1.2-windowsservercore-2004"
}
}
dockerCommand {
name = "manifest push teamcity-agent:2021.1.2-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-agent:2021.1.2-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:2021.1.2-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-agent:2021.1.2-windowsservercore --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:latest-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:latest-windowsservercore %docker.deployRepository%teamcity-agent:2021.1.2-windowsservercore-1809 %docker.deployRepository%teamcity-agent:2021.1.2-windowsservercore-2004"
}
}
dockerCommand {
name = "manifest push teamcity-agent:latest-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-agent:latest-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:latest-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-agent:latest-windowsservercore --verbose"
}
}
}
dependencies {
snapshot(AbsoluteId("TC_Trunk_BuildDistDocker"))
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(PushHubLinux.push_hub_linux)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
snapshot(PushHubWindows.push_hub_windows)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
}
requirements {
noLessThanVer("docker.version", "18.05.0")
equals("docker.server.osType", "windows")
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

