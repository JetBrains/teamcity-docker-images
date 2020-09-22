import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    try {
        // TeamCity
        val serverRootDir = if (args.size > 0) File(args[0]).ensureDirExists("Server") else File(".")
        // TeamCity\buildAgent
        val agentRootDir = (if (args.size > 1) File(args[1]) else File(serverRootDir, "buildAgent")).ensureDirExists("Agent")

        // TeamCity\webapps\ROOT\WEB-INF\plugins
        val serverPluginsDir = File(serverRootDir, "webapps/ROOT/WEB-INF/plugins").ensureDirExists("Server plugins")
        // TeamCity\buildAgent\plugins
        val agentPluginsDir = File(agentRootDir, "plugins").ensureDirExists("Agent plugins", true)
        // TeamCity\buildAgent\tools
        val agentToolsDir = File(agentRootDir, "tools").ensureDirExists("Agent tools", true)
        // TeamCity\buildAgent\system\.teamcity-agent
        val agentMetadataDir = File(agentRootDir, "system/.teamcity-agent").ensureDirExists("Agent metadata", true)
        // TeamCity2\webapps\ROOT\update\buildAgent.zip
        val agentUpdateFile = File(serverRootDir, "webapps/ROOT/update/buildAgent.zip")

        val pluginsSource=
                // TeamCity\webapps\ROOT\WEB-INF\plugins
                serverPluginsDir
                // dir TeamCity\webapps\ROOT\WEB-INF\plugins
                .dir()
                .mapNotNull { file ->
                    println("${file.name}")
                    when {

                        // TeamCity\webapps\ROOT\WEB-INF\plugins\*.zip or *.jar
                        file.isFile && isAccepted(file) -> file
                                // TeamCity\webapps\ROOT\WEB-INF\plugins\*.zip\
                                .unzip()
                                .filter { !it.entry.isDirectory && isAccepted(File(it.entry.name)) }
                                // TeamCity\webapps\ROOT\WEB-INF\plugins\*.zip\agent\ or TeamCity\webapps\ROOT\WEB-INF\plugins\*.zip\bundled*\
                                .mapNotNull { zip ->
                                    when {
                                        zip.entry.name.startsWith("agent/", true) -> PluginType.Plugin
                                        zip.entry.name.startsWith("bundled", true) -> PluginType.Bundled
                                        else -> null
                                    }?.let { pluginType ->
                                            // Copy zip stream to buffer
                                            PluginSource(pluginType, File(zip.entry.name).name, zip.stream.buffered(zip.entry.size))
                                    }
                                }

                        // TeamCity\webapps\ROOT\WEB-INF\plugins\ant
                        file.isDirectory -> file
                                .dir()
                                .filter { it.isDirectory }
                                // TeamCity\webapps\ROOT\WEB-INF\plugins\ant\agent\*.* or TeamCity\webapps\ROOT\WEB-INF\plugins\ant\bundled*\*.*
                                .mapNotNull { baseDir ->
                                    when {
                                        baseDir.name.equals("agent", true) -> PluginType.Plugin
                                        baseDir.name.startsWith("bundled", true) -> PluginType.Bundled
                                        else -> null
                                    }?.let { pluginType ->
                                        baseDir
                                                // TeamCity\webapps\ROOT\WEB-INF\plugins\ant\agent\*.*
                                                .dir()
                                                // TeamCity\webapps\ROOT\WEB-INF\plugins\ant\agent\*.zip or *.jar
                                                .filter { it.isFile && isAccepted(it) }
                                                // Copy zip file to buffer
                                                .map { zip -> FileInputStream(zip).use { PluginSource(pluginType, zip.name, it.buffered(zip.length())) } }
                                    }
                                }
                                .flatMap { it }

                        else -> emptySequence<PluginSource>()
                    }
                }
                .flatMap { it }
                .map { pluginSource ->
                    val metadata = pluginSource.zipStream.getPluginMetadata(pluginSource.pluginType)
                    val destinationDir = if (metadata.type == PluginType.Plugin) agentPluginsDir else agentToolsDir
                    val name = File(pluginSource.name).nameWithoutExtension
                    val files = pluginSource
                            .zipStream
                            .unzip()
                            .save() { relativePath ->
                                val pluginDir = if (File(relativePath).startsWith(File(name))) destinationDir else File(destinationDir, name)
                                val file = File(pluginDir.ensureDirExists("", true), relativePath)
                                file
                            }

                    Plugin(pluginSource.name, File(destinationDir.relativeTo(agentRootDir), name), metadata, files)
                }

        tryGetAgentUpdateMetadata(agentRootDir, agentUpdateFile)?.let {
            val plugins = pluginsSource.toList()
            for (plugin in plugins) {
                if (plugin.metadata.type != PluginType.Bundled) {
                    // Copy plugin to agent
                    plugin.copy()
                }
            }

            saveMetadata(agentMetadataDir, it, plugins)
            exitProcess(0)
        }
    } catch (error: Throwable) {
        println(error.message)
    }

    exitProcess(1)
}

private fun isAccepted(file: File) =
        "zip".equals(file.extension, true) ||
                "jar".equals(file.extension, true)

private data class PluginSource(val pluginType: PluginType, val name: String, val zipStream: InputStream)
