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
    var relativePath = this.entry.name
    if (relativePath.startsWith("/") || relativePath.startsWith("\\")) {
        relativePath = relativePath.substring(1)
    }

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

data class ZipFile(val entry: ZipEntry, val stream: InputStream)