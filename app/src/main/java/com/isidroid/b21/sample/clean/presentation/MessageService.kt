package com.isidroid.b21.sample.clean.presentation

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class MessageService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Timber.tag("push_token").i(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.i("onMessageReceived ${remoteMessage.data}")
    }
}