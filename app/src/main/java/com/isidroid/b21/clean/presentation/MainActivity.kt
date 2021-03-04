package com.isidroid.b21.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.models.db.Post
import com.isidroid.b21.utils.core.BindActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

    }
}