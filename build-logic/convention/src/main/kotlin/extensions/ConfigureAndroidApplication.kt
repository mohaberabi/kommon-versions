package extensions

import com.android.build.api.dsl.ApplicationExtension
import model.AndroidConfig


fun ApplicationExtension.configureAndroid(
    androidConfig: AndroidConfig
) {
    namespace = androidConfig.namespace
    defaultConfig {
        namespace = androidConfig.namespace
        versionCode = androidConfig.versionCode
        versionName = androidConfig.versionName
    }
    flavorDimensions.addAll(androidConfig.flavorDimensions)
    productFlavors {
        androidConfig.flavors.forEach { flavor ->
            create(flavor.name) {
                dimension = flavor.dimension
                applicationIdSuffix = flavor.applicationIdSuffix
                versionNameSuffix = flavor.versionNameSuffix
                flavor.appName?.let { manifestPlaceholders["app_name"] = it }
                flavor.appNameSuffix?.let { manifestPlaceholders["app_name_suffix"] = it }
            }
        }
    }
}
