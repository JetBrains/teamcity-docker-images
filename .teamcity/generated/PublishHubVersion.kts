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
name = "manifest create teamcity-server:2022.04.5"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-server:2022.04.5 %docker.deployRepository%teamcity-server:2022.04.5-linux %docker.deployRepository%teamcity-server:2022.04.5-nanoserver-1809 %docker.deployRepository%teamcity-server:2022.04.5-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-server:2022.04.5"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-server:2022.04.5"
}
}
dockerCommand {
name = "manifest inspect teamcity-server:2022.04.5"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-server:2022.04.5 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:2022.04.5"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:2022.04.5 %docker.deployRepository%teamcity-agent:2022.04.5-linux %docker.deployRepository%teamcity-agent:2022.04.5-nanoserver-1809 %docker.deployRepository%teamcity-agent:2022.04.5-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-agent:2022.04.5"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-agent:2022.04.5"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:2022.04.5"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-agent:2022.04.5 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent:2022.04.5"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-minimal-agent:2022.04.5 %docker.deployRepository%teamcity-minimal-agent:2022.04.5-linux %docker.deployRepository%teamcity-minimal-agent:2022.04.5-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:2022.04.5-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent:2022.04.5"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-minimal-agent:2022.04.5"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent:2022.04.5"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-minimal-agent:2022.04.5 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:2022.04.5-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:2022.04.5-windowsservercore %docker.deployRepository%teamcity-agent:2022.04.5-windowsservercore-1809 %docker.deployRepository%teamcity-agent:2022.04.5-windowsservercore-2004"
}
}
dockerCommand {
name = "manifest push teamcity-agent:2022.04.5-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-agent:2022.04.5-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:2022.04.5-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-agent:2022.04.5-windowsservercore --verbose"
}
}
}
dependencies {
snapshot(AbsoluteId("TC2022_04_BuildDistDocker"))
{
reuseBuilds = ReuseBuilds.ANY
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
contains("docker.server.osType", "windows")
contains("system.agent.name", "docker")
contains("system.agent.name", "windows10")
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

