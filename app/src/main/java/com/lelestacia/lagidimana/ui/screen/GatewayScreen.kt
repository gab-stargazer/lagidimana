package com.lelestacia.lagidimana.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.lelestacia.lagidimana.ui.LocalParentNavigator
import com.lelestacia.lagidimana.ui.util.LocalParentSnackbarManager
import com.lelestacia.lagidimana.ui.util.SnackbarManager
import com.lelestacia.lagidimana.util.MainRoute.Gateway
import com.lelestacia.lagidimana.util.MainRoute.HasPermission
import com.lelestacia.lagidimana.util.MainRoute.NoPermission
import com.lelestacia.lagidimana.util.getApplicationPermission
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
fun NavGraphBuilder.gatewayScreen() {
    composable<Gateway> {
        val parentNavigator = LocalParentNavigator.current
        val parentSnackbarManager = LocalParentSnackbarManager.current
        val scope = rememberCoroutineScope()

        val permissions =
            rememberMultiplePermissionsState(
                permissions = getApplicationPermission(),
                onPermissionsResult = { result ->
                    if (result.values.any { !it }) {
                        parentNavigator.navigate(NoPermission) {
                            popUpTo<NoPermission>()
                        }
                    } else {
                        scope.launch {
                            parentSnackbarManager.queue(
                                SnackbarManager.SnackbarData("Seluruh Izin Aplikasi yang diminta diperlukan untuk menggunakan aplikasi ini")
                            )
                        }

                        parentNavigator.navigate(HasPermission) {
                            popUpTo<HasPermission>()
                        }
                    }
                }
            )

        LaunchedEffect(key1 = Unit) {
            if (permissions.allPermissionsGranted) {
                parentNavigator.navigate(HasPermission) {
                    popUpTo<HasPermission>()
                }
            } else {
                permissions.launchMultiplePermissionRequest()
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}