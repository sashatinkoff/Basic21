package com.isidroid.b21.network

import com.google.gson.annotations.SerializedName
import com.isidroid.b21.clean.presentation.service.MessageService
import com.isidroid.b21.models.db.Account

data class ChatReq(
    @SerializedName("data") val data: Data,
    @SerializedName("notification") val notification: Notification,
    @SerializedName("priority") val priority: String = "high",
    @SerializedName("to") val to: String = "/topics/${MessageService.PUSH_TOPIC}",
    @SerializedName("collapse_key") val collapseKey: String = "Chat updates"
) {
    data class Data(
        @SerializedName("user") val user: String,
        @SerializedName("text") val text: String
    )

    data class Notification(
        @SerializedName("title") val title: String,
        @SerializedName("body") val body: String,
        @SerializedName("tag") val tag: String = ""
    )

    companion object {
        fun create(account: Account, message: String, collapseKey: String = "Chat updates") =
            ChatReq(
                data = Data(user = account.name, text = message),
                notification = Notification(
                    title = "${account.name} sent new message",
                    body = message,
                    tag = collapseKey
                ),
                collapseKey = collapseKey
            )
    }
}
