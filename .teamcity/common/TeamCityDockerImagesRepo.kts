package common

import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object TeamCityDockerImagesRepo : GitVcsRoot({
    name = "TeamCity Docker Images"
    url = "https://github.com/JetBrains/teamcity-docker-images.git"
    branch = "TEAMCITY-QA-T-84-3-change-target-image-to-staging"
})