package com.isidroid.b21.sample

import com.isidroid.b21.sample.clean.domain.IPostListUseCase
import com.isidroid.b21.sample.di.DaggerSdkComponent
import javax.inject.Inject

class SquareOffSdk private constructor() {
    private val sdkComponent by lazy { DaggerSdkComponent.factory().create() }

    init {
        sdkComponent.inject(this)
    }

    @Inject lateinit var useCase: IPostListUseCase

    companion object {
        val instance by lazy { SquareOffSdk() }
    }
}