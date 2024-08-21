package com.lelestacia.lagidimana

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.lelestacia.lagidimana.ui.LocalParentNavigator
import com.lelestacia.lagidimana.ui.screen.gatewayScreen
import com.lelestacia.lagidimana.ui.theme.LagiDimanaTheme
import com.lelestacia.lagidimana.ui.util.LocalParentSnackbarManager
import com.lelestacia.lagidimana.util.LocationManager
import com.lelestacia.lagidimana.util.MainRoute.Gateway
import com.lelestacia.lagidimana.util.MainRoute.HasPermission
import com.lelestacia.lagidimana.util.MainRoute.NoPermission

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            LagiDimanaTheme {
                Surface {
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(hostState = LocalParentSnackbarManager.current.snackbarHostState)
                        }
                    ) { paddingValues ->
                        NavHost(
                            navController = LocalParentNavigator.current,
                            startDestination = Gateway,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            gatewayScreen()

                            composable<NoPermission> {

                            }

                            composable<HasPermission> {
                                val context = LocalContext.current
                                LaunchedEffect(key1 = Unit) {
                                    val serviceIntent = Intent(context, LocationManager::class.java)
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        startForegroundService(serviceIntent)
                                    } else {
                                        startService(serviceIntent)
                                    }
                                }

                                val cameraPositionState = rememberCameraPositionState()

                                LaunchedEffect(key1 = Unit) {
                                    cameraPositionState.animate(
                                        CameraUpdateFactory.newLatLngZoom(
                                            LatLng(
                                                -0.7893,
                                                113.9213
                                            ),
                                            10F
                                        )
                                    )
                                }

                                GoogleMap(
                                    cameraPositionState = cameraPositionState,
                                    uiSettings = MapUiSettings(
                                        compassEnabled = false,
                                        indoorLevelPickerEnabled = false,
                                        rotationGesturesEnabled = false,
                                    ),
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LagiDimanaTheme {
        Greeting("Android")
    }
}