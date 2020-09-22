import org.w3c.dom.NodeList
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.InputStream
import java.security.MessageDigest
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory


fun File.computeHashCode(): String =
    FileInputStream(this).use {
        return it.computeHashCode()
    }

fun InputStream.computeHashCode(): String {
    val buff = ByteArray(65536)
    val digest: MessageDigest = MessageDigest.getInstance("SHA-1")
    digest.update(byteArrayOf(0x05, 0x06, 87))
    var count: Int
    while (this.read(buff).also { count = it } > 0) {
        digest.update(buff, 0, count)
    }

    return "SHA-1" + String(org.apache.commons.codec.binary.Base64().encode(digest.digest()))
}

fun InputStream.getPluginMetadata(pluginType: PluginType): PluginMetadata {
    val hash = this.computeHashCode()
    this.reset()

    try {
        return this
            .unzip()
            .filter { "teamcity-plugin.xml".equals(File(it.entry.name).name, true) }
            .firstOrNull()
            ?.let {
                val docBuilder =  DocumentBuilderFactory.newInstance().newDocumentBuilder()
                val doc = docBuilder.parse(it.stream)
                var xpath = XPathFactory.newInstance().newXPath()
                val pluginDeployment = xpath.evaluate("/teamcity-agent-plugin/plugin-deployment", doc, XPathConstants.NODESET) as NodeList
                val toolDeployment = xpath.evaluate("/teamcity-agent-plugin/tool-deployment", doc, XPathConstants.NODESET) as NodeList
                when {
                    pluginDeployment.length == 1 -> PluginMetadata(PluginType.Plugin, hash)
                    toolDeployment.length == 1 -> PluginMetadata(if (pluginType == PluginType.Bundled) PluginType.Bundled else PluginType.Tool, hash)
                    else -> null
                }
            }
            ?: PluginMetadata(pluginType, hash)
    } finally {
        this.reset()
    }
}

fun tryGetAgentUpdateMetadata(agentRootDir: File, agentFile: File) =
        if (agentFile.isFile)
            agentRootDir
                    .dir()
                    .filter { it.isFile }
                    .filter { it.name.startsWith("BUILD_") }
                    .firstOrNull()
                    ?.name?.substring("BUILD_".length)
                    ?.toInt()
                    ?.toString()
                    ?.let { AgentUpdateMetadata(it, agentFile.computeHashCode()) }
        else
            null

fun saveMetadata(destinationDir: File, agentUpdateMetadata: AgentUpdateMetadata, plugins: List<Plugin>) {
     val doc = DocumentBuilderFactory
            .newInstance()
            .newDocumentBuilder()
            .newDocument()

    doc.xmlStandalone = true
    doc
            .appendChild(
                    E("agent",
                            E("core",
                                    E("file")
                                            .a("name", "buildAgent.zip")
                                            .a("hash", agentUpdateMetadata.hash)
                            ),
                            E("plugins",
                                    plugins
                                            .sortedBy { it.name }
                                            .map {
                                                E("file")
                                                        .a("name", it.name)
                                                        .a("hash", it.metadata.hash)
                                                        .a("type", if (it.metadata.type != PluginType.Bundled) "PLUGIN" else "TOOL")
                                                        .a("allowLoadingOnDemand", if (it.metadata.type != PluginType.Bundled) "false" else "true")
                                            }
                                            .asSequence()
                            )
                    ).a("agent-version", agentUpdateMetadata.version))
            .ownerDocument
            .save(File(destinationDir, "teamcity-agent.xml"))

    val lineSeparator = System.getProperty("line.separator")
    FileWriter(File(destinationDir, "unpacked-plugins.xml"))
            .use { writer ->
                for (plugin in plugins.filter { it.metadata.type != PluginType.Bundled }) {
                    writer.write(plugin.name)
                    writer.write("=")
                    writer.write(plugin.path.path.replace("\\", "/"))
                    writer.write(lineSeparator)
                }
            }
}