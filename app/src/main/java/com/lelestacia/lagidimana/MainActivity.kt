package com.lelestacia.lagidimana

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import com.lelestacia.lagidimana.ui.LocalParentNavigator
import com.lelestacia.lagidimana.ui.screen.gatewayScreen
import com.lelestacia.lagidimana.ui.screen.mainScreen
import com.lelestacia.lagidimana.ui.screen.noPermissionScreen
import com.lelestacia.lagidimana.ui.theme.LagiDimanaTheme
import com.lelestacia.lagidimana.ui.util.LocalParentSnackbarManager
import com.lelestacia.lagidimana.ui.util.MainRoute.Gateway

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

                            noPermissionScreen()

                            mainScreen()
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