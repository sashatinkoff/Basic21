package com.isidroid.b21.sample.clean.presentation

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import com.isidroid.b21.mvp.BasePresenter
import com.isidroid.b21.sample.network.Api
import timber.log.Timber
import java.lang.RuntimeException
import javax.inject.Inject

class MainPresenter(view: IMainView, lifecycle: Lifecycle) :
    BasePresenter<IMainView>(view, lifecycle) {
    @Inject lateinit var api: Api

    fun posts() = io(
        doBefore = { Timber.i("gdfgfgdfgdfgdfg doBefore") },
        doWork = {
            Thread.sleep(5_000)
            api.list().execute().body()
        },
        onComplete = { Timber.i("gdfgfgdfgdfgdfg onComplete ${it?.size}") },
        onError = { Timber.e("gdfgfgdfgdfgdfg ${it}") }
    )

}