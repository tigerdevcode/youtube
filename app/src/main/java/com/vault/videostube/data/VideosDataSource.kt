package com.vault.videostube.data

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import com.vault.videostube.data.model.SearchVideo
import com.vault.videostube.data.model.Video

interface VideosDataSource {

    fun getVideos(): LiveData<PagedList<Video>>

    fun addVideo(video: Video)

    fun deleteVideo(title: String)

    fun searchVideos(query: String): LiveData<PagedList<SearchVideo>>
}