package com.isidroid.b21.sample.clean.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.utils.BindFragment
import dagger.Lazy
import kotlinx.android.synthetic.main.sample_main_fragment.*
import timber.log.Timber
import javax.inject.Inject

class MainFragment : BindFragment(layoutRes = R.layout.sample_main_fragment) {
    @Inject lateinit var test: Lazy<String>
    private val viewModel by activityViewModels<MainViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button.setOnClickListener { Timber.i("gdfgfgdfgdfgdfg test ${test.get()}") }
    }
}