package com.isidroid.b21.sample.clean.presentation

import android.net.Uri
import android.os.Bundle
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.isidroid.b21.BuildConfig
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.utils.BindActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BindActivity(layoutRes = R.layout.activity_main), IMainView {
    private val uri =
        Uri.parse("https://stream.mux.com/KpD6DVtWgBVNKmomzkJ4xqc1Ju012EaTCoTqxSCrk9Ac.m3u8")

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        createVideoPlayer()
    }

    private fun createVideoPlayer() {
        val player = SimpleExoPlayer.Builder(this).build()
        playerView.player = player
        player.playWhenReady = true

        val dataSourceFactory = DefaultDataSourceFactory(this, BuildConfig.APPLICATION_ID)
        val videoSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)

        player.prepare(videoSource)

    }


}