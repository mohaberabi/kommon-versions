import model.android
import model.flavor
import model.ios
import model.schema
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.common.version)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        iosMain {}
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        commonMain {
            kotlin.srcDir(layout.buildDirectory.dir("generated/kotlin"))
        }
    }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

commonVersions {
    val appVersionCode = libs.versions.version.code.get().toIntOrNull() ?: 1
    val appVersionName = libs.versions.version.name.get()
    val appPackageName = libs.versions.application.id.get()
    android = android {
        namespace = appPackageName
        versionCode = appVersionCode
        versionName = appVersionName
        flavorDimensions += "default"
        flavors += flavor {
            name = "dev"
            dimension = "default"
            versionNameSuffix = "-dev"
            appName = "KmpVersion"
            appNameSuffix = "Develop"
            applicationIdSuffix = ".dev"
        }
        flavors += flavor {
            name = "prd"
            dimension = "default"
            appName = "KmpVersion"
        }
    }
    ios = ios {
        projectFolderName = "iosApp"
        appName = "KmpVersion"
        bundleId = appPackageName
        marketingVersion = appVersionName
        currentVersion = appVersionCode.toString()
        schemas += schema {
            bundleSuffix = "dev"
            name = "development"
            nameSuffix = "Develop"
        }
    }

}



