import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
version = "2019.2"

object push_local_linux_18_04 : BuildType({
name = "Build and push linux 18.04"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
description  = "teamcity-server:2020.1.3-linux,latest,2020.1.3 teamcity-minimal-agent:2020.1.3-linux,latest,2020.1.3 teamcity-agent:2020.1.3-linux,latest,2020.1.3:2020.1.3-linux-sudo"
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
name = "build teamcity-server:2020.1.3-linux"
commandType = build {
source = file {
path = """context/generated/linux/Server/Ubuntu/18.04/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-server:2020.1.3-linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "build teamcity-minimal-agent:2020.1.3-linux"
commandType = build {
source = file {
path = """context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-minimal-agent:2020.1.3-linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "build teamcity-agent:2020.1.3-linux"
commandType = build {
source = file {
path = """context/generated/linux/Agent/Ubuntu/18.04/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:2020.1.3-linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "build teamcity-agent:2020.1.3-linux-sudo"
commandType = build {
source = file {
path = """context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:2020.1.3-linux-sudo
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "tag teamcity-server:2020.1.3-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:2020.1.3-linux %docker.buildRepository%teamcity-server:2020.1.3-linux"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.1.3-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:2020.1.3-linux %docker.buildRepository%teamcity-minimal-agent:2020.1.3-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:2020.1.3-linux %docker.buildRepository%teamcity-agent:2020.1.3-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-linux-sudo"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:2020.1.3-linux-sudo %docker.buildRepository%teamcity-agent:2020.1.3-linux-sudo"
}
}

dockerCommand {
name = "push teamcity-server:2020.1.3-linux"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-server:2020.1.3-linux
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-minimal-agent:2020.1.3-linux"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-minimal-agent:2020.1.3-linux
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-linux"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:2020.1.3-linux
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-linux-sudo"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:2020.1.3-linux-sudo
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "4gb"
failBuild = true
}
swabra {
forceCleanCheckout = true
}
}
dependencies {
dependency(AbsoluteId("TC2020_1_BuildDistTarGzWar")) {
snapshot { onDependencyFailure = FailureAction.IGNORE }
artifacts {
artifactRules = "TeamCity-*.tar.gz!/**=>context"
}
}
}
})

object push_local_windows_1803 : BuildType({
name = "ON PAUSE Build and push windows 1803"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
description  = "teamcity-server:EAP-nanoserver-1803,EAP teamcity-minimal-agent:EAP-nanoserver-1803,EAP teamcity-agent:EAP-windowsservercore-1803,EAP-windowsservercore,-windowsservercore:EAP-nanoserver-1803,EAP"
})

object push_local_windows_1809 : BuildType({
name = "Build and push windows 1809"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
description  = "teamcity-server:2020.1.3-nanoserver-1809,latest,2020.1.3 teamcity-minimal-agent:2020.1.3-nanoserver-1809,latest,2020.1.3 teamcity-agent:2020.1.3-windowsservercore-1809,2020.1.3-windowsservercore,latest-windowsservercore:2020.1.3-nanoserver-1809,latest,2020.1.3"
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
name = "build teamcity-server:2020.1.3-nanoserver-1809"
commandType = build {
source = file {
path = """context/generated/windows/Server/nanoserver/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-server:2020.1.3-nanoserver-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-minimal-agent:2020.1.3-nanoserver-1809"
commandType = build {
source = file {
path = """context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-minimal-agent:2020.1.3-nanoserver-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:2020.1.3-windowsservercore-1809"
commandType = build {
source = file {
path = """context/generated/windows/Agent/windowsservercore/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:2020.1.3-windowsservercore-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:2020.1.3-nanoserver-1809"
commandType = build {
source = file {
path = """context/generated/windows/Agent/nanoserver/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:2020.1.3-nanoserver-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "tag teamcity-server:2020.1.3-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:2020.1.3-nanoserver-1809 %docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.1.3-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:2020.1.3-nanoserver-1809 %docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-windowsservercore-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:2020.1.3-windowsservercore-1809 %docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:2020.1.3-nanoserver-1809 %docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-server:2020.1.3-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-minimal-agent:2020.1.3-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-windowsservercore-1809"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1809
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1809
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "33gb"
failBuild = true
}
swabra {
forceCleanCheckout = true
}
}
dependencies {
dependency(AbsoluteId("TC2020_1_BuildDistTarGzWar")) {
snapshot { onDependencyFailure = FailureAction.IGNORE }
artifacts {
artifactRules = "TeamCity-*.tar.gz!/**=>context"
}
}
}
})

object push_local_windows_1903 : BuildType({
name = "Build and push windows 1903"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
description  = "teamcity-server:2020.1.3-nanoserver-1903,latest,2020.1.3 teamcity-minimal-agent:2020.1.3-nanoserver-1903,latest,2020.1.3 teamcity-agent:2020.1.3-windowsservercore-1903,2020.1.3-windowsservercore,latest-windowsservercore:2020.1.3-nanoserver-1903,latest,2020.1.3"
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
name = "build teamcity-server:2020.1.3-nanoserver-1903"
commandType = build {
source = file {
path = """context/generated/windows/Server/nanoserver/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-server:2020.1.3-nanoserver-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-minimal-agent:2020.1.3-nanoserver-1903"
commandType = build {
source = file {
path = """context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-minimal-agent:2020.1.3-nanoserver-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:2020.1.3-windowsservercore-1903"
commandType = build {
source = file {
path = """context/generated/windows/Agent/windowsservercore/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:2020.1.3-windowsservercore-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:2020.1.3-nanoserver-1903"
commandType = build {
source = file {
path = """context/generated/windows/Agent/nanoserver/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:2020.1.3-nanoserver-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "tag teamcity-server:2020.1.3-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:2020.1.3-nanoserver-1903 %docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.1.3-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:2020.1.3-nanoserver-1903 %docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-windowsservercore-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:2020.1.3-windowsservercore-1903 %docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:2020.1.3-nanoserver-1903 %docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1903"
}
}

dockerCommand {
name = "push teamcity-server:2020.1.3-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-minimal-agent:2020.1.3-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-windowsservercore-1903"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1903
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1903
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "33gb"
failBuild = true
}
swabra {
forceCleanCheckout = true
}
}
dependencies {
dependency(AbsoluteId("TC2020_1_BuildDistTarGzWar")) {
snapshot { onDependencyFailure = FailureAction.IGNORE }
artifacts {
artifactRules = "TeamCity-*.tar.gz!/**=>context"
}
}
}
})

object push_local_windows_1909 : BuildType({
name = "ON PAUSE Build and push windows 1909"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
description  = "teamcity-server:EAP-nanoserver-1909,EAP teamcity-minimal-agent:EAP-nanoserver-1909,EAP teamcity-agent:EAP-windowsservercore-1909,EAP-windowsservercore,-windowsservercore:EAP-nanoserver-1909,EAP"
})

object publish_local: BuildType(
{
name = "Publish"
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
commandArgs = "create %docker.buildRepository%teamcity-server:latest %docker.buildRepository%teamcity-server:2020.1.3-linux %docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1809 %docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-server:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-server:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-server:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-server:latest --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent:latest %docker.buildRepository%teamcity-agent:2020.1.3-linux %docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1809 %docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent:latest --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-minimal-agent:latest %docker.buildRepository%teamcity-minimal-agent:2020.1.3-linux %docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1809 %docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-minimal-agent:latest"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent:latest"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-minimal-agent:latest --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-server:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-server:2020.1.3 %docker.buildRepository%teamcity-server:2020.1.3-linux %docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1809 %docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-server:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-server:2020.1.3"
}
}
dockerCommand {
name = "manifest inspect teamcity-server:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-server:2020.1.3 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent:2020.1.3 %docker.buildRepository%teamcity-agent:2020.1.3-linux %docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1809 %docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent:2020.1.3"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent:2020.1.3 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-minimal-agent:2020.1.3 %docker.buildRepository%teamcity-minimal-agent:2020.1.3-linux %docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1809 %docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-minimal-agent:2020.1.3"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-minimal-agent:2020.1.3 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:2020.1.3-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore %docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1809 %docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:2020.1.3-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:2020.1.3-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:latest-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent:latest-windowsservercore %docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1809 %docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:latest-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent:latest-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:latest-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent:latest-windowsservercore --verbose"
}
}
}
dependencies {
snapshot(AbsoluteId("TC2020_1_BuildDistTarGzWar"))
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(push_local_linux_18_04)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
snapshot(push_local_windows_1809)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
snapshot(push_local_windows_1903)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
}
requirements {
noLessThanVer("docker.version", "18.05.0")
equals("docker.server.osType", "windows")
}
features {
}
})

object push_hub_linux: BuildType(
{
name = "Push linux"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
steps {
dockerCommand {
name = "pull teamcity-server:2020.1.3-linux"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-server:2020.1.3-linux"
}
}

dockerCommand {
name = "tag teamcity-server:2020.1.3-linux"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-server:2020.1.3-linux %docker.deployRepository%teamcity-server:2020.1.3-linux"
}
}

dockerCommand {
name = "push teamcity-server:2020.1.3-linux"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-server:2020.1.3-linux
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:2020.1.3-linux"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-linux"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-linux %docker.deployRepository%teamcity-agent:2020.1.3-linux"
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-linux"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2020.1.3-linux
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:2020.1.3-linux-sudo"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-linux-sudo"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-linux-sudo"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-linux-sudo %docker.deployRepository%teamcity-agent:2020.1.3-linux-sudo"
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-linux-sudo"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2020.1.3-linux-sudo
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-minimal-agent:2020.1.3-linux"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:2020.1.3-linux"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.1.3-linux"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:2020.1.3-linux %docker.deployRepository%teamcity-minimal-agent:2020.1.3-linux"
}
}

dockerCommand {
name = "push teamcity-minimal-agent:2020.1.3-linux"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-minimal-agent:2020.1.3-linux
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "4gb"
failBuild = true
}
swabra {
forceCleanCheckout = true
}
}
requirements {
equals("docker.server.osType", "linux")
}
dependencies {
snapshot(publish_local)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
}
})

object push_hub_windows: BuildType(
{
name = "Push windows"
buildNumberPattern="%dockerImage.teamcity.buildNumber%-%build.counter%"
steps {
dockerCommand {
name = "pull teamcity-server:2020.1.3-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-server:2020.1.3-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1809 %docker.deployRepository%teamcity-server:2020.1.3-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-server:2020.1.3-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-server:2020.1.3-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:2020.1.3-windowsservercore-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-windowsservercore-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1809 %docker.deployRepository%teamcity-agent:2020.1.3-windowsservercore-1809"
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-windowsservercore-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2020.1.3-windowsservercore-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:2020.1.3-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1809 %docker.deployRepository%teamcity-agent:2020.1.3-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2020.1.3-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-minimal-agent:2020.1.3-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.1.3-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-minimal-agent:2020.1.3-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-server:2020.1.3-nanoserver-1903"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-server:2020.1.3-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-server:2020.1.3-nanoserver-1903 %docker.deployRepository%teamcity-server:2020.1.3-nanoserver-1903"
}
}

dockerCommand {
name = "push teamcity-server:2020.1.3-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-server:2020.1.3-nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:2020.1.3-windowsservercore-1903"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-windowsservercore-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-windowsservercore-1903 %docker.deployRepository%teamcity-agent:2020.1.3-windowsservercore-1903"
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-windowsservercore-1903"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2020.1.3-windowsservercore-1903
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:2020.1.3-nanoserver-1903"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:2020.1.3-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:2020.1.3-nanoserver-1903 %docker.deployRepository%teamcity-agent:2020.1.3-nanoserver-1903"
}
}

dockerCommand {
name = "push teamcity-agent:2020.1.3-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:2020.1.3-nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-minimal-agent:2020.1.3-nanoserver-1903"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:2020.1.3-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1903 %docker.deployRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1903"
}
}

dockerCommand {
name = "push teamcity-minimal-agent:2020.1.3-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1903
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "36gb"
failBuild = true
}
swabra {
forceCleanCheckout = true
}
}
requirements {
equals("docker.server.osType", "windows")
}
dependencies {
snapshot(publish_local)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
}
})

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
commandArgs = "create %docker.deployRepository%teamcity-server:latest %docker.deployRepository%teamcity-server:2020.1.3-linux %docker.deployRepository%teamcity-server:2020.1.3-nanoserver-1809 %docker.deployRepository%teamcity-server:2020.1.3-nanoserver-1903"
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
commandArgs = "create %docker.deployRepository%teamcity-agent:latest %docker.deployRepository%teamcity-agent:2020.1.3-linux %docker.deployRepository%teamcity-agent:2020.1.3-nanoserver-1809 %docker.deployRepository%teamcity-agent:2020.1.3-nanoserver-1903"
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
commandArgs = "create %docker.deployRepository%teamcity-minimal-agent:latest %docker.deployRepository%teamcity-minimal-agent:2020.1.3-linux %docker.deployRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1903"
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
snapshot(push_hub_linux)
{
onDependencyFailure = FailureAction.IGNORE
}
snapshot(push_hub_windows)
{
onDependencyFailure = FailureAction.IGNORE
}
}
requirements {
noLessThanVer("docker.version", "18.05.0")
equals("docker.server.osType", "windows")
}
features {
}
})

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
name = "manifest create teamcity-server:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-server:2020.1.3 %docker.deployRepository%teamcity-server:2020.1.3-linux %docker.deployRepository%teamcity-server:2020.1.3-nanoserver-1809 %docker.deployRepository%teamcity-server:2020.1.3-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-server:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-server:2020.1.3"
}
}
dockerCommand {
name = "manifest inspect teamcity-server:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-server:2020.1.3 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:2020.1.3 %docker.deployRepository%teamcity-agent:2020.1.3-linux %docker.deployRepository%teamcity-agent:2020.1.3-nanoserver-1809 %docker.deployRepository%teamcity-agent:2020.1.3-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-agent:2020.1.3"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-agent:2020.1.3 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-minimal-agent:2020.1.3 %docker.deployRepository%teamcity-minimal-agent:2020.1.3-linux %docker.deployRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:2020.1.3-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-minimal-agent:2020.1.3"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent:2020.1.3"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-minimal-agent:2020.1.3 --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:2020.1.3-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:2020.1.3-windowsservercore %docker.deployRepository%teamcity-agent:2020.1.3-windowsservercore-1809 %docker.deployRepository%teamcity-agent:2020.1.3-windowsservercore-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:2020.1.3-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-agent:2020.1.3-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:2020.1.3-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-agent:2020.1.3-windowsservercore --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:latest-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:latest-windowsservercore %docker.deployRepository%teamcity-agent:2020.1.3-windowsservercore-1809 %docker.deployRepository%teamcity-agent:2020.1.3-windowsservercore-1903"
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
snapshot(push_hub_linux)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
snapshot(push_hub_windows)
{
onDependencyFailure =  FailureAction.FAIL_TO_START
}
}
requirements {
noLessThanVer("docker.version", "18.05.0")
equals("docker.server.osType", "windows")
}
features {
}
})

object LocalProject : Project({
name = "Local registry"
buildType(push_local_linux_18_04)
buildType(push_local_windows_1803)
buildType(push_local_windows_1809)
buildType(push_local_windows_1903)
buildType(push_local_windows_1909)
buildType(publish_local)
})
object HubProject : Project({
name = "Docker hub"
buildType(push_hub_linux)
buildType(push_hub_windows)
buildType(publish_hub_latest)
buildType(publish_hub_version)
})
project {
vcsRoot(RemoteTeamcityImages)
subProject(LocalProject)
subProject(HubProject)
params {
param("dockerImage.teamcity.buildNumber", "%dep.TC2020_1_BuildDistTarGzWar.build.number%")
}
}

object RemoteTeamcityImages : GitVcsRoot({
name = "remote teamcity images"
url = "https://github.com/JetBrains/teamcity-docker-images.git"
branch = "refs/heads/Lakhnau-2020.1.x"
})
