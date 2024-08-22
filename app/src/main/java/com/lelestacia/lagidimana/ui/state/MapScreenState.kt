package com.lelestacia.lagidimana.ui.state

import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.domain.model.Location
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class MapScreenState(
    val lastLocation: LatLng = LatLng(-0.7893, 113.9213),
    val last25Location: ImmutableList<Location> = emptyList<Location>().toImmutableList()
)
