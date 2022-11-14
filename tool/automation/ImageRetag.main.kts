@file:Import("common/OsUtilities.kts")
@file:Import("docker/DockerImageValidationException.kts")

fun main(args: Array<String>) {
    if (args.size < 4) {
        throw IllegalArgumentException("Not enough CLI arguments.")
    }
    // names divided by comma
    val imageNames = args[0]
    val newRegistry = args[1]
    val oldTagPrefix = args[2]
    val newTagPrefix = args[3]

    print("Changing " + imageNames + " to " + newRegistry + " from tag " + oldTagPrefix + " to new tag " + newTagPrefix)
}

main(args)
