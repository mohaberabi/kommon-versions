# KommonVersions (KMP Common Versions Plugin)

### A Unified Versioning, Flavor, and Build Metadata System for Kotlin Multiplatform (Android + iOS)

This repository contains a **custom Gradle convention plugin** that provides a **single source of
truth** for versioning, flavors, schemas, application IDs, and runtime build metadata across *
*Android** and **iOS** in Kotlin Multiplatform projects.

Instead of manually maintaining:

- Android BuildConfig
- iOS Info.plist keys
- xcconfig files
- duplicated version codes
- flavor/schema naming rules
- separate app names
- custom suffixes per environment

…this plugin generates **everything automatically** based on a single clean DSL:

```kotlin
kommonVersions {
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
