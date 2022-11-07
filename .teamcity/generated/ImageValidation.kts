// NOTE: THIS IS A MOCK BUILD CONFIGURATION THAT'S USED AS A BASELINE FOR DEVELOPMENT OF ...
// ... DOCKER IMAGE RELEASE PROCESS ENHANCEMENTS. AS FOR NOW, THE FILE ITSELF IS NOT ...
// ... USED ANYWHERE.
package generated

object image_validation: BuildType({
    name = "Validation (post-push) of Docker images"
    steps {
        script {
            scriptContent = "echo 'Hello world!'"
        }
    }
})
