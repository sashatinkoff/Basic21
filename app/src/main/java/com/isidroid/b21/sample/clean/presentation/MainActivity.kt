package com.isidroid.b21.sample.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import com.google.gson.Gson
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.utils.BindActivity
import timber.log.Timber

class MainActivity : BindActivity(layoutRes = R.layout.activity_main), IMainView {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)

        val extras = with(intent.extras) {
            this ?: return@with ""

            val result = mutableMapOf<String, Any?>()
            keySet().forEach { result[it] = get(it) }

            Gson().toJson(this)
        }


        Timber.i("sdfsdfsdfdsf $extras")
    }
}