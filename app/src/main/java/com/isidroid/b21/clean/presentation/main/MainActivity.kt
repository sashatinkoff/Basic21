package com.isidroid.b21.clean.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.observe
import com.isidroid.b21.utils.BindActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)

        button.setOnClickListener { viewModel.send() }

        val data = with(intent.extras) {
            this?.keySet()?.map { "$it=${this.get(it)}\n" }
        }

        Timber.i(
            "MessagingService onCreate action=${intent.action}, " +
                    "\nextras=$data"
        )
        finishAfterTransition()
    }

    override fun onCreateViewModel() {
        observe(viewModel.state) { onState(it) }
    }

    // Livedata
    private fun onState(state: MainViewModel.State?) {
        when (state) {
            is MainViewModel.State.OnError -> onError(state.t)
        }
    }

    private fun onError(t: Throwable) {
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }
}