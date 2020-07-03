package com.isidroid.b21

import android.os.Bundle
import androidx.activity.viewModels
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.observe
import timber.log.Timber

class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)

        viewModel.create(this)
    }
}