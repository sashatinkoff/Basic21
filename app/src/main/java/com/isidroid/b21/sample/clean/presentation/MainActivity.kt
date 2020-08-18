package com.isidroid.b21.sample.clean.presentation

import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.observe
import com.isidroid.b21.sample.clean.model.PostRepository
import com.isidroid.b21.utils.BindActivity
import com.isidroid.b21.utils.UiCoroutine
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BindActivity(layoutRes = R.layout.activity_main), IMainView {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private val presenter = MainPresenter(view = this, lifecycle = lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)


        button.setOnClickListener { viewModel.create() }
        buttonCancel.setOnClickListener { Handler().post { PostRepository.dp() } }
        buttonDestroy.setOnClickListener {
            UiCoroutine(lifecycle).io(
                doWork = { PostRepository.dp() }
            )
        }
    }

    override fun onCreateViewModel() {
        observe(viewModel.data) { }
    }
}