package com.lelestacia.lagidimana.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.ui.Location
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    fun getLiveLocation(): Flow<LatLng?>

    suspend fun insertLiveLocation(location: Location)
}