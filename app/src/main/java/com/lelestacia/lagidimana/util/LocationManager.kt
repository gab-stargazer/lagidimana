package com.lelestacia.lagidimana.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.R
import com.lelestacia.lagidimana.domain.repository.MapRepository
import com.lelestacia.lagidimana.ui.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.Date

class LocationManager : Service() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val repository: MapRepository by inject()
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { location_ ->
                val currentLocation = LatLng(location_.latitude, location_.longitude)
                Log.d(TAG, "onLocationResult: location updated to $currentLocation")
                CoroutineScope(Dispatchers.IO).launch {
                    repository.insertLiveLocation(
                        Location(
                            location = currentLocation,
                            timeStamp = Date().time
                        )
                    )
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location_channel",
                "Location Service",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for location tracking"
            }

            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        Log.i(TAG, "onCreate: Location Manager Service Launched")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: Location Manager Service Stopped")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification: Notification = NotificationCompat.Builder(this, "location_channel")
            .setContentTitle("Tracking Location")
            .setContentText("Your location is being tracked in real-time with 5 minute interval")
            .setSmallIcon(R.drawable.ic_location_on_24)
            .build()

        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(1, notification)

        startForeground(1, notification)
        startLocationTracking()

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun startLocationTracking() {
        if (!checkLocationPermission()) {
            Log.w(TAG, "onCreate: Location Permission Not Granted")
            stopSelf()
            return
        }

        fusedLocationProviderClient.requestLocationUpdates(
            LocationRequest
                .Builder(Priority.PRIORITY_HIGH_ACCURACY, 300000)
                .setMinUpdateIntervalMillis(300000)


                .setMaxUpdateDelayMillis(360000)
                .build(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationTracking() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}