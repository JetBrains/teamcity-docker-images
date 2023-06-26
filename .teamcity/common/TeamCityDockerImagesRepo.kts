package common

import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object TeamCityDockerImagesRepo : GitVcsRoot({
    name = "TeamCity Docker Images"
    url = "https://github.com/JetBrains/teamcity-docker-images.git"
    branch = "refs/heads/%teamcity.branch%"
})
