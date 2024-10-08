package com.lelestacia.lagidimana.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lelestacia.lagidimana.data.db.model.LocationEntity
import com.lelestacia.lagidimana.data.db.dao.LocationDao

@Database(
    entities = [LocationEntity::class],
    version = 1,
    exportSchema = true
)
abstract class LocationDB: RoomDatabase() {

    abstract fun getLocationDao(): LocationDao
}