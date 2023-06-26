package hosted.utils

import hosted.utils.models.ImageInfo

class ImageInfoRepository {
    companion object {

        /**
         * Returns tags of all expected TeamCity Server instances.
         */
        fun getAllServerTags(version: String = "%tc.image.version%"): List<String> {
            return listOf(
                "$version-linux",
                "$version-nanoserver-1809",
                "$version-nanoserver-2004",
                "$version-linux-arm64"
            )
        }

        /**
         * Returns tags of all expected TeamCity Agent instances.
         */
        fun getAllAgentTags(version: String): List<String> {
            return listOf(
                "$version-linux",
                "$version-nanoserver-1809",
                "$version-nanoserver-2004 ",
                "$version-linux-arm64"
            )
        }

        /**
         * Returns tags for WindowsServerCore TeamCity Agents.
         * Usually, they must match WinServerCore manifest, e.g. 'teamcity-agent:EAP-windowsservercore'
         */
        fun getWindowsCoreAgentTags(version: String): List<String> {
            return listOf(
                "${version}-windowsservercore-1809",
                "${version}-windowsservercore-2004"
            )
        }

        /**
         * Returns the tags of all expected TeamCity Minimal Agent instances.
         */
        fun getAllMinimalAgentTags(version: String): List<String> {
            // for now, the tags are equal to regular agent's
            return getAllAgentTags(version)
        }

        /**
         * Returns a set of Ubuntu 20.04 (amd64)-based TeamCity Docker Images
         */
        fun getAmdImages(
            stagingRepo: String = "%docker.buildRepository%",
            version: String = "%tc.image.version%",
            deployTag: String = "%tc.image.version%",
            namePostfix: String = "%docker.buildImagePostfix%",
            prodRepo: String = "%docker.deployRepository%"
        ): Set<ImageInfo> {
            return linkedSetOf(
                // Minimal Agents
                ImageInfo(
                    "teamcity-minimal-agent:${version}-linux",
                    "context/generated/linux/MinimalAgent/Ubuntu/20.04/Dockerfile",
                    "teamcity-minimal-agent:${deployTag}-linux",
                    "${stagingRepo}teamcity-minimal-agent${namePostfix}:${version}-linux",
                    "${prodRepo}teamcity-minimal-agent:${version}-linux",
                ),

                // Regular Agents
                ImageInfo(
                    "teamcity-agent:${version}-linux",
                    "context/generated/linux/Agent/Ubuntu/20.04/Dockerfile",
                    "teamcity-agent:${deployTag}-linux",
                    "${stagingRepo}teamcity-agent${namePostfix}:${version}-linux",
                    "${prodRepo}teamcity-agent:${version}-linux"
                ),
                ImageInfo(
                    "teamcity-agent:${version}-linux-sudo",
                    "context/generated/linux/Agent/Ubuntu/20.04-sudo/Dockerfile",
                    "teamcity-agent:${deployTag}-linux-sudo",
                    "${stagingRepo}teamcity-agent${namePostfix}:${version}-linux-sudo",
                    "${prodRepo}teamcity-agent:${version}-linux-sudo"
                ),

                // Servers
                ImageInfo(
                    "teamcity-server:${version}-linux",
                    "context/generated/linux/Server/Ubuntu/20.04/Dockerfile",
                    "teamcity-server:${deployTag}-linux",
                    "${stagingRepo}teamcity-server${namePostfix}:${version}-linux",
                    "${prodRepo}teamcity-server:${version}-linux"
                )
            )
        }

        /**
         * Returns a set of Ubuntu 20.04 (aarch64)-based TeamCity Docker Image.
         *
         */
        fun getArmImages(
            stagingRepo: String = "%docker.buildRepository%",
            version: String = "%tc.image.version%",
            deployTag: String = "%tc.image.version%",
            namePostfix: String = "%docker.buildImagePostfix%",
            prodRepo: String = "%docker.deployRepository%"
        ): Set<ImageInfo> {
            return linkedSetOf(
                // Minimal Agents
                ImageInfo(
                    "teamcity-minimal-agent:${version}-linux-arm64",
                    "context/generated/linux/MinimalAgent/UbuntuARM/20.04/Dockerfile",
                    "teamcity-minimal-agent:${deployTag}-linux-arm64",
                    "${stagingRepo}teamcity-minimal-agent${namePostfix}:${version}-linux-arm64",
                    "${prodRepo}teamcity-minimal-agent:${version}-linux-arm64"
                ),

                // Regular Agents
                ImageInfo(
                    "teamcity-agent:${version}-linux-arm64",
                    "context/generated/linux/Agent/UbuntuARM/20.04/Dockerfile",
                    "teamcity-agent:${deployTag}-linux-arm64",
                    "${stagingRepo}teamcity-agent${namePostfix}:${version}-linux-arm64",
                    "${prodRepo}teamcity-agent:${version}-linux-arm64"

                ),
                ImageInfo(
                    "teamcity-agent:${version}-linux-arm64-sudo",
                    "context/generated/linux/Agent/UbuntuARM/20.04-sudo/Dockerfile",
                    "teamcity-agent:${deployTag}-linux-arm64-sudo",
                    "${stagingRepo}teamcity-agent${namePostfix}:${version}-linux-arm64-sudo",
                    "${prodRepo}teamcity-agent:${version}-linux-arm64-sudo"
                ),

                // Servers
                ImageInfo(
                    "teamcity-server:${version}-linux-arm64",
                    "context/generated/linux/Server/UbuntuARM/20.04/Dockerfile",
                    "teamcity-server:${deployTag}-linux-arm64",
                    "${stagingRepo}teamcity-server${namePostfix}:${version}-linux-arm64",
                    "${prodRepo}teamcity-server:${version}-linux-arm64"
                )
            )
        }

        /**
         */
        fun getWindowsImages2004(
            stagingRepo: String = "%docker.buildRepository%",
            version: String = "%tc.image.version%",
            deployTag: String = "%tc.image.version%",
            namePostfix: String = "%docker.buildImagePostfix%",
            prodRepo: String = "%docker.deployRepository%"
        ): Set<ImageInfo> {
            return linkedSetOf(
                // Servers
                ImageInfo(
                    "teamcity-server:${version}-nanoserver-2004",
                    "context/generated/windows/Server/nanoserver/2004/Dockerfile",
                    "teamcity-server:${deployTag}-nanoserver-2004",
                    "${stagingRepo}teamcity-server${namePostfix}:${version}-nanoserver-2004",
                    "${prodRepo}teamcity-server:${version}-nanoserver-2004\""
                ),

                // Minimal Agent - nanoserver
                ImageInfo(
                    "teamcity-minimal-agent:${version}-nanoserver-2004",
                    "context/generated/windows/MinimalAgent/nanoserver/2004/Dockerfile",
                    "teamcity-minimal-agent:${deployTag}-nanoserver-2004",
                    "${stagingRepo}teamcity-minimal-agent${namePostfix}:${version}-nanoserver-2004",
                    "${prodRepo}teamcity-minimal-agent:${version}-nanoserver-2004"
                ),

                // Agents
                // Windows Server Core
                ImageInfo(
                    "teamcity-agent:${version}-windowsservercore-2004",
                    "context/generated/windows/Agent/windowsservercore/2004/Dockerfile",
                    "teamcity-agent:${deployTag}-windowsservercore-2004",
                    "${stagingRepo}teamcity-agent${namePostfix}:${version}-windowsservercore-2004",
                    "${prodRepo}teamcity-agent:${version}-windowsservercore-2004"
                ),

                // Nanoserver
                ImageInfo(
                    "teamcity-agent:${version}-nanoserver-2004",
                    "context/generated/windows/Agent/nanoserver/2004/Dockerfile",
                    "teamcity-agent:${deployTag}-nanoserver-2004",
                    "${stagingRepo}teamcity-agent${namePostfix}:${version}-nanoserver-2004",
                    "${prodRepo}teamcity-agent${namePostfix}:${version}-nanoserver-2004"
                )
            )
        }
    }
}
