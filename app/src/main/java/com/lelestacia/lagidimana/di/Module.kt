package com.lelestacia.lagidimana.di

import androidx.room.Room
import com.lelestacia.lagidimana.data.db.LocationDB
import com.lelestacia.lagidimana.data.db.dao.LocationDao
import com.lelestacia.lagidimana.data.repository.MapRepositoryImpl
import com.lelestacia.lagidimana.domain.repository.MapRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.binds
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val module = module {
    single<LocationDB>(createdAtStart = true) {
        Room.databaseBuilder(
            context = androidContext(),
            klass = LocationDB::class.java,
            name = "location_db"
        ).fallbackToDestructiveMigration().build()
    }

    single<LocationDao>(createdAtStart = true) {
        get<LocationDB>().getLocationDao()
    }

    singleOf(::MapRepositoryImpl) {
        createdAtStart()
        binds(listOf(MapRepository::class))
    }
}