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
description  = "teamcity-server:EAP-linux,EAP teamcity-minimal-agent:EAP-linux,EAP teamcity-agent:EAP-linux,EAP:EAP-linux-sudo"
vcs {root(TeamCityDockerImagesRepo)}
steps {
dockerCommand {
name = "pull ubuntu:18.04"
commandType = other {
subCommand = "pull"
commandArgs = "ubuntu:18.04"
}
}

dockerCommand {
name = "build teamcity-server:EAP-linux"
commandType = build {
source = file {
path = """context/generated/linux/Server/Ubuntu/18.04/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-server:EAP-linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "build teamcity-minimal-agent:EAP-linux"
commandType = build {
source = file {
path = """context/generated/linux/MinimalAgent/Ubuntu/18.04/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-minimal-agent:EAP-linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "build teamcity-agent:EAP-linux"
commandType = build {
source = file {
path = """context/generated/linux/Agent/Ubuntu/18.04/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:EAP-linux
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "build teamcity-agent:EAP-linux-sudo"
commandType = build {
source = file {
path = """context/generated/linux/Agent/Ubuntu/18.04-sudo/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:EAP-linux-sudo
""".trimIndent()
}
param("dockerImage.platform", "linux")
}

dockerCommand {
name = "tag teamcity-server:EAP-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:EAP-linux %docker.buildRepository%teamcity-server:EAP-linux"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:EAP-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:EAP-linux %docker.buildRepository%teamcity-minimal-agent:EAP-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-linux"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:EAP-linux %docker.buildRepository%teamcity-agent:EAP-linux"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-linux-sudo"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:EAP-linux-sudo %docker.buildRepository%teamcity-agent:EAP-linux-sudo"
}
}

dockerCommand {
name = "push teamcity-server:EAP-linux"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-server:EAP-linux
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-minimal-agent:EAP-linux"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-minimal-agent:EAP-linux
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:EAP-linux"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:EAP-linux
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:EAP-linux-sudo"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:EAP-linux-sudo
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
dependencies {
dependency(AbsoluteId("TC_Trunk_BuildDistTarGzWar")) {
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
description  = "teamcity-server:EAP-nanoserver-1809,EAP teamcity-minimal-agent:EAP-nanoserver-1809,EAP teamcity-agent:EAP-windowsservercore-1809,EAP-windowsservercore,-windowsservercore:EAP-nanoserver-1809,EAP"
vcs {root(TeamCityDockerImagesRepo)}
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
name = "build teamcity-server:EAP-nanoserver-1809"
commandType = build {
source = file {
path = """context/generated/windows/Server/nanoserver/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-server:EAP-nanoserver-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-minimal-agent:EAP-nanoserver-1809"
commandType = build {
source = file {
path = """context/generated/windows/MinimalAgent/nanoserver/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-minimal-agent:EAP-nanoserver-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:EAP-windowsservercore-1809"
commandType = build {
source = file {
path = """context/generated/windows/Agent/windowsservercore/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:EAP-windowsservercore-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:EAP-nanoserver-1809"
commandType = build {
source = file {
path = """context/generated/windows/Agent/nanoserver/1809/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:EAP-nanoserver-1809
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "tag teamcity-server:EAP-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:EAP-nanoserver-1809 %docker.buildRepository%teamcity-server:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:EAP-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:EAP-nanoserver-1809 %docker.buildRepository%teamcity-minimal-agent:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-windowsservercore-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:EAP-windowsservercore-1809 %docker.buildRepository%teamcity-agent:EAP-windowsservercore-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:EAP-nanoserver-1809 %docker.buildRepository%teamcity-agent:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-server:EAP-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-server:EAP-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-minimal-agent:EAP-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-minimal-agent:EAP-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:EAP-windowsservercore-1809"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:EAP-windowsservercore-1809
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:EAP-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:EAP-nanoserver-1809
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "38gb"
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
dependencies {
dependency(AbsoluteId("TC_Trunk_BuildDistTarGzWar")) {
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
description  = "teamcity-server:EAP-nanoserver-1903,EAP teamcity-minimal-agent:EAP-nanoserver-1903,EAP teamcity-agent:EAP-windowsservercore-1903,EAP-windowsservercore,-windowsservercore:EAP-nanoserver-1903,EAP"
vcs {root(TeamCityDockerImagesRepo)}
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
name = "build teamcity-server:EAP-nanoserver-1903"
commandType = build {
source = file {
path = """context/generated/windows/Server/nanoserver/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-server:EAP-nanoserver-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-minimal-agent:EAP-nanoserver-1903"
commandType = build {
source = file {
path = """context/generated/windows/MinimalAgent/nanoserver/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-minimal-agent:EAP-nanoserver-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:EAP-windowsservercore-1903"
commandType = build {
source = file {
path = """context/generated/windows/Agent/windowsservercore/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:EAP-windowsservercore-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "build teamcity-agent:EAP-nanoserver-1903"
commandType = build {
source = file {
path = """context/generated/windows/Agent/nanoserver/1903/Dockerfile"""
}
contextDir = "context"
namesAndTags = """
teamcity-agent:EAP-nanoserver-1903
""".trimIndent()
}
param("dockerImage.platform", "windows")
}

dockerCommand {
name = "tag teamcity-server:EAP-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-server:EAP-nanoserver-1903 %docker.buildRepository%teamcity-server:EAP-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:EAP-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-minimal-agent:EAP-nanoserver-1903 %docker.buildRepository%teamcity-minimal-agent:EAP-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-windowsservercore-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:EAP-windowsservercore-1903 %docker.buildRepository%teamcity-agent:EAP-windowsservercore-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "teamcity-agent:EAP-nanoserver-1903 %docker.buildRepository%teamcity-agent:EAP-nanoserver-1903"
}
}

dockerCommand {
name = "push teamcity-server:EAP-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-server:EAP-nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-minimal-agent:EAP-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-minimal-agent:EAP-nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:EAP-windowsservercore-1903"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:EAP-windowsservercore-1903
""".trimIndent()
}
}

dockerCommand {
name = "push teamcity-agent:EAP-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.buildRepository%teamcity-agent:EAP-nanoserver-1903
""".trimIndent()
}
}

}
features {
freeDiskSpace {
requiredSpace = "38gb"
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
dependencies {
dependency(AbsoluteId("TC_Trunk_BuildDistTarGzWar")) {
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
name = "manifest create teamcity-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent:EAP %docker.buildRepository%teamcity-agent:EAP-linux %docker.buildRepository%teamcity-agent:EAP-nanoserver-1809 %docker.buildRepository%teamcity-agent:EAP-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent:EAP"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent:EAP --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-minimal-agent:EAP %docker.buildRepository%teamcity-minimal-agent:EAP-linux %docker.buildRepository%teamcity-minimal-agent:EAP-nanoserver-1809 %docker.buildRepository%teamcity-minimal-agent:EAP-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-minimal-agent:EAP"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-minimal-agent:EAP --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-server:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-server:EAP %docker.buildRepository%teamcity-server:EAP-linux %docker.buildRepository%teamcity-server:EAP-nanoserver-1809 %docker.buildRepository%teamcity-server:EAP-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-server:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-server:EAP"
}
}
dockerCommand {
name = "manifest inspect teamcity-server:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-server:EAP --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:EAP-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent:EAP-windowsservercore %docker.buildRepository%teamcity-agent:EAP-windowsservercore-1809 %docker.buildRepository%teamcity-agent:EAP-windowsservercore-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:EAP-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent:EAP-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:EAP-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent:EAP-windowsservercore --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.buildRepository%teamcity-agent:-windowsservercore %docker.buildRepository%teamcity-agent:EAP-windowsservercore-1809 %docker.buildRepository%teamcity-agent:EAP-windowsservercore-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.buildRepository%teamcity-agent:-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.buildRepository%teamcity-agent:-windowsservercore --verbose"
}
}
}
dependencies {
snapshot(AbsoluteId("TC_Trunk_BuildDistTarGzWar"))
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
dockerSupport {
cleanupPushedImages = true
loginToRegistry = on {
dockerRegistryId = "PROJECT_EXT_315,PROJECT_EXT_4003,PROJECT_EXT_4022"
}
}
}
})

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
name = "pull teamcity-agent:EAP-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-nanoserver-1809 %docker.deployRepository%teamcity-agent:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-agent:EAP-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:EAP-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:EAP-windowsservercore-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-windowsservercore-1809"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-windowsservercore-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-windowsservercore-1809 %docker.deployRepository%teamcity-agent:EAP-windowsservercore-1809"
}
}

dockerCommand {
name = "push teamcity-agent:EAP-windowsservercore-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:EAP-windowsservercore-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-minimal-agent:EAP-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:EAP-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:EAP-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-minimal-agent:EAP-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-server:EAP-nanoserver-1809"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-server:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "tag teamcity-server:EAP-nanoserver-1809"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-server:EAP-nanoserver-1809 %docker.deployRepository%teamcity-server:EAP-nanoserver-1809"
}
}

dockerCommand {
name = "push teamcity-server:EAP-nanoserver-1809"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-server:EAP-nanoserver-1809
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:EAP-nanoserver-1903"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-nanoserver-1903 %docker.deployRepository%teamcity-agent:EAP-nanoserver-1903"
}
}

dockerCommand {
name = "push teamcity-agent:EAP-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:EAP-nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-agent:EAP-windowsservercore-1903"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-windowsservercore-1903"
}
}

dockerCommand {
name = "tag teamcity-agent:EAP-windowsservercore-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-agent:EAP-windowsservercore-1903 %docker.deployRepository%teamcity-agent:EAP-windowsservercore-1903"
}
}

dockerCommand {
name = "push teamcity-agent:EAP-windowsservercore-1903"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-agent:EAP-windowsservercore-1903
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-minimal-agent:EAP-nanoserver-1903"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:EAP-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-minimal-agent:EAP-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-minimal-agent:EAP-nanoserver-1903 %docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-1903"
}
}

dockerCommand {
name = "push teamcity-minimal-agent:EAP-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-1903
""".trimIndent()
}
}

dockerCommand {
name = "pull teamcity-server:EAP-nanoserver-1903"
commandType = other {
subCommand = "pull"
commandArgs = "%docker.buildRepository%teamcity-server:EAP-nanoserver-1903"
}
}

dockerCommand {
name = "tag teamcity-server:EAP-nanoserver-1903"
commandType = other {
subCommand = "tag"
commandArgs = "%docker.buildRepository%teamcity-server:EAP-nanoserver-1903 %docker.deployRepository%teamcity-server:EAP-nanoserver-1903"
}
}

dockerCommand {
name = "push teamcity-server:EAP-nanoserver-1903"
commandType = push {
namesAndTags = """
%docker.deployRepository%teamcity-server:EAP-nanoserver-1903
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
name = "manifest create teamcity-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:EAP %docker.deployRepository%teamcity-agent:EAP-linux %docker.deployRepository%teamcity-agent:EAP-nanoserver-1809 %docker.deployRepository%teamcity-agent:EAP-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-agent:EAP"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-agent:EAP --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-minimal-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-minimal-agent:EAP %docker.deployRepository%teamcity-minimal-agent:EAP-linux %docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-1809 %docker.deployRepository%teamcity-minimal-agent:EAP-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-minimal-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-minimal-agent:EAP"
}
}
dockerCommand {
name = "manifest inspect teamcity-minimal-agent:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-minimal-agent:EAP --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-server:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-server:EAP %docker.deployRepository%teamcity-server:EAP-linux %docker.deployRepository%teamcity-server:EAP-nanoserver-1809 %docker.deployRepository%teamcity-server:EAP-nanoserver-1903"
}
}
dockerCommand {
name = "manifest push teamcity-server:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-server:EAP"
}
}
dockerCommand {
name = "manifest inspect teamcity-server:EAP"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-server:EAP --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:EAP-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:EAP-windowsservercore %docker.deployRepository%teamcity-agent:EAP-windowsservercore-1809 %docker.deployRepository%teamcity-agent:EAP-windowsservercore-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:EAP-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-agent:EAP-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:EAP-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-agent:EAP-windowsservercore --verbose"
}
}
dockerCommand {
name = "manifest create teamcity-agent:-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "create %docker.deployRepository%teamcity-agent:-windowsservercore %docker.deployRepository%teamcity-agent:EAP-windowsservercore-1809 %docker.deployRepository%teamcity-agent:EAP-windowsservercore-1903"
}
}
dockerCommand {
name = "manifest push teamcity-agent:-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "push %docker.deployRepository%teamcity-agent:-windowsservercore"
}
}
dockerCommand {
name = "manifest inspect teamcity-agent:-windowsservercore"
commandType = other {
subCommand = "manifest"
commandArgs = "inspect %docker.deployRepository%teamcity-agent:-windowsservercore --verbose"
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
dockerSupport {
cleanupPushedImages = true
loginToRegistry = on {
dockerRegistryId = "PROJECT_EXT_315,PROJECT_EXT_4003,PROJECT_EXT_4022"
}
}
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
buildType(publish_hub_version)
})
project {
vcsRoot(TeamCityDockerImagesRepo)
subProject(LocalProject)
subProject(HubProject)
params {
param("dockerImage.teamcity.buildNumber", "%dep.TC_Trunk_BuildDistTarGzWar.build.number%")
param("teamcity.ui.settings.readOnly", "false")
}
}

object TeamCityDockerImagesRepo : GitVcsRoot({
name = "TeamCity Docker Images"
url = "https://github.com/JetBrains/teamcity-docker-images.git"
branch = "refs/heads/%teamcity.branch%"
})
