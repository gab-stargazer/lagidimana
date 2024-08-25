package com.lelestacia.lagidimana.geo_api

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.LatLng

internal class GeoApiServiceImpl(
    private val geoApiContext: GeoApiContext
) : GeoApiService {

    override suspend fun getLocation(lat: Double, lon: Double): Result<String> = runCatching {
        val geocodingResult = GeocodingApi.reverseGeocode(
            geoApiContext,
            LatLng(lat, lon)
        ).await()

        try {
            val index = geocodingResult.first().formattedAddress.indexOf(
                ',',
                0,
                ignoreCase = true
            )
            geocodingResult.first().formattedAddress.removeRange(IntRange(0, index + 1))
        } catch (e: Exception) {
            geocodingResult.first().formattedAddress
        }
    }
}