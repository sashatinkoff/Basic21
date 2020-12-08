package com.isidroid.b21.clean.domain

import android.app.Activity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

interface ISessionUseCase {
    fun requestCode(
        activity: Activity,
        phone: String,
        resendingToken: PhoneAuthProvider.ForceResendingToken?,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

    fun confirm(credential: PhoneAuthCredential): FirebaseUser?
}