package com.lelestacia.lagidimana.domain.model

import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.data.db.model.LocationEntity
import com.lelestacia.lagidimana.ui.screen.map.ClusterItemImpl

data class Location(
    val location: LatLng,
    val isOnline: Boolean = false,
    val address: String? = null,
    val timeStamp: Long
)

fun Location.toEntity(): LocationEntity {
    return LocationEntity(
        locationLat = location.latitude,
        locationLng = location.longitude,
        isOnline = isOnline,
        address = address,
        timeStamp = timeStamp
    )
}

fun Location.toClusterItem(
    title: String?,
    snippet: String?
): ClusterItemImpl {
    return ClusterItemImpl(
        location = location,
        isOnline = isOnline,
        itemTitle = title,
        address = address,
        timestamp = timeStamp,
        itemSnippet = snippet
    )
}

fun Location.toClusterItem(): ClusterItemImpl {
    return ClusterItemImpl(
        location = location,
        isOnline = isOnline,
        itemTitle = null,
        address = address,
        timestamp = timeStamp,
        itemSnippet = null
    )
}