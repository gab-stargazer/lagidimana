package com.lelestacia.lagidimana.ui

import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.data.db.model.LocationModel

data class Location(
    val location: LatLng,
    val timeStamp: Long
)

fun Location.toModel(): LocationModel {
    return LocationModel(
        locationLat = location.latitude,
        locationLng = location.longitude,
        timeStamp = timeStamp
    )
}
