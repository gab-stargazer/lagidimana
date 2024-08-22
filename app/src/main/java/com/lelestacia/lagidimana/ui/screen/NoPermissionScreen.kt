package com.lelestacia.lagidimana.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.lelestacia.lagidimana.R
import com.lelestacia.lagidimana.ui.LocalParentNavigator
import com.lelestacia.lagidimana.ui.theme.LagiDimanaTheme
import com.lelestacia.lagidimana.ui.util.LocalParentSnackbarManager
import com.lelestacia.lagidimana.ui.util.MainRoute.HasPermission
import com.lelestacia.lagidimana.ui.util.MainRoute.NoPermission
import com.lelestacia.lagidimana.ui.util.SnackbarManager
import com.lelestacia.lagidimana.util.getApplicationPermission
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun NoPermissionScreen(
    modifier: Modifier = Modifier
) {
    val parentNavigator = LocalParentNavigator.current
    val parentSnackbarManager = LocalParentSnackbarManager.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val permission = rememberMultiplePermissionsState(
        permissions = getApplicationPermission(),
        onPermissionsResult = { result ->
            if (result.values.any { !it }) {
                scope.launch {
                    parentSnackbarManager.queue(
                        SnackbarManager.SnackbarData(context.getString(R.string.all_permission_required))
                    )
                }
            } else{
                parentNavigator.navigate(HasPermission) {
                    popUpTo<NoPermission> {
                        inclusive = true
                    }
                }
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

fun NavGraphBuilder.noPermissionScreen() {
    composable<NoPermission> {
        NoPermissionScreen(
            modifier = Modifier.fillMaxSize()
        )
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
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}