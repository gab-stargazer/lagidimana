package com.lelestacia.lagidimana.ui.util

import kotlinx.serialization.Serializable

sealed class MainRoute {

    @Serializable
    data object Gateway : MainRoute()

    @Serializable
    data object NoPermission : MainRoute()

    @Serializable
    data object HasPermission : MainRoute()
}

sealed class ChildRoute(val route: String) {

    data object Map : ChildRoute("map")

    data object History : ChildRoute("history")
}

