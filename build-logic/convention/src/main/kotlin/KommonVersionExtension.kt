import com.android.build.api.dsl.ApplicationExtension
import constants.androidGeneratedTemplate
import constants.commonGeneratedTemplate
import constants.iosGeneratedTemplate
import extensions.configureAndroid
import extensions.generateFile
import extensions.registerIosTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue

const val EXT_NAME = "kommonVersions"
const val GENERATED_PACKAGE = "com/kommon.versions"

class KommonVersionExtension : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
            }
            val ext = extensions.create(
                EXT_NAME,
                KommonVersionsExtension::class.java,
                objects
            )

            afterEvaluate {
                val android = ext.android.orNull
                android?.let {
                    extensions.configure<ApplicationExtension>() {
                        configureAndroid(it)
                    }
                }
                val ios = ext.ios.orNull
                ios?.let { registerIosTask(it) }
                val generateTask by tasks.register(EXT_NAME) {
                    val fileName = "KommonVersions.kt"
                    val commonMain = layout.dirForSourceSet("commonMain")
                    generateFile(
                        outputDir = commonMain,
                        fileContent = commonGeneratedTemplate,
                        fileName = fileName,
                    )
                    ios?.let {
                        val iosDir = layout.dirForSourceSet("iosMain")
                        generateFile(
                            outputDir = iosDir,
                            fileName = fileName,
                            fileContent = iosGeneratedTemplate,
                        )
                    }
                    android?.let {
                        val androidDir = layout.dirForSourceSet("androidMain")
                        generateFile(
                            outputDir = androidDir,
                            fileName = fileName,
                            fileContent = androidGeneratedTemplate,
                        )
                    }
                }
            }
        }
    }


}

fun ProjectLayout.dirForSourceSet(
    sourceSet: String,
) = projectDirectory.dir("src/${sourceSet}/kotlin/${GENERATED_PACKAGE}").asFile

