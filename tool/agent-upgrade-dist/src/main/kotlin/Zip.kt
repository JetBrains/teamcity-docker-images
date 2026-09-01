import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

fun File.unzip() = FileInputStream(this).unzip()

fun InputStream.unzip(): Sequence<ZipFile> =
    sequence {
        val unzippedStream = ZipInputStream(BufferedInputStream(this@unzip))
        unzippedStream.use {
            while (true) {
                it.nextEntry?.let { yield(ZipFile(it, unzippedStream)) } ?: break
            }
        }
    }

fun Sequence<ZipFile>.save(targetResolver: (relativePath: String) -> File) = this.map { it.save(targetResolver) }

fun ZipFile.save(targetResolver: (relativePath: String) -> File): File {
    val relativePath = this.entry.name.requireSafeZipEntryName()

    val file = targetResolver(relativePath)
    if (this.entry.isDirectory) {
        file.ensureDirExists("", true)
    } else {
        if (file.exists()) {
            file.delete()
        }

        BufferedOutputStream(FileOutputStream(file)).use { outputStream ->
            this.stream.copyTo(outputStream)
            outputStream.flush()
        }
    }

    return file
}

private fun String.requireSafeZipEntryName(): String {
    if (!isSafeZipEntryName()) {
        throw IllegalArgumentException("ZIP entry escapes extraction directory: $this")
    }
    return this
}

private fun String.isSafeZipEntryName(): Boolean {
    val normalizedName = replace('\\', '/')
    return normalizedName != ".." &&
            !normalizedName.startsWith("../") &&
            !normalizedName.contains("/../") &&
            !normalizedName.endsWith("/..") &&
            !normalizedName.startsWith("/") &&
            !normalizedName.contains("//") &&
            !normalizedName.containsWindowsDriveSegment()
}

private val WINDOWS_DRIVE_SEGMENT = Regex("""(^|/)[A-Za-z]:""")

private fun String.containsWindowsDriveSegment() = WINDOWS_DRIVE_SEGMENT.containsMatchIn(this)

data class ZipFile(val entry: ZipEntry, val stream: InputStream)
