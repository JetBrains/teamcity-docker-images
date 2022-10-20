package generated

import common.TeamCityDockerImagesRepo.TeamCityDockerImagesRepo
import jetbrains.buildServer.configs.kotlin.*

object BuildAndPushHosted : BuildType({
    name = "Build and push for teamcity.jetbrains.com"

    buildNumberPattern = "%dockerImage.teamcity.buildNumber%-%build.counter%"
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(TeamCityDockerImagesRepo)
    }
    steps {

        kotlinFile {
            path = "tool/automation/ImageValidation.kts"
            arguments = "%docker.buildRepository%teamcity-server-staging:%dockerImage.teamcity.buildNumber% %docker.buildRepository%teamcity-server-staging:%dockerImage.teamcity.buildNumber-1%"
        }
    }

    requirements {}
    features {}
})
