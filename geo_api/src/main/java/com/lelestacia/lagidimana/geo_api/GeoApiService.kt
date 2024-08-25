package com.lelestacia.lagidimana.geo_api

interface GeoApiService {

    suspend fun getLocation(lat: Double, lon: Double): Result<String>
}