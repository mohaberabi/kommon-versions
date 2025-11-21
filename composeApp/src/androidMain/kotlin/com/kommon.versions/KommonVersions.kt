package com.kommon.versions

import com.mohaberabi.buildversionkmp.BuildConfig

actual object KommonVersions {
    actual val APPLICATION_ID: String = BuildConfig.APPLICATION_ID
    actual val BUILD_TYPE: String = BuildConfig.BUILD_TYPE
    actual val VERSION_CODE: Int = BuildConfig.VERSION_CODE
    actual val VERSION_NAME: String = BuildConfig.VERSION_NAME
    actual val FLAVOR: String = BuildConfig.FLAVOR
}