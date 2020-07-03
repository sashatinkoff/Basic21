package com.isidroid.b21

import androidx.lifecycle.ViewModel
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {
    fun create() {
        Timber.i("HJello world")
    }
}