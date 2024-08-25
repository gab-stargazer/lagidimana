package com.lelestacia.lagidimana.ui.util

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalParentSnackbarManager = compositionLocalOf<SnackbarManager> { error("Parent Snackbar Manager Not Instantiated") }

val LocalChildNavigator = compositionLocalOf<NavHostController> { error("Child Navigator is not instantiated") }