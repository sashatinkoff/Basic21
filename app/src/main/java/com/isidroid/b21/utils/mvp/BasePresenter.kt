package com.isidroid.b21.utils.mvp

import androidx.lifecycle.Lifecycle

abstract class BasePresenter<out V : IBaseView>(
    protected val view: V,
    protected val lifecycle: Lifecycle
) {
    private val coroutine = UiCoroutine(lifecycle)

    init {
        inject()
    }

    fun <T> io(
        doWork: () -> T,
        doBefore: (() -> Unit)? = null,
        onComplete: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) = coroutine.io(
        doWork = doWork,
        doBefore = doBefore,
        onComplete = onComplete,
        onError = onError
    )

    fun cancel() = coroutine.cancel()

    private fun inject() {
        when (this) {
//            is MainPresenter -> injector.inject(this)
        }
    }

}