package com.isidroid.b21.sample

import android.content.Context
import com.isidroid.b21.di.DaggerAppComponent
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.sample.clean.domain.IPostListUseCase
import javax.inject.Inject

class SquareOffSdk private constructor() {
//    private val sdkComponent by lazy { DaggerAppComponent.factory().create() }
//
//    init {
//        sdkComponent.inject(this)
//    }
//
//    @Inject lateinit var useCase: IPostListUseCase

    fun a(){
        val context = Context()
        context.appComponent().
    }

    companion object {
        val instance by lazy { SquareOffSdk() }
    }
}