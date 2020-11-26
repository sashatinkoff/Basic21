package com.isidroid.b21.clean.presentation

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.isidroid.b21.R
import com.isidroid.b21.utils.core.BindActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

private const val CODE_CALL = 200

class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) makeCall()
        }

    private val permissionPhoneState =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) registerReceiver(
                receiver,
                IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
            )
        }

    private val isGranted
        get() = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, data: Intent?) {
            val intent = data

            val intState = intent.getIn
            when (val state = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)) {
                null -> {
                    val number = intent?.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
                    Timber.i("sdfsdfsdf outgoing number : $number state=$state")

                    val iterator = intent?.extras?.keySet()?.iterator() ?: return
                    while (iterator.hasNext()) {
                        val key = iterator.next()
                        Timber.i("sdfsdfsdf $key=${intent.extras?.get(key)}")
                    }

                }

                else -> Timber.i("sdfsdfsdf $state")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionPhoneState.launch(Manifest.permission.READ_PHONE_STATE)


        button.setOnClickListener {
            if (!isGranted) permission.launch(Manifest.permission.CALL_PHONE)
            else makeCall()
        }
    }

    private fun makeCall() {
        startActivityForResult(
            Intent(Intent.ACTION_CALL, "tel:89024731471".toUri())
                .setFlags(FLAG_ACTIVITY_NEW_TASK),
            CODE_CALL
        )
    }
}