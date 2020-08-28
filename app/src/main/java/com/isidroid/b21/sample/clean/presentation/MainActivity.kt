package com.isidroid.b21.sample.clean.presentation

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.isidroid.a18.clean.presentation.Router
import com.isidroid.b21.BuildConfig
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.utils.BindActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : BindActivity(layoutRes = R.layout.activity_main), IMainView {
    private val uri =
        Uri.parse("https://stream.mux.com/VhFtEqmNAxYwCRkAHgVXdpG6Hlh2QAgDZ7D2b2iONmk.m3u8")

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)

        debug()
        button.setOnClickListener {
            open(this, ActivityB::class.java)
        }
    }
}

class ActivityB : BindActivity(layoutRes = R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        debug()
        button.setOnClickListener {
            open(this, MainActivity::class.java)
        }
    }
}

fun open(caller: Activity, activity: Class<out Activity>) = Router(
    caller = caller,
    activity = activity,
    isAnimate = true
).open()

fun Activity.debug() {
    val tasks =
        (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getRunningTasks(10)
    for (task in tasks) {
        Timber.tag("tasksinfoaasas").i(
            "" +
                    " task.baseActivity=${task.baseActivity}\n" +
                    "            task.numActivities=${task.numActivities}\n" +
                    "            task.id=${task.id}\n" +
                    "            task.topActivity=${task.topActivity}"
        )

    }
}