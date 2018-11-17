package com.vault.videostube.ui.videos

import android.arch.paging.PagedListAdapter
import android.content.Intent
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.vault.videostube.R
import com.vault.videostube.data.model.Video
import com.vault.videostube.ui.player.PlayerActivity
import kotlinx.android.synthetic.main.video_item.view.*


class VideosAdapter: PagedListAdapter<Video, VideoHolder>(diffCallback) {

    val logTag = "VideosAdapter"

    var videos: List<Video> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
                    oldItem == newItem

        }

    }



}

class VideoHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val logTag = "VideoHolder"

    val image: ImageView = view.image
    val title: TextView = view.title


    fun bindView(video: Video?) {
        if (video != null) {
            title.text = video.snippet.title
            Log.e(logTag, "load url = ${video.snippet.thumbnails.maxres?.url}\n" +
                    "id = ${video.id}")
            if (video.snippet.thumbnails.maxres == null) {
                Glide.with(view).load(video.snippet.thumbnails.default?.url).into(image)
            } else {
                Glide.with(view).load(video.snippet.thumbnails.maxres!!.url).into(image)
            }
            view.setOnClickListener {
                val intent = Intent(view.context, PlayerActivity::class.java)
                intent.putExtra(PlayerActivity.ID_KEY, video.id)
                view.context.startActivity(intent)
            }
        } else {
            title.text = "waiting..."
        }
    }
}

/*class VideosAdapter: RecyclerView.Adapter<VideoHolder>() {

    var videos: List<Video> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return VideoHolder(view)
    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        holder.bindView(videos[position])
    }
}

class VideoHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val logTag = "VideoHolder"

    val image: ImageView = view.image
    val title: TextView = view.title


    fun bindView(video: Video) {
        title.text = video.snippet.title
        Log.e(logTag, "load url = ${video.snippet.thumbnails.maxres?.url}\n" +
                "id = ${video.id}")
        if (video.snippet.thumbnails.maxres == null) {
            Glide.with(view).load(video.snippet.thumbnails.default?.url).into(image)
        } else {
            Glide.with(view).load(video.snippet.thumbnails.maxres!!.url).into(image)
        }
        view.setOnClickListener {
            val intent = Intent(view.context, PlayerActivity::class.java)
            intent.putExtra(PlayerActivity.ID_KEY, video.id)
            view.context.startActivity(intent)
        }
    }
}*/
