package com.lelestacia.lagidimana.domain.repository

import androidx.paging.PagingData
import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    fun getLiveLocation(): Flow<LatLng?>

    suspend fun insertLiveLocation(location: Location)

    fun getHistoryLocation(): Flow<PagingData<Location>>

    fun getLast25Location(): Flow<List<Location>>
}