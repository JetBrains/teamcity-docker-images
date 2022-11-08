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

object push_local_linux_20_04 : BuildType({
name = "Build and push linux 20.04"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
description  = "teamcity-server:EAP-linux,EAP teamcity-minimal-agent:EAP-linux,EAP teamcity-agent:EAP-linux,EAP:EAP-linux-sudo:EAP-linux-arm64,EAP:EAP-linux-arm64-sudo"
vcs {root(TeamCityDockerImagesRepo)}
steps {
dockerCommand {
name = "pull ubuntu:20.04"
commandType = other {
subCommand = "pull"
commandArgs = "ubuntu:20.04"
}
}

script {
name = "context teamcity-server:EAP-linux"
scriptContent = """
echo 2> context/.dockerignore
echo TeamCity/buildAgent >> context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
""".trimIndent()
}

dockerCommand {
name = "build teamcity-server:EAP-linux"
commandType = build {
source = file {
path = """context/generated/linux/Server/Ubuntu/20.04/Dockerfile"""
}
contextDir = "context"
commandArgs = "--no-cache"
namesAndTags = """
teamcity-server:EAP-linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

script {
name = "context teamcity-minimal-agent:EAP-linux"
scriptContent = """
echo 2> context/.dockerignore
echo TeamCity/webapps >> context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
""".trimIndent()
}

dockerCommand {
name = "build teamcity-minimal-agent:EAP-linux"
commandType = build {
source = file {
path = """context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile"""
}
contextDir = "context"
commandArgs = "--no-cache"
namesAndTags = """
teamcity-minimal-agent:EAP-linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

script {
name = "context teamcity-agent:EAP-linux"
scriptContent = """
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
""".trimIndent()
}

dockerCommand {
name = "build teamcity-agent:EAP-linux"
commandType = build {
source = file {
path = """context/generated/linux/Agent/Ubuntu/20.04/Dockerfile"""
}
contextDir = "context"
commandArgs = "--no-cache"
namesAndTags = """
teamcity-agent:EAP-linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

script {
name = "context teamcity-agent:EAP-linux-sudo"
scriptContent = """
echo 2> context/.dockerignore
echo TeamCity >> context/.dockerignore
""".trimIndent()
}

dockerCommand {
name = "build teamcity-agent:EAP-linux-sudo"
commandType = build {
source = file {
path = """context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile"""
}
contextDir = "context"
commandArgs = "--no-cache"
namesAndTags = """
teamcity-agent:EAP-linux-sudo
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

//script {
//name = "context teamcity-agent:EAP-linux-arm64"
//scriptContent = """
//echo 2> context/.dockerignore
//echo TeamCity >> context/.dockerignore
//""".trimIndent()
//}

//dockerCommand {
//name = "build teamcity-agent:EAP-linux-arm64"
//commandType = build {
//source = file {
//path = """context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile"""
//}
//contextDir = "context"
//commandArgs = "--no-cache"
//namesAndTags = """
//teamcity-agent:EAP-linux-arm64
//""".trimIndent()
//}
//param("dockerImage.platform", "linux")
//}
//
//script {
//name = "context teamcity-agent:EAP-linux-arm64-sudo"
//scriptContent = """
//echo 2> context/.dockerignore
//echo TeamCity >> context/.dockerignore
//""".trimIndent()
//}
//
//dockerCommand {
//name = "build teamcity-agent:EAP-linux-arm64-sudo"
//commandType = build {
//source = file {
//path = """context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile"""
//}
//contextDir = "context"
//commandArgs = "--no-cache"
//namesAndTags = """
//teamcity-agent:EAP-linux-arm64-sudo
//""".trimIndent()
//}
//param("dockerImage.platform", "linux")
//}

dockerCommand {
name = "tag teamcity-server:EAP-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:EAP-linux %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-linux"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:EAP-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:EAP-linux %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:EAP-linux %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-linux-sudo"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:EAP-linux-sudo %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-sudo"
}
}

//dockerCommand {
//name = "tag teamcity-agent:EAP-linux-arm64"
//commandType = other {
//subCommand = "tag"
//commandArgs = "teamcity-agent:EAP-linux-arm64 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-arm64"
//}
//}

//dockerCommand {
//name = "tag teamcity-agent:EAP-linux-arm64-sudo"
//commandType = other {
//subCommand = "tag"
//commandArgs = "teamcity-agent:EAP-linux-arm64-sudo %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-arm64-sudo"
//}
//}

dockerCommand {
name = "push teamcity-server:EAP-linux"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-linux
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "push teamcity-minimal-agent:EAP-linux"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-linux
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "push teamcity-agent:EAP-linux"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "push teamcity-agent:EAP-linux-sudo"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-sudo
""".trimIndent()
removeImageAfterPush = false
}
}

//dockerCommand {
//name = "push teamcity-agent:EAP-linux-arm64"
//commandType = push {
//namesAndTags = """
//%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-arm64
//""".trimIndent()
//removeImageAfterPush = false
//}
//}

//dockerCommand {
//name = "push teamcity-agent:EAP-linux-arm64-sudo"
//commandType = push {
//namesAndTags = """
//%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-linux-arm64-sudo
//""".trimIndent()
//removeImageAfterPush = false
//}
//}
    // NOTE: The following steps are experimental identification of an EAP TeamCity agent Docker image
    dockerCommand {
        name = "Identify EAP TeamCity Agent Image"

        commandType = other {
            subCommand = "tag"
            commandArgs = "teamcity-agent:EAP-linux-sudo %docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:%dockerImage.teamcity.buildNumber%-%build.counter%-linux-sudo"
        }
    }

    dockerCommand {
        name = "Push identified EAP TeamCity Agent Image"
        commandType = push {
            namesAndTags = """
%docker.deployRepository%teamcity-agent%docker.buildImagePostfix%:%dockerImage.teamcity.buildNumber%-%build.counter%-linux-sudo
""".trimIndent()
            removeImageAfterPush = false
        }
    }

}
features {
freeDiskSpace {
requiredSpace = "8gb"
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
dependencies {
dependency(AbsoluteId("TC_Trunk_BuildDistDocker")) {
snapshot { onDependencyFailure = FailureAction.IGNORE
reuseBuilds = ReuseBuilds.ANY }
artifacts {
artifactRules = "TeamCity.zip!/**=>context/TeamCity"
}
}
}
params {
param("system.teamcity.agent.ensure.free.space", "8gb")
}
requirements {
}
})

