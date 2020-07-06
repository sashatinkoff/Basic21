package com.isidroid.b21.sample.clean.presentation.location

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationResult
import com.isidroid.b21.sample.clean.domain.DbLocationUseCase
import com.isidroid.b21.sample.clean.domain.LocationUseCase
import com.isidroid.b21.utils.CoroutineViewModel
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase,
    private val dbLocation: DbLocationUseCase
) : CoroutineViewModel() {
    val location = MutableLiveData<Location>()

    fun start() {
        locationUseCase.subscribe { onLocationUpdate(it.lastLocation) }
    }

     fun onLocationUpdate(lastLocation: Location) = io(
        doWork = { dbLocation.save(lastLocation) },
        onComplete = { location.value = lastLocation }
    )

    override fun cancel() {
        super.cancel()
        locationUseCase.unsubscribe()
    }
}