// NOTE: THIS IS A MOCK BUILD CONFIGURATION THAT'S USED AS A BASELINE FOR DEVELOPMENT OF ...
// ... DOCKER IMAGE RELEASE PROCESS ENHANCEMENTS. AS FOR NOW, THE FILE ITSELF IS NOT ...
// ... USED ANYWHERE.
package generated

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object image_validation: BuildType({
    name = "Validation (post-push) of Docker images"
    steps {
        script {
            scriptContent = "echo 'Hello world!'"
        }
    }
})
