package automation.docker.hub.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigInteger


@Serializable
class DockerImage {
    lateinit var architecture: String
    lateinit var os: String
    lateinit var digest: String
    @Contextual
    lateinit var size: String
}
