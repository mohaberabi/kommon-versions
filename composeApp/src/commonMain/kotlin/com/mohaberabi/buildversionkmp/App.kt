package com.mohaberabi.buildversionkmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import buildversionkmp.composeapp.generated.resources.Res
import buildversionkmp.composeapp.generated.resources.compose_multiplatform
import com.kommon.versions.KommonVersions

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold { padding ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("AppId--- ${KommonVersions.APPLICATION_ID}")
                Text("VersionName--- ${KommonVersions.VERSION_NAME}")
                Text("VersionCode--- ${KommonVersions.VERSION_CODE}")
                Text("BuildType--- ${KommonVersions.BUILD_TYPE}")
                Text("Flavor--- ${KommonVersions.FLAVOR}")
            }
        }
    }
}