package com.isidroid.b21.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.ext.pxToDp
import com.isidroid.b21.ext.screenWidthPx
import com.isidroid.b21.utils.core.BindActivity
import timber.log.Timber


class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        val dnsity = resources.displayMetrics.density
        val fontScale = resources.configuration.fontScale

    }
}