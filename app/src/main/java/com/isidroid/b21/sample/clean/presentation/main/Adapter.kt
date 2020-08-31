package com.isidroid.b21.sample.clean.presentation.main

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.isidroid.b21.BuildConfig
import com.isidroid.b21.R
import com.isidroid.b21.databinding.ItemVideoBinding
import com.isidroid.b21.sample.clean.presentation.main.exolist.BaseExoAdapter
import com.isidroid.b21.utils.views.adapters.CoreBindAdapter
import com.isidroid.b21.utils.views.adapters.CoreBindHolder
import com.isidroid.b21.utils.views.adapters.CoreHolder
import kotlinx.android.synthetic.main.item_video.view.*
import timber.log.Timber
import java.util.*

class Adapter(context: Context) : BaseExoAdapter<RecyclerView.ViewHolder>(context) {
    private val layoutInflater = LayoutInflater.from(context)
    private val items = mutableListOf<VideoDto>()
    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemVideoBinding.inflate(layoutInflater, parent, false).root
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            val item = items[position]
            textView.text = if (isActive(position)) "$position" else "${textView.text}.$position"
            textView.setBackgroundColor(if (isActive(position)) Color.RED else Color.BLACK)

            playVideo(position = position, url = item.url, playerView = playerView, posterView = posterView)
        }
    }

    fun insert(videos: List<VideoDto>) = items.addAll(videos)
}