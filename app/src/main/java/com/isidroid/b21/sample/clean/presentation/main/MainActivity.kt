package com.isidroid.b21.sample.clean.presentation.main

import android.Manifest
import android.os.Bundle
import androidx.activity.viewModels
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.observe
import com.isidroid.b21.ext.permission
import com.isidroid.b21.sample.clean.presentation.location.LocationService
import com.isidroid.b21.utils.BindActivity
import timber.log.Timber

class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)

        permission(permission = Manifest.permission.ACCESS_FINE_LOCATION,
            onGranted = { LocationService.start(this) })
    }

    override fun onCreateViewModel() {
        observe(viewModel.data) { }
    }
}