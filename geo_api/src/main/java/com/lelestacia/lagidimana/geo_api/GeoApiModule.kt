package com.lelestacia.lagidimana.geo_api

import com.google.maps.GeoApiContext
import org.koin.core.module.dsl.binds
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val geoApiModule = module {
    single<GeoApiContext>(createdAtStart = true) {
        GeoApiContext.Builder()
            .apiKey(get())
            .maxRetries(5)
            .build()
    }

    singleOf(::GeoApiServiceImpl) {
        createdAtStart()
        binds(listOf(GeoApiService::class))
    }
}