package com.isidroid.b21.sample.clean.domain

import com.google.android.gms.location.LocationResult

interface LocationUseCase {
    fun subscribe(updates: (LocationResult) -> Unit)
    fun unsubscribe()
}