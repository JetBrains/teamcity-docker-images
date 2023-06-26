package hosted

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import hosted.utils.dsl.general.teamCityBuildDistDocker
import hosted.utils.dsl.general.teamCityImageBuildFeatures
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
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
        teamCityImageBuildFeatures(requiredSpaceGb = 4)
    }

    dependencies {
        teamCityBuildDistDocker()
    }
})
