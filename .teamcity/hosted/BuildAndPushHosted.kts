package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.buildSteps.DockerCommandStep
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object BuildAndPushHosted : BuildType({
    name = "Build and push for teamcity.jetbrains.com"

    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"

    params {
        param("system.teamcity.agent.ensure.free.space", "4gb")
    }

    vcs {
        root(TeamCityDockerImagesRepo)
    }
    steps {

        kotlinFile {
            path = "tool/automation/ImageValidation.kts"
            arguments = "%docker.buildRepository%teamcity-server-staging:%dockerImage.teamcity.buildNumber% %docker.buildRepository%teamcity-server-staging:%dockerImage.teamcity.buildNumber-1%"
        }
    }

})
