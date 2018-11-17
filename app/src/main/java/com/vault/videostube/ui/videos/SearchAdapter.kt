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
import com.vault.videostube.data.model.SearchVideo
import com.vault.videostube.ui.player.PlayerActivity
import kotlinx.android.synthetic.main.video_item.view.*

class SearchAdapter : PagedListAdapter<SearchVideo, SearchVideoHolder>(diffCallback) {

    val TAG = "SearchAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVideoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return SearchVideoHolder(view)
    }

    override fun onBindViewHolder(holder: SearchVideoHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<SearchVideo>() {
            override fun areItemsTheSame(oldItem: SearchVideo, newItem: SearchVideo): Boolean = oldItem.id.videoId == newItem.id.videoId

            override fun areContentsTheSame(oldItem: SearchVideo, newItem: SearchVideo): Boolean =
                    oldItem == newItem

        }

    }



}

class SearchVideoHolder(val view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        private const val TAG = "SearchVideoHolder"
    }


    val image: ImageView = view.image
    val title: TextView = view.title

    fun bindView(video: SearchVideo?) {
        if (video != null) {
            title.text = video.snippet.title
            Log.e(TAG, "load url = ${video.snippet.thumbnails.maxres?.url}\n" +
                    "id = ${video.id.videoId}")
            if (video.snippet.thumbnails.maxres == null) {
                Glide.with(view).load(video.snippet.thumbnails.high?.url).into(image)
            } else {
                Glide.with(view).load(video.snippet.thumbnails.maxres!!.url).into(image)
            }
            view.setOnClickListener {
                val intent = Intent(view.context, PlayerActivity::class.java)
                intent.putExtra(PlayerActivity.ID_KEY, video.id.videoId)
                view.context.startActivity(intent)
            }
        } else {
            title.text = "waiting..."
        }
    }
}
