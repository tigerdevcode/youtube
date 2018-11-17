package com.vault.videostube.data

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.vault.videostube.data.model.SearchVideo
import com.vault.videostube.data.remote.YoutubeService
import com.vault.videostube.utils.BaseSchedulerProvider

class SearchDataSource(
        private val query: String,
        private val youtubeService: YoutubeService,
        private val schedulerProvider: BaseSchedulerProvider) : PageKeyedDataSource<String, SearchVideo>() {

    companion object {
        private const val TAG = "SearchDataSource"
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, SearchVideo>) {
        Log.e(TAG, "loadInitial()")
        youtubeService.search(query)
                .subscribeOn(schedulerProvider.io())
                .subscribe ({
                    Log.e(TAG, "loaded from loadInitial()")
                    callback.onResult(it.items, null, it.nextPageToken)
                },
                        {
                            Log.e(TAG, "loaded from loadInitial() with error ${it.message}")
                        })
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, SearchVideo>) {
        Log.e(TAG, "loadAfter() nextPage = ${params.key}")
        youtubeService.search(query, nextPageToken = params.key)
                .subscribeOn(schedulerProvider.io())
                .subscribe ({
                    Log.e(TAG, "loaded from loadAfter()")
                    callback.onResult(it.items, it.nextPageToken)
                },
                        {
                            Log.e(TAG, "loaded from loadAfter() with error ${it.message}")
                        })
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, SearchVideo>) {
        Log.e(TAG, "loadBefore()")
    }

}