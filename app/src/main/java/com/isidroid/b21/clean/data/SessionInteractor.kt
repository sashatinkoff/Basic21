package com.isidroid.b21.clean.data

import android.app.Activity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.*
import com.isidroid.b21.clean.domain.ISessionUseCase
import java.util.concurrent.TimeUnit

class SessionInteractor : ISessionUseCase {
    private val auth = FirebaseAuth.getInstance()


    override fun requestCode(
        activity: Activity,
        phone: String,
        resendingToken: PhoneAuthProvider.ForceResendingToken?,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)

        resendingToken?.also { options.setForceResendingToken(it) }
        PhoneAuthProvider.verifyPhoneNumber(options.build())
    }

    override fun confirm(credential: PhoneAuthCredential): FirebaseUser? {
        val result = with(auth.signInWithCredential(credential)) { Tasks.await(this) }
        return result.user
    }
}