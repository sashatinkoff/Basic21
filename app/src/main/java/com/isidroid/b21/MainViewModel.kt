package com.isidroid.b21

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    var data = MutableLiveData<String>()

    fun create(caller: Any) {
        Handler().postDelayed({ data.value = "HJelldsf" }, 2_000)
    }
}