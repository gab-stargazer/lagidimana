package com.lelestacia.lagidimana.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.lelestacia.lagidimana.data.db.model.LocationModel
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Upsert
    suspend fun upsertLocation(location: LocationModel)

    @Query("SELECT * FROM location ORDER BY timestamp DESC")
    fun getLocationHistoryPaging(): PagingSource<Int, LocationModel>

    @Query("SELECT * FROM location ORDER BY timestamp DESC LIMIT 1")
    fun getLatestLocation(): Flow<LocationModel?>

    @Delete
    suspend fun deleteLocation(locationModel: LocationModel)
}