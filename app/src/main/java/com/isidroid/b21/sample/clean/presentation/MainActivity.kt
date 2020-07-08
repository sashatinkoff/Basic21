package com.isidroid.b21.sample.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import com.isidroid.b21.utils.BindActivity
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.observe
import com.isidroid.b21.sample.SquareOffSdk
import dagger.Lazy
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BindActivity(layoutRes = R.layout.activity_main), IMainView {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private val presenter = MainPresenter(view = this, lifecycle = lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)


        button.setOnClickListener { Timber.i("button: ${SquareOffSdk.instance.useCase.name}") }
        buttonCancel.setOnClickListener {
            val name = presenter.posts()
            Timber.i("cancel: $name")
        }
        buttonDestroy.setOnClickListener { finish() }
    }

    override fun onCreateViewModel() {
        observe(viewModel.data) { }
    }
}