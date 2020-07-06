package com.isidroid.b21.sample.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import com.isidroid.b21.utils.BindActivity
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.observe
import dagger.Lazy
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    @Inject lateinit var test: Lazy<String>
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)

        button.setOnClickListener { Timber.i("gdfgfgdfgdfgdfg test ${test.get()}") }
    }

    override fun onCreateViewModel() {
        observe(viewModel.data) { }
    }
}