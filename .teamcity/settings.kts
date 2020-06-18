import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
version = "2019.2"

object TC_Trunk_BuildDistTarGzWar_18_04_linux : BuildType({
name = "Build 18.04 linux"
description  = "teamcity-server:18.04,linux teamcity-minimal-agent:18.04,linux teamcity-agent:18.04,linux:18.04-sudo"
vcs {root(RemoteTeamcityImages)}
steps {
dockerCommand {
name = "pull ubuntu:18.04"
commandType = other {
subCommand = "pull"
commandArgs = "ubuntu:18.04"
}
}

dockerCommand {
name = "build teamcity-server:18.04,linux"
commandType = build {
source = file {
path = """context/generated/linux/Server/Ubuntu/18.04/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-server:18.04
teamcity-server:linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "build teamcity-minimal-agent:18.04,linux"
commandType = build {
source = file {
path = """context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-minimal-agent:18.04
teamcity-minimal-agent:linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "build teamcity-agent:18.04,linux"
commandType = build {
source = file {
path = """context/generated/linux/Agent/Ubuntu/18.04/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:18.04
teamcity-agent:linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "build teamcity-agent:18.04-sudo"
commandType = build {
source = file {
path = """context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:18.04-sudo
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "tag teamcity-server:latest-18.04"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:18.04 %docker.pushRepository%teamcity-server:latest-18.04"
}
}

dockerCommand {
name = "tag teamcity-server:latest-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:linux %docker.pushRepository%teamcity-server:latest-linux"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:latest-18.04"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:18.04 %docker.pushRepository%teamcity-minimal-agent:latest-18.04"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:latest-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:linux %docker.pushRepository%teamcity-minimal-agent:latest-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:latest-18.04"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:18.04 %docker.pushRepository%teamcity-agent:latest-18.04"
}
}

dockerCommand {
name = "tag teamcity-agent:latest-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:linux %docker.pushRepository%teamcity-agent:latest-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:latest-18.04-sudo"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:18.04-sudo %docker.pushRepository%teamcity-agent:latest-18.04-sudo"
}
}

dockerCommand {
name = "tag teamcity-server:2020.1.1-18.04"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:18.04 %docker.pushRepository%teamcity-server:2020.1.1-18.04"
}
}

dockerCommand {
name = "tag teamcity-server:2020.1.1-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:linux %docker.pushRepository%teamcity-server:2020.1.1-linux"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.1.1-18.04"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:18.04 %docker.pushRepository%teamcity-minimal-agent:2020.1.1-18.04"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.1.1-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:linux %docker.pushRepository%teamcity-minimal-agent:2020.1.1-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.1-18.04"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:18.04 %docker.pushRepository%teamcity-agent:2020.1.1-18.04"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.1-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:linux %docker.pushRepository%teamcity-agent:2020.1.1-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.1-18.04-sudo"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:18.04-sudo %docker.pushRepository%teamcity-agent:2020.1.1-18.04-sudo"
}
}

dockerCommand {
name = "tag teamcity-server:eap-18.04"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:18.04 %docker.pushRepository%teamcity-server:eap-18.04"
}
}

dockerCommand {
name = "tag teamcity-server:eap-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:linux %docker.pushRepository%teamcity-server:eap-linux"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:eap-18.04"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:18.04 %docker.pushRepository%teamcity-minimal-agent:eap-18.04"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:eap-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:linux %docker.pushRepository%teamcity-minimal-agent:eap-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:eap-18.04"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:18.04 %docker.pushRepository%teamcity-agent:eap-18.04"
}
}

dockerCommand {
name = "tag teamcity-agent:eap-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:linux %docker.pushRepository%teamcity-agent:eap-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:eap-18.04-sudo"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:18.04-sudo %docker.pushRepository%teamcity-agent:eap-18.04-sudo"
}
}

dockerCommand {
name = "push teamcity-server:latest-18.04,2020.1.1-18.04,eap-18.04,latest-linux,2020.1.1-linux,eap-linux"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-server:latest-18.04
%docker.pushRepository%teamcity-server:2020.1.1-18.04
%docker.pushRepository%teamcity-server:eap-18.04
%docker.pushRepository%teamcity-server:latest-linux
%docker.pushRepository%teamcity-server:2020.1.1-linux
%docker.pushRepository%teamcity-server:eap-linux
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-minimal-agent:latest-18.04,2020.1.1-18.04,eap-18.04,latest-linux,2020.1.1-linux,eap-linux"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-minimal-agent:latest-18.04
%docker.pushRepository%teamcity-minimal-agent:2020.1.1-18.04
%docker.pushRepository%teamcity-minimal-agent:eap-18.04
%docker.pushRepository%teamcity-minimal-agent:latest-linux
%docker.pushRepository%teamcity-minimal-agent:2020.1.1-linux
%docker.pushRepository%teamcity-minimal-agent:eap-linux
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:latest-18.04,2020.1.1-18.04,eap-18.04,latest-linux,2020.1.1-linux,eap-linux"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-agent:latest-18.04
%docker.pushRepository%teamcity-agent:2020.1.1-18.04
%docker.pushRepository%teamcity-agent:eap-18.04
%docker.pushRepository%teamcity-agent:latest-linux
%docker.pushRepository%teamcity-agent:2020.1.1-linux
%docker.pushRepository%teamcity-agent:eap-linux
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:latest-18.04-sudo,2020.1.1-18.04-sudo,eap-18.04-sudo"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-agent:latest-18.04-sudo
%docker.pushRepository%teamcity-agent:2020.1.1-18.04-sudo
%docker.pushRepository%teamcity-agent:eap-18.04-sudo
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
dockerRegistryId = "PROJECT_EXT_315,PROJECT_EXT_2277"
}
}
swabra {
forceCleanCheckout = true
}
}
})

object TC_Trunk_BuildDistTarGzWar_nanoserver_1809 : BuildType({
name = "Build nanoserver-1809"
description  = "teamcity-server:nanoserver-1809 teamcity-minimal-agent:nanoserver-1809 teamcity-agent:windowsservercore-1809:nanoserver-1809"
vcs {root(RemoteTeamcityImages)}
steps {
dockerCommand {
name = "pull mcr.microsoft.com/powershell:nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "mcr.microsoft.com/powershell:nanoserver-1809"
}
}

dockerCommand {
name = "pull mcr.microsoft.com/windows/nanoserver:1809"
commandType = other {
subCommand = "pull"
commandArgs = "mcr.microsoft.com/windows/nanoserver:1809"
}
}

dockerCommand {
name = "pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019"
commandType = other {
subCommand = "pull"
commandArgs = "mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-ltsc2019"
}
}

dockerCommand {
name = "build teamcity-server:nanoserver-1809"
commandType = build {
source = file {
path = """context/generated/windows/Server/nanoserver/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-server:nanoserver-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-minimal-agent:nanoserver-1809"
commandType = build {
source = file {
path = """context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-minimal-agent:nanoserver-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:windowsservercore-1809"
commandType = build {
source = file {
path = """context/generated/windows/Agent/windowsservercore/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:windowsservercore-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:nanoserver-1809"
commandType = build {
source = file {
path = """context/generated/windows/Agent/nanoserver/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:nanoserver-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "tag teamcity-server:latest-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:nanoserver-1809 %docker.pushRepository%teamcity-server:latest-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:latest-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:nanoserver-1809 %docker.pushRepository%teamcity-minimal-agent:latest-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:latest-windowsservercore-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:windowsservercore-1809 %docker.pushRepository%teamcity-agent:latest-windowsservercore-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:latest-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:nanoserver-1809 %docker.pushRepository%teamcity-agent:latest-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-server:2020.1.1-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:nanoserver-1809 %docker.pushRepository%teamcity-server:2020.1.1-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.1.1-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:nanoserver-1809 %docker.pushRepository%teamcity-minimal-agent:2020.1.1-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.1-windowsservercore-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:windowsservercore-1809 %docker.pushRepository%teamcity-agent:2020.1.1-windowsservercore-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.1-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:nanoserver-1809 %docker.pushRepository%teamcity-agent:2020.1.1-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-server:eap-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:nanoserver-1809 %docker.pushRepository%teamcity-server:eap-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:eap-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:nanoserver-1809 %docker.pushRepository%teamcity-minimal-agent:eap-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:eap-windowsservercore-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:windowsservercore-1809 %docker.pushRepository%teamcity-agent:eap-windowsservercore-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:eap-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:nanoserver-1809 %docker.pushRepository%teamcity-agent:eap-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-server:latest-nanoserver-1809,2020.1.1-nanoserver-1809,eap-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-server:latest-nanoserver-1809
%docker.pushRepository%teamcity-server:2020.1.1-nanoserver-1809
%docker.pushRepository%teamcity-server:eap-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-minimal-agent:latest-nanoserver-1809,2020.1.1-nanoserver-1809,eap-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-minimal-agent:latest-nanoserver-1809
%docker.pushRepository%teamcity-minimal-agent:2020.1.1-nanoserver-1809
%docker.pushRepository%teamcity-minimal-agent:eap-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:latest-windowsservercore-1809,2020.1.1-windowsservercore-1809,eap-windowsservercore-1809"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-agent:latest-windowsservercore-1809
%docker.pushRepository%teamcity-agent:2020.1.1-windowsservercore-1809
%docker.pushRepository%teamcity-agent:eap-windowsservercore-1809
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:latest-nanoserver-1809,2020.1.1-nanoserver-1809,eap-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-agent:latest-nanoserver-1809
%docker.pushRepository%teamcity-agent:2020.1.1-nanoserver-1809
%docker.pushRepository%teamcity-agent:eap-nanoserver-1809
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "27gb"
failBuild = true
}
dockerSupport {
cleanupPushedImages = true
loginToRegistry = on {
dockerRegistryId = "PROJECT_EXT_315,PROJECT_EXT_2277"
}
}
swabra {
forceCleanCheckout = true
}
}
})

object TC_Trunk_BuildDistTarGzWar_nanoserver_1903 : BuildType({
name = "Build nanoserver-1903"
description  = "teamcity-server:nanoserver-1903 teamcity-minimal-agent:nanoserver-1903 teamcity-agent:windowsservercore-1903:nanoserver-1903"
vcs {root(RemoteTeamcityImages)}
steps {
dockerCommand {
name = "pull mcr.microsoft.com/powershell:nanoserver-1903"
commandType = other {
subCommand = "pull"
commandArgs = "mcr.microsoft.com/powershell:nanoserver-1903"
}
}

dockerCommand {
name = "pull mcr.microsoft.com/windows/nanoserver:1903"
commandType = other {
subCommand = "pull"
commandArgs = "mcr.microsoft.com/windows/nanoserver:1903"
}
}

dockerCommand {
name = "pull mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903"
commandType = other {
subCommand = "pull"
commandArgs = "mcr.microsoft.com/dotnet/framework/sdk:4.8-windowsservercore-1903"
}
}

dockerCommand {
name = "build teamcity-server:nanoserver-1903"
commandType = build {
source = file {
path = """context/generated/windows/Server/nanoserver/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-server:nanoserver-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-minimal-agent:nanoserver-1903"
commandType = build {
source = file {
path = """context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-minimal-agent:nanoserver-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:windowsservercore-1903"
commandType = build {
source = file {
path = """context/generated/windows/Agent/windowsservercore/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:windowsservercore-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:nanoserver-1903"
commandType = build {
source = file {
path = """context/generated/windows/Agent/nanoserver/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:nanoserver-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "tag teamcity-server:latest-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:nanoserver-1903 %docker.pushRepository%teamcity-server:latest-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:latest-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:nanoserver-1903 %docker.pushRepository%teamcity-minimal-agent:latest-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:latest-windowsservercore-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:windowsservercore-1903 %docker.pushRepository%teamcity-agent:latest-windowsservercore-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:latest-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:nanoserver-1903 %docker.pushRepository%teamcity-agent:latest-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-server:2020.1.1-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:nanoserver-1903 %docker.pushRepository%teamcity-server:2020.1.1-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.1.1-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:nanoserver-1903 %docker.pushRepository%teamcity-minimal-agent:2020.1.1-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.1-windowsservercore-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:windowsservercore-1903 %docker.pushRepository%teamcity-agent:2020.1.1-windowsservercore-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.1-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:nanoserver-1903 %docker.pushRepository%teamcity-agent:2020.1.1-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-server:eap-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:nanoserver-1903 %docker.pushRepository%teamcity-server:eap-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:eap-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:nanoserver-1903 %docker.pushRepository%teamcity-minimal-agent:eap-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:eap-windowsservercore-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:windowsservercore-1903 %docker.pushRepository%teamcity-agent:eap-windowsservercore-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:eap-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:nanoserver-1903 %docker.pushRepository%teamcity-agent:eap-nanoserver-1903"
}
}

dockerCommand {
name = "push teamcity-server:latest-nanoserver-1903,2020.1.1-nanoserver-1903,eap-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-server:latest-nanoserver-1903
%docker.pushRepository%teamcity-server:2020.1.1-nanoserver-1903
%docker.pushRepository%teamcity-server:eap-nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-minimal-agent:latest-nanoserver-1903,2020.1.1-nanoserver-1903,eap-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-minimal-agent:latest-nanoserver-1903
%docker.pushRepository%teamcity-minimal-agent:2020.1.1-nanoserver-1903
%docker.pushRepository%teamcity-minimal-agent:eap-nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:latest-windowsservercore-1903,2020.1.1-windowsservercore-1903,eap-windowsservercore-1903"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-agent:latest-windowsservercore-1903
%docker.pushRepository%teamcity-agent:2020.1.1-windowsservercore-1903
%docker.pushRepository%teamcity-agent:eap-windowsservercore-1903
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:latest-nanoserver-1903,2020.1.1-nanoserver-1903,eap-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.pushRepository%teamcity-agent:latest-nanoserver-1903
%docker.pushRepository%teamcity-agent:2020.1.1-nanoserver-1903
%docker.pushRepository%teamcity-agent:eap-nanoserver-1903
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "27gb"
failBuild = true
}
dockerSupport {
cleanupPushedImages = true
loginToRegistry = on {
dockerRegistryId = "PROJECT_EXT_315,PROJECT_EXT_2277"
}
}
swabra {
forceCleanCheckout = true
}
}
})

object TC_Trunk_BuildDistTarGzWar_build_all: BuildType(
{
name = "Build"
steps {
}
dependencies {
snapshot(AbsoluteId("TC_Trunk_BuildDistTarGzWar"))
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_18_04_linux)
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_nanoserver_1809)
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_nanoserver_1903)
{
onDependencyFailure = FailureAction.IGNORE
}
}
})

object TC_Trunk_BuildDistTarGzWar_latest_manifest: BuildType(
{
name = "Manifest latest on Space"
steps {
dockerCommand {
name = "manifest create teamcity-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "create -a %docker.pushRepository%teamcity-agent:latest %docker.pushRepository%teamcity-agent:latest-18.04 %docker.pushRepository%teamcity-agent:latest-nanoserver-1809 %docker.pushRepository%teamcity-agent:latest-windowsservercore-1809 %docker.pushRepository%teamcity-agent:latest-nanoserver-1903 %docker.pushRepository%teamcity-agent:latest-windowsservercore-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.pushRepository%teamcity-agent:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.pushRepository%teamcity-agent:latest --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "create -a %docker.pushRepository%teamcity-minimal-agent:latest %docker.pushRepository%teamcity-minimal-agent:latest-18.04 %docker.pushRepository%teamcity-minimal-agent:latest-nanoserver-1809 %docker.pushRepository%teamcity-minimal-agent:latest-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.pushRepository%teamcity-minimal-agent:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.pushRepository%teamcity-minimal-agent:latest --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-server"
commandType = other {
subCommand = "manifest"
commandArgs = "create -a %docker.pushRepository%teamcity-server:latest %docker.pushRepository%teamcity-server:latest-18.04 %docker.pushRepository%teamcity-server:latest-nanoserver-1809 %docker.pushRepository%teamcity-server:latest-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-server"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.pushRepository%teamcity-server:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-server"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.pushRepository%teamcity-server:latest --verbose"
}
}
}
dependencies {
snapshot(AbsoluteId("TC_Trunk_BuildDistTarGzWar"))
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_build_all)
{
onDependencyFailure = FailureAction.IGNORE
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
dockerRegistryId = "PROJECT_EXT_315,PROJECT_EXT_2277"
}
}
}
})

object TC_Trunk_BuildDistTarGzWar_2020_1_1_manifest: BuildType(
{
name = "Manifest 2020.1.1 on Space"
steps {
dockerCommand {
name = "manifest create teamcity-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "create -a %docker.pushRepository%teamcity-agent:2020.1.1 %docker.pushRepository%teamcity-agent:2020.1.1-18.04 %docker.pushRepository%teamcity-agent:2020.1.1-nanoserver-1809 %docker.pushRepository%teamcity-agent:2020.1.1-windowsservercore-1809 %docker.pushRepository%teamcity-agent:2020.1.1-nanoserver-1903 %docker.pushRepository%teamcity-agent:2020.1.1-windowsservercore-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.pushRepository%teamcity-agent:2020.1.1"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.pushRepository%teamcity-agent:2020.1.1 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "create -a %docker.pushRepository%teamcity-minimal-agent:2020.1.1 %docker.pushRepository%teamcity-minimal-agent:2020.1.1-18.04 %docker.pushRepository%teamcity-minimal-agent:2020.1.1-nanoserver-1809 %docker.pushRepository%teamcity-minimal-agent:2020.1.1-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.pushRepository%teamcity-minimal-agent:2020.1.1"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.pushRepository%teamcity-minimal-agent:2020.1.1 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-server"
commandType = other {
subCommand = "manifest"
commandArgs = "create -a %docker.pushRepository%teamcity-server:2020.1.1 %docker.pushRepository%teamcity-server:2020.1.1-18.04 %docker.pushRepository%teamcity-server:2020.1.1-nanoserver-1809 %docker.pushRepository%teamcity-server:2020.1.1-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-server"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.pushRepository%teamcity-server:2020.1.1"
}
}
dockerCommand {
name = "manifest inspect teamcity-server"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.pushRepository%teamcity-server:2020.1.1 --verbose"
}
}
}
dependencies {
snapshot(AbsoluteId("TC_Trunk_BuildDistTarGzWar"))
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_build_all)
{
onDependencyFailure = FailureAction.IGNORE
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
dockerRegistryId = "PROJECT_EXT_315,PROJECT_EXT_2277"
}
}
}
})

object TC_Trunk_BuildDistTarGzWar_eap_manifest: BuildType(
{
name = "Manifest eap on Space"
steps {
dockerCommand {
name = "manifest create teamcity-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "create -a %docker.pushRepository%teamcity-agent:eap %docker.pushRepository%teamcity-agent:eap-18.04 %docker.pushRepository%teamcity-agent:eap-nanoserver-1809 %docker.pushRepository%teamcity-agent:eap-windowsservercore-1809 %docker.pushRepository%teamcity-agent:eap-nanoserver-1903 %docker.pushRepository%teamcity-agent:eap-windowsservercore-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.pushRepository%teamcity-agent:eap"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.pushRepository%teamcity-agent:eap --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "create -a %docker.pushRepository%teamcity-minimal-agent:eap %docker.pushRepository%teamcity-minimal-agent:eap-18.04 %docker.pushRepository%teamcity-minimal-agent:eap-nanoserver-1809 %docker.pushRepository%teamcity-minimal-agent:eap-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.pushRepository%teamcity-minimal-agent:eap"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.pushRepository%teamcity-minimal-agent:eap --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-server"
commandType = other {
subCommand = "manifest"
commandArgs = "create -a %docker.pushRepository%teamcity-server:eap %docker.pushRepository%teamcity-server:eap-18.04 %docker.pushRepository%teamcity-server:eap-nanoserver-1809 %docker.pushRepository%teamcity-server:eap-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-server"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.pushRepository%teamcity-server:eap"
}
}
dockerCommand {
name = "manifest inspect teamcity-server"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.pushRepository%teamcity-server:eap --verbose"
}
}
}
dependencies {
snapshot(AbsoluteId("TC_Trunk_BuildDistTarGzWar"))
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_build_all)
{
onDependencyFailure = FailureAction.IGNORE
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
dockerRegistryId = "PROJECT_EXT_315,PROJECT_EXT_2277"
}
}
}
})

object TC_Trunk_BuildDistTarGzWar_linux_deploy: BuildType(
{
name = "Deploy linux"
steps {
dockerCommand {
name = "pull teamcity-agent"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-agent:18.04-sudo"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-agent:18.04-sudo"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-agent:18.04-sudo jetbrains/teamcity-agent:18.04-sudo"
}
}

dockerCommand {
name = "push jetbrains/teamcity-agent:18.04-sudo"
commandType = push {
namesAndTags = """
jetbrains/teamcity-agent:18.04-sudo
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-agent:18.04"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-agent:18.04"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-agent:18.04 jetbrains/teamcity-agent:18.04"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-agent:linux"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-agent:18.04 jetbrains/teamcity-agent:linux"
}
}

dockerCommand {
name = "push jetbrains/teamcity-agent:18.04,linux"
commandType = push {
namesAndTags = """
jetbrains/teamcity-agent:18.04
jetbrains/teamcity-agent:linux
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-minimal-agent"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-minimal-agent:18.04"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-minimal-agent:18.04"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-minimal-agent:18.04 jetbrains/teamcity-minimal-agent:18.04"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-minimal-agent:linux"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-minimal-agent:18.04 jetbrains/teamcity-minimal-agent:linux"
}
}

dockerCommand {
name = "push jetbrains/teamcity-minimal-agent:18.04,linux"
commandType = push {
namesAndTags = """
jetbrains/teamcity-minimal-agent:18.04
jetbrains/teamcity-minimal-agent:linux
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-server"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-server:18.04"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-server:18.04"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-server:18.04 jetbrains/teamcity-server:18.04"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-server:linux"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-server:18.04 jetbrains/teamcity-server:linux"
}
}

dockerCommand {
name = "push jetbrains/teamcity-server:18.04,linux"
commandType = push {
namesAndTags = """
jetbrains/teamcity-server:18.04
jetbrains/teamcity-server:linux
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
dockerRegistryId = "PROJECT_EXT_315,PROJECT_EXT_2277"
}
}
swabra {
forceCleanCheckout = true
}
}
dependencies {
snapshot(AbsoluteId("TC_Trunk_BuildDistTarGzWar"))
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_latest_manifest)
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_2020_1_1_manifest)
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_eap_manifest)
{
onDependencyFailure = FailureAction.IGNORE
}
}
})

object TC_Trunk_BuildDistTarGzWar_windows_deploy: BuildType(
{
name = "Deploy windows"
steps {
dockerCommand {
name = "pull teamcity-agent"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-agent:nanoserver-1809"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-agent:nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-agent:nanoserver-1809 jetbrains/teamcity-agent:nanoserver-1809"
}
}

dockerCommand {
name = "push jetbrains/teamcity-agent:nanoserver-1809"
commandType = push {
namesAndTags = """
jetbrains/teamcity-agent:nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-agent:windowsservercore-1809"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-agent:windowsservercore-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-agent:windowsservercore-1809 jetbrains/teamcity-agent:windowsservercore-1809"
}
}

dockerCommand {
name = "push jetbrains/teamcity-agent:windowsservercore-1809"
commandType = push {
namesAndTags = """
jetbrains/teamcity-agent:windowsservercore-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-minimal-agent"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-minimal-agent:nanoserver-1809"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-minimal-agent:nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-minimal-agent:nanoserver-1809 jetbrains/teamcity-minimal-agent:nanoserver-1809"
}
}

dockerCommand {
name = "push jetbrains/teamcity-minimal-agent:nanoserver-1809"
commandType = push {
namesAndTags = """
jetbrains/teamcity-minimal-agent:nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-server"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-server:nanoserver-1809"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-server:nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-server:nanoserver-1809 jetbrains/teamcity-server:nanoserver-1809"
}
}

dockerCommand {
name = "push jetbrains/teamcity-server:nanoserver-1809"
commandType = push {
namesAndTags = """
jetbrains/teamcity-server:nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-agent:nanoserver-1903"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-agent:nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-agent:nanoserver-1903 jetbrains/teamcity-agent:nanoserver-1903"
}
}

dockerCommand {
name = "push jetbrains/teamcity-agent:nanoserver-1903"
commandType = push {
namesAndTags = """
jetbrains/teamcity-agent:nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-agent:windowsservercore-1903"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-agent:windowsservercore-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-agent:windowsservercore-1903 jetbrains/teamcity-agent:windowsservercore-1903"
}
}

dockerCommand {
name = "push jetbrains/teamcity-agent:windowsservercore-1903"
commandType = push {
namesAndTags = """
jetbrains/teamcity-agent:windowsservercore-1903
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-minimal-agent"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-minimal-agent:nanoserver-1903"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-minimal-agent:nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-minimal-agent:nanoserver-1903 jetbrains/teamcity-minimal-agent:nanoserver-1903"
}
}

dockerCommand {
name = "push jetbrains/teamcity-minimal-agent:nanoserver-1903"
commandType = push {
namesAndTags = """
jetbrains/teamcity-minimal-agent:nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-server"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.pushRepository%teamcity-server:nanoserver-1903"
}
}

dockerCommand {
name = "tag jetbrains/teamcity-server:nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.pushRepository%teamcity-server:nanoserver-1903 jetbrains/teamcity-server:nanoserver-1903"
}
}

dockerCommand {
name = "push jetbrains/teamcity-server:nanoserver-1903"
commandType = push {
namesAndTags = """
jetbrains/teamcity-server:nanoserver-1903
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "30gb"
failBuild = true
}
dockerSupport {
cleanupPushedImages = true
loginToRegistry = on {
dockerRegistryId = "PROJECT_EXT_315,PROJECT_EXT_2277"
}
}
swabra {
forceCleanCheckout = true
}
}
dependencies {
snapshot(AbsoluteId("TC_Trunk_BuildDistTarGzWar"))
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_latest_manifest)
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_2020_1_1_manifest)
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_eap_manifest)
{
onDependencyFailure = FailureAction.IGNORE
}
}
})

object TC_Trunk_BuildDistTarGzWar_deploy_all: BuildType(
{
name = "Deploy"
steps {
}
dependencies {
snapshot(AbsoluteId("TC_Trunk_BuildDistTarGzWar"))
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_linux_deploy)
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(TC_Trunk_BuildDistTarGzWar_windows_deploy)
{
onDependencyFailure = FailureAction.IGNORE
}
}
})

project {
vcsRoot(RemoteTeamcityImages)
buildType(TC_Trunk_BuildDistTarGzWar_latest_manifest)
buildType(TC_Trunk_BuildDistTarGzWar_2020_1_1_manifest)
buildType(TC_Trunk_BuildDistTarGzWar_eap_manifest)
buildType(TC_Trunk_BuildDistTarGzWar_build_all)
buildType(TC_Trunk_BuildDistTarGzWar_deploy_all)
buildType(TC_Trunk_BuildDistTarGzWar_18_04_linux)
buildType(TC_Trunk_BuildDistTarGzWar_nanoserver_1809)
buildType(TC_Trunk_BuildDistTarGzWar_nanoserver_1903)
buildType(TC_Trunk_BuildDistTarGzWar_linux_deploy)
buildType(TC_Trunk_BuildDistTarGzWar_windows_deploy)
}

object RemoteTeamcityImages : GitVcsRoot({
name = "remote teamcity images"
url = "https://github.com/JetBrains/teamcity-docker-images.git"
})
