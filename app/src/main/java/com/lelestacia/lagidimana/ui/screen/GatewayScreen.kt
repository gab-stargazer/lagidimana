package com.lelestacia.lagidimana.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.lelestacia.lagidimana.ui.LocalParentNavigator
import com.lelestacia.lagidimana.ui.util.MainRoute.Gateway
import com.lelestacia.lagidimana.ui.util.MainRoute.HasPermission
import com.lelestacia.lagidimana.ui.util.MainRoute.NoPermission
import com.lelestacia.lagidimana.util.getApplicationPermission

@OptIn(ExperimentalPermissionsApi::class)
fun NavGraphBuilder.gatewayScreen() {
    composable<Gateway> {
        val parentNavigator = LocalParentNavigator.current

        val permissions: MultiplePermissionsState =
            rememberMultiplePermissionsState(getApplicationPermission())

        LaunchedEffect(key1 = Unit) {
            if (permissions.allPermissionsGranted) {
                parentNavigator.navigate(HasPermission) {
                    popUpTo<Gateway> {
                        inclusive = true
                    }
                }
            } else {
                parentNavigator.navigate(NoPermission) {
                    popUpTo<Gateway>() {
                        inclusive = true
                    }
                }
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