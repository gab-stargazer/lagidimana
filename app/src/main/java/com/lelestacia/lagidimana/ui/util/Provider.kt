package com.lelestacia.lagidimana.ui.util

import androidx.compose.runtime.compositionLocalOf

val LocalParentSnackbarManager = compositionLocalOf<SnackbarManager> { error("Parent Snackbar Manager Not Instantiated") }