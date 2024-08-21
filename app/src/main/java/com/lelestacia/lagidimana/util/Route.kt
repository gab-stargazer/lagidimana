package com.lelestacia.lagidimana.util

import kotlinx.serialization.Serializable

sealed class MainRoute {

    @Serializable
    data object Gateway: MainRoute()

    @Serializable
    data object NoPermission: MainRoute()

    @Serializable
    data object HasPermission: MainRoute()
}