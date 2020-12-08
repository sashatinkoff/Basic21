package com.isidroid.b21.clean.presentation

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.isidroid.b21.clean.domain.ISessionUseCase
import com.isidroid.b21.utils.CoroutineViewModel
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sessionUseCase: ISessionUseCase
) : CoroutineViewModel() {
    sealed class State {
        data class OnInfo(val message: String) : State()
        data class OnError(val t: Throwable) : State()
        data class OnComplete(val user: FirebaseUser) : State()
        object OnCodeSent : State()
    }

    val state = MutableLiveData<State>()
    private var resendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var verificationId: String? = null

    fun requestCode(activity: Activity, phone: String, reset: Boolean) {
        if (reset) resendingToken = null

        FirebaseAuth.getInstance().firebaseAuthSettings.setAppVerificationDisabledForTesting(true)

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                state.value = State.OnInfo("onVerificationCompleted")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                state.value = State.OnError(p0)
                Timber.e(p0)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                this@MainViewModel.verificationId = verificationId
                resendingToken = token
                state.value = State.OnCodeSent
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                state.value = State.OnInfo("onCodeAutoRetrievalTimeOut $p0")
            }
        }

        sessionUseCase.requestCode(activity, phone, resendingToken, callbacks)
    }

    fun confirm(code: String) = io(
        doWork = {
            sessionUseCase.confirm(PhoneAuthProvider.getCredential(verificationId!!, code))
        },
        onComplete = { state.value = State.OnComplete(it!!) },
        onError = { state.value = State.OnError(it) }
    )
}