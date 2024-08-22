package com.lelestacia.lagidimana.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.lelestacia.lagidimana.data.db.model.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Upsert
    suspend fun upsertLocation(location: LocationEntity)

    @Query("SELECT * FROM location ORDER BY timestamp DESC")
    fun getLocationHistoryPaging(): PagingSource<Int, LocationEntity>

    @Query("SELECT * FROM location ORDER BY timestamp DESC LIMIT 1")
    fun getLatestLocation(): Flow<LocationEntity?>

    @Query("SELECT * FROM location ORDER BY timestamp DESC LIMIT 25")
    fun getLast25Location(): Flow<List<LocationEntity>>

    @Delete
    suspend fun deleteLocation(locationEntity: LocationEntity)
}