data class PluginMetadata(val type: PluginType, val hash: String)

enum class PluginType {
    Plugin,
    Tool,
    Bundled
}