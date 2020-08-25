package com.isidroid.b21.clean.presentation

import androidx.lifecycle.Lifecycle
import com.isidroid.b21.mvp.BasePresenter

class MainPresenter(view: IMainView, lifecycle: Lifecycle) :
    BasePresenter<IMainView>(view, lifecycle) {
//    @Inject lateinit var api: Api
//    @Inject lateinit var postUseCase: IPostListUseCase
//
//    fun posts() = postUseCase.name

}