package com.isidroid.b21.sample.clean.domain

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.os.Looper
import com.google.android.gms.location.*
import com.isidroid.b21.sample.clean.presentation.main.LocationReceiver
import javax.inject.Inject

class LocationInteractor @Inject constructor(private val locationClient: FusedLocationProviderClient) :
    LocationUseCase {
    private lateinit var onUpdate: (LocationResult) -> Unit

    private val callback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            p0?.let { onUpdate(it) }
        }

        override fun onLocationAvailability(p0: LocationAvailability?) {
        }
    }

    private val pendingIntent =
        PendingIntent.getBroadcast(
            locationClient.applicationContext,
            LocationReceiver.CODE,
            LocationReceiver.intent, 0
        )

    @SuppressLint("MissingPermission")
    override fun subscribe(onUpdate: (LocationResult) -> Unit) {
        this.onUpdate = onUpdate
        val request = LocationRequest().apply {
            interval = 15_000
            fastestInterval = 15_000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }


//        locationClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
        locationClient.requestLocationUpdates(request, pendingIntent)
    }

    override fun unsubscribe() {
//        locationClient.removeLocationUpdates(callback)
        locationClient.removeLocationUpdates(pendingIntent)
    }
}