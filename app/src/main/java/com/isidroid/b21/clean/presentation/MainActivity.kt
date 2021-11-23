package com.isidroid.b21.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePaddingRelative
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.databinding.ActivityMainBinding
import com.isidroid.b21.ext.getRootView
import com.isidroid.b21.ext.observe
import com.isidroid.b21.models.dto.StatisticDto
import com.isidroid.b21.utils.base.BindActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : BindActivity<ActivityMainBinding>(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private val adapter = StatsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(getRootView()) { _, insets ->
//            recyclerView.updateLayoutParams<CoordinatorLayout.LayoutParams> {
//                topMargin =
//            }

            recyclerView.updatePaddingRelative(top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top)


            insets
        }

        viewModel.single()
        viewModel.double()
    }

    override fun onCreateViewModel() {
        observe(viewModel.state) { state ->
            when (state) {
                is MainViewModel.State.Statistics ->
                    onStatistics(state.name, state.first, state.second)
            }
        }
    }

    override fun createAdapter() {
        recyclerView.adapter = adapter
    }

    private fun onStatistics(
        name: String,
        first: List<StatisticDto>,
        second: List<StatisticDto>
    ) {
        adapter.add("$name first block")
        adapter.insert(first)

        adapter.add("$name second block")
        adapter.insert(second)
    }

}