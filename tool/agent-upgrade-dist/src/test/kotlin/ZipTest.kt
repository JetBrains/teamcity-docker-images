import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ZipTest {
    @Test
    fun `rejects entries escaping extraction directory`() {
        assertUnsafeEntryRejected("../outside/pwned.txt")
    }

    @Test
    fun `rejects windows-style entries escaping extraction directory`() {
        assertUnsafeEntryRejected("..\\outside\\pwned.txt")
    }

    @Test
    fun `rejects nested windows-style parent traversal entries`() {
        assertUnsafeEntryRejected("outside\\..\\pwned.txt")
    }

    private fun assertUnsafeEntryRejected(entryName: String) {
        val workDir = Files.createTempDirectory("agent-upgrade-dist-zip-test")
        val targetDir = Files.createDirectories(workDir.resolve("target"))
        val outsideFile = Files.createDirectories(workDir.resolve("outside")).resolve("pwned.txt")

        try {
            val result = runCatching {
                ByteArrayInputStream(zipWithEntry(entryName, "pwned")).unzip()
                    .save { targetDir.resolve(it).toFile() }
                    .toList()
            }

            assertFalse(Files.exists(outsideFile), "Zip extraction must not write outside target directory")
            assertTrue(result.isFailure, "Expected unsafe zip entry to be rejected")
        } finally {
            workDir.toFile().deleteRecursively()
        }
    }

    private fun zipWithEntry(entryName: String, content: String): ByteArray {
        val output = ByteArrayOutputStream()
        ZipOutputStream(output).use { zip ->
            zip.putNextEntry(ZipEntry(entryName))
            zip.write(content.toByteArray(Charsets.UTF_8))
            zip.closeEntry()
        }
        return output.toByteArray()
    }
}
