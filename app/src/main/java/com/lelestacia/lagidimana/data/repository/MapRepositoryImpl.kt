package com.lelestacia.lagidimana.data.repository

import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.data.db.dao.LocationDao
import com.lelestacia.lagidimana.domain.repository.MapRepository
import com.lelestacia.lagidimana.ui.Location
import com.lelestacia.lagidimana.ui.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapRepositoryImpl(
    private val locationDao: LocationDao
) : MapRepository {

    override fun getLiveLocation(): Flow<LatLng?> =
        locationDao.getLatestLocation().map {
            it?.let {
                LatLng(it.locationLat, it.locationLng)
            }
        }

    override suspend fun insertLiveLocation(location: Location) {
        locationDao.upsertLocation(location.toModel())
    }
}