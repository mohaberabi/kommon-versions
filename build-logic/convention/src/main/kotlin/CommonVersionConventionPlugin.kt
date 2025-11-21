import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.internal.lint.KotlinMultiplatformExtensionWrapper
import com.android.tools.r8.internal.ba
import constants.androidGeneratedTemplate
import constants.commonGeneratedTemplate
import constants.iosGeneratedTemplate
import extensions.configureAndroid
import extensions.generateFile
import extensions.registerIosTask
import model.AndroidConfig
import model.IosConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getValue
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.io.File

const val EXT_NAME = "commonVersions"

const val GENERATED_PACKAGE = "com/kommon.versions"

class CommonVersionConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
            }
            val ext = extensions.create(
                EXT_NAME,
                CommonVersionsExtension::class.java,
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
                configureKmpVersion(ios = ios, android = android)
                val generateTask by tasks.register("generateKommon") {
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

fun DirectoryProperty.forSourceSet(sourceSet: String) =
    dir("generated/kotlin/$sourceSet").get().asFile

fun Project.configureKmpVersion(
    ios: IosConfig? = null,
    android: AndroidConfig? = null
) {
    extensions.configure<KotlinMultiplatformExtension>() {
        val buildDir = layout.buildDirectory.dir("generated/kotlin")
        val commonMain = sourceSets["commonMain"] ?: return@configure
        commonMain.kotlin.srcDir(buildDir)
        android?.let {
            val androidMain = sourceSets["androidMain"] ?: return@let
            androidMain.kotlin.srcDir(buildDir)
        }
        ios?.let {
            val iosMain = sourceSets["iosMain"] ?: return@let
            iosMain.kotlin.srcDir(buildDir)
        }
    }
}

