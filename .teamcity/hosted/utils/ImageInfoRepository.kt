package hosted.utils

import hosted.utils.models.ImageInfo

class ImageInfoRepository {
    companion object {

        /**
         * Returns a set of Ubuntu 20.04 (amd64)-based TeamCity Docker Images
         */
        fun getAmdImages(): Set<ImageInfo> {
            return linkedSetOf(
                // Minimal Agents
                ImageInfo(
                    "teamcity-minimal-agent:%tc.image.version%-linux",
                    "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile",
                    "teamcity-minimal-agent:%tc.image.version%-linux",
                    "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:%tc.image.version%-linux"
                ),

                // Regular Agents
                ImageInfo(
                    "teamcity-agent:%tc.image.version%-linux",
                    "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile",
                    "teamcity-agent:%tc.image.version%-linux",
                    "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-linux"
                ),
                ImageInfo(
                    "teamcity-agent:%tc.image.version%-linux-sudo",
                    "context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile",
                    "teamcity-agent:%tc.image.version%-linux-sudo",
                    "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-linux-sudo"
                ),

                // Servers
                ImageInfo(
                    "teamcity-server:%tc.image.version%-linux",
                    "context/generated/linux/Server/Ubuntu/20.04/Dockerfile",
                    "teamcity-server:%tc.image.version%-linux",
                    "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:%tc.image.version%-linux"
                )
            )
        }

        /**
         * Returns a set of Ubuntu 20.04 (aarch64)-based TeamCity Docker Images
         */
        fun getArmImages(): Set<ImageInfo> {
            return linkedSetOf(
                // Minimal Agents
                ImageInfo(
                    "teamcity-minimal-agent:%tc.image.version%-linux-arm64",
                    "context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile",
                    "teamcity-minimal-agent:%tc.image.version%-linux-arm64",
                    "%docker.buildRepository%teamcity-minimal-agent%docker.buildImagePostfix%:%tc.image.version%-linux-arm64"
                ),

                // Regular Agents
                ImageInfo(
                    "teamcity-agent:%tc.image.version%-linux-arm64",
                    "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile",
                    "teamcity-agent:%tc.image.version%-linux-arm64",
                    "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-linux-arm64"
                ),
                ImageInfo(
                    "teamcity-agent:%tc.image.version%-linux-arm64-sudo",
                    "context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile",
                    "teamcity-agent:%tc.image.version%-linux-arm64-sudo",
                    "%docker.buildRepository%teamcity-agent%docker.buildImagePostfix%:%tc.image.version%-linux-arm64-sudo"
                ),

                // Servers
                ImageInfo(
                    "teamcity-server:%tc.image.version%-linux-arm64",
                    "context/generated/linux/Server/UbuntuARM/20.04/Dockerfile",
                    "teamcity-server:%tc.image.version%-linux-arm64",
                    "%docker.buildRepository%teamcity-server%docker.buildImagePostfix%:%tc.image.version%-linux-arm64"
                )
            )
        }
    }
}