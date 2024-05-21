package hosted

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.FailureAction
import jetbrains.buildServer.configs.kotlin.v2019_2.ReuseBuilds
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object BuildAndPushHosted : BuildType({
    name = "Build and push for teamcity.jetbrains.com"
    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    vcs { root(TeamCityDockerImagesRepo) }
    steps {
        dockerCommand {
            name = "pull ubuntu"
            commandType = other {
                subCommand = "pull"
                commandArgs = "ubuntu:%hostedLinuxVersion%"
            }
        }

        script {
            name = "context"
            scriptContent = """
echo 2> context/.dockerignore
echo TeamCity/buildAgent >> context/.dockerignore
echo TeamCity/temp >> context/.dockerignore
""".trimIndent()
        }
        
        dockerCommand {
            name = "build teamcity-server-staging"
            commandType = build {
                source = file {
                    path = """context/generated/linux/Server/Ubuntu/%hostedLinuxVersion%/Dockerfile"""
                }
                contextDir = "context"
                namesAndTags = "teamcity-server-staging:%dockerImage.teamcity.buildNumber%"
            }
            param("dockerImage.platform", "linux")
        }

        dockerCommand {
            name = "tag teamcity-server-staging"
            commandType = other {
                subCommand = "tag"
                commandArgs = "teamcity-server-staging:%dockerImage.teamcity.buildNumber% %docker.buildRepository%teamcity-server-staging:%dockerImage.teamcity.buildNumber%"
            }
        }

        dockerCommand {
            name = "push teamcity-server-staging"
            commandType = push {
                namesAndTags = "%docker.buildRepository%teamcity-server-staging:%dockerImage.teamcity.buildNumber%"
            }
        }
    }
    features {
        freeDiskSpace {
            requiredSpace = "4gb"
            failBuild = true
        }
        dockerSupport {
            cleanupPushedImages = false
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_774"
            }
        }
        swabra {
            forceCleanCheckout = true
        }
    }
    dependencies {
        dependency(AbsoluteId("TC2022_04_BuildDistDocker")) {
            snapshot {
                onDependencyFailure = FailureAction.IGNORE
                reuseBuilds = ReuseBuilds.ANY
            }
            artifacts {
                artifactRules = "TeamCity.zip!/**=>context/TeamCity"
            }
        }
    }
    params {
        param("system.teamcity.agent.ensure.free.space", "4gb")
    }
})