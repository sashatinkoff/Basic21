package com.isidroid.b21.sample.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import com.isidroid.b21.BindActivity
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.observe
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)

        button.setOnClickListener { viewModel.create() }
    }

    override fun onCreateViewModel() {
        observe(viewModel.data) { Timber.e("sdfsdfd $it") }
    }
}