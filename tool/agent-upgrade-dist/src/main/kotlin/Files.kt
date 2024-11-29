import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.nio.file.Paths

fun File.dir(): Sequence<File> = this.listFiles()?.asSequence() ?: emptySequence()

fun File.ensureDirExists(description: String = "", createIfNotExists: Boolean = false): File {
    if (this.exists()) {
        if (!this.isDirectory) {
            throw Exception("\"$this\" - is not a directory.")
        }
        return Paths.get(path).toRealPath().toFile()
    }

    if (createIfNotExists) {
        this.mkdirs()
        if (description.isNotBlank()) {
            println("$description directory: ${this.canonicalFile}")
        }
        return Paths.get(path).toRealPath().toFile()
    }

    throw Exception("\"$this\" does not exist")
}

fun InputStream.buffered(bufferSize: Long): InputStream {
    val buffer = ByteArrayOutputStream(bufferSize.toInt())
    this.copyTo(buffer)
    return ByteArrayInputStream(buffer.toByteArray())
}