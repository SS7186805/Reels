package com.example.reels.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.reels.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory


class VideoAdapter(var context: Context) : RecyclerView.Adapter<VideoAdapter.VideoHolder>() {

    var list = ArrayList<String>()

    fun setData(dataItem: ArrayList<String>) {
        this.list.addAll(dataItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.load_video_layout, parent, false)
        return VideoHolder(view, context)
    }

    override fun onBindViewHolder(holder: VideoAdapter.VideoHolder, position: Int) {
        return holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class VideoHolder(view: View, context1: Context) : RecyclerView.ViewHolder(view) {
        private var player: SimpleExoPlayer ? = null
        private var playWhenReady = false
        private var currentWindow = 0
        private var playbackPosition: Long = 0
        private var video : PlayerView ?= null

        fun bind(str: String) {
            video = itemView.findViewById<PlayerView>(R.id.tvVideo)
            val loader = itemView.findViewById<ProgressBar>(R.id.loader)

            video?.player = player
            val mediaItem: MediaItem = MediaItem.fromUri(str)
            player?.setMediaItem(mediaItem)
            player?.setPlayWhenReady(playWhenReady)
            player?.seekTo(currentWindow, playbackPosition)
            player?.prepare()
        }
    }
}