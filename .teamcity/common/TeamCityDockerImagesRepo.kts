package common

import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object TeamCityDockerImagesRepo : GitVcsRoot({
    name = "TeamCity Docker Images"
    url = "https://github.com/AndreyKoltsov1997/teamcity-docker-images.git"
    branch = "refs/heads/TW-78090-add-docker-image-validation-test"
})