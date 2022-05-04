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
name = "pull teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809 %docker.deployRepository%teamcity-agent:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:EAP-nanoserver-1809
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "pull teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809"
}
}

dockerCommand {
name = "tag teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809 %docker.deployRepository%teamcity-agent:EAP-windowsservercore-1809"
}
}

dockerCommand {
name = "push teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:EAP-windowsservercore-1809
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "pull teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-1809
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "pull teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809 %docker.deployRepository%teamcity-server:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-server:EAP-nanoserver-1809
""".trimIndent()
removeImageAfterPush = false
}
}

}
features {
freeDiskSpace {
requiredSpace = "26gb"
failBuild = true
}
dockerSupport {
cleanupPushedImages = true
loginToRegistry = on {
dockerRegistryId = "PROJECT_EXT_774"
}
}
swabra {
forceCleanCheckout = true
}
}
params {
param("system.teamcity.agent.ensure.free.space", "26gb")
}
requirements {
contains("docker.server.osType", "windows")
contains("system.agent.name", "-docker-")
}
dependencies {
snapshot(PublishLocal.publish_local)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
}
})

