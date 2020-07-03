package com.isidroid.b21

import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    private val uid = UUID.randomUUID().toString().substring(0, 4)
    fun create(caller: Any) {
        Timber.i("HJello world $uid from ${caller} $this")
    }
}