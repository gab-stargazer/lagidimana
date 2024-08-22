package com.lelestacia.lagidimana.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.os.Looper
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
import com.lelestacia.lagidimana.domain.model.Location
import com.parassidhu.simpledate.toTimeStandardWithoutSeconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.Date

class LocationManager : Service() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val repository: MapRepository by inject()
    private val logger: Logger by inject()

//    private val locationCallback = object : LocationCallback() {
//
//        override fun onLocationResult(result: LocationResult) {
//            result.lastLocation?.let { location ->
//                val currentLocation = Location(
//                    location = LatLng(location.latitude, location.longitude),
//                    timeStamp = Date().time
//                )
//
//                logger.debug(
//                    ClassName(this@LocationManager::class.simpleName.orEmpty()),
//                    Message(
//                        "location updated to ${currentLocation.location} on ${
//                            Date(
//                                currentLocation.timeStamp
//                            ).toTimeStandardWithoutSeconds()
//                        }"
//                    )
//                )
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    repository.insertLiveLocation(currentLocation)
//                }
//            }
//        }
//    }

    override fun onCreate() {
        super.onCreate()

        isRunning = true

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(applicationContext)

        logger.info(
            ClassName(this@LocationManager::class.simpleName.orEmpty()),
            Message("Location Manager Service Launched")
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        logger.info(
            ClassName(this@LocationManager::class.simpleName.orEmpty()),
            Message("Location Manager Service Stopped")
        )

        isRunning = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

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

        val notification: Notification = NotificationCompat.Builder(this, "location_channel")
            .setContentTitle("Tracking Location")
            .setContentText("Your location is being tracked in real-time with 5 minute interval")
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_location_on_24)
            .build()

        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(1, notification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION)
        } else {
            startForeground(1, notification)
        }

        startLocationTracking()

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun startLocationTracking() = with(logger) {
        if (!checkLocationPermission()) {
            warning(
                ClassName(this@LocationManager::class.simpleName.orEmpty()),
                Message("Location Permission Not Granted and service will be stopped")
            )
            stopSelf()
            return@with
        }

        fusedLocationProviderClient.requestLocationUpdates(
            LocationRequest
                .Builder(Priority.PRIORITY_HIGH_ACCURACY, 300000)
                .setMinUpdateIntervalMillis(300000)
                .setIntervalMillis(300000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build(),
            object : LocationCallback() {

                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let { location ->
                        val currentLocation = Location(
                            location = LatLng(location.latitude, location.longitude),
                            timeStamp = Date().time
                        )

                        debug(
                            ClassName(this@LocationManager::class.simpleName.orEmpty()),
                            Message(
                                "location updated to ${currentLocation.location} on ${
                                    Date(
                                        currentLocation.timeStamp
                                    ).toTimeStandardWithoutSeconds()
                                }"
                            )
                        )

                        CoroutineScope(Dispatchers.IO).launch {
                            repository.insertLiveLocation(currentLocation)
                        }
                        return
                    }

                    debug(
                        ClassName(this@LocationManager::class.simpleName.orEmpty()),
                        Message("location is not found")
                    )
                }
            },
            Looper.getMainLooper()
        )
    }

//    private fun stopLocationTracking() {
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
//    }

    companion object {
        var isRunning = false
    }
}