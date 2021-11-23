package com.isidroid.b21.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.databinding.ActivityMainBinding
import com.isidroid.b21.ext.observe
import com.isidroid.b21.models.dto.StatisticDto
import com.isidroid.b21.utils.base.BindActivity
import timber.log.Timber


class MainActivity : BindActivity<ActivityMainBinding>(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)


        viewModel.single()
    }

    override fun onCreateViewModel() {
        observe(viewModel.state) { state ->
            when (state) {
                is MainViewModel.State.SingleStatistics ->
                    onSingleStatistics(state.first, state.second)
            }
        }
    }

    private fun onSingleStatistics(first: List<StatisticDto>, second: List<StatisticDto>) {
        print("Single statistic first block", true)
        first.forEach { print("$it") }

        print("Single statistic second block", true)
        second.forEach { print("$it") }
    }

    private fun print(message: String, isCritical: Boolean = false) =
        with(Timber.tag("statistic")) {
            if (isCritical) e(message) else i(message)
        }
}