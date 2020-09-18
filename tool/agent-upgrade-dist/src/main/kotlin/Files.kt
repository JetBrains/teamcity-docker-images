import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

fun File.dir(): Sequence<File> = this.listFiles()?.asSequence() ?: emptySequence()

fun File.ensureDirExists(description: String = "", createIfNotExists: Boolean = false): File {
    if (this.isDirectory()) {
        if (description.isNotBlank()) {
            println("$description directory: ${this.canonicalFile}")
        }
        return this.canonicalFile
    }

    if (!this.exists()) {
        if (createIfNotExists) {
            this.mkdirs();
            if (description.isNotBlank()) {
                println("$description directory: ${this.canonicalFile}")
            }
            return this.canonicalFile
        }

        throw Exception("Cannot find directory \"$this\".");
    }

    throw Exception("\"$this\" - is not a directory.");
}

fun InputStream.buffered(bufferSize: Long): InputStream {
    val buffer = ByteArrayOutputStream(bufferSize.toInt())
    this.copyTo(buffer)
    return ByteArrayInputStream(buffer.toByteArray())
}