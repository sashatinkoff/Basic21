package com.isidroid.b21.clean.presentation.main

import androidx.lifecycle.MutableLiveData
import com.isidroid.b21.clean.domain.IFirebaseMessagingUseCase
import com.isidroid.b21.utils.CoroutineViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val firebaseMessagingUseCase: IFirebaseMessagingUseCase) :
    CoroutineViewModel() {
    sealed class State {
        data class OnError(val t: Throwable) : State()
    }

    val state = MutableLiveData<State>()

    fun send() = io(
        doWork = { firebaseMessagingUseCase.send() },
        onComplete = {},
        onError = {}
    )
}