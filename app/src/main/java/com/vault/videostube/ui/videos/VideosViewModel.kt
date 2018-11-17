package com.vault.videostube.ui.videos

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import android.arch.paging.PositionalDataSource
import android.util.Log
import com.vault.videostube.data.VideosDataSource
import com.vault.videostube.data.VideosRepository
import com.vault.videostube.data.model.SearchVideo
import com.vault.videostube.data.model.Video

class VideosViewModel(private val repository: VideosDataSource): ViewModel() {


    companion object {
        private const val TAG = "VideosViewModel"
    }

    val searching = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun searchVideos(query: String): LiveData<PagedList<SearchVideo>> {
        Log.e(TAG, "searchVideos query = $query")
        return repository.searchVideos(query)
    }

    fun allVideos(): LiveData<PagedList<Video>> {
        Log.e(TAG, "allVideos")
        return repository.getVideos()
    }

    fun addVideo(video: Video) {
        repository.addVideo(video)
    }

    fun deleteVideo(title: String) {
        repository.deleteVideo(title)
    }
}