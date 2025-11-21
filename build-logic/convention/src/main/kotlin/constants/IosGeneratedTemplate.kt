package constants

val iosGeneratedTemplate = """
    package com.kommon.versions
    
    import platform.Foundation.NSBundle
    import platform.posix.gets
    import kotlin.experimental.ExperimentalNativeApi


    @OptIn(ExperimentalNativeApi::class)
    actual object KommonVersions {

        private const val BUNDLE_IDENTIFIER = "CFBundleIdentifier"
        private const val CF_BUNDLE_VERSION_STRING = "CFBundleShortVersionString"
        private const val CF_BUNDLE_CODE = "CFBundleVersion"
        private const val APP_SCHEME = "AppScheme"

        private val bundleMap = NSBundle.mainBundle.infoDictionary
        private fun getString(
            key: String,
            default: String = ""
        ): String = (bundleMap?.get(key) as? String) ?: default

        val isDebug = Platform.isDebugBinary

    actual val APPLICATION_ID = getString(BUNDLE_IDENTIFIER)
    actual val BUILD_TYPE = if (isDebug) "debug" else "release"
    actual val VERSION_CODE = getString(CF_BUNDLE_CODE).toIntOrNull() ?: 1
    actual val VERSION_NAME = getString(CF_BUNDLE_VERSION_STRING)
    actual val FLAVOR = getString(APP_SCHEME)

    }

""".trimIndent()