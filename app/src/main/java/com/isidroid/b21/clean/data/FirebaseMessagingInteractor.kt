package com.isidroid.b21.clean.data

import com.google.android.gms.tasks.Tasks
import com.google.firebase.iid.FirebaseInstanceId
import com.isidroid.b21.clean.domain.IFirebaseMessagingUseCase
import com.isidroid.b21.network.ApiFirebaseMessaging
import javax.inject.Inject

class FirebaseMessagingInteractor @Inject constructor(
    private val api: ApiFirebaseMessaging,
    private val registrationToken: String
) : IFirebaseMessagingUseCase {

    override fun send(): String {
        val token = with(FirebaseInstanceId.getInstance().instanceId)
        { Tasks.await(this).token }

        val notificationRequest = ApiFirebaseMessaging.NotificationRequest(
            title = "It's a title of sample notification",
            body = "Hello world"
        )

        val messageRequest = ApiFirebaseMessaging.MessageRequest(
            notification = notificationRequest,
            to = token
        )

        val response = api.send(
            authorization = "key=$registrationToken",
            body = messageRequest
        ).execute()

        return response.body()!!.string()
    }
}