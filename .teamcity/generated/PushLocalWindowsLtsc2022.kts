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

object push_local_windows_ltsc2022 : BuildType({
name = "Build and push windows ltsc2022"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
description  = "teamcity-server:EAP-nanoserver-ltsc2022,EAP teamcity-minimal-agent:EAP-nanoserver-ltsc2022,EAP teamcity-agent:EAP-windowsservercore-ltsc2022,EAP-windowsservercore,-windowsservercore:EAP-nanoserver-ltsc2022,EAP"
vcs {root(TeamCityDockerImagesRepo)}
steps {
dockerCommand {
name = "pull mcr.microsoft.com/powershell:nanoserver-ltsc2022"
commandType = other {
subCommand = "pull"
commandArgs = "mcr.microsoft.com/powershell:nanoserver-ltsc2022"
}
}

dockerCommand {
name = "pull mcr.microsoft.com/windows/nanoserver:ltsc2022"
commandType = other {
subCommand = "pull"
commandArgs = "mcr.microsoft.com/windows/nanoserver:ltsc2022"
}
}

dockerCommand {
name = "pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2022"
commandType = other {
subCommand = "pull"
commandArgs = "mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2022"
}
}

script {
name = "context teamcity-server:EAP-nanoserver-ltsc2022"
scriptContent = """
echo 2> context/.dockerignore
echo TeamCity/buildAgent >> context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
""".trimIndent()
}

dockerCommand {
name = "build teamcity-server:EAP-nanoserver-ltsc2022"
commandType = build {
source = file {
path = """context/generated/windows/Server/nanoserver/ltsc2022/Dockerfile"""
}
contextDir = "context"
commandArgs = "--no-cache"
namesAndTags = """
teamcity-server:EAP-nanoserver-ltsc2022
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

script {
name = "context teamcity-minimal-agent:EAP-nanoserver-ltsc2022"
scriptContent = """
echo 2> context/.dockerignore
echo TeamCity/webapps >> context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
""".trimIndent()
}

dockerCommand {
name = "build teamcity-minimal-agent:EAP-nanoserver-ltsc2022"
commandType = build {
source = file {
path = """context/generated/windows/MinimalAgent/nanoserver/ltsc2022/Dockerfile"""
}
contextDir = "context"
commandArgs = "--no-cache"
namesAndTags = """
teamcity-minimal-agent:EAP-nanoserver-ltsc2022
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

script {
name = "context teamcity-agent:EAP-windowsservercore-ltsc2022"
scriptContent = """
echo 2> context/.dockerignore
echo TeamCity/webapps >> context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
""".trimIndent()
}

dockerCommand {
name = "build teamcity-agent:EAP-windowsservercore-ltsc2022"
commandType = build {
source = file {
path = """context/generated/windows/Agent/windowsservercore/ltsc2022/Dockerfile"""
}
contextDir = "context"
commandArgs = "--no-cache"
namesAndTags = """
teamcity-agent:EAP-windowsservercore-ltsc2022
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

script {
name = "context teamcity-agent:EAP-nanoserver-ltsc2022"
scriptContent = """
echo 2> context/.dockerignore
echo TeamCity/webapps >> context/.dockerignore
echo TeamCity/devPackage >> context/.dockerignore
echo TeamCity/lib >> context/.dockerignore
""".trimIndent()
}

dockerCommand {
name = "build teamcity-agent:EAP-nanoserver-ltsc2022"
commandType = build {
source = file {
path = """context/generated/windows/Agent/nanoserver/ltsc2022/Dockerfile"""
}
contextDir = "context"
commandArgs = "--no-cache"
namesAndTags = """
teamcity-agent:EAP-nanoserver-ltsc2022
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "tag teamcity-server:EAP-nanoserver-ltsc2022"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:EAP-nanoserver-ltsc2022 %docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-ltsc2022"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:EAP-nanoserver-ltsc2022"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:EAP-nanoserver-ltsc2022 %docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-ltsc2022"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-windowsservercore-ltsc2022"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:EAP-windowsservercore-ltsc2022 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-ltsc2022"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-nanoserver-ltsc2022"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:EAP-nanoserver-ltsc2022 %docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-ltsc2022"
}
}

dockerCommand {
name = "push teamcity-server:EAP-nanoserver-ltsc2022"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:EAP-nanoserver-ltsc2022
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "push teamcity-minimal-agent:EAP-nanoserver-ltsc2022"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:EAP-nanoserver-ltsc2022
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "push teamcity-agent:EAP-windowsservercore-ltsc2022"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-windowsservercore-ltsc2022
""".trimIndent()
removeImageAfterPush = false
}
}

dockerCommand {
name = "push teamcity-agent:EAP-nanoserver-ltsc2022"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:EAP-nanoserver-ltsc2022
""".trimIndent()
removeImageAfterPush = false
}
}

}
features {
freeDiskSpace {
requiredSpace = "43gb"
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
param("system.teamcity.agent.ensure.free.space", "43gb")
}
requirements {
contains("teamcity.agent.jvm.os.name", "Windows")
}
})

