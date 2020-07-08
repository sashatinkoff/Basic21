package com.isidroid.b21.sample.clean.presentation

import com.isidroid.b21.mvp.BasePresenter
import com.isidroid.b21.sample.network.Api
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import timber.log.Timber
import javax.inject.Inject

class MainPresenter(view: IMainView) : BasePresenter<IMainView>(view) {
    @Inject lateinit var api: Api


    fun posts() {
        Timber.i("gdfgfgdfgdfgdfg runPosts")
        GlobalScope.async {
            Timber.i("gdfgfgdfgdfgdfg async on ${Thread.currentThread().name}")
            try {
                val response = api.list().execute()
                Timber.i("gdfgfgdfgdfgdfg result=${response.body()}")
            } catch (t: Throwable){
                Timber.e("gdfgfgdfgdfgdfg ${t.message}")
            }

        }

    }
}