package com.lelestacia.lagidimana.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalParentNavigator = compositionLocalOf<NavHostController> { error("Main Nav Controller is not instantiated") }