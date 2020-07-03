package com.isidroid.b21.sample.clean.presentation.location

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleService
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.observe
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class LocationService : LifecycleService() {
    @Inject lateinit var viewModel: LocationViewModel
    private val channel =
        NotificationChannel("dsfsdfsdf", "dsfsdfsdf", NotificationManager.IMPORTANCE_HIGH)

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        NotificationManagerCompat.from(this).createNotificationChannel(channel)

        startForeground(1, notification())

        observe(viewModel.location) {
            NotificationManagerCompat.from(this).notify(
                1, notification(
                    "text=${Date()}, " +
                            "location=${it.lastLocation.latitude}, ${it.lastLocation.longitude}"
                )
            )
        }
        viewModel.start()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun notification(text: String = "") = NotificationCompat.Builder(this, "dsfsdfsdf")
        .setContentText(text)
        .setSmallIcon(R.mipmap.ic_launcher)
        .build()

    companion object {
        fun start(context: Context) = ContextCompat.startForegroundService(
            context,
            Intent(context, LocationService::class.java)
        )
    }
}

