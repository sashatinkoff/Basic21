package com.isidroid.b21.sample.clean.presentation.location

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.observe
import com.isidroid.b21.sample.clean.presentation.main.LocationReceiver
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val ACTION_CANCEL = "ACTION_CANCEL"

@RequiresApi(Build.VERSION_CODES.O)
class LocationService : LifecycleService() {
    @Inject lateinit var viewModel: LocationViewModel
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val channel =
        NotificationChannel("dsfsdfsdf", "dsfsdfsdf", NotificationManager.IMPORTANCE_DEFAULT)
    private val action by lazy {
        NotificationCompat.Action.Builder(null, "Cancel", pendingIntent).build()
    }

    private val locationReceiver = LocationReceiver(callback = { viewModel.onLocationUpdate(it) })

    private val pendingIntent by lazy {
        val intent = Intent(this, LocationService::class.java)
            .setAction(ACTION_CANCEL)

        PendingIntent.getService(this, 1, intent, 0)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancel()
        unregisterReceiver(locationReceiver)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_CANCEL) {
            stopForeground(true)
            stopSelf()
            return super.onStartCommand(intent, flags, startId)
        }

        registerReceiver(locationReceiver, LocationReceiver.intentFilter)

        NotificationManagerCompat.from(this).createNotificationChannel(channel)
        startForeground(1, notification())

        observe(viewModel.location) { onLocation(it) }
        viewModel.start()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun onLocation(location: Location) {
        NotificationManagerCompat.from(this).notify(
            1, notification(
                "text=${dateFormat.format(Date())}, " +
                        "location=${location.latitude}, ${location.longitude}"
            )
        )
    }

    private fun notification(text: String = "Tracking enabled, upd. ${dateFormat.format(Date())}") =
        NotificationCompat.Builder(this, "dsfsdfsdf")
            .setContentText(text)
            .setSmallIcon(R.mipmap.ic_launcher)
            .addAction(action)
            .build()

    companion object {
        fun start(context: Context) = ContextCompat.startForegroundService(
            context,
            Intent(context, LocationService::class.java)
        )
    }
}

