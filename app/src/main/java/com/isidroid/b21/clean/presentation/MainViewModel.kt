package com.isidroid.b21.clean.presentation

import com.google.android.gms.tasks.Tasks
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.installations.FirebaseInstallationsApi
import com.google.firebase.installations.FirebaseInstallationsRegistrar
import com.google.firebase.messaging.FirebaseMessaging
import com.isidroid.b21.utils.CoroutineViewModel
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor() : CoroutineViewModel() {
    private fun token() = with(FirebaseMessaging.getInstance().token) { Tasks.await(this) }//.take(12)


    fun recreate() = io(
        doWork = {
//            FirebaseMessaging.getInstance().isAutoInitEnabled = false
//            Timber.i("==> Current token isAutoInit=${FirebaseMessaging.getInstance().isAutoInitEnabled}")
//            Timber.e(token())
//
////            FirebaseInstanceId.getInstance().deleteInstanceId()
//
//            with(FirebaseMessaging.getInstance().deleteToken()) { Tasks.await(this) }
//                .also {
//                    Timber.i("Deleted")
//                }
//
//            Timber.i("==> Refreshed token")
//            Timber.e(token())
        }
    )
}