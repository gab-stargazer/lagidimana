package com.lelestacia.lagidimana.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.ui.Location

@Entity(tableName = "location")
data class LocationModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("lat")
    val locationLat: Double,

    @ColumnInfo("lng")
    val locationLng: Double,

    @ColumnInfo("timestamp")
    val timeStamp: Long
)

fun LocationModel.toDomain(): Location {
    return Location(
        location = LatLng(locationLat, locationLng),
        timeStamp = timeStamp
    )
}