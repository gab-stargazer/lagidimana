package com.lelestacia.lagidimana.util

import android.app.Notification
import android.app.NotificationManager
import android.app.Service.NOTIFICATION_SERVICE
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.lelestacia.lagidimana.R
import com.lelestacia.lagidimana.data.db.LocationDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.concurrent.TimeUnit

class DbCleanerWorkManager(
    private val db: LocationDB,
    private val logger: Logger,
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                db.clearAllTables()

                val notification: Notification = NotificationCompat.Builder(context, "main_channel")
                    .setContentTitle(context.getString(R.string.msg_notification_history_deleted_title))
                    .setContentText(context.getString(R.string.msg_notification_history_deleted_description))
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.ic_auto_delete_24)
                    .build()

                val notificationManager: NotificationManager =
                    context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

                notificationManager.notify(2, notification)

                Result.success()
            } catch (e: Exception) {
                logger.error(
                    ClassName(this::class.simpleName.orEmpty()),
                    Message(e.stackTraceToString())
                )
                Result.failure()
            }
        }
    }
}

fun Context.launchWorkManager() {
    val currentTime = Calendar.getInstance()

    val targetTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 22)  // 10 PM
        set(Calendar.MINUTE, 50)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    if (currentTime.after(targetTime)) {
        targetTime.add(Calendar.DATE, 1)
    }

    val initialDelay = targetTime.timeInMillis - currentTime.timeInMillis

    val dailyWorkRequest: PeriodicWorkRequest =
        PeriodicWorkRequestBuilder<DbCleanerWorkManager>(24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

    WorkManager.getInstance(this).enqueueUniquePeriodicWork(
        "DailyCleanUp",
        ExistingPeriodicWorkPolicy.UPDATE,
        dailyWorkRequest
    )
}