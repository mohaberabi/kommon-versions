package extensions

import org.gradle.api.Task
import java.io.File

internal fun Task.generateFile(
    outputDir: File,
    fileName: String,
    fileContent: String,
) {
    val outputFile = outputDir.resolve(fileName)
    outputs.file(outputFile)
    doLast {
        outputDir.mkdirs()
        outputFile.writeText(fileContent)
    }
}
