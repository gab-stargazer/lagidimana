package com.lelestacia.lagidimana.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lelestacia.lagidimana.data.db.model.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Query("SELECT * FROM location ORDER BY timestamp DESC")
    fun getLocationHistoryPaging(): PagingSource<Int, LocationEntity>

    @Query("SELECT * FROM location ORDER BY timestamp DESC LIMIT 1")
    fun getLatestLocation(): Flow<LocationEntity?>

    @Query("SELECT * FROM location ORDER BY timestamp DESC LIMIT 25")
    fun getLast25Location(): Flow<List<LocationEntity>>
}