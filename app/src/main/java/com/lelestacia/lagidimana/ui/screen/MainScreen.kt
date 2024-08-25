package com.lelestacia.lagidimana.ui.screen

import android.content.Intent
import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.window.core.layout.WindowWidthSizeClass
import com.lelestacia.lagidimana.domain.viewmodel.HistoryViewModel
import com.lelestacia.lagidimana.domain.viewmodel.MapViewModel
import com.lelestacia.lagidimana.ui.mainNavigation
import com.lelestacia.lagidimana.ui.screen.history.LocationHistoryScreen
import com.lelestacia.lagidimana.ui.screen.history.locationHistoryScreen
import com.lelestacia.lagidimana.ui.screen.map.MapScreen
import com.lelestacia.lagidimana.ui.screen.map.mapScreen
import com.lelestacia.lagidimana.ui.theme.LagiDimanaTheme
import com.lelestacia.lagidimana.ui.util.ChildRoute.Map
import com.lelestacia.lagidimana.ui.util.LocalChildNavigator
import com.lelestacia.lagidimana.ui.util.MainRoute.HasPermission
import com.lelestacia.lagidimana.util.LocationManager
import com.lelestacia.lagidimana.util.Logger
import com.lelestacia.lagidimana.util.Message
import com.lelestacia.lagidimana.util.launchWorkManager
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
private fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentMenu = navBackStackEntry?.destination?.route

    CompositionLocalProvider(
        values = arrayOf(
            LocalChildNavigator provides navController
        )
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    mainNavigation.forEach { menu ->
                        val isActive = currentMenu.equals(menu.destination)
                        NavigationBarItem(
                            selected = isActive,
                            onClick = {
                                navController.navigate(menu.destination) {
                                    popUpTo(Map.route) {
                                        saveState = true
                                    }
                                    restoreState = true
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                AnimatedContent(
                                    targetState = isActive,
                                    label = "Icon Animation"
                                ) {
                                    when (it) {
                                        true -> Icon(
                                            imageVector = menu.activeIcon,
                                            contentDescription = stringResource(menu.title)
                                        )

                                        false -> Icon(
                                            imageVector = menu.inactiveIcon,
                                            contentDescription = stringResource(menu.title)
                                        )
                                    }
                                }
                            },
                            alwaysShowLabel = false
                        )
                    }
                }
            },
            modifier = modifier
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Map.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                mapScreen()

                locationHistoryScreen()
            }
        }
    }
}

@Composable
private fun ExpandedMainScreen(modifier: Modifier = Modifier) {
    val mapViewModel = koinViewModel<MapViewModel>()
    val historyViewModel = koinViewModel<HistoryViewModel>()

    val last25Location by mapViewModel.last25Location.collectAsStateWithLifecycle()
    val locationPaging = historyViewModel.locationHistory.collectAsLazyPagingItems()

    Row(
        modifier = modifier
    ) {
        MapScreen(
            last25Location = last25Location, modifier = Modifier
                .fillMaxHeight()
                .weight(3f)
        )

        LocationHistoryScreen(
            histories = locationPaging,
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f)
        )
    }
}

fun NavGraphBuilder.mainScreen() {
    composable<HasPermission> {
        val context = LocalContext.current
        val logger = koinInject<Logger>()

        LaunchedEffect(key1 = Unit) {
            context.launchWorkManager()

            if (!LocationManager.isRunning) {
                val serviceIntent = Intent(context, LocationManager::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }
            } else {
                logger.warning(
                    message = Message("SERVICE ALREADY RUNNING")
                )
            }
        }

        val currentWindowSize = currentWindowAdaptiveInfo().windowSizeClass
        if (currentWindowSize.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            ExpandedMainScreen()
        } else {
            MainScreen()
        }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun PreviewMainScreen() {
    LagiDimanaTheme {
        Surface {
            MainScreen(modifier = Modifier.fillMaxSize())
        }
    }
}