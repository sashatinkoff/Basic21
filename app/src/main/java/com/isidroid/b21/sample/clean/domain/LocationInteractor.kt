package com.isidroid.b21.sample.clean.domain

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.*
import javax.inject.Inject

class LocationInteractor @Inject constructor(private val locationClient: FusedLocationProviderClient) :
    LocationUseCase {

    @SuppressLint("MissingPermission")
    override fun subscribe(onUpdate: (LocationResult) -> Unit) {
        val request = LocationRequest().apply {
            interval = 15_000
            fastestInterval = 15_000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val callback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                p0?.let { onUpdate(it) }
            }

            override fun onLocationAvailability(p0: LocationAvailability?) {
            }
        }


        locationClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
    }


}