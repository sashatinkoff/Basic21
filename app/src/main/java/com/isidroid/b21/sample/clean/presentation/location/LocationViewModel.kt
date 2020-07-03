package com.isidroid.b21.sample.clean.presentation.location

import android.annotation.SuppressLint
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.isidroid.b21.sample.clean.domain.LocationUseCase
import com.isidroid.b21.utils.CoroutineViewModel
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase
) : CoroutineViewModel() {

    val location = MutableLiveData<LocationResult>()

    fun start() {
        locationUseCase.subscribe { location.value = it }
    }
}