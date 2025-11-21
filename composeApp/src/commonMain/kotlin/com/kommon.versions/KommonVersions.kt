package com.kommon.versions

expect object KommonVersions {
    val APPLICATION_ID: String
    val BUILD_TYPE: String
    val VERSION_CODE: Int
    val VERSION_NAME: String
    val FLAVOR: String
}
