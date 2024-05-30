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
name = "pull teamcity-server%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-server%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2022.04.7-nanoserver-1809 %docker.deployRepository%teamcity-server:2022.04.7-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-server%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-server:2022.04.7-nanoserver-1809
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "pull teamcity-agent%docker.buildImagePostfix%:2022.04.7-windowsservercore-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2022.04.7-windowsservercore-1809"
}
}

dockerCommand {
name = "tag teamcity-agent%docker.buildImagePostfix%:2022.04.7-windowsservercore-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2022.04.7-windowsservercore-1809 %docker.deployRepository%teamcity-agent:2022.04.7-windowsservercore-1809"
}
}

dockerCommand {
name = "push teamcity-agent%docker.buildImagePostfix%:2022.04.7-windowsservercore-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2022.04.7-windowsservercore-1809
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "pull teamcity-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-1809 %docker.deployRepository%teamcity-agent:2022.04.7-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2022.04.7-nanoserver-1809
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "pull teamcity-minimal-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:2022.04.7-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-minimal-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-minimal-agent:2022.04.7-nanoserver-1809
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "pull teamcity-server%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
}
}

dockerCommand {
name = "tag teamcity-server%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:2022.04.7-nanoserver-2004 %docker.deployRepository%teamcity-server:2022.04.7-nanoserver-2004"
}
}

dockerCommand {
name = "push teamcity-server%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-server:2022.04.7-nanoserver-2004
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "pull teamcity-agent%docker.buildImagePostfix%:2022.04.7-windowsservercore-2004"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2022.04.7-windowsservercore-2004"
}
}

dockerCommand {
name = "tag teamcity-agent%docker.buildImagePostfix%:2022.04.7-windowsservercore-2004"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2022.04.7-windowsservercore-2004 %docker.deployRepository%teamcity-agent:2022.04.7-windowsservercore-2004"
}
}

dockerCommand {
name = "push teamcity-agent%docker.buildImagePostfix%:2022.04.7-windowsservercore-2004"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2022.04.7-windowsservercore-2004
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "pull teamcity-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
}
}

dockerCommand {
name = "tag teamcity-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-2004 %docker.deployRepository%teamcity-agent:2022.04.7-nanoserver-2004"
}
}

dockerCommand {
name = "push teamcity-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2022.04.7-nanoserver-2004
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "pull teamcity-minimal-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-2004 %docker.deployRepository%teamcity-minimal-agent:2022.04.7-nanoserver-2004"
}
}

dockerCommand {
name = "push teamcity-minimal-agent%docker.buildImagePostfix%:2022.04.7-nanoserver-2004"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-minimal-agent:2022.04.7-nanoserver-2004
""".trimIndent()
removeImageAfterPush = false
}
}

}
features {
freeDiskSpace {
requiredSpace = "52gb"
failBuild = true
}
dockerSupport {
loginToRegistry = on {
dockerRegistryId = "PROJECT_EXT_774"
}
}
swabra {
forceCleanCheckout = true
}
}
params {
param("system.teamcity.agent.ensure.free.space", "52gb")
}
requirements {
contains("docker.server.osType", "windows")
	// In order to correctly build AMD-based images, we wouldn't want it to be scheduled on ARM-based agent
	doesNotContain("teamcity.agent.name", "arm")
	contains("system.agent.name", "tc-win10")
}
dependencies {
snapshot(PublishLocal.publish_local)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
}
})

