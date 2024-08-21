package com.lelestacia.lagidimana.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.lelestacia.lagidimana.ui.theme.LagiDimanaTheme
import com.lelestacia.lagidimana.util.getApplicationPermission

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NoPermissionScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val permission = rememberMultiplePermissionsState(
        permissions = getApplicationPermission(),
        onPermissionsResult = { result ->
            if (result.values.any { !it }) {

            }
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "Aplikasi memerlukan izin yang telah ditentukan untuk dapat digunakan")
        Button(
            onClick = {
                permission.launchMultiplePermissionRequest()
            }
        ) {
            Text(text = "Izinkan Aplikasi")
        }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun PreviewNoPermissionScreen() {
    LagiDimanaTheme(
        dynamicColor = false
    ) {
        Surface {
            NoPermissionScreen(
                navController = rememberNavController(),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}