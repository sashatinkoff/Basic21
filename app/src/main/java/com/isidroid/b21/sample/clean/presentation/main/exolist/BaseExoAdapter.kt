package com.isidroid.b21.sample.clean.presentation.main.exolist

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.isidroid.b21.BuildConfig
import com.isidroid.b21.R
import com.isidroid.b21.ext.visible
import java.util.*

abstract class BaseExoAdapter<VH : RecyclerView.ViewHolder>
    (private val context: Context) : RecyclerView.Adapter<VH>() {

    private val players = WeakHashMap<Int, Player>()
    private val playbackPositions = hashMapOf<Int, Long>()
    private val dataSourceFactory = DefaultDataSourceFactory(context, BuildConfig.APPLICATION_ID)
    private val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)
    //HlsMediaSource.Factory(dataSourceFactory)

    private var currentPosition = 0

    fun isActive(position: Int) = currentPosition == position
    fun activate(position: Int) {
        if (position == currentPosition) return
        val oldPosition = currentPosition
        currentPosition = position

        notifyItemChanged(position, position)
        notifyItemChanged(oldPosition, oldPosition)
    }

    override fun onViewRecycled(holder: VH) {
        val position = holder.adapterPosition
        val player = players[position] ?: return

        playbackPositions[position] = player.currentPosition
        player.release()
        players.remove(position)
    }

    fun destroy() {
        players.map {
            playbackPositions[it.key] = it.value?.currentPosition ?: 0
            it.value?.release()
        }
        players.clear()
    }

    protected fun playVideo(
        position: Int,
        url: String,
        playerView: PlayerView,
        posterView: ImageView
    ) {
        posterView.setImageResource(R.drawable.ic_artwork)

        val uri = Uri.parse(url)
        val isActive = position == currentPosition
        posterView.visible(!isActive)
        if (isActive) {
            if (!players.containsKey(position))
                players[position] = SimpleExoPlayer.Builder(context).build()
                    .apply {
                        prepare(videoSource.createMediaSource(uri))
                        playbackPositions[position]?.let { seekTo(it) }
                        playWhenReady = true
                    }

            players[position]?.apply {
                playWhenReady = true
                playerView.player = this
            }
        } else {
            players[position]?.playWhenReady = false
        }
    }
}