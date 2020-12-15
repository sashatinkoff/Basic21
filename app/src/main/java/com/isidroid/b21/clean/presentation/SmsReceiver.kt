package com.isidroid.b21.clean.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import timber.log.Timber


class SmsReceiver : BroadcastReceiver() {
    init {
        onInfo("Init")
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        onInfo("Received action=${intent?.action}")

        val extras = intent?.extras ?: run {
            onInfo("Received action=${intent?.action} with no extras")
            return
        }

        val status = extras[SmsRetriever.EXTRA_STATUS] as Status?
        val message = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String?

        onInfo("Received action=${intent.action}, status=$status, message=$message")

        when (status?.statusCode) {
            CommonStatusCodes.SUCCESS -> {
            }
            CommonStatusCodes.TIMEOUT -> {
            }
        }
    }

    private fun onInfo(message: String?) {
        Timber.i("NoListener $message")
    }
}