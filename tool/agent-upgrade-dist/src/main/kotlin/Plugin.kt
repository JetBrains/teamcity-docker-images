import java.io.File

class Plugin(val name: String, val metadata: PluginMetadata, private val _files: Sequence<File>) {
    fun copy() =
        // Materialize files to copy plugin
        _files.toList()
}