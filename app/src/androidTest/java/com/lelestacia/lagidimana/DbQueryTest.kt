package com.lelestacia.lagidimana

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.data.db.LocationDB
import com.lelestacia.lagidimana.data.db.dao.LocationDao
import com.lelestacia.lagidimana.data.db.model.LocationEntity
import com.lelestacia.lagidimana.data.db.model.toDomain
import com.lelestacia.lagidimana.domain.model.Location
import com.lelestacia.lagidimana.domain.model.toEntity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before

@RunWith(AndroidJUnit4::class)
class DbQueryTest {

    private lateinit var dao: LocationDao

    @Before
    fun setup() {
        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocationDB::class.java
        ).build()

        dao = db.getLocationDao()
    }

    @Test
    fun lastLocationShouldBeEmptyAtFirst() = runTest {
        val firstResult = dao.getLatestLocation().firstOrNull()
        Assert.assertNull("First Result should be null", firstResult)

        for (i in 0..10) {
            dao.insertLocation(
                Location(
                    location = LatLng(10.toDouble(), 10.toDouble()),
                    isOnline = false,
                    null,
                    i.toLong()
                ).toEntity()
            )
        }

        val secondResult = dao.getLatestLocation().firstOrNull()
        Assert.assertNotNull("Second Result should not be null", secondResult)
        Assert.assertEquals(
            "Last Timestamp should be equal to 10",
            10.toLong(),
            secondResult?.timeStamp
        )
    }

    fun last25LocationShouldNotEmptyAndMatch() = runTest {
        for (i in 0..25) {
            dao.insertLocation(
                Location(
                    LatLng(10.toDouble(), 10.toDouble()),
                    false,
                    null,
                    i.toLong()
                ).toEntity()
            )
        }

        val last25Location = dao.getLast25Location().firstOrNull()
        Assert.assertEquals(
            "First Timestamp should be 25",
            25.toLong(),
            last25Location?.firstOrNull()?.timeStamp
        )
        Assert.assertEquals(
            "Last Timestamp should be 0",
            0.toLong(),
            last25Location?.lastOrNull()?.timeStamp
        )
        Assert.assertTrue("Size should match the inserted item", last25Location?.size == 25)
    }
}