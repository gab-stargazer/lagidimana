package com.lelestacia.lagidimana.util

import android.location.Geocoder
import android.os.Build

@Suppress("DEPRECATION")
fun Geocoder.getAddress(
    latitude: Double,
    longitude: Double,
    address: (android.location.Address?) -> Unit
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getFromLocation(latitude, longitude, 1) {
            Logger().debug(
                message = Message("Result Geocode: ${it.toString()}")
            )
            address(it.firstOrNull())
        }
        return
    }

    try {
        val result = getFromLocation(latitude, longitude, 1)
        Logger().debug(
            message = Message("Result Geocode: ${result.toString()}")
        )
        address(result?.firstOrNull())
    } catch (e: Exception) {
        address(null)
    }
}