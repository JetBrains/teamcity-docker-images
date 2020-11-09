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

object push_hub_linux: BuildType(
{
name = "Push linux"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
steps {
dockerCommand {
name = "pull teamcity-agent:EAP-linux-sudo"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-linux-sudo"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-linux-sudo"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-linux-sudo %docker.deployRepository%teamcity-agent:EAP-linux-sudo"
}
}

dockerCommand {
name = "push teamcity-agent:EAP-linux-sudo"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:EAP-linux-sudo
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:EAP-linux"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-linux"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-linux %docker.deployRepository%teamcity-agent:EAP-linux"
}
}

dockerCommand {
name = "push teamcity-agent:EAP-linux"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:EAP-linux
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-minimal-agent:EAP-linux"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:EAP-linux"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:EAP-linux"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:EAP-linux %docker.deployRepository%teamcity-minimal-agent:EAP-linux"
}
}

dockerCommand {
name = "push teamcity-minimal-agent:EAP-linux"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-minimal-agent:EAP-linux
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-server:EAP-linux"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-server:EAP-linux"
}
}

dockerCommand {
name = "tag teamcity-server:EAP-linux"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-server:EAP-linux %docker.deployRepository%teamcity-server:EAP-linux"
}
}

dockerCommand {
name = "push teamcity-server:EAP-linux"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-server:EAP-linux
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "4gb"
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
param("system.teamcity.agent.ensure.free.space", "4gb")
}
requirements {
equals("docker.server.osType", "linux")
}
dependencies {
snapshot(PublishLocal.publish_local)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
}
})

