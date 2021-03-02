package com.isidroid.b21.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class MessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Timber.i(token)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        Timber.i(" onMessageReceived ${p0.notification?.body}")

    }
}