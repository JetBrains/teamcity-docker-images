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

object publish_hub_latest: BuildType(
{
name = "Publish as latest"
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
name = "manifest create teamcity-server:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-server:latest %docker.deployRepository%teamcity-server:2020.2.3-linux %docker.deployRepository%teamcity-server:2020.2.3-nanoserver-1809 %docker.deployRepository%teamcity-server:2020.2.3-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-server:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-server:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-server:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-server:latest --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:latest %docker.deployRepository%teamcity-agent:2020.2.3-linux %docker.deployRepository%teamcity-agent:2020.2.3-nanoserver-1809 %docker.deployRepository%teamcity-agent:2020.2.3-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-agent:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-agent:latest --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-minimal-agent:latest %docker.deployRepository%teamcity-minimal-agent:2020.2.3-linux %docker.deployRepository%teamcity-minimal-agent:2020.2.3-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:2020.2.3-nanoserver-2004"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-minimal-agent:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-minimal-agent:latest --verbose"
}
}
}
dependencies {
snapshot(AbsoluteId("TC2020_2_BuildDistDocker"))
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

