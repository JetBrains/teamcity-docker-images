package hosted.scheduled.build.model

data class DockerImageInfo(val repository: String, val tag: String, val dockerfilePath: String)
