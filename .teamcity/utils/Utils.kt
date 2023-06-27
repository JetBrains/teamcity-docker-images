package utils

import utils.models.ImageInfo

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

        /**
         * Creates sample docker-compose manifest and returns file that matches it.
         */
        fun getSampleComposeFile(repo: String, version: String, namePostfix: String = "", platform: String = "amd"): String {
            val isArmArch = (platform.lowercase().contains("arm") || platform.lowercase().contains("arch"))
            val dockerPlatformId = if (isArmArch) "linux/arm64" else "linux/amd64"
            return """
                version: "3.3"
                services:
                  linux-server:

                    image: ${repo}teamcity-server${namePostfix}:${version}-linux
                    platform: $dockerPlatformId
                    
                    privileged: true
                    user: root

                    environment:
                      - TEAMCITY_SERVER_OPTS="-Dteamcity.startup.maintenance=false -Dteamcity.csrf.origin.check.enabled=logOnly"
                      - TEAMCITY_SERVER_MEM_OPTS="-Xmx2048m"
                    ports:
                      - "8111:8111"
                      
                    volumes:
                      - ./docker-images-test/data/server:/data/teamcity_server/datadir
                      - ./docker-images-test/logs/server:/opt/teamcity/logs
                      - ./docker-images-test/temp/server:/opt/teamcity/temp

                    healthcheck:
                      test: grep "TeamCity initialized" /opt/teamcity/logs/teamcity-server.log || exit 1
                      interval: 1m
                      timeout: 15m
                      retries: 16


                  linux-agent:
                    image: ${repo}teamcity-agent${namePostfix}:${version}-linux
                    platform: $dockerPlatformId
                   
                    depends_on:
                      - linux-server

                    privileged: true
                    user: root

                    environment:
                      - SERVER_URL=http://linux-server:8111
                      - AGENT_NAME=tc-agent-linux-regular
                      - TEAMCITY_AGENT_EC2_DISABLE=true
                    volumes:
                      - ./docker-images-test/data/agent:/data/teamcity_agent/conf
                      - ./docker-images-test/logs/agent:/opt/buildagent/logs
                      - ./docker-images-test/temp/agent:/opt/buildagent/temp

                    healthcheck:
                      # Plugin initialization should be completed at this point
                      test: grep "Build Agent version" /opt/buildagent/logs/teamcity-agent.log || exit 1
                      interval: 1m
                      timeout: 15m
                      retries: 16

                  linux-minimal-agent:
                    image: ${repo}teamcity-minimal-agent:${version}-linux
                    platform: $dockerPlatformId

                    privileged: true
                    user: root

                    depends_on:
                      - linux-server

                    deploy:
                      resources:
                        limits:
                          memory: 2000m
                        reservations:
                          memory: 2000m
                    environment:
                      - SERVER_URL=http://linux-server:8111
                      - AGENT_NAME=tc-agent-linux-minimal
                      - TEAMCITY_AGENT_EC2_DISABLE=true

                    volumes:
                      - ./docker-images-test/data/min-agent:/data/teamcity_agent/conf
                      - ./docker-images-test/logs/min-agent:/opt/buildagent/logs
                      - ./docker-images-test/temp/min-agent:/opt/buildagent/temp

                    healthcheck:
                      # Plugin initialization should be completed at this point
                      test: grep "Build Agent version" /opt/buildagent/logs/teamcity-agent.log || exit 1
                      interval: 1m
                      timeout: 15m
                      retries: 16
            """
        }
    }
}
