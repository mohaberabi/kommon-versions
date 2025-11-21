package model

import KommonVersionsExtension

data class AndroidConfig(
    val namespace: String,
    val flavorDimensions: List<String>,
    val versionName: String,
    val versionCode: Int,
    val flavors: List<FlavorConfig>
)

data class FlavorConfig(
    val name: String,
    val dimension: String,
    val applicationIdSuffix: String,
    val versionNameSuffix: String,
    val appName: String? = null,
    val appNameSuffix: String? = null,
)

class FlavorConfigBuilder {
    var name: String = ""
    var dimension: String = ""
    var applicationIdSuffix: String = ""
    var versionNameSuffix: String = ""
    var appName: String? = null
    var appNameSuffix: String? = null
}

class AndroidConfigBuilder {
    var namespace: String = ""
    var flavorDimensions: MutableList<String> = mutableListOf()
    var versionName: String = ""
    var versionCode: Int = 0
    val flavors: MutableList<FlavorConfig> = mutableListOf()
}

fun AndroidConfigBuilder.flavor(
    block: FlavorConfigBuilder. () -> Unit
): FlavorConfig {
    val builder = FlavorConfigBuilder().apply(block)
    return FlavorConfig(
        name = builder.name,
        dimension = builder.dimension,
        applicationIdSuffix = builder.applicationIdSuffix,
        appName = builder.appName,
        appNameSuffix = builder.appNameSuffix,
        versionNameSuffix = builder.versionNameSuffix,
    )
}

fun KommonVersionsExtension.android(
    block: AndroidConfigBuilder. () -> Unit
): AndroidConfig {
    val builder = AndroidConfigBuilder().apply(block)
    return AndroidConfig(
        namespace = builder.namespace,
        versionName = builder.versionName,
        versionCode = builder.versionCode,
        flavors = builder.flavors,
        flavorDimensions = builder.flavorDimensions,
    )
}

