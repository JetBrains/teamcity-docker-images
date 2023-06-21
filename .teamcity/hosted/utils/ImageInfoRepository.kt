package hosted.utils

import hosted.utils.models.ImageInfo

class ImageInfoRepository {
    companion object {

        /**
         * Returns a set of Ubuntu 20.04 (amd64)-based TeamCity Docker Images
         */
        fun getAmdImages(repo: String = "%docker.buildRepository%",
                         version: String = "%tc.image.version%",
                         deployTag: String = "EAP",
                         namePostfix: String = "%docker.buildImagePostfix%"): Set<ImageInfo> {
            return linkedSetOf(
                // Minimal Agents
                ImageInfo(
                    "teamcity-minimal-agent:${version}-linux",
                    "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile",
                    "teamcity-minimal-agent:${deployTag}-linux",
                    "${repo}teamcity-minimal-agent${namePostfix}:${version}-linux"
                ),

                // Regular Agents
                ImageInfo(
                    "teamcity-agent:${version}-linux",
                    "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile",
                    "teamcity-agent:${deployTag}-linux",
                    "${repo}teamcity-agent${namePostfix}:${version}-linux"
                ),
                ImageInfo(
                    "teamcity-agent:${version}-linux-sudo",
                    "context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile",
                    "teamcity-agent:${deployTag}-linux-sudo",
                    "${repo}teamcity-agent${namePostfix}:${version}-linux-sudo"
                ),

                // Servers
                ImageInfo(
                    "teamcity-server:${version}-linux",
                    "context/generated/linux/Server/Ubuntu/20.04/Dockerfile",
                    "teamcity-server:${deployTag}-linux",
                    "${repo}teamcity-server${namePostfix}:${version}-linux"
                )
            )
        }

        /**
         * Returns a set of Ubuntu 20.04 (aarch64)-based TeamCity Docker Images
         */
        fun getArmImages(repo: String = "%docker.buildRepository%",
                         version: String = "%tc.image.version%",
                         deployTag: String = "EAP",
                         namePostfix: String = "%docker.buildImagePostfix%"): Set<ImageInfo> {
            return linkedSetOf(
                // Minimal Agents
                ImageInfo(
                    "teamcity-minimal-agent:${version}-linux-arm64",
                    "context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile",
                    "teamcity-minimal-agent:${deployTag}-linux-arm64",
                    "${repo}teamcity-minimal-agent${namePostfix}:${version}-linux-arm64"
                ),

                // Regular Agents
                ImageInfo(
                    "teamcity-agent:${version}-linux-arm64",
                    "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile",
                    "teamcity-agent:${deployTag}-linux-arm64",
                    "${repo}teamcity-agent${namePostfix}:${version}-linux-arm64"
                ),
                ImageInfo(
                    "teamcity-agent:${version}-linux-arm64-sudo",
                    "context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile",
                    "teamcity-agent:${deployTag}-linux-arm64-sudo",
                    "${repo}teamcity-agent${namePostfix}:${version}-linux-arm64-sudo"
                ),

                // Servers
                ImageInfo(
                    "teamcity-server:${version}-linux-arm64",
                    "context/generated/linux/Server/UbuntuARM/20.04/Dockerfile",
                    "teamcity-server:${deployTag}-linux-arm64",
                    "${repo}teamcity-server${namePostfix}:${version}-linux-arm64"
                )
            )
        }
    }
}
