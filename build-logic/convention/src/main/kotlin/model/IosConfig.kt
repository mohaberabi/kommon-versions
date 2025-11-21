package model

import CommonVersionsExtension

data class IosSchemaConfig(
    val schemaName: String,
    val schemaAppNameSuffix: String,
    val schemaBundleSuffix: String,
)

class IosSchemaBuilder {
    var name: String = ""
    var nameSuffix: String = ""
    var bundleSuffix: String = ""
}

fun IosConfigBuilder.schema(
    block: IosSchemaBuilder.() -> Unit
): IosSchemaConfig {
    val builder = IosSchemaBuilder().apply(block)
    return IosSchemaConfig(
        schemaName = builder.name,
        schemaBundleSuffix = builder.bundleSuffix,
        schemaAppNameSuffix = builder.nameSuffix
    )
}

data class IosConfig(
    val projectFolderName: String = "iosApp",
    val appName: String = "",
    val teamId: String = "",
    val bundleId: String = "",
    val schemas: List<IosSchemaConfig> = listOf(),
    val marketingVersion: String = "",
    val currentVersion: String = ""
)

class IosConfigBuilder {
    var projectFolderName: String = "iosApp"
    var appName: String = ""
    var teamId: String = ""
    var bundleId: String = ""
    val schemas: MutableList<IosSchemaConfig> = mutableListOf()
    var marketingVersion: String = ""
    var currentVersion: String = ""
}

fun CommonVersionsExtension.ios(
    block: IosConfigBuilder.() -> Unit
): IosConfig {
    val builder = IosConfigBuilder().apply(block)
    return IosConfig(
        projectFolderName = builder.projectFolderName,
        appName = builder.appName,
        teamId = builder.teamId,
        bundleId = builder.bundleId,
        schemas = builder.schemas,
        currentVersion = builder.currentVersion,
        marketingVersion = builder.marketingVersion
    )
}