package common

import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/**
 * teamcity-docker-images that allow execution from each branch. The use must be limited to development ...
 * ... configurations only.
 */
object TeamCityDockerImagesRepo_AllBranches: GitVcsRoot({
    name = "TeamCity Docker Images - All Branches"
    url = "https://github.com/JetBrains/teamcity-docker-images.git"
    branchSpec = "+:refs/heads/*"
    branch = "refs/heads/%teamcity.branch%"
})