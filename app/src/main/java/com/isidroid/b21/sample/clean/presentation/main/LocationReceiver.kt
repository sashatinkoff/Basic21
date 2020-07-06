package com.isidroid.b21.sample.clean.presentation.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location


class LocationReceiver(private val callback: (Location) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val location =
            intent?.getParcelableExtra<Location>("com.google.android.location.LOCATION") ?: return
        callback(location)
    }

    companion object {
        const val CODE = 566

        private const val ACTION_LOCATION = "ACTION_LOCATION"
        val intent = Intent(ACTION_LOCATION)
        val intentFilter = IntentFilter(ACTION_LOCATION)
    }
}