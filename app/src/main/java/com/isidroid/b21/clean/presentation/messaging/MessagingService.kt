package com.isidroid.b21.clean.presentation.messaging

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class MessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Timber.i("onNewToken $token")
        FirebaseMessaging.getInstance().subscribeToTopic("test")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data.map { "${it.key}=${it.value}\n" }

        Timber.i(
            "onMessageReceived " +
                    "title=${remoteMessage.notification?.title}, " +
                    "\n " +
                    "$data"
        )
    }
}