package hosted.utils

import hosted.utils.models.ImageInfo

/**
 * Utilities for the build up of Docker images.
 */
class Utils {
    companion object {
        /**
         * Returns .dockerignore context based on given image.
         * @param info information about target Docker image
         */
        fun getDockerignoreCtx(info: ImageInfo): String {
            val imageFqdn = info.name.lowercase()
            return when {
                imageFqdn.contains("minimal") -> """
                     echo TeamCity/webapps >> context/.dockerignore
                     echo TeamCity/devPackage >> context/.dockerignore
                     echo TeamCity/lib >> context/.dockerignore
                """.trimIndent()

                imageFqdn.contains("server") -> """
                    echo 2> context/.dockerignore
                    echo TeamCity/buildAgent >> context/.dockerignore
                    echo TeamCity/temp >> context/.dockerignore
                """.trimIndent()

                imageFqdn.contains("agent") -> """
                    echo 2> context/.dockerignore
		            echo TeamCity >> context/.dockerignore
                """.trimIndent()
                else -> ""
            }
        }
    }
}