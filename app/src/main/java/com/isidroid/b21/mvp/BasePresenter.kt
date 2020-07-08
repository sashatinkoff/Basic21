package com.isidroid.b21.mvp

import com.isidroid.b21.sample.clean.presentation.MainPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

abstract class BasePresenter<out V : IBaseView>(protected val view: V) {
    private val injector = DaggerPresenterInjector.builder()
        .view(view)
        .build()

    private var job = Job()
    private var dispatcher = Dispatchers.Main + job

    init {
        Timber.i("gdfgfgdfgdfgdfg init")
        inject()
    }

    private fun inject() {
        when (this) {
            is MainPresenter -> injector.inject(this)
        }
    }
}