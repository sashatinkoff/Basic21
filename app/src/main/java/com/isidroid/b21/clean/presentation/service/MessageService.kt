package com.isidroid.b21.clean.presentation.service

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.isidroid.b21.models.dto.Message
import com.isidroid.b21.models.settings.Settings
import timber.log.Timber
import java.util.*

class MessageService : FirebaseMessagingService() {
    companion object {
        const val ACTION_MESSAGE = "ACTION_MESSAGE"
        const val PUSH_TOPIC = "firechat_topic_201126"
    }

    override fun onNewToken(p0: String) {
        Settings.firebaseToken = p0
        Timber.i(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val message = Message(
            user = remoteMessage.data["user"].orEmpty(),
            date = Date(remoteMessage.sentTime),
            text = remoteMessage.data["text"].orEmpty()
        )

        val intent = Intent(ACTION_MESSAGE)
            .putExtra("message", message)

        sendBroadcast(intent)
    }
}