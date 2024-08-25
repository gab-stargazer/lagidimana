package com.lelestacia.lagidimana.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Map
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.lelestacia.lagidimana.R
import com.lelestacia.lagidimana.ui.util.ChildRoute

val LocalParentNavigator =
    compositionLocalOf<NavHostController> { error("Main Nav Controller is not instantiated") }

data class NavigationMenu(
    @StringRes val title: Int,
    val destination: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
)

val mainNavigation = listOf(
    NavigationMenu(
        title = R.string.menu_maps,
        destination = ChildRoute.Map.route,
        activeIcon = Icons.Filled.Map,
        inactiveIcon = Icons.Outlined.Map
    ),
    NavigationMenu(
        title = R.string.menu_history,
        destination = ChildRoute.History.route,
        activeIcon = Icons.Filled.History,
        inactiveIcon = Icons.Outlined.History
    ),
)