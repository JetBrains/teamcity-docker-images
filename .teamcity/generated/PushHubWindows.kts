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

object push_hub_windows: BuildType(
{
name = "Push windows"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
steps {
dockerCommand {
name = "pull teamcity-server:2020.2-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-server:2020.2-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-server:2020.2-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-server:2020.2-nanoserver-1809 %docker.deployRepository%teamcity-server:2020.2-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-server:2020.2-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-server:2020.2-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:2020.2-windowsservercore-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.2-windowsservercore-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.2-windowsservercore-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.2-windowsservercore-1809 %docker.deployRepository%teamcity-agent:2020.2-windowsservercore-1809"
}
}

dockerCommand {
name = "push teamcity-agent:2020.2-windowsservercore-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2020.2-windowsservercore-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:2020.2-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.2-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.2-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.2-nanoserver-1809 %docker.deployRepository%teamcity-agent:2020.2-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-agent:2020.2-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2020.2-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-minimal-agent:2020.2-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:2020.2-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.2-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:2020.2-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:2020.2-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-minimal-agent:2020.2-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-minimal-agent:2020.2-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-server:2020.2-nanoserver-2004"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-server:2020.2-nanoserver-2004"
}
}

dockerCommand {
name = "tag teamcity-server:2020.2-nanoserver-2004"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-server:2020.2-nanoserver-2004 %docker.deployRepository%teamcity-server:2020.2-nanoserver-2004"
}
}

dockerCommand {
name = "push teamcity-server:2020.2-nanoserver-2004"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-server:2020.2-nanoserver-2004
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:2020.2-windowsservercore-2004"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.2-windowsservercore-2004"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.2-windowsservercore-2004"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.2-windowsservercore-2004 %docker.deployRepository%teamcity-agent:2020.2-windowsservercore-2004"
}
}

dockerCommand {
name = "push teamcity-agent:2020.2-windowsservercore-2004"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2020.2-windowsservercore-2004
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:2020.2-nanoserver-2004"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.2-nanoserver-2004"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.2-nanoserver-2004"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.2-nanoserver-2004 %docker.deployRepository%teamcity-agent:2020.2-nanoserver-2004"
}
}

dockerCommand {
name = "push teamcity-agent:2020.2-nanoserver-2004"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2020.2-nanoserver-2004
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-minimal-agent:2020.2-nanoserver-2004"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:2020.2-nanoserver-2004"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.2-nanoserver-2004"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:2020.2-nanoserver-2004 %docker.deployRepository%teamcity-minimal-agent:2020.2-nanoserver-2004"
}
}

dockerCommand {
name = "push teamcity-minimal-agent:2020.2-nanoserver-2004"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-minimal-agent:2020.2-nanoserver-2004
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "42gb"
failBuild = true
}
dockerSupport {
cleanupPushedImages = true
loginToRegistry = on {
dockerRegistryId = "PROJECT_EXT_315,PROJECT_EXT_4003,PROJECT_EXT_4022"
}
}
swabra {
forceCleanCheckout = true
}
}
params {
param("system.teamcity.agent.ensure.free.space", "42gb")
}
requirements {
equals("docker.server.osType", "windows")
}
dependencies {
snapshot(PublishLocal.publish_local)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
}
})

