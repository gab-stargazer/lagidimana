package com.lelestacia.lagidimana.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.android.gms.maps.model.LatLng
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.lelestacia.lagidimana.data.db.dao.LocationDao
import com.lelestacia.lagidimana.data.db.model.LocationEntity
import com.lelestacia.lagidimana.data.db.model.toDomain
import com.lelestacia.lagidimana.domain.repository.MapRepository
import com.lelestacia.lagidimana.domain.model.Location
import com.lelestacia.lagidimana.domain.model.toEntity
import com.lelestacia.lagidimana.util.ClassName
import com.lelestacia.lagidimana.util.ConnectionManager
import com.lelestacia.lagidimana.util.Logger
import com.lelestacia.lagidimana.util.Message
import com.parassidhu.simpledate.toTimeStandardWithoutSeconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import com.google.maps.model.LatLng as GeocodeLatLng

class MapRepositoryImpl(
    private val locationDao: LocationDao,
    private val geocoder: GeoApiContext,
    private val connectionManager: ConnectionManager,
    private val logger: Logger
) : MapRepository {

    override fun getLiveLocation(): Flow<LatLng?> =
        locationDao.getLatestLocation().map {
            it?.let {
                LatLng(it.locationLat, it.locationLng)
            }
        }

    override suspend fun insertLiveLocation(location: Location) {
        with(location) {
            val isOnline = connectionManager.isOnline()
            var newLocation = copy(isOnline = isOnline)

            if (isOnline) {
                try {
                    val geocodingResult = GeocodingApi.reverseGeocode(
                        geocoder,
                        GeocodeLatLng(newLocation.location.latitude, newLocation.location.longitude)
                    ).await()

                    val fullAddress = try {
                        logger.debug(
                            ClassName(this@MapRepositoryImpl::class.simpleName.orEmpty()),
                            message = Message("Formatted Address is ${geocodingResult.first().formattedAddress}")
                        )

                        val index = geocodingResult.first().formattedAddress.indexOf(
                            ',',
                            0,
                            ignoreCase = true
                        )

                        geocodingResult.first().formattedAddress.removeRange(IntRange(0, index + 1))
                    } catch (e: Exception) {
                        logger.warning(
                            ClassName(this@MapRepositoryImpl::class.simpleName.orEmpty()),
                            message = Message("Address is not in common format")
                        )

                        geocodingResult.first().formattedAddress
                    }

                    newLocation = newLocation.copy(
                        address = fullAddress
                    )

                } catch (e: Exception) {
                    logger.warning(
                        ClassName(this@MapRepositoryImpl::class.simpleName.orEmpty()),
                        message = Message(e.stackTraceToString())
                    )
                }
            }

            val formattedTimeStamp = Date(newLocation.timeStamp).toTimeStandardWithoutSeconds()
            logger.debug(
                ClassName(this@MapRepositoryImpl::class.simpleName.orEmpty()),
                message = Message("Current location updated to $newLocation on $formattedTimeStamp")
            )

            locationDao.insertLocation(newLocation.toEntity())
        }
    }

    override fun getHistoryLocation(): Flow<PagingData<Location>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                prefetchDistance = 5,
                initialLoadSize = 50
            ),
            pagingSourceFactory = {
                locationDao.getLocationHistoryPaging()
            }
        ).flow.map {
            it.map(LocationEntity::toDomain)
        }
    }

    override fun getLast25Location(): Flow<List<Location>> {
        return locationDao.getLast25Location().map {
            it.map(LocationEntity::toDomain)
        }
    }
}