package com.lelestacia.lagidimana.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.domain.model.Location

@Entity(tableName = "location")
data class LocationEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("lat")
    val locationLat: Double,

    @ColumnInfo("lng")
    val locationLng: Double,

    @ColumnInfo("status")
    val isOnline: Boolean,

    @ColumnInfo("address")
    val address: String?,

    @ColumnInfo("timestamp")
    val timeStamp: Long
)

fun LocationEntity.toDomain(): Location {
    return Location(
        location = LatLng(locationLat, locationLng),
        isOnline = isOnline,
        address = address,
        timeStamp = timeStamp
    )
}